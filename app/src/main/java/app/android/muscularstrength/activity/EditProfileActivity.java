package app.android.muscularstrength.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import app.android.muscularstrength.R;
import app.android.muscularstrength.model.User;
import app.android.muscularstrength.session.SessionManager;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by laxman singh on 11/1/2015.
 */
public class EditProfileActivity extends AppCompatActivity implements View.OnClickListener {
    User userObj;
    SessionManager session;
    String errorMessage;
    ImageView actionbarmenu, back_Btn;
    CircleImageView userProfileImg;
    TextView user, account_type, level;
    TextView title;
    ProgressDialog pDialog;
    Button browseBtn, saveBtn;
    Spinner goal_sp, gender_sp,year_sp,month_sp,day_sp,inch_sp,weight_sp;
    EditText location,feet,inchs,weight;

    //String[]gender={"Gender","Male","Female"};
    List<String>goals,gender, year,months,days,inch,w_unit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_profile);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        View v = getSupportActionBar().getCustomView();
        Toolbar parent = (Toolbar) v.getParent();//first get parent toolbar of current action bar
        parent.setContentInsetsAbsolute(0, 0);
        actionbarmenu = (ImageView) v.findViewById(R.id.menu_icon);
        back_Btn = (ImageView) v.findViewById(R.id.back_icon);
        back_Btn.setOnClickListener(this);
        title = (TextView) v.findViewById(R.id.titleactionbar);
        title.setText("EDIT PROFILE");
        session = new SessionManager(this);
        Gson gson = new Gson();
        userObj = gson.fromJson(session.getSession(), User.class);
        View headerlayout = (View)findViewById(R.id.header);
        userProfileImg = (CircleImageView) headerlayout.findViewById(R.id.profileImg);
        user = (TextView) headerlayout.findViewById(R.id.user);
        account_type = (TextView) headerlayout.findViewById(R.id.account_type);
        level = (TextView) headerlayout.findViewById(R.id.level);
        Glide.with(this).load(userObj.getFullImage()).into(userProfileImg);
        user.setText(userObj.getFirstName() + "" + userObj.getLastName());
        account_type.setText(userObj.getAccountType());
        level.setText(userObj.getUserLevel());
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("loading...");
        actionbarmenu.setVisibility(View.GONE);
        back_Btn.setVisibility(View.VISIBLE);

        goals=new ArrayList<String>();
        gender=new ArrayList<String>();
        year=new ArrayList<String>();
        months=new ArrayList<String>();
        days=new ArrayList<String>();
        inch=new ArrayList<String>();
        w_unit=new ArrayList<String>();
        init();

        settingSpinner(goals, goal_sp);
       /* settingSpinner(gender,gender_sp);
        settingSpinner(year,year_sp);
        settingSpinner(months,month_sp);
        settingSpinner(days,day_sp);
        settingSpinner(inch,inch_sp);
        settingSpinner(w_unit,weight_sp);*/

    }

    private void init() {
        goal_sp=(Spinner)findViewById(R.id.goal);
        gender_sp=(Spinner)findViewById(R.id.gender);
        year_sp=(Spinner)findViewById(R.id.syear);
        month_sp=(Spinner)findViewById(R.id.smonth);
        day_sp=(Spinner)findViewById(R.id.sday);
        inch_sp=(Spinner)findViewById(R.id.inches);
        weight_sp=(Spinner)findViewById(R.id.weight_unit);

        for(int i=0;i<getResources().getStringArray(R.array.goal_arrays).length;i++){
            goals.add(getResources().getStringArray(R.array.goal_arrays)[i]);
        }


       /* int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        //adding years
        gender.add("Gender");
        gender.add("Male");
        gender.add("Female");
        inch.add("in");
        inch.add("m");
        w_unit.add("lbs");
        w_unit.add("kg");
        w_unit.add("st");

        for (int i = 2010; i <= thisYear-1; i++) {
            year.add(Integer.toString(i));
        }
        //adding days and months
        for (int i=1;1<32;i++){
            if(i<13) {
                months.add(Integer.toString(i));
            }
            days.add(Integer.toString(i));
        }*/
    }
    private void settingSpinner(List<String> list,Spinner spinner){
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.myspinner_style, list);
        adapter.setDropDownViewResource(R.layout.myspinner_style);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_icon:
                finish();
                break;
            case R.id.browseBtn:
                break;
            case R.id.saveBtn:
                break;
        }
    }
}
