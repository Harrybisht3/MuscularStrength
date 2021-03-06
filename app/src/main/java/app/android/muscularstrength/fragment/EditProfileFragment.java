package app.android.muscularstrength.fragment;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import app.android.muscularstrength.Util.Constants;
import app.android.muscularstrength.Util.Util;
import app.android.muscularstrength.activity.DashBoardActivity;
import app.android.muscularstrength.model.User;
import app.android.muscularstrength.session.SessionManager;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by laxman singh on 12/5/2015.
 */
public class EditProfileFragment extends Fragment implements View.OnClickListener {
    View rootView;
    User userObj;
    SessionManager session;
    String errorMessage;
    CircleImageView userProfileImg;
    TextView user, account_type, level;
    TextView title;
    ProgressDialog pDialog;
    Button browseBtn, saveBtn;
    Spinner goal_sp, gender_sp, year_sp, month_sp, day_sp, inch_sp, weight_sp;
    EditText location, feet, inchs, weight;
    private static final int PICKFILE_RESULT_CODE = 1;
    private File selectedFile;
    String type;
    FragmentManager fragmentManager;
    ImageView profile,message,notification;
    //String[]gender={"Gender","Male","Female"};
    List<String> goals, gender, year, months, days, inch, w_unit;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.edit_profile, container, false);
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity.mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("EDIT PROFILE");
        DashBoardActivity.actionbarmenu.setVisibility(View.GONE);
        DashBoardActivity.back_Btn.setVisibility(View.VISIBLE);
        session = new SessionManager(getActivity());
        Gson gson = new Gson();
        userObj = gson.fromJson(session.getSession(), User.class);
        View headerlayout = (View)rootView. findViewById(R.id.header);
        userProfileImg = (CircleImageView) headerlayout.findViewById(R.id.profileImg);
        user = (TextView) headerlayout.findViewById(R.id.user);
        account_type = (TextView) headerlayout.findViewById(R.id.account_type);
        level = (TextView) headerlayout.findViewById(R.id.level);
        profile=(ImageView)headerlayout.findViewById(R.id.profile);
        message=(ImageView)headerlayout.findViewById(R.id.message);
        notification=(ImageView)headerlayout.findViewById(R.id.notification);
        fragmentManager=getActivity().getSupportFragmentManager();
        Glide.with(this).load(userObj.getFullImage()).into(userProfileImg);
        user.setText(userObj.getFirstName() + "" + userObj.getLastName());
        account_type.setText(userObj.getAccountType());
        level.setText(userObj.getUserLevel());
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("loading...");
        Bundle args = getArguments();
        type=args.getString("Type");
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
        DashBoardActivity.back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.actionbarmenu.setVisibility(View.VISIBLE);
                DashBoardActivity.back_Btn.setVisibility(View.GONE);
                if(type.equalsIgnoreCase("Fragment")){
                    getActivity().getSupportFragmentManager().popBackStack();
                }else{
                    Util.setFragment(fragmentManager,Constants.DASHHOME);
                }
                //DashBoardActivity.mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
        });
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
    private void init() {
        goal_sp = (Spinner)rootView. findViewById(R.id.goal);
        gender_sp = (Spinner)rootView.  findViewById(R.id.gender);
        year_sp = (Spinner)rootView.  findViewById(R.id.syear);
        month_sp = (Spinner)rootView.  findViewById(R.id.smonth);
        day_sp = (Spinner)rootView.  findViewById(R.id.sday);
        inch_sp = (Spinner) rootView. findViewById(R.id.inches);
        weight_sp = (Spinner)rootView.  findViewById(R.id.weight_unit);
        browseBtn=(Button)rootView. findViewById(R.id.browseBtn);
        saveBtn=(Button)rootView. findViewById(R.id.saveBtn);
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
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.myspinner_style, list);
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
               // onBackPressed();
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
            Toast.makeText(getActivity(), "Please install a File Manager.",
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
                System.out.println("SELECTED URI Fragment======" + selectedFileURI);

                System.out.println("SELECTED URI======" + getPath(getActivity(),selectedFileURI));


            }

        }
    }
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context The context.
     * @param uri The Uri to query.
     * @param selection (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

   /* @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(type.equalsIgnoreCase("Fragment")){

        }else{
            callDashBoard();
        }
        finish();
    }
    private void callDashBoard(){
        Intent it=new Intent(EditProfileActivity.this, DashBoardActivity.class);
        startActivity(it);


    }*/
}
