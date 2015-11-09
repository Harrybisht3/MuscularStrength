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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
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
import app.android.muscularstrength.Util.Util;
import app.android.muscularstrength.activity.DashBoardActivity;
import app.android.muscularstrength.adapter.MemberRoutineAdapter;
import app.android.muscularstrength.model.Routine;
import app.android.muscularstrength.model.RoutineParser;
import app.android.muscularstrength.model.User;
import app.android.muscularstrength.model.Video;
import app.android.muscularstrength.model.VideoParse;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.session.SessionManager;
import app.android.muscularstrength.webservice.WebServices;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Bisht Bhawna on 8/26/2015.
 */
public class RoutineFragment extends Fragment {
    public static final String TAG = "RoutineFragment";
    View rootView;
    int from;
    float density;
    private int page_no = 1;
    ProgressDialog pDialog;
    CircleImageView userProfileImg;
    TextView user, account_type, level;
    SessionManager session;
    User userObj;
   // ExpandableListView list_routine;
    MemberRoutineAdapter adapter;
    List<Routine> dataRoutine;
    List<Video>dataVideo;
    String errorMessage;
    GridView list_memberRoutine;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity.mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("ROUTINES");
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.new_routine_fragment, container, false);
           // list_routine = (ExpandableListView) rootView.findViewById(R.id.list_routine);
            list_memberRoutine=(GridView)rootView.findViewById(R.id.gridRoutine);
            density = Util.getDensity(getActivity());
            dataRoutine = new ArrayList<Routine>();
            dataVideo=new ArrayList<Video>();
            adapter = new MemberRoutineAdapter(getActivity());

            //header View
            View headerlayout= rootView.findViewById(R.id.header);
           // list_routine.addHeaderView(headerlayout, null, false);
           // View view1 = View.inflate(getActivity(), R.layout.footer_layout, null);
          //  list_routine.addFooterView(view1, null, false);
            //list_routine.setAdapter(adapter);
            list_memberRoutine.setAdapter(adapter);
            density = Util.getDensity(getActivity());
            session = new SessionManager(getActivity());
            Gson gson = new Gson();
            userObj = gson.fromJson(session.getSession(), User.class);

            // View headerlayout= rootView.findViewById(R.id.header);
            userProfileImg = (CircleImageView) headerlayout.findViewById(R.id.profileImg);
            user = (TextView) headerlayout.findViewById(R.id.user);
            account_type = (TextView) headerlayout.findViewById(R.id.account_type);
            level = (TextView) headerlayout.findViewById(R.id.level);
            Glide.with(getActivity()).load(userObj.getFullImage()).into(userProfileImg);
            user.setText(userObj.getFirstName() + "" + userObj.getLastName());
            account_type.setText(userObj.getAccountType());
            level.setText(userObj.getUserLevel());
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("loading...");
            Bundle args = getArguments();
            from = args.getInt("from");
            Log.i(TAG, "called From=" + from);
            //getRoutine();
            getMemberRoutine();
           /* list_routine.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                @Override
                public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                    //Util.setExpendableListViewHeight(parent, groupPosition);
                    //return false;
                    return true;
                }
            });*/

            //getNewsfeed();
        }


        return rootView;
    }

    private void getRoutine() {
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // mainHandler.sendMessage(mainHandler.obtainMessage(1));
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("userid", "" + userObj.getUserId());
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.Routine, "GET", params);
                try {
                    if (json != null) {
                        if (json.getString("result").equalsIgnoreCase("SUCCESS")) {
                            Gson gson = new Gson();
                            RoutineParser data = gson.fromJson(json.toString(), RoutineParser.class);
                            //if (data.getResult().equalsIgnoreCase("SUCCESS")) {
                            dataRoutine.clear();
                            dataRoutine.addAll(data.getRoutines());
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
    private void getMemberRoutine() {
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                // mainHandler.sendMessage(mainHandler.obtainMessage(1));
                HashMap<String, String> params = new HashMap<String, String>();
               // params.put("userid", "" + userObj.getUserId());
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.member_routine, "GET", params);
                try {
                    if (json != null) {
                        if (json.getString("result").equalsIgnoreCase("SUCCESS")) {
                            Gson gson = new Gson();
                            VideoParse data = gson.fromJson(json.toString(), VideoParse.class);
                            //if (data.getResult().equalsIgnoreCase("SUCCESS")) {
                            dataVideo.clear();
                            dataVideo.addAll(data.getVideos());
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
                            // storyTxt.setText(Html.fromHtml(story));
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
        adapter.notifyDataSetChanged();
        //list_memberRoutine.setSelection(0);
        // Util.setExpendableListViewHeight(list_newsfeed, 0);
    }
}
