package app.android.muscularstrength.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageView;

import app.android.muscularstrength.R;
import app.android.muscularstrength.adapter.MyPagerAdapter;
import app.android.muscularstrength.custom.NonSwipeableViewPager;

/**
 * Created by Bisht Bhawna on 7/11/2015.
 */
public class RegistrationActivity extends FragmentActivity {
    public static NonSwipeableViewPager regpager;
    MyPagerAdapter adapter;
    public static ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_layout);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        regpager = (NonSwipeableViewPager) findViewById(R.id.regpager);
        backBtn = (ImageView) findViewById(R.id.back_icon);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (regpager.getCurrentItem() == 1) {
                    backBtn.setVisibility(View.GONE);
                }
                regpager.setCurrentItem(regpager.getCurrentItem() - 1);
            }
        });
        //backBtn.setVisibility(View.GONE);
        regpager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        if (regpager.getCurrentItem() == 1) {
            backBtn.setVisibility(View.GONE);
        }
        if(regpager.getCurrentItem() != 0){
            regpager.setCurrentItem(regpager.getCurrentItem() - 1);
        }
        else {
            super.onBackPressed();
        }
    }
}
