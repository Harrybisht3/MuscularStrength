package app.android.muscularstrength.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import app.android.muscularstrength.R;
import app.android.muscularstrength.session.SessionManager;


public class SplashActivity extends AppCompatActivity {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
