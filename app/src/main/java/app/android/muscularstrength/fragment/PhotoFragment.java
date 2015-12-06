package app.android.muscularstrength.fragment;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
import app.android.muscularstrength.Util.Constants;
import app.android.muscularstrength.Util.Util;
import app.android.muscularstrength.activity.AddPhotoActivity;
import app.android.muscularstrength.activity.CreateAlbumActivity;
import app.android.muscularstrength.activity.DashBoardActivity;
import app.android.muscularstrength.adapter.AlbumAdapter;
import app.android.muscularstrength.custom.NonScrollableGridView;
import app.android.muscularstrength.model.Album;
import app.android.muscularstrength.model.PhotoParser;
import app.android.muscularstrength.model.User;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.session.SessionManager;
import app.android.muscularstrength.webservice.WebServices;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sa on 8/27/2015.
 */
public class PhotoFragment extends Fragment implements View.OnClickListener {
    public static final String TAG = "PhotoFragment";
    View rootView;
    int from;
    float density;
    private int page_no = 1;
    private NonScrollableGridView gridAlbum;
    private Button addPhotos, createAlbum;
    ProgressDialog pDialog;
    CircleImageView userProfileImg;
    TextView user, account_type, level;
    SessionManager session;
    User userObj;
    AlbumAdapter adapter;
    List<Album> album;
    PhotoParser data;
    String errorMessage;
    FragmentManager fragmentManager;
    ImageView profile,message,notification;


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity.mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("PHOTOS");
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.photo_fragment, container, false);
            density = Util.getDensity(getActivity());
            session = new SessionManager(getActivity());
            gridAlbum = (NonScrollableGridView) rootView.findViewById(R.id.albumGrid);
            createAlbum = (Button) rootView.findViewById(R.id.createAlbum);
            addPhotos = (Button) rootView.findViewById(R.id.addPhotos);
            createAlbum.setOnClickListener(this);
            addPhotos.setOnClickListener(this);
            Gson gson = new Gson();
            adapter = new AlbumAdapter(getActivity());
            gridAlbum.setAdapter(adapter);
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
            getPhoto();
           /* gridAlbum.setOnTouchListener(new View.OnTouchListener() {
                // Setting on Touch Listener for handling the touch inside ScrollView
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    // Disallow the touch request for parent scroll on touch of child view
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    return false;
                }
            });*/

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

    //get photos
    private void getPhoto() {
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("userid", "" + userObj.getUserId());
                 /* params.put("display","15");*/
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.photos, "GET", params);
                try {
                    if(json!=null) {
                        if (json.getString("result").equalsIgnoreCase("SUCCESS")) {
                            Gson gson = new Gson();
                            data = gson.fromJson(json.toString(), PhotoParser.class);
                            //if (data.getResult().equalsIgnoreCase("SUCCESS")) {
                            //datanewsFeed.addAll(data.getData().getNewsfeed());
                            album = new ArrayList<Album>();
                            album.clear();
                            album.addAll(data.getData().getData());
                            Log.i(TAG,"ALBUM SIZE="+album.size());
                            mainHandler.sendMessage(mainHandler.obtainMessage(1));
                        } else {
                            mainHandler.sendMessage(mainHandler.obtainMessage(0));
                        }
                    }
                    else{
                        errorMessage=getResources().getString(R.string.errorMessage);
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
                            Toast.makeText(getActivity(),""+errorMessage,Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            setGridAdapter();
                            // storyTxt.setText(Html.fromHtml(story));
                            break;

                    }
                }
            } catch (Resources.NotFoundException e) {

            }
        }
    };

    private void setGridAdapter() {
        adapter.clear();
        for (int i = 0; i < album.size(); i++) {
            adapter.add(album.get(i));
        }
        // Toast.makeText(getActivity(),"COUNT A="+adapter.getCount(),Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createAlbum:
                Intent it1 = new Intent(getActivity(), CreateAlbumActivity.class);
                startActivityForResult(it1, 3);
                break;
            case R.id.addPhotos:
               /* ArrayList<String> id = new ArrayList<String>();
                ArrayList<String> albumname = new ArrayList<String>();
                for (Album alb : album) {
                    id.add(alb.getId());
                    albumname.add(alb.getTitle());
                }*/
                Intent it = new Intent(getActivity(), AddPhotoActivity.class);
                // it.putStringArrayListExtra("AlbumID", id);
                //it.putStringArrayListExtra("AlbumName", albumname);
                it.putExtra("ParcelableList", data);
                startActivity(it);
                break;

            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            getPhoto();
        }
    }
}
