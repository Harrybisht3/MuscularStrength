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
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import app.android.muscularstrength.R;
import app.android.muscularstrength.Util.Constants;
import app.android.muscularstrength.Util.Util;
import app.android.muscularstrength.activity.DashBoardActivity;
import app.android.muscularstrength.model.StoryParser;
import app.android.muscularstrength.model.User;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.session.SessionManager;
import app.android.muscularstrength.webservice.WebServices;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sa on 8/26/2015.
 */
public class UserStoryFragment extends Fragment {
    public static final String TAG = "UserStoryFragment";
    View rootView;
    int from;
    float density;
    private int page_no = 1;
    ProgressDialog pDialog;
    CircleImageView userProfileImg;
    TextView user, account_type, level;
    SessionManager session;
    User userObj;
    TextView storyTxt;
    String story,errorMessage;
    FragmentManager fragmentManager;
    ImageView profile,message,notification;



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity.mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("VIDEOS");
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.userstoryfragment, container, false);
            density = Util.getDensity(getActivity());
            storyTxt = (TextView) rootView.findViewById(R.id.story_txt);
            session = new SessionManager(getActivity());
            Gson gson = new Gson();
            userObj = gson.fromJson(session.getSession(), User.class);
            //header View
            View headerlayout = rootView.findViewById(R.id.header);
            userProfileImg = (CircleImageView) headerlayout.findViewById(R.id.profileImg);
            user = (TextView) headerlayout.findViewById(R.id.user);
            account_type = (TextView) headerlayout.findViewById(R.id.account_type);
            level = (TextView) headerlayout.findViewById(R.id.level);
            profile=(ImageView)headerlayout.findViewById(R.id.profile);
            message=(ImageView)headerlayout.findViewById(R.id.message);
            notification=(ImageView)headerlayout.findViewById(R.id.notification);
            fragmentManager=getActivity().getSupportFragmentManager();
            Glide.with(getActivity()).load(userObj.getFullImage()).into(userProfileImg);
            user.setText(userObj.getFirstName() + "" + userObj.getLastName());
            account_type.setText(userObj.getAccountType());
            level.setText(userObj.getUserLevel());
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("loading...");
            Bundle args = getArguments();
            from = args.getInt("from");
            Log.i(TAG, "called From=" + from);
            getStroy();

            //getNewsfeed();
        }
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.setFragment(fragmentManager, Constants.FRIEND);
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.setFragment(fragmentManager, Constants.MESSAGE);
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.setFragment(fragmentManager, Constants.NOTIFICATION);
            }
        });



        return rootView;
    }


    //get stroy
    private void getStroy() {
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("userid", "" + userObj.getUserId());
                 /* params.put("display","15");*/
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.story, "GET", params);
                try {
                    if(json!=null){
                    if (json.getString("result").equalsIgnoreCase("SUCCESS")) {
                        Gson gson = new Gson();
                        StoryParser data = gson.fromJson(json.toString(), StoryParser.class);
                        // if (data.getResult().equalsIgnoreCase("SUCCESS")) {
                        //datanewsFeed.addAll(data.getData().getNewsfeed());
                        story = data.getData().getData();
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
                            Toast.makeText(getActivity(),""+errorMessage,Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            storyTxt.setText(Html.fromHtml(story));
                            break;

                    }
                }
            } catch (Resources.NotFoundException e) {

            }
        }
    };
}
