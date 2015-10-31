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
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import app.android.muscularstrength.R;
import app.android.muscularstrength.activity.DashBoardActivity;
import app.android.muscularstrength.adapter.VideoAdapter;
import app.android.muscularstrength.model.Video;
import app.android.muscularstrength.model.VideoParse;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.webservice.WebServices;

/**
 * Created by sa on 8/10/2015.
 */
public class ExclusiveVideoFragment extends Fragment implements AdapterView.OnItemClickListener {
    View rootView;
    String id,errorMessage;
    ListView list_videos;
    VideoAdapter adapter;
    ArrayList<Video> dataVideos;
    private int page_no=1;
    ProgressDialog pDialog;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity.mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("EXCLUSIVE");
        DashBoardActivity.actionbarmenu.setVisibility(View.GONE);
        DashBoardActivity.back_Btn.setVisibility(View.VISIBLE);
        if(rootView==null) {
            rootView = inflater.inflate(R.layout.exlusive_video, container, false);
            // getActivity().getActionBar().show();
             // DashBoardActivity.mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            list_videos = (ListView) rootView.findViewById(R.id.list_exclusiveVideo);
            list_videos.setOnItemClickListener(this);
            adapter = new VideoAdapter(getActivity());
            list_videos.setAdapter(adapter);
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("loading...");

            // DashBoardActivity. mainView.setBackgroundColor(getResources().getColor(R.color.tansparent));
            Bundle args = getArguments();
            id = args.getString("id");
            Log.i("Cat ID",""+id);


            getVideos(id);
        }
        DashBoardActivity.back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.actionbarmenu.setVisibility(View.VISIBLE);
                DashBoardActivity.back_Btn.setVisibility(View.GONE);
                getActivity().getSupportFragmentManager().popBackStack();
                //DashBoardActivity.mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
        });
        return  rootView;
    }

    //get articles
    private void getVideos(final String cat_id){
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String,String> params=new HashMap<String, String>();
                params.put("categoryID",cat_id);
                JSONParser parser = new JSONParser();
                JSONObject json=parser.makeHttpRequest(WebServices.Exclusive_Videos,"GET",params);
                try {
                    if(json!=null){
                    if(json.getString("result").equalsIgnoreCase("SUCCESS")) {
                        Gson gson = new Gson();
                        VideoParse data = gson.fromJson(json.toString(), VideoParse.class);
                       // if (data.getResult().equalsIgnoreCase("SUCCESS")) {
                            dataVideos = new ArrayList<Video>();
                            dataVideos.addAll(data.getVideos());
                            mainHandler.sendMessage(mainHandler.obtainMessage(1));
                       /* } else {
                            mainHandler.sendMessage(mainHandler.obtainMessage(0));
                        }*/
                    }
                    else{
                        mainHandler.sendMessage(mainHandler.obtainMessage(0));
                    }
                } else{
                    errorMessage=getResources().getString(R.string.errorMessage);
                    mainHandler.sendMessage(mainHandler.obtainMessage(0));
                }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void setListAdapter(){
        for(int i=0;i<dataVideos.size();i++){
            adapter.add(dataVideos.get(i));
        }
        adapter.notifyDataSetChanged();

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
                            setListAdapter();
                            break;
                    }
                }
            } catch (Resources.NotFoundException e) {

            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.list_exclusiveVideo:
                Video item=adapter.getItem(position);
                showPlayerVideo(item.getVideoLink());
                break;
        }

    }
    private void showPlayerVideo(String Url) {
        /*ThreadFragment kBDetailFragment = new ThreadFragment();
        Bundle args = new Bundle();
        args.putString("threadID", id);
        kBDetailFragment.setArguments(args);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        //fm.beginTransaction().detach(this).commit();
        fm.beginTransaction()
                .replace(DashBoardFragment.frame.getId(), kBDetailFragment, "threadfragment")
                .addToBackStack(null)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();  FragmentTransaction ft = getChildFragmentManager().beginTransaction();*/
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("videoUrl", Url);
        Fragment fragment=new PlayerFragment();
        fragment.setArguments(bundle);
        replaceFragment(fragment);
        ft.commit();

    }
    private void replaceFragment (Fragment fragment){
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);
        if (!fragmentPopped){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.contentframe, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }
}
