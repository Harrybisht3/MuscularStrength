package app.android.muscularstrength.fragment;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import app.android.muscularstrength.R;
import app.android.muscularstrength.activity.DashBoardActivity;
import app.android.muscularstrength.adapter.SubForumAdapter;
import app.android.muscularstrength.model.SubForumParser;
import app.android.muscularstrength.model.Thread;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.webservice.WebServices;

/**
 * Created by sa on 8/12/2015.
 */
public class SubForumFragment extends Fragment implements AdapterView.OnItemClickListener {
    View rootView;
    String id;
    ListView list_subForum;
    TextView txtheading,txtcategories;
    String heading,category;
    SubForumAdapter adapter;
    ArrayList<app.android.muscularstrength.model.Thread> dataSubForum;
    private int page_no=1;
    ProgressDialog pDialog;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity.mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("FORUM");
        DashBoardActivity.actionbarmenu.setVisibility(View.GONE);
        DashBoardActivity.back_Btn.setVisibility(View.VISIBLE);
        if(rootView==null) {
            rootView = inflater.inflate(R.layout.subforum_fragment, container, false);
            // getActivity().getActionBar().show();
           // DashBoardActivity.mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
            list_subForum = (ListView) rootView.findViewById(R.id.list_subforum);
            txtheading = (TextView) rootView.findViewById(R.id.txtheading);
            txtcategories = (TextView) rootView.findViewById(R.id.txtcategories);
            adapter = new SubForumAdapter(getActivity());
            list_subForum.setAdapter(adapter);
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("loading...");
            list_subForum.setOnItemClickListener(this);

            // DashBoardActivity. mainView.setBackgroundColor(getResources().getColor(R.color.tansparent));
            Bundle args = getArguments();
            id = args.getString("subForumID");
            Log.i("SUBFORM ID=", "" + id);
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

            getSubForum(id);
        }
        DashBoardActivity.back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.actionbarmenu.setVisibility(View.VISIBLE);
                DashBoardActivity.back_Btn.setVisibility(View.GONE);
                getActivity().getSupportFragmentManager().popBackStack();
                DashBoardActivity.mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
        });

        return  rootView;
    }

    //get articles
    private void getSubForum(final String subForumID){
        pDialog.show();
        new java.lang.Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String,String> params=new HashMap<String, String>();
                params.put("subForumID",subForumID);
                JSONParser parser = new JSONParser();
                JSONObject json=parser.makeHttpRequest(WebServices.Forums,"GET",params);
                Gson gson = new Gson();
                SubForumParser data=gson.fromJson(json.toString(),SubForumParser.class);
                if(data.getResult().equalsIgnoreCase("SUCCESS")){
                    dataSubForum=new ArrayList<Thread>();
                    //System.out.println("SIZE="+data.getForum().size());
                    dataSubForum.addAll(data.getForum().get(0).getTh());
                   heading=data.getForum().get(0).getHeading();
                   category=data.getForum().get(0).getCategories();
                    mainHandler.sendMessage(mainHandler.obtainMessage(1));
                }
                else{
                    mainHandler.sendMessage(mainHandler.obtainMessage(0));
                }
            }
        }).start();
    }


    private void setListAdapter(){
        txtheading.setText(heading);
        txtcategories.setText(category);
        for(int i=0;i<dataSubForum.size();i++){
            adapter.add(dataSubForum.get(i));
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
    switch (parent.getId()){
        case R.id.list_subforum:
            Thread data=adapter.getItem(position);
            showSubForum(data.getId());
            break;

    }
    }
    private void showSubForum(String id) {
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
        bundle.putString("threadID", id);
        Fragment fragment=new ThreadFragment();
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
