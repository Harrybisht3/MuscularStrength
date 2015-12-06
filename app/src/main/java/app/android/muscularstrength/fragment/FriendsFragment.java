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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import app.android.muscularstrength.Util.Constants;
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
public class FriendsFragment extends Fragment {
    View rootView;
    int from;
    SessionManager session;
    User userObj;
    ProgressDialog pDialog;
    CircleImageView userProfileImg;
    TextView user, account_type, level;
    String errorMessage;
    ListView list_friends;
    ArrayList<Friend> dataFriend;
    FriendAdapter adapter;
    Fragment fragmentcontext;
    FragmentManager fragmentManager;
    ImageView profile,message,notification;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // getActivity().getActionBar().show();
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity.mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("FRIENDS");
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.friends_fragment, container, false);

            session = new SessionManager(getActivity());
            fragmentcontext = FriendsFragment.this;
            Gson gson = new Gson();
            userObj = gson.fromJson(session.getSession(), User.class);
            //header View
            list_friends = (ListView) rootView.findViewById(R.id.list_friend);
            adapter = new FriendAdapter(getActivity(), fragmentcontext);
            list_friends.setAdapter(adapter);
            //list_friends.setOnItemClickListener(this);
            View headerlayout = rootView.findViewById(R.id.header);
            userProfileImg = (CircleImageView) headerlayout.findViewById(R.id.profileImg);
            user = (TextView) headerlayout.findViewById(R.id.user);
            account_type = (TextView) headerlayout.findViewById(R.id.account_type);
            level = (TextView) headerlayout.findViewById(R.id.level);
            profile=(ImageView)headerlayout.findViewById(R.id.profile);
            message=(ImageView)headerlayout.findViewById(R.id.message);
            notification=(ImageView)headerlayout.findViewById(R.id.notification);
            profile.setAlpha(0.5f);
            fragmentManager=getActivity().getSupportFragmentManager();
            Glide.with(getActivity()).load(userObj.getFullImage()).into(userProfileImg);
            user.setText(userObj.getFirstName() + "" + userObj.getLastName());
            account_type.setText(userObj.getAccountType());
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("loading...");
            getFriends();
        }

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Util.setFragment(fragmentManager, Constants.FRIEND);
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

    //get friendRequest
    private void getFriends() {
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("userid", userObj.getUserId());
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.friends, "GET", params);
                try {
                    if (json != null) {
                        if (json.getString("result").equalsIgnoreCase("SUCCESS")) {
                            Gson gson = new Gson();
                            FriendParser data = gson.fromJson(json.toString(), FriendParser.class);
                            // if (data.getResult().equalsIgnoreCase("SUCCESS")) {
                            dataFriend = new ArrayList<Friend>();
                            dataFriend.addAll(data.getData().getFriend());
                            mainHandler.sendMessage(mainHandler.obtainMessage(1));
                            // } else {
                            //mainHandler.sendMessage(mainHandler.obtainMessage(0));
                            //}
                        } else {
                            errorMessage = json.getJSONObject("data").getString("friend");
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

    private void setListAdapter() {
        for (int i = 0; i < dataFriend.size(); i++) {
            adapter.add(dataFriend.get(i));
        }
        adapter.notifyDataSetChanged();
        Util.setListViewHeight(list_friends);

    }
   /* @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
       switch (view.getId()){
           case R.id.list_friend:

               final TextView viewProfile= (TextView)((View)view.getParent()).findViewById(R.id.accept_txt);
               final Friend user=adapter.getItem(position);
               Log.i("LOG", "" + user.getId());
               //String txt2 = txt.getText().toString();
               viewProfile.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       showProfile(user.getId());
                   }
               });
              //
               break;
           default:
               break;
       }
    }*/


}
