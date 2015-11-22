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
import java.util.List;

import app.android.muscularstrength.R;
import app.android.muscularstrength.activity.DashBoardActivity;
import app.android.muscularstrength.adapter.LiftAdapter;
import app.android.muscularstrength.model.Lift;
import app.android.muscularstrength.model.LiftParser;
import app.android.muscularstrength.model.User;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.session.SessionManager;
import app.android.muscularstrength.webservice.WebServices;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by laxman singh on 11/4/2015.
 */
public class LiftFragment extends Fragment{
    View rootView;
    ProgressDialog pDialog;
    CircleImageView userProfileImg;
    TextView user,account_type,level;
    String errorMessage;
    ListView list_lift;
    SessionManager session;
    User userObj;
    List<Lift> data_lift;
    LiftAdapter adapter;
    
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity. mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("LIFTS");
        rootView=inflater.inflate(R.layout.lift_fragment,container,false);
       init();
        return rootView;
    }
    private void init(){
        session=new SessionManager(getActivity());
        Gson gson=new Gson();
        userObj=gson.fromJson(session.getSession(),User.class);
        adapter=new LiftAdapter(getActivity());
        list_lift=(ListView)rootView.findViewById(R.id.list_lift);
        //header View
        View headerlayout= View.inflate(getActivity(), R.layout.header_layout, null);
        userProfileImg=(CircleImageView)headerlayout.findViewById(R.id.profileImg);
        user = (TextView)headerlayout.findViewById(R.id.user);
        account_type = (TextView)headerlayout.findViewById(R.id.account_type);
        level = (TextView)headerlayout.findViewById(R.id.level);
        list_lift.addHeaderView(headerlayout);
        View footerlayout=View.inflate(getActivity(), R.layout.footer_layout, null);
        list_lift.addFooterView(footerlayout);
       // list_message.setAdapter(adapter);
        Glide.with(getActivity()).load(userObj.getFullImage()).into(userProfileImg);
        user.setText(userObj.getFirstName() + "" + userObj.getLastName());
        account_type.setText(userObj.getAccountType());
        level.setText(userObj.getUserLevel());
        pDialog=new ProgressDialog(getActivity());
        pDialog.setMessage("loading...");
        getLift();

    }
    private void getLift(){
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
              // params.put("userid", userObj.getUserId());
                params.put("userid", "2");
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.lift, "GET", params);
                try {
                    if (json != null) {
                        if (json.getString("result").equalsIgnoreCase("SUCCESS")) {
                            Gson gson = new Gson();
                            LiftParser data = gson.fromJson(json.toString(), LiftParser.class);
                            // if (data.getResult().equalsIgnoreCase("SUCCESS")) {
                           data_lift=new ArrayList<Lift>();
                            data_lift.addAll(data.getData().getLifts());
                            mainHandler.sendMessage(mainHandler.obtainMessage(1));
                        } else {
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
                e.printStackTrace();
            }
        }
    };
    private void setListAdapter() {
        adapter.clear();
        for (Lift lift:data_lift) {
            adapter.add(lift);
        }
        list_lift.setAdapter(adapter);
        //Util.setListViewHeight(list_lift);
        // adapter.notifyDataSetChanged();
        //list_memberRoutine.setSelection(0);
        // Util.setExpendableListViewHeight(list_newsfeed, 0);
    }

}
