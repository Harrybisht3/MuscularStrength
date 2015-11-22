package app.android.muscularstrength.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import app.android.muscularstrength.activity.LoginActivity;

/**
 * Created by logan on 13/7/15.
 */

public class SessionManager {
    // Shared Preferences
    SharedPreferences pref,pref1;

    // Editor for Shared preferences
    SharedPreferences.Editor editor,editor1;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "MuscularPref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";
    // Email address (make variable public to access from outside)
    public static final String KEY_USEROBJECT = "userObj";
    public static final String KEY_REMEMBER = "remember";
    public static final String KEY_USER = "user";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
        pref1 = _context.getSharedPreferences("Muscular1", PRIVATE_MODE);
        editor1 = pref1.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String userObj){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

      /*  // Storing name in pref
        editor.putString(KEY_NAME, name);

        // Storing email in pref
        editor.putString(KEY_EMAIL, email);*/
        editor.putString(KEY_USEROBJECT,userObj);

        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_NAME, pref.getString(KEY_NAME, null));

        // user email id
        user.put(KEY_EMAIL, pref.getString(KEY_EMAIL, null));

        // return user
        return user;
    }
    public  String getSession(){
        return pref.getString(KEY_USEROBJECT,null);
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
    public boolean isRemember(){

        return pref1.getBoolean(KEY_REMEMBER, false);
    }
    public String getUser(){
        return pref1.getString(KEY_USER, "");
    }
    public void setRemember(boolean value,String userid){

        editor1.putBoolean(KEY_REMEMBER,value);
        editor1.putString(KEY_USER, userid);
        editor1.commit();
    }
}
