package app.android.muscularstrength.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import app.android.muscularstrength.R;
import app.android.muscularstrength.activity.DashBoardActivity;
import app.android.muscularstrength.activity.EditProfileActivity;
import app.android.muscularstrength.model.User;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.session.SessionManager;
import app.android.muscularstrength.webservice.WebServices;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Bisht Bhawna on 7/19/2015.
 */
public class ProfileFragment extends Fragment implements View.OnClickListener {
    View rootView;
    TextView goal, gender, age, height, weight, neck, forearm, clave, arm, thigh, chest;
    Button edit_Profile;
    SessionManager session;
    User userObj;
    CircleImageView userProfileImg;
    TextView user, account_type, level;
    String errorMessage;
    ProgressDialog pDialog;
    JSONObject json;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.profile_fragment, container, false);
        // getActivity().getActionBar().show();
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity.mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("PROFILE");
        init();
        getProfile();

        return rootView;
    }

    private void init() {
        session = new SessionManager(getActivity());
        Gson gson = new Gson();
        userObj = gson.fromJson(session.getSession(), User.class);
        //header View
        View headerlayout = rootView.findViewById(R.id.header);
        userProfileImg = (CircleImageView) headerlayout.findViewById(R.id.profileImg);
        user = (TextView) headerlayout.findViewById(R.id.user);
        account_type = (TextView) headerlayout.findViewById(R.id.account_type);
        level = (TextView) headerlayout.findViewById(R.id.level);
        Glide.with(getActivity()).load(userObj.getFullImage()).into(userProfileImg);
        user.setText(userObj.getFirstName() + "" + userObj.getLastName());
        account_type.setText(userObj.getAccountType());
        level.setText(userObj.getUserLevel());
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("loading...");
        goal = (TextView) rootView.findViewById(R.id.goalTxt);
        gender = (TextView) rootView.findViewById(R.id.gendertxt);
        age = (TextView) rootView.findViewById(R.id.ageTxt);
        height = (TextView) rootView.findViewById(R.id.heightTxt);
        weight = (TextView) rootView.findViewById(R.id.weightTxt);
        neck = (TextView) rootView.findViewById(R.id.neckTxt);
        forearm = (TextView) rootView.findViewById(R.id.forearmTxt);
        clave = (TextView) rootView.findViewById(R.id.calveTxt);
        arm = (TextView) rootView.findViewById(R.id.armTxt);
        thigh = (TextView) rootView.findViewById(R.id.thighTxt);
        chest = (TextView) rootView.findViewById(R.id.chestTxt);
        edit_Profile = (Button) rootView.findViewById(R.id.edit_profile);
        edit_Profile.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.edit_profile:
                Intent it=new Intent(getActivity(), EditProfileActivity.class);
                startActivity(it);
                break;
            default:
                break;
        }
    }

    private void getProfile() {
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("userid", userObj.getUserId());
                JSONParser parser = new JSONParser();
                JSONObject json1 = parser.makeHttpRequest(WebServices.userProfile, "GET", params);
                try {
                    if (json1 != null) {
                        if (json1.getString("result").equalsIgnoreCase("SUCCESS")) {
                            json = json1.getJSONArray("Profile").getJSONObject(0);
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

    public Handler mainHandler = new Handler() {
        public void handleMessage(android.os.Message message) {
            try {

                if (isAdded()) {
                    pDialog.dismiss();
                    pDialog.cancel();
                    switch (message.what) {
                        case 0:
                            break;
                        case 1:
                            setValues();
                            break;
                        default:
                            break;
                    }
                }
            } catch (Resources.NotFoundException e) {

            }
        }
    };

    private void setValues() {
        Log.i("TAG", "HERE COMES");
        try {
            doColorSpanForFirstString("Goal: ",json.getString("goal"),goal);
            doColorSpanForFirstString("Gender: :",json.getString("gender"),gender);
            doColorSpanForFirstString("Age: ",json.getString("age"),age);
            doColorSpanForFirstString("Height: ",json.getString("height"),height);
            doColorSpanForFirstString("Weight: ",json.getString("weight"),weight);
            doColorSpanForFirstString("Neck: ",json.getString("neck"),neck);
            doColorSpanForFirstString("Forearm: ",json.getString("forearm"),forearm);
            doColorSpanForFirstString("Calve: ",json.getString("calve"),clave);
            doColorSpanForFirstString("Arm: ",json.getString("arm"),arm);
            doColorSpanForFirstString("Thigh: ",json.getString("thigh"),thigh);
            doColorSpanForFirstString("Chest: ",json.getString("chest"),chest);

          /* // goal.setText(Html.fromHtml("<font color=" + getResources().getColor(R.color.cat_color) + ">Goal: </font> <b style=\"color:" + getResources().getColor(R.color.white) + "\">" + json.getString("goal") + "</b>"));
            //gender.setText(Html.fromHtml("<font color=" + getResources().getColor(R.color.cat_color) + ">Gender: </font> <b style=\"color:" + getResources().getColor(R.color.white) + "\">" + json.getString("gender") + "</b>"));
            //age.setText(Html.fromHtml("<font color=" + getResources().getColor(R.color.cat_color) + ">Age: </font> <b style=\"color:" + getResources().getColor(R.color.white) + "\">" + json.getString("age") + "</b>"));
            height.setText(Html.fromHtml("<font color=" + getResources().getColor(R.color.cat_color) + ">Height: </font> <b style=\"color:" + getResources().getColor(R.color.white) + "\">" + json.getString("height") + "</b>"));
            weight.setText(Html.fromHtml("<font color=" + getResources().getColor(R.color.cat_color) + ">Weight: </font> <b style=\"color:" + getResources().getColor(R.color.white) + "\">" + json.getString("weight") + "</b>"));
            neck.setText(Html.fromHtml("<font color=" + getResources().getColor(R.color.cat_color) + ">Neck: </font> <b style=\"color:" + getResources().getColor(R.color.white) + "\">" + json.getString("neck") + "</b>"));
            forearm.setText(Html.fromHtml("<font color=" + getResources().getColor(R.color.cat_color) + ">Forearm: </font> <b style=\"color:" + getResources().getColor(R.color.white) + "\">" + json.getString("forearm") + "</b>"));
            clave.setText(Html.fromHtml("<font color=" + getResources().getColor(R.color.cat_color) + ">Clave: </font> <b style=\"color:" + getResources().getColor(R.color.white) + "\">" + json.getString("calve") + "</b>"));
            arm.setText(Html.fromHtml("<font color=" + getResources().getColor(R.color.cat_color) + ">Arm: </font> <b style=\"color:" + getResources().getColor(R.color.white) + "\">" + json.getString("arm") + "</b>"));
            thigh.setText(Html.fromHtml("<font color=" + getResources().getColor(R.color.cat_color) + ">Thigh: </font> <b style=\"color:" + getResources().getColor(R.color.white) + "\">" + json.getString("thigh") + "</b>"));
            chest.setText(Html.fromHtml("<font color=" + getResources().getColor(R.color.cat_color) + ">Chest: </font> <b style=\"color:" + getResources().getColor(R.color.white) + "\">" + json.getString("chest") + "</b>"));
            //goal.setText("GOAL:"+json.get("goal"));*/
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doColorSpanForFirstString(String firstString, String lastString, TextView txtSpan) {
        SpannableStringBuilder finalString = new SpannableStringBuilder();
        finalString.append(firstString);
        finalString.setSpan(new ForegroundColorSpan(getResources()
                .getColor(R.color.cat_color)), 0, finalString.length(), 0);
        int start = finalString.length();
        finalString.append(lastString);
        finalString.setSpan(new ForegroundColorSpan(getResources()
                .getColor(R.color.white)), start, finalString.length(), 0);
        finalString.setSpan(new StyleSpan(Typeface.BOLD), start,
                finalString.length(), 0);
        txtSpan.setText(finalString.toString());
        txtSpan.setMovementMethod(LinkMovementMethod.getInstance());

    }
}
