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
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.android.muscularstrength.R;
import app.android.muscularstrength.Util.Util;
import app.android.muscularstrength.activity.DashBoardActivity;
import app.android.muscularstrength.adapter.CommonViewAdapter;
import app.android.muscularstrength.model.PhotoParser;
import app.android.muscularstrength.model.StoryParser;
import app.android.muscularstrength.model.User;
import app.android.muscularstrength.model.UserVideoMaster;
import app.android.muscularstrength.model.UserVideoParser;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.session.SessionManager;
import app.android.muscularstrength.webservice.WebServices;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Bisht Bhawna on 8/21/2015.
 */
public class CommonFragment extends Fragment {
    public static final String TAG = "CommonFragment";
    View rootView;
    int from;
    float density;
    ExpandableListView list_common;
    CommonViewAdapter adapter;
    private int page_no = 1;
    ProgressDialog pDialog;
    CircleImageView userProfileImg;
    TextView user, account_type, level;
    SessionManager session;
    User userObj;
    List<UserVideoMaster> Video;
    String story;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity.mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("VIDEOS");
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.profile_fragment, container, false);
           // list_common = (ExpandableListView) rootView.findViewById(R.id.list_option);
            density = Util.getDensity(getActivity());
            adapter = new CommonViewAdapter(getActivity(), getResources().getStringArray(R.array.comman_arrays));

            View view = View.inflate(getActivity(), R.layout.headerview_common, null);
            list_common.addHeaderView(view, null, false);
            View view1 = View.inflate(getActivity(), R.layout.footerview_common, null);
            list_common.addFooterView(view1, null, false);
            list_common.setAdapter(adapter);
            session = new SessionManager(getActivity());
            Gson gson = new Gson();
            userObj = gson.fromJson(session.getSession(), User.class);
            View head = (View) view.findViewById(R.id.header);
            userProfileImg = (CircleImageView) head.findViewById(R.id.profileImg);
            user = (TextView) head.findViewById(R.id.user);
            account_type = (TextView) head.findViewById(R.id.account_type);
            level = (TextView) head.findViewById(R.id.level);
            Glide.with(getActivity()).load(userObj.getFullImage()).into(userProfileImg);
            user.setText(userObj.getFirstName() + "" + userObj.getLastName());
            account_type.setText(userObj.getAccountType());
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("loading...");
            Bundle args = getArguments();
            from = args.getInt("from");
            Log.i(TAG, "called From=" + from);
            if (from == 1) {
                getStroy();
            } else if (from == 2) {
                getPhoto();
            } else if (from == 3) {
                getVideo();
            } else if (from == 4) {
                getRoutine();
            } else if (from == 5) {
                getMealPlan();
            } else if (from == 6) {
                getLifts();
            } else if (from == 7) {
                getProgress();
            }
            //getNewsfeed();
        }
        /*list_common.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int len = adapter.getGroupCount();
                for (int i = 0; i < len; i++) {
                    if (i != groupPosition) {
                        list_common.collapseGroup(i);
                    }
                }
            }
        });*/
       list_common.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
               // parent.expandGroup(groupPosition);
                //Toast.makeText(getActivity(), "Gpostion" + groupPosition, Toast.LENGTH_SHORT).show();
               // Util.setExpendableListViewHeight(parent, groupPosition);
               // return false;
                 return true;
            }
        });
         /*list_common.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
                list_common.collapseGroup(groupPosition);
            }
        });*/
     /*   list_newsfeed.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
// Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });*/

        return rootView;
    }

    public int GetDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    //get stroy
    private void getStroy() {
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("userid", "" + 2);
                 /* params.put("display","15");*/
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.story, "GET", params);
                Gson gson = new Gson();
                StoryParser data = gson.fromJson(json.toString(), StoryParser.class);
                if (data.getResult().equalsIgnoreCase("SUCCESS")) {
                    //datanewsFeed.addAll(data.getData().getNewsfeed());
                    story=data.getData().getData();
                    mainHandler.sendMessage(mainHandler.obtainMessage(from));
                } else {
                    mainHandler.sendMessage(mainHandler.obtainMessage(0));
                }
            }
        }).start();
    }

    //get photos
    private void getPhoto() {
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("userid", "" + 2);
                 /* params.put("display","15");*/
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.photos, "GET", params);
                Gson gson = new Gson();
                PhotoParser data = gson.fromJson(json.toString(), PhotoParser.class);
                if (data.getResult().equalsIgnoreCase("SUCCESS")) {
                    //datanewsFeed.addAll(data.getData().getNewsfeed());
                    mainHandler.sendMessage(mainHandler.obtainMessage(from));
                } else {
                    mainHandler.sendMessage(mainHandler.obtainMessage(0));
                }
            }
        }).start();
    }

    //get photos
    private void getVideo() {
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("userid", "" + 2);
                 /* params.put("display","15");*/
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.videos, "GET", params);
                Gson gson = new Gson();
                UserVideoParser data = gson.fromJson(json.toString(), UserVideoParser.class);
                if (data.getResult().equalsIgnoreCase("SUCCESS")) {
                    Video=new ArrayList<UserVideoMaster>();
                    Video.addAll(data.getData().getVideo());
                    //datanewsFeed.addAll(data.getData().getNewsfeed());
                    mainHandler.sendMessage(mainHandler.obtainMessage(from));
                } else {
                    mainHandler.sendMessage(mainHandler.obtainMessage(0));
                }
            }
        }).start();
    }

    //get Routine
    private void getRoutine() {
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mainHandler.sendMessage(mainHandler.obtainMessage(from));
               /* HashMap<String, String> params = new HashMap<String, String>();
                params.put("userid", "" + 2);
                 *//* params.put("display","15");*//*
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.meal_plan, "GET", params);
                Gson gson = new Gson();
                UserVideoParser data = gson.fromJson(json.toString(), UserVideoParser.class);
                if (data.getResult().equalsIgnoreCase("SUCCESS")) {
                    //datanewsFeed.addAll(data.getData().getNewsfeed());
                    mainHandler.sendMessage(mainHandler.obtainMessage(from));
                } else {
                    mainHandler.sendMessage(mainHandler.obtainMessage(0));
                }*/
            }
        }).start();
    }

    //get meal plan
    private void getMealPlan() {
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mainHandler.sendMessage(mainHandler.obtainMessage(from));
             /*   HashMap<String, String> params = new HashMap<String, String>();
                params.put("userid", "" + 2);
                 *//* params.put("display","15");*//*
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.meal_plan, "GET", params);
                Gson gson = new Gson();
                UserVideoParser data = gson.fromJson(json.toString(), UserVideoParser.class);
                if (data.getResult().equalsIgnoreCase("SUCCESS")) {
                    //datanewsFeed.addAll(data.getData().getNewsfeed());
                    mainHandler.sendMessage(mainHandler.obtainMessage(1));
                } else {
                    mainHandler.sendMessage(mainHandler.obtainMessage(0));
                }*/
            }
        }).start();
    }

    //get Lifts
    private void getLifts() {
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mainHandler.sendMessage(mainHandler.obtainMessage(from));
             /*   HashMap<String, String> params = new HashMap<String, String>();
                params.put("userid", "" + 2);
                 *//* params.put("display","15");*//*
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.meal_plan, "GET", params);
                Gson gson = new Gson();
                UserVideoParser data = gson.fromJson(json.toString(), UserVideoParser.class);
                if (data.getResult().equalsIgnoreCase("SUCCESS")) {
                    //datanewsFeed.addAll(data.getData().getNewsfeed());
                    mainHandler.sendMessage(mainHandler.obtainMessage(1));
                } else {
                    mainHandler.sendMessage(mainHandler.obtainMessage(0));
                }*/
            }
        }).start();
    }

    //get Progress
    private void getProgress() {
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                mainHandler.sendMessage(mainHandler.obtainMessage(from));
             /*   HashMap<String, String> params = new HashMap<String, String>();
                params.put("userid", "" + 2);
                 *//* params.put("display","15");*//*
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.meal_plan, "GET", params);
                Gson gson = new Gson();
                UserVideoParser data = gson.fromJson(json.toString(), UserVideoParser.class);
                if (data.getResult().equalsIgnoreCase("SUCCESS")) {
                    //datanewsFeed.addAll(data.getData().getNewsfeed());
                    mainHandler.sendMessage(mainHandler.obtainMessage(1));
                } else {
                    mainHandler.sendMessage(mainHandler.obtainMessage(0));
                }*/
            }
        }).start();
    }




   /* private void setListAdapter() {
        adapter.notifyDataSetChanged();
        list_newsfeed.setSelection(0);
        Util.setExpendableListViewHeight(list_newsfeed, 0);
    }*/

    private Handler mainHandler = new Handler() {
        public void handleMessage(Message message) {
            try {

                if (isAdded()) {
                    pDialog.dismiss();
                    pDialog.cancel();
                    switch (message.what) {
                        case 0:
                            break;
                        case 1:
                            adapter = new CommonViewAdapter(getActivity(), getResources().getStringArray(R.array.comman_arrays),story);
                            list_common.setAdapter(adapter);
                            break;
                        case 2:
                            break;
                        case 3:
                            adapter = new CommonViewAdapter(getActivity(), getResources().getStringArray(R.array.comman_arrays),Video,0);
                            list_common.setAdapter(adapter);
                            break;
                        case 4:
                            break;
                        case 5:
                            break;
                        case 6:
                            break;
                        case 7:
                            break;
                    }
                    list_common.expandGroup(message.what);
                    list_common.setSelection(message.what);
                }
            } catch (Resources.NotFoundException e) {

            }
        }
    };

}
