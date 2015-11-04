package app.android.muscularstrength.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
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
    Spinner goal_sp, gender_sp, year_sp, month_sp, day_sp, inch_sp, weight_sp;
    EditText location, feet, inchs, weight;
    private static final int PICKFILE_RESULT_CODE = 1;
    private File selectedFile;

    //String[]gender={"Gender","Male","Female"};
    List<String> goals, gender, year, months, days, inch, w_unit;

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
        View headerlayout = (View) findViewById(R.id.header);
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

        goals = new ArrayList<String>();
        gender = new ArrayList<String>();
        year = new ArrayList<String>();
        months = new ArrayList<String>();
        days = new ArrayList<String>();
        inch = new ArrayList<String>();
        w_unit = new ArrayList<String>();
        init();

        settingSpinner(goals, goal_sp, 1);
        gender.add("Gender");
        gender.add("Male");
        gender.add("Female");
        settingSpinner(gender, gender_sp, 2);
        inch.add("in");
        inch.add("m");
        w_unit.add("lbs");
        w_unit.add("kg");
        w_unit.add("st");
        settingSpinner(inch, inch_sp, 3);
        settingSpinner(w_unit, weight_sp, 4);
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1925; i <= thisYear - 1; i++) {
            year.add(Integer.toString(i));
        }
        settingSpinner(year, year_sp, 5);
        setMonthDays();
        settingSpinner(months, month_sp, 6);
        settingSpinner(days, day_sp, 7);


    }

    private void init() {
        goal_sp = (Spinner) findViewById(R.id.goal);
        gender_sp = (Spinner) findViewById(R.id.gender);
        year_sp = (Spinner) findViewById(R.id.syear);
        month_sp = (Spinner) findViewById(R.id.smonth);
        day_sp = (Spinner) findViewById(R.id.sday);
        inch_sp = (Spinner) findViewById(R.id.inches);
        weight_sp = (Spinner) findViewById(R.id.weight_unit);
        browseBtn=(Button)findViewById(R.id.browseBtn);
        saveBtn=(Button)findViewById(R.id.saveBtn);
        browseBtn.setOnClickListener(this);

        for (int i = 0; i < getResources().getStringArray(R.array.goal_arrays).length; i++) {
            goals.add(getResources().getStringArray(R.array.goal_arrays)[i]);
        }


       /*
        //adding years



        //adding days and months
       */
    }

    private void setMonthDays() {
        for (int i = 1; i < 32; i++) {
            if (i < 13) {
                months.add(Integer.toString(i));
            }
            days.add(Integer.toString(i));
        }

    }

    private void settingSpinner(List<String> list, Spinner spinner, int value) {
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.myspinner_style, list);
        adapter.setDropDownViewResource(R.layout.myspinner_style);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                // pos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back_icon:
                finish();
                break;
            case R.id.browseBtn:
                chooseFile();
                break;
            case R.id.saveBtn:
                break;
        }
    }

    public void chooseFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("*/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // intent.setAction(Intent.ACTION_GET_CONTENT);
        try {
            startActivityForResult(
                    Intent.createChooser(intent, "Select a File to Upload"),
                    PICKFILE_RESULT_CODE);
        } catch (android.content.ActivityNotFoundException ex) {
            // Potentially direct the user to the Market with a Dialog
            Toast.makeText(EditProfileActivity.this, "Please install a File Manager.",
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("inside", "onActivityResult");
        if (requestCode == PICKFILE_RESULT_CODE) {
            // Check if the user actually selected an image:
            if (resultCode == Activity.RESULT_OK) {
                // This gets the URI of the image the user selected:
                Uri selectedFileURI = data.getData();
                System.out.println("SELECTED URI======" + selectedFileURI);
                if (!TextUtils.equals(selectedFileURI.getAuthority(),
                        MediaStore.AUTHORITY)) {

                    // Handle content URIs for other content providers

                    // For a MediaStore content URI
                } else {
                    selectedFile = new File(getRealPathFromURI(selectedFileURI));
                    if (selectedFile != null) {
                        System.out.println("SELECTED FILE----->>"
                                + selectedFile.toString());
                        // Create a new Intent to send to the next Activity:
                       /* String[] image = selectedFile.toString().split("/");
                         String selected_image = image[image.length - 1];

                        Stringextension1 = selected_image
                                .substring(selected_image.lastIndexOf(".") + 1);
                        // System.out.println("SELECTED IMAGE Extension" +
                        // extension1);
                        // String[] extension = selected_image.split("\\.");
                        try {
                            if (extension1.equalsIgnoreCase("gif")) {
                                //file_name.setText("" + image[image.length - 1]);
                            } else {
                                Utility.showMessage(getActivity()," Please Select  gif Image");
                                file_name.setText("No file choosen");
                                selectedFile = null;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        file_name.setText("No file choosen");
                        selectedFile = null;
                    }*/
                    }
                }

            }

        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result = null;
        try {
            Cursor cursor = this.getContentResolver().query(
                    contentURI, null, null, null, null);
            // System.out.println("CURSOR------>>>"+cursor.toString());
            if (cursor == null) { // Source is Dropbox or other similar local
                // file
                // path
                result = contentURI.getPath();
            } else {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
                result = cursor.getString(idx);
                cursor.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
