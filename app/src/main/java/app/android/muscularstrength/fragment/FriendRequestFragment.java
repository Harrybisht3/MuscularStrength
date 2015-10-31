package app.android.muscularstrength.fragment;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Build;
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
import app.android.muscularstrength.adapter.FriendRequestAdapter;
import app.android.muscularstrength.model.FriendRequest;
import app.android.muscularstrength.model.FriendRequestParser;
import app.android.muscularstrength.model.User;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.session.SessionManager;
import app.android.muscularstrength.webservice.WebServices;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sa on 8/11/2015.
 */
public class FriendRequestFragment extends Fragment{
    View rootView;
    int from;
    SessionManager session;
    User userObj;
    ListView list_friendrequest;
    FriendRequestAdapter adapter;
    ArrayList<FriendRequest> dataFriendRequest;
    private int page_no=1;
    ProgressDialog pDialog;
    CircleImageView userProfileImg;
    TextView user,account_type,level;
    String errorMessage;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.friendrequest_fragment, container, false);
        // getActivity().getActionBar().show();
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity. mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("FRIEND REQUESTS");
        list_friendrequest=(ListView)rootView.findViewById(R.id.list_friendrequest);
        adapter=new FriendRequestAdapter(getActivity());
        list_friendrequest.setAdapter(adapter);
        session=new SessionManager(getActivity());
        Gson gson=new Gson();
        userObj=gson.fromJson(session.getSession(),User.class);
        //header View
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

        // DashBoardActivity. mainView.setBackgroundColor(getResources().getColor(R.color.tansparent));
        Bundle args = getArguments();
        from=args.getInt("from");
       /* rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == event.ACTION_UP
                        && keyCode == KeyEvent.KEYCODE_BACK) {
                    if (from == 0) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.contentframe, new DashBoardFragment());
                        ft.commit();
                    } else {
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                }
                return true;
            }
        });*/
       /* list_articles.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                page_no = page;
                getArticle(page_no);
            }
        });*/




        getFriendRequest();
        return  rootView;
    }

    //get articles
    private void getFriendRequest(){
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String,String> params=new HashMap<String, String>();
                params.put("userid",userObj.getUserId());
                JSONParser parser = new JSONParser();
                JSONObject json=parser.makeHttpRequest(WebServices.Friend_Request,"GET",params);
                try {
                    if(json!=null){
                    if(json.getString("result").equalsIgnoreCase("SUCCESS")) {
                        Gson gson = new Gson();
                        FriendRequestParser data = gson.fromJson(json.toString(), FriendRequestParser.class);
                       // if (data.getResult().equalsIgnoreCase("SUCCESS")) {
                            dataFriendRequest = new ArrayList<FriendRequest>();
                            dataFriendRequest.addAll(data.getData().getFriendRequest());
                            mainHandler.sendMessage(mainHandler.obtainMessage(1));
                       // } else {
                            //mainHandler.sendMessage(mainHandler.obtainMessage(0));
                        //}
                    }
                    else{
                        errorMessage=json.getJSONObject("data").getString("friend_request");
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

    private void setListAdapter(){
        for(int i=0;i<dataFriendRequest.size();i++){
            adapter.add(dataFriendRequest.get(i));
        }
        adapter.notifyDataSetChanged();
        Util.setListViewHeight(list_friendrequest);

    }

    private Handler mainHandler = new Handler() {
        public void handleMessage(Message message) {
            try {

                if (isAdded()) {
                    pDialog.dismiss();
                    pDialog.cancel();
                    switch (message.what) {
                        case 0:
                            Toast.makeText(getActivity(), ""+errorMessage, Toast.LENGTH_SHORT).show();
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

}
