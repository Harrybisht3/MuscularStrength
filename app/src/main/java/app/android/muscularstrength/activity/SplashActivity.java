package app.android.muscularstrength.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import app.android.muscularstrength.R;
import app.android.muscularstrength.session.SessionManager;


public class SplashActivity extends Activity {
    private final static int SPLASH_TIME=3000;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        session=new SessionManager(SplashActivity.this);
        splashTimer();
       // compareString();
    }


    private void splashTimer(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!session.isLoggedIn()) {
                    callLandingPage();
                }
                else{
                    callDashBoard();
                }
            }
        },SPLASH_TIME );
    }
    private void callLandingPage(){
        Intent intent=new Intent(this,LandingActivity.class);
        startActivity(intent);
        finish();
    }
    private void callDashBoard(){
        Intent intent=new Intent(this,DashBoardActivity.class);
        startActivity(intent);
        finish();
    }

}
