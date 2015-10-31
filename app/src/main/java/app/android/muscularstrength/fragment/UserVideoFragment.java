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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
import app.android.muscularstrength.adapter.VideoViewPagerAdapter;
import app.android.muscularstrength.custom.NonSwipeableViewPager;
import app.android.muscularstrength.model.User;
import app.android.muscularstrength.model.UserVideoMaster;
import app.android.muscularstrength.model.UserVideoParser;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.session.SessionManager;
import app.android.muscularstrength.webservice.WebServices;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sa on 8/26/2015.
 */
public class UserVideoFragment extends Fragment {
    public static final String TAG = "UserVideoFragment";
    View rootView;
    int from;
    float density;
    private int page_no = 1;
    ProgressDialog pDialog;
    CircleImageView userProfileImg;
    TextView user, account_type, level;
    SessionManager session;
    User userObj;
    List<UserVideoMaster> Video;
    TextView albumTxt;
    NonSwipeableViewPager viewpager;
    VideoViewPagerAdapter adapter;
    ImageView previous,next;
    Button addBtn;
    FragmentTransaction ft;
    FragmentManager manager;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity.mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("VIDEOS");
        ft = getChildFragmentManager().beginTransaction();
        manager = getActivity().getSupportFragmentManager();
       if (rootView == null) {
            rootView = inflater.inflate(R.layout.uservideo_fragment, container, false);
            density = Util.getDensity(getActivity());
            viewpager = (NonSwipeableViewPager)rootView.findViewById(R.id.videopager);
            albumTxt=(TextView)rootView.findViewById(R.id.albumTxt);
            previous=(ImageView)rootView.findViewById(R.id.previous);
            next=(ImageView)rootView.findViewById(R.id.next);
            // albumTxt.setText(adapter.get);
            addBtn=(Button)rootView.findViewById(R.id.addVideo);
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
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("loading...");
            Bundle args = getArguments();
            from = args.getInt("from");
            Log.i(TAG, "called From=" + from);
            getVideo();

            //getNewsfeed();
        }
        else{
           mainHandler.sendMessage(mainHandler.obtainMessage(1));
       }
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if(viewPager.getChildCount()) {
                viewpager.setCurrentItem(viewpager.getCurrentItem() + 1, true);
                // albumTxt.setText("ALBUM-"));
                // }
            }
        });
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  if (getItem(-1)>0) {
                viewpager.setCurrentItem(viewpager.getCurrentItem() - 1, true);
                // albumTxt.setText("ALBUM-" + getItem(-1));
                // }
            }
        });

        return rootView;
    }




    //get photos
    private void getVideo() {
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("userid", "" + userObj.getUserId());
                 /* params.put("display","15");*/
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.videos, "GET", params);
                try {
                    if(json.getString("result").equalsIgnoreCase("SUCCESS")){
                    Gson gson = new Gson();
                    UserVideoParser data = gson.fromJson(json.toString(), UserVideoParser.class);
                   // if (data.getResult().equalsIgnoreCase("SUCCESS")) {
                        Video=new ArrayList<UserVideoMaster>();
                        Video.addAll(data.getData().getVideo());
                        //datanewsFeed.addAll(data.getData().getNewsfeed());
                        mainHandler.sendMessage(mainHandler.obtainMessage(1));
                    } else {
                        mainHandler.sendMessage(mainHandler.obtainMessage(0));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
                            adapter = new VideoViewPagerAdapter(getActivity(),Video,albumTxt,ft,manager);
                            // Binds the Adapter to the ViewPager
                            viewpager.setAdapter(adapter);
                            break;

                    }
                }
            } catch (Resources.NotFoundException e) {

            }
        }
    };
}
