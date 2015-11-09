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
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.android.muscularstrength.R;
import app.android.muscularstrength.Util.Util;
import app.android.muscularstrength.activity.DashBoardActivity;
import app.android.muscularstrength.activity.YouTubePlayerActivity;
import app.android.muscularstrength.adapter.HelpAdapter;
import app.android.muscularstrength.custom.HorizontalListView;
import app.android.muscularstrength.model.HelpData;
import app.android.muscularstrength.model.HelpParser;
import app.android.muscularstrength.model.User;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.session.SessionManager;
import app.android.muscularstrength.webservice.WebServices;

/**
 * Created by laxman singh on 11/4/2015.
 */
public class HelpFragment extends Fragment {
    View rootView;
    ProgressDialog pDialog;
    SessionManager session;
    User userObj;
    String errorMessage;
   List<HelpData> datahelp;
    HorizontalListView list_help;
    ImageView help_back,help_next;
    HelpAdapter adapter;
    DisplayMetrics display;
    int height,width;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity. mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("HELP");
        rootView=inflater.inflate(R.layout.help_fragment,container,false);
        pDialog=new ProgressDialog(getActivity());
        pDialog.setMessage("loading...");
        session=new SessionManager(getActivity());
        Gson gson=new Gson();
        list_help=(HorizontalListView)rootView.findViewById(R.id.list_help);
        help_back=(ImageView)rootView.findViewById(R.id.help_back);
        help_next=(ImageView)rootView.findViewById(R.id.help_next);
       // list_help.setOnTouchListener(new OnTouch());
        userObj=gson.fromJson(session.getSession(),User.class);
        adapter = new HelpAdapter(getActivity());
        list_help.setAdapter(adapter);
        display= Util.getDisplay(getActivity());
        width=display.widthPixels;
        help_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // if(list_help.getLastVisiblePosition()<adapter.getCount()-1) {
                   // list_help.scrollTo((int) list_help.getScrollX() + ((width / 2) - (int) getResources().getDimension(R.dimen._20sdp)), (int) list_help.getScrollY());
                //}
                }
        });
        help_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list_help.scrollTo((int)list_help.getScrollX() + 10, (int)list_help.getScrollY());
               // if(list_help.getFirstVisiblePosition()>0 )
              // list_help.scrollTo(list_help.getScrollX() - ((width/2)-(int)getResources().getDimension(R.dimen._20sdp)), 0);
            }
        });
        list_help.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent ii=new Intent(getActivity(), YouTubePlayerActivity.class);
                HelpData data=adapter.getItem(position);
                ii.putExtra("videoUrl",""+data.getVideoLink());
                startActivity(ii);
            }
        });
        getHelp();
        return rootView;
    }
    //get articles
    private void getHelp(){
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String,String> params=new HashMap<String, String>();
                params.put("userid",""+userObj.getUserId());
                JSONParser parser = new JSONParser();
                JSONObject json=parser.makeHttpRequest(WebServices.help,"GET",params);
                try {
                    if(json!=null) {
                        if (json.getString("result").equalsIgnoreCase("SUCCESS")) {
                            Gson gson = new Gson();
                            HelpParser data = gson.fromJson(json.toString(), HelpParser.class);
                            // if (data.getResult().equalsIgnoreCase("SUCCESS")) {
                            datahelp = new ArrayList<HelpData>();
                            datahelp.addAll(data.getData().getWalkThrough());
                            mainHandler.sendMessage(mainHandler.obtainMessage(1));
                       /* } else {

                        }*/
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
                            break;
                        case 1:
                            setListAdapter();
                            break;
                       /* case 2:
                            adapter.clear();
                            isSearch=true;
                            setSearchedListAdapter();
                            break;*/
                        default:
                            break;
                    }
                }
            } catch (Resources.NotFoundException e) {

            }
        }
    };
    private void setListAdapter(){
        for (int i = 0; i < datahelp.size(); i++) {
            adapter.add(datahelp.get(i));
        }
        // Toast.makeText(getActivity(),"COUNT A="+adapter.getCount(),Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();
       /* list_help.postDelayed(new Runnable() {
            @Override
            public void run() {
                list_help.setSelection(list_help.getAdapter().getCount()-1);
            }
        }, 500);*/
    }
    private class OnTouch implements View.OnTouchListener
    {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    }
}
