package app.android.muscularstrength.fragment;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import app.android.muscularstrength.R;
import app.android.muscularstrength.Util.Util;
import app.android.muscularstrength.activity.DashBoardActivity;
import app.android.muscularstrength.adapter.FriendAdapter;
import app.android.muscularstrength.model.Friend;
import app.android.muscularstrength.model.FriendParser;
import app.android.muscularstrength.model.User;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.session.SessionManager;
import app.android.muscularstrength.webservice.WebServices;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by laxman singh on 11/2/2015.
 */
public class FriendsFragment extends Fragment{
    View rootView;
    int from;
    SessionManager session;
    User userObj;
    ProgressDialog pDialog;
    CircleImageView userProfileImg;
    TextView user,account_type,level;
    String errorMessage;
    ListView list_friends;
    ArrayList<Friend> dataFriend;
    FriendAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.friends_fragment, container, false);
        // getActivity().getActionBar().show();
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity. mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("FRIENDS");
        session=new SessionManager(getActivity());
        Gson gson=new Gson();
        userObj=gson.fromJson(session.getSession(),User.class);
        //header View
        list_friends=(ListView)rootView.findViewById(R.id.list_friend);
        adapter=new FriendAdapter(getActivity());
        list_friends.setAdapter(adapter);
        View headerlayout= rootView.findViewById(R.id.header);
        userProfileImg=(CircleImageView)headerlayout.findViewById(R.id.profileImg);
        user = (TextView)headerlayout.findViewById(R.id.user);
        account_type = (TextView)headerlayout.findViewById(R.id.account_type);
        level = (TextView)headerlayout.findViewById(R.id.level);
        Glide.with(getActivity()).load(userObj.getFullImage()).into(userProfileImg);
        user.setText(userObj.getFirstName() + "" + userObj.getLastName());
        account_type.setText(userObj.getAccountType());
        pDialog=new ProgressDialog(getActivity());
        pDialog.setMessage("loading...");
        getFriends();
        return rootView;
    }
    //get friendRequest
    private void getFriends(){
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String,String> params=new HashMap<String, String>();
                params.put("userid",userObj.getUserId());
                JSONParser parser = new JSONParser();
                JSONObject json=parser.makeHttpRequest(WebServices.friends,"GET",params);
                try {
                    if(json!=null){
                        if(json.getString("result").equalsIgnoreCase("SUCCESS")) {
                            Gson gson = new Gson();
                            FriendParser data = gson.fromJson(json.toString(), FriendParser.class);
                            // if (data.getResult().equalsIgnoreCase("SUCCESS")) {
                            dataFriend = new ArrayList<Friend>();
                            dataFriend.addAll(data.getData().getFriend());
                            mainHandler.sendMessage(mainHandler.obtainMessage(1));
                            // } else {
                            //mainHandler.sendMessage(mainHandler.obtainMessage(0));
                            //}
                        }
                        else{
                            errorMessage=json.getJSONObject("data").getString("friend");
                            mainHandler.sendMessage(mainHandler.obtainMessage(0));
                        }
                    } else {
                        errorMessage = getResources().getString(R.string.errorMessage);
                        mainHandler.sendMessage(mainHandler.obtainMessage(0));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private Handler mainHandler = new Handler() {
        public void handleMessage(Message message) {
            try {

                if (isAdded()) {
                    pDialog.dismiss();
                    pDialog.cancel();
                    switch (message.what) {
                        case 0:
                            Toast.makeText(getActivity(), "" + errorMessage, Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            setListAdapter();
                            break;
                    }
                }
            } catch (Resources.NotFoundException e) {

            }
        }
    };
    private void setListAdapter(){
        for(int i=0;i<dataFriend.size();i++){
            adapter.add(dataFriend.get(i));
        }
        adapter.notifyDataSetChanged();
        Util.setListViewHeight(list_friends);

    }

}
