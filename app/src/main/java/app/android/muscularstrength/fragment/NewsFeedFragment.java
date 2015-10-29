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
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import app.android.muscularstrength.R;
import app.android.muscularstrength.Util.Util;
import app.android.muscularstrength.activity.DashBoardActivity;
import app.android.muscularstrength.adapter.NewsFeedAdapter;
import app.android.muscularstrength.model.NewsFeedParser;
import app.android.muscularstrength.model.Newsfeed;
import app.android.muscularstrength.model.User;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.session.SessionManager;
import app.android.muscularstrength.webservice.WebServices;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by logan on 13/7/15.
 */
public class NewsFeedFragment extends Fragment {
    View rootView;
    int from;
    float density;
    ExpandableListView list_newsfeed;
    NewsFeedAdapter adapter;
    ArrayList<Newsfeed> datanewsFeed;
    private int page_no = 1;
    ProgressDialog pDialog;
    CircleImageView userProfileImg;
    TextView user,account_type,level;
    SessionManager session;
    User userObj;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity.mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("News Feed");
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.newsfeed_fragment, container, false);
            list_newsfeed = (ExpandableListView) rootView.findViewById(R.id.list_newsfeed);
            density = Util.getDensity(getActivity());
            datanewsFeed = new ArrayList<Newsfeed>();
            adapter = new NewsFeedAdapter(getActivity(), datanewsFeed);
            // list_newsfeed.setIndicatorBounds(Util.getDisplay(getActivity()).widthPixels - GetDipsFromPixel(50), Util.getDisplay(getActivity()).widthPixels - GetDipsFromPixel(10));
            list_newsfeed.setAdapter(adapter);
            session=new SessionManager(getActivity());
            Gson gson=new Gson();
            userObj=gson.fromJson(session.getSession(),User.class);
            View headerlayout = View.inflate(getActivity(), R.layout.header_layout, null);
            list_newsfeed.addHeaderView(headerlayout, null, false);
            View view1 = View.inflate(getActivity(), R.layout.footer_layout, null);
            list_newsfeed.addFooterView(view1, null, false);
            // View headerlayout= rootView.findViewById(R.id.header);
            userProfileImg=(CircleImageView)headerlayout.findViewById(R.id.profileImg);
            user = (TextView)headerlayout.findViewById(R.id.user);
            account_type = (TextView)headerlayout.findViewById(R.id.account_type);
            level = (TextView)headerlayout.findViewById(R.id.level);
            Glide.with(getActivity()).load(userObj.getFullImage()).into(userProfileImg);
            user.setText(userObj.getFirstName() + "" + userObj.getLastName());
            account_type.setText(userObj.getAccountType());
            level.setText(userObj.getUserLevel());
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("loading...");
            Bundle args = getArguments();
            from = args.getInt("from");
            getNewsfeed();
        }
        list_newsfeed.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //Util.setExpendableListViewHeight(parent, groupPosition);
                //return false;
                 return true;
            }
        });
     /*   list_newsfeed.setOnTouchListener(new View.OnTouchListener() {
            // Setting on Touch Listener for handling the touch inside ScrollView
            @Override
            public boolean onTouch(View v, MotionEvent event) {
// Disallow the touch request for parent scroll on touch of child view
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });*/
       /* list_newsfeed.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
                int len = adapter.getGroupCount();
                for (int i = 0; i < len; i++) {
                    if (i != groupPosition) {
                        list_newsfeed.collapseGroup(i);
                    }
                }
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

    //get articles
    private void getNewsfeed() {
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("userid", "" + userObj.getUserId());
                 /* params.put("display","15");*/
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.newsFeed, "GET", params);
                Gson gson = new Gson();
                NewsFeedParser data = gson.fromJson(json.toString(), NewsFeedParser.class);
                if (data.getResult().equalsIgnoreCase("SUCCESS")) {
                    datanewsFeed.addAll(data.getData().getNewsfeed());
                    mainHandler.sendMessage(mainHandler.obtainMessage(1));
                } else {
                    mainHandler.sendMessage(mainHandler.obtainMessage(0));
                }
            }
        }).start();
    }

    private void setListAdapter() {
        adapter.notifyDataSetChanged();
        list_newsfeed.setSelection(0);
       // Util.setExpendableListViewHeight(list_newsfeed, 0);
    }

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
                            //  isSearch=false;
                            // search_forum.setText("");
                            setListAdapter();
                            break;
                        case 2:
                            // isSearch=true;
                            setListAdapter();
                            break;
                    }
                }
            } catch (Resources.NotFoundException e) {

            }
        }
    };
}
