package app.android.muscularstrength.fragment;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;

import app.android.muscularstrength.R;
import app.android.muscularstrength.activity.DashBoardActivity;
import app.android.muscularstrength.custom.FitVideoView;

/**
 * Created by Bisht Bhawna on 8/18/2015.
 */
public class PlayerFragment extends Fragment {
    View root;
    private static final String TAG = "PlayerFragment";
    private FitVideoView videoView;
    private String videoUri;
    private MediaController mediaControls;
    private ProgressDialog progressDialog;
    private int position = 0;
    int width,height;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.playerlayout, container, false);
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity.mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("EXCLUSIVE");
        DashBoardActivity.actionbarmenu.setVisibility(View.GONE);
        DashBoardActivity.back_Btn.setVisibility(View.VISIBLE);
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

        //set the media controller buttons
        if (mediaControls == null) {
            mediaControls = new MediaController(getActivity());
        }
        // create a progress bar while the video file is loading
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
      //  DashBoardActivity.mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        videoView = (FitVideoView) root.findViewById(R.id.surface);
        videoUri = getArguments().getString("videoUrl");
        Log.i(TAG,"URL VIDEO="+videoUri);
        try {
            videoView.setMediaController(mediaControls);
            videoView.setVideoURI(Uri.parse(videoUri));

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();

        }
        videoView.requestFocus();
        //we also set an setOnPreparedListener in order to know when the video file is ready for playback

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mediaPlayer) {

                // close the progress bar and play the video
                progressDialog.dismiss();
                //if we have a position on savedInstanceState, the video playback should start from here
                videoView.seekTo(position);

                if (position == 0) {
                    videoView.start();
                } else {
                    //if we come from a resumed activity, video playback will be paused
                    videoView.pause();
                }
            }

        });
        videoView.setOnErrorListener(new MediaPlayer.OnErrorListener() {
            @Override
            public boolean onError(MediaPlayer mp, int what, int extra) {
                progressDialog.dismiss();
                return false;
            }
        });
        int currentOrientation=getResources().getConfiguration().orientation;
        getScreen();
        if(currentOrientation== Configuration.ORIENTATION_PORTRAIT){
            DashBoardActivity.actionBar.show();
           // getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }
        else{
            DashBoardActivity.actionBar.hide();
           // getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setVideoRatio();

        DashBoardActivity.back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.actionbarmenu.setVisibility(View.VISIBLE);
                DashBoardActivity.back_Btn.setVisibility(View.GONE);
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
               // DashBoardActivity.mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
        });

        return root;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);//
        //we use onSaveInstanceState in order to store the video playback position for orientation change
        savedInstanceState.putInt("Position", videoView.getCurrentPosition());
        videoView.pause();
    }
  /*  @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //we use onRestoreInstanceState in order to play the video playback from the stored position
        position = savedInstanceState.getInt("Position");
        videoView.seekTo(position);
    }*/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            // Restore last state for checked position.
            position = savedInstanceState.getInt("Position", 0);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        getScreen();
        if(newConfig.orientation== Configuration.ORIENTATION_PORTRAIT){
            DashBoardActivity.actionBar.show();
           // getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }
        else{
            DashBoardActivity.actionBar.hide();
           // getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setVideoRatio();
    }

    public void playVideo() {
        Uri uri = Uri.parse(videoUri);
        Log.d(TAG, "Uri is: " + uri);
        setVideoLocation(uri);
        if (!videoView.isPlaying()) {
            videoView.start();
        }
    }

    private void setVideoLocation(Uri uri) {
        try {
            videoView.setVideoURI(uri);
        } catch (Exception e) {
            Log.e(TAG, "VideoPlayer uri was invalid", e);
            Toast.makeText(getActivity(), "Not found", Toast.LENGTH_SHORT).show();
        }
    }

    public void pauseVideo() {
        videoView.pause();
    }

    @Override
    public void onPause() {
        super.onPause();
        pauseVideo();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        videoView.stopPlayback();
    }

    private void getScreen(){
        DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
        width=metrics.widthPixels;
        height=metrics.heightPixels;

    }
    private void setVideoRatio(){
        //Get the SurfaceView layout parameters
        android.view.ViewGroup.LayoutParams lp = videoView.getLayoutParams();
          lp.height=height;
        lp.width=width;
        videoView.setLayoutParams(lp);
    }
}
