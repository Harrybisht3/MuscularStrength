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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import app.android.muscularstrength.R;
import app.android.muscularstrength.Util.Util;
import app.android.muscularstrength.activity.DashBoardActivity;
import app.android.muscularstrength.adapter.MealPlanAdapter;
import app.android.muscularstrength.model.MealPlanMaster;
import app.android.muscularstrength.model.MealPlanParser;
import app.android.muscularstrength.model.User;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.session.SessionManager;
import app.android.muscularstrength.webservice.WebServices;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sa on 8/27/2015.
 */
public class MealPlanFragment extends Fragment {
    public static final String TAG = "MealPlanFragment";
    View rootView;
    int from;
    float density;
    private int page_no = 1;
    ProgressDialog pDialog;
    CircleImageView userProfileImg;
    TextView user, account_type, level;
    SessionManager session;
    User userObj;
    MealPlanAdapter adapter;
    ListView list_mealplan;
    ArrayList<MealPlanMaster> dataMealplan;



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity.mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("MEAL PLAN");
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.mealplan_fragment, container, false);
            density = Util.getDensity(getActivity());
            list_mealplan=(ListView)rootView.findViewById(R.id.list_mealplan);
            adapter=new MealPlanAdapter(getActivity());
            list_mealplan.setAdapter(adapter);
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
            getMealplan();

            //getNewsfeed();
        }


        return rootView;
    }
    private void getMealplan() {
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
               // mainHandler.sendMessage(mainHandler.obtainMessage(1));
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("userid", "" + 2);
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.meal_plan, "GET", params);
                Gson gson = new Gson();
                MealPlanParser data = gson.fromJson(json.toString(), MealPlanParser.class);
                if (data.getResult().equalsIgnoreCase("SUCCESS")) {
                    dataMealplan=new ArrayList<MealPlanMaster>();
                    dataMealplan.addAll(data.getData().getData());
                    mainHandler.sendMessage(mainHandler.obtainMessage(1));
                } else {
                    mainHandler.sendMessage(mainHandler.obtainMessage(0));
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

                    }
                }
            } catch (Resources.NotFoundException e) {

            }
        }
    };
    private void setListAdapter(){
        for(int i=0;i<dataMealplan.size();i++){
            adapter.add(dataMealplan.get(i));
        }
        adapter.notifyDataSetChanged();
        Util.setListViewHeight(list_mealplan);

    }
}
