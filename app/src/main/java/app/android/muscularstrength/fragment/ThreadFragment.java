package app.android.muscularstrength.fragment;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import app.android.muscularstrength.R;
import app.android.muscularstrength.activity.DashBoardActivity;
import app.android.muscularstrength.adapter.ThreadAdapter;
import app.android.muscularstrength.model.Post;
import app.android.muscularstrength.model.ThreadParser;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.webservice.WebServices;

/**
 * Created by sa on 8/12/2015.
 */
public class ThreadFragment extends Fragment {
    View rootView;
    String id;
    ListView list_thread;
   // TextView txtheading,txtcategories;
    //String heading,category;
    ThreadAdapter adapter;
    ArrayList<Post> datathread;
    private int page_no=1;
    ProgressDialog pDialog;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.thread_fragment, container, false);
        // getActivity().getActionBar().show();

        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity. mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("DISCUSSION BOARD");
        DashBoardActivity.actionbarmenu.setVisibility(View.GONE);
        DashBoardActivity.back_Btn.setVisibility(View.VISIBLE);
        //DashBoardActivity.mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        list_thread=(ListView)rootView.findViewById(R.id.list_thread);
      //  txtheading=(TextView)rootView.findViewById(R.id.txtheading);
        //txtcategories=(TextView)rootView.findViewById(R.id.txtcategories);
        adapter=new ThreadAdapter(getActivity());
        list_thread.setAdapter(adapter);
        pDialog=new ProgressDialog(getActivity());
        pDialog.setMessage("loading...");

        // DashBoardActivity. mainView.setBackgroundColor(getResources().getColor(R.color.tansparent));
        Bundle args = getArguments();
        id=args.getString("threadID");
        Log.i("threadID=", "" + id);
      /*  rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == event.ACTION_UP
                        && keyCode == KeyEvent.KEYCODE_BACK) {
                   *//* if (from == 1) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.contentframe, new DashBoardFragment());
                        ft.commit();
                    } else {
                        getActivity().getSupportFragmentManager().popBackStack();
                    }*//*
                    DashBoardActivity.actionbarmenu.setVisibility(View.VISIBLE);
                    DashBoardActivity.back_Btn.setVisibility(View.GONE);
                    getActivity().getSupportFragmentManager().popBackStack();
                    DashBoardActivity.mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                }
                return true;
            }
        });*/
        DashBoardActivity.back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.actionbarmenu.setVisibility(View.VISIBLE);
                DashBoardActivity.back_Btn.setVisibility(View.GONE);
                getActivity().getSupportFragmentManager().popBackStack();
               // DashBoardActivity.mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
        });
        getThreads(id);
        return  rootView;
    }

    //get articles
    private void getThreads(final String threadID){
        pDialog.show();
        new java.lang.Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String,String> params=new HashMap<String, String>();
                params.put("threadID",threadID);
                JSONParser parser = new JSONParser();
                JSONObject json=parser.makeHttpRequest(WebServices.Forums,"GET",params);
                Gson gson = new Gson();
                ThreadParser data=gson.fromJson(json.toString(),ThreadParser.class);
                if(data.getResult().equalsIgnoreCase("SUCCESS")){
                    datathread=new ArrayList<Post>();
                    //System.out.println("SIZE="+data.getForum().size());
                    datathread.addAll(data.getPosts());
                   // heading=data.getForum().get(0).getHeading();
                   // category=data.getForum().get(0).getCategories();
                    mainHandler.sendMessage(mainHandler.obtainMessage(1));
                }
                else{
                    mainHandler.sendMessage(mainHandler.obtainMessage(0));
                }
            }
        }).start();
    }


    private void setListAdapter(){
        //txtheading.setText(heading);
       // txtcategories.setText(category);
        for(int i=0;i<datathread.size();i++){
            adapter.add(datathread.get(i));
        }
        adapter.notifyDataSetChanged();

    }

    private Handler mainHandler = new Handler() {
        public void handleMessage(android.os.Message message) {
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
}
