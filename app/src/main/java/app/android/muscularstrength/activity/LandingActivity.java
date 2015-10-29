package app.android.muscularstrength.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.viewpagerindicator.CirclePageIndicator;

import app.android.muscularstrength.R;
import app.android.muscularstrength.Util.Constants;
import app.android.muscularstrength.Util.Util;
import app.android.muscularstrength.adapter.ViewPagerAdapter;

/**
 * Created by Bisht Bhawna on 7/11/2015.
 */
public class LandingActivity extends Activity implements View.OnClickListener {

    Button sign_upBtn,login_nowBtn;

    // Declare Variables
    ViewPager viewPager;
    PagerAdapter adapter;
    String[] title;
    String[] tag;
    int[] bgimg;
    int[] flag;
    CirclePageIndicator mIndicator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.landing_layout);
        viewPager=(ViewPager)findViewById(R.id.pager);
        sign_upBtn=(Button)findViewById(R.id.signupBtn);
        login_nowBtn=(Button)findViewById(R.id.loginNowBtn);
        login_nowBtn.setTypeface(Util.getTypeFace(LandingActivity.this, Constants.MEDIUM));
        sign_upBtn.setTypeface(Util.getTypeFace(LandingActivity.this, Constants.MEDIUM));
        sign_upBtn.setOnClickListener(this);
        login_nowBtn.setOnClickListener(this);

        String text="<big>FOR ONLY <font color='#ffea00'>$7.99</font><br>A MONTH</big>";

        //<span style='color:#FFFFFF; text-transform: uppercase;'>for only  </span><span style='color:#ffea00;'>$7.99</span><br><span style='color:#FFFFFF; text-transform: uppercase;'>a month</span>
        // Generate sample data
        title = new String[] { "Sign up<br>for a platinum membership today", "<span style='color:#FFFFFF; text-transform: uppercase;'>INTERACTIVE BODY<br>DIAGRAM</span>", " <span style='color:#FFFFFF; text-transform: uppercase;'>LEARN ABOUT<br>MEAL PLANNING</span>", "<span style='color:#FFFFFF; text-transform: uppercase;'>EXCLUSIVE<br>CONTENT</span>"};

        tag = new String[] { text, "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer neque velit, sodales ac dapibus ut, mollis ut odio.", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer neque velit, sodales ac dapibus ut, mollis ut odio.",
                "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Integer neque velit, sodales ac dapibus ut, mollis ut odio."};

        bgimg = new int[] {R.drawable.slide_1,R.drawable.slide_2,R.drawable.slide_3,R.drawable.slide_4};

//String text="for only <font color='#ffea00'>$7.99</font><br>a month";

        // Locate the ViewPager in viewpager_main.xml
        viewPager = (ViewPager) findViewById(R.id.pager);
        mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
        // Pass results to ViewPagerAdapter Class
        adapter = new ViewPagerAdapter(LandingActivity.this, title, tag, bgimg);
        // Binds the Adapter to the ViewPager
        viewPager.setAdapter(adapter);
        mIndicator.setViewPager(viewPager);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signupBtn:
                callRegistrationPage();
                break;
            case R.id.loginNowBtn:
                callLoginPage();
                break;
        }
    }
    private void callLoginPage(){
        Intent intent=new Intent(this,LoginActivity.class);
        startActivity(intent);
       // finish();
    }
    private void callRegistrationPage(){
        Intent intent=new Intent(this,RegistrationActivity.class);
        startActivity(intent);
        // finish();
    }

}
