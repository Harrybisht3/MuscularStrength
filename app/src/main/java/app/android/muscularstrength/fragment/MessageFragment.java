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

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import app.android.muscularstrength.R;
import app.android.muscularstrength.Util.Util;
import app.android.muscularstrength.activity.DashBoardActivity;
import app.android.muscularstrength.adapter.MessageAdapter;
import app.android.muscularstrength.model.MessageParser;
import app.android.muscularstrength.model.User;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.session.SessionManager;
import app.android.muscularstrength.webservice.WebServices;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Bisht Bhawna on 8/10/2015.
 */
public class MessageFragment extends Fragment {
    View rootView;
    int from;
    ListView list_message;
    MessageAdapter adapter;
    SessionManager session;
    User userObj;
    ArrayList<app.android.muscularstrength.model.Message> dataMessage;
    private int page_no=1;
    ProgressDialog pDialog;
    CircleImageView userProfileImg;
    TextView user,account_type,level;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.message_layout, container, false);
        // getActivity().getActionBar().show();
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity. mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("MESSAGES");
        list_message=(ListView)rootView.findViewById(R.id.list_message);
        adapter=new MessageAdapter(getActivity());
        list_message.setAdapter(adapter);
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
        level.setText(userObj.getUserLevel());
        pDialog=new ProgressDialog(getActivity());
        pDialog.setMessage("loading...");

        // DashBoardActivity. mainView.setBackgroundColor(getResources().getColor(R.color.tansparent));
        Bundle args = getArguments();
        from=args.getInt("from");




        getMessages();
        return  rootView;
    }

    //get articles
    private void getMessages(){
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String,String> params=new HashMap<String, String>();
                params.put("userid",userObj.getUserId());
                JSONParser parser = new JSONParser();
                JSONObject json=parser.makeHttpRequest(WebServices.Message,"GET",params);
                try {
                    if(json.getString("result").equalsIgnoreCase("SUCCESS")){
                    Gson gson = new Gson();
                    MessageParser data=gson.fromJson(json.toString(),MessageParser.class);
                    //if(data.getResult().equalsIgnoreCase("SUCCESS")){
                        dataMessage=new ArrayList<app.android.muscularstrength.model.Message>();
                        dataMessage.addAll(data.getData().getMessages());
                        mainHandler.sendMessage(mainHandler.obtainMessage(1));
                    }
                    else{
                        mainHandler.sendMessage(mainHandler.obtainMessage(0));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void setListAdapter(){
        for(int i=0;i<dataMessage.size();i++){
            adapter.add(dataMessage.get(i));
        }
        adapter.notifyDataSetChanged();
        Util.setListViewHeight(list_message);

    }

    public Handler mainHandler = new Handler() {
        public void handleMessage(Message message) {
            try {

                if (isAdded()) {
                    pDialog.dismiss();
                    pDialog.cancel();
                    switch (message.what) {
                        case 0:
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
