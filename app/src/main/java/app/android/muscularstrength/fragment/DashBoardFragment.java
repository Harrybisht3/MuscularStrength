package app.android.muscularstrength.fragment;


import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import app.android.muscularstrength.R;
import app.android.muscularstrength.activity.DashBoardActivity;

/**
 * Created by logan on 12/7/15.
 */
public class DashBoardFragment extends Fragment implements View.OnClickListener{
    View rootView;
    private String TAG = "DashBoardFragment";
    Context context;
    int POS=0;
    public static FrameLayout frame;
    float density;
    int width;
    int height;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_frame, container, false);
       // getActivity().getActionBar().hide();
        DashBoardActivity.actionBar.hide();
        DashBoardActivity.menuView.setVisibility(View.VISIBLE);
        DashBoardActivity. mainView.setBackgroundResource(R.drawable.dash_bg);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            initUIControls();
        } catch (Exception e) {
            try {
                throw new Exception(TAG + "Failed to initialize controls..");
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            rootView.setFocusableInTouchMode(true);
            rootView.requestFocus();
            rootView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (event.getAction() == event.ACTION_UP
                            && keyCode == KeyEvent.KEYCODE_BACK) {
                       /* if(POS!=0) {

                          *//*  FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction ft = fragmentManager.beginTransaction();
                            ft.replace(R.id.contentframe, new DashBoardFragment());
                            ft.commit();*//*
                            selectItem(0);
                        }
                        else{

                        }*/
                        if (getActivity().getSupportFragmentManager().getBackStackEntryCount() == 1){
                            getActivity().finish();
                        }

                    }
                    return true;
                }
            });
        }
       // DashBoardActivity. mainView.setBackgroundColor(getResources().getColor(R.color.tansparent));
        return  rootView;
    }
    public void initUIControls() {
        context = getActivity();
      //  typeface = Typeface.createFromAsset(context.getAssets(), "TAHOMA.TTF");
		/*pd = new ProgressDialog(context);
		pd.setMessage("Loading..");
		pd.setCanceledOnTouchOutside(false);*/
       // HomeActivity.lin.setVisibility(View.VISIBLE);
       // session = new SessionManager(context);
      //  objuser = session.getUserDetails();
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        density = metrics.density;
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height = size.y;

        frame = (FrameLayout) rootView.findViewById(R.id.mainframe);
        // if (HomeActivity.isAdd) {
        // selectItem(1);
        // } else {
        selectItem(0);
        // }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }
    }
    public void selectItem(int position){
     //   clearBackStack();
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("from", 1);
        Fragment fragment=null;
        switch (position){
            case 0:
                fragment=new DashBoardInner();
                ft.replace(frame.getId(), fragment);
                break;
            case 1:
                POS=1;
                fragment=new ArticleFragment();
                fragment.setArguments(bundle);
                ft.replace(frame.getId(),fragment).addToBackStack(null);
                break;
            case 2:
                POS=2;
                //ft.replace(frame.getId(), new ArticleFragment()).addToBackStack(null);
                break;
            case 3:
                POS=3;
                fragment=new ForumsFragment();
                fragment.setArguments(bundle);
                ft.replace(frame.getId(),fragment).addToBackStack(null);
                break;
            case 4:
                POS=4;
                fragment=new RecipesFragment();
                fragment.setArguments(bundle);
                ft.replace(frame.getId(),fragment).addToBackStack(null);
                break;
            case 5:
                POS = 5;
                fragment=new ExclusiveFragment();
                fragment.setArguments(bundle);
                ft.replace(frame.getId(),fragment).addToBackStack(null);
                break;
            case 6:
                POS = 6;
                fragment=new NewsFeedFragment();
                fragment.setArguments(bundle);
                ft.replace(frame.getId(),fragment).addToBackStack(null);
                break;
            default:
                break;
        }
        ft.commit();
    }
    private void replaceFragment (Fragment fragment){
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);
        if (!fragmentPopped){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(frame.getId(), fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }
    // To animate view slide out from bottom to top

    public class DashBoardInner extends Fragment implements View.OnClickListener{
        private String TAG = "DashBoardInner";
        Context context;
        View rootView;
        boolean isShown=false;
        Button moreOptions;
        Button moreOption;
        private RelativeLayout moreoption_lay,hideoption_lay;
        RelativeLayout articles,workouts,forums,recipes,exclusive,newsfeed,story,photos,videos,routines,meal_plan,lifts,progress;
        public DashBoardInner(){
            POS=0;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            rootView=inflater.inflate(R.layout.dashboard_dummy, container,
                    false);
            try {
                initUIControls();
            } catch (Exception e) {
                try {
                    throw new Exception(TAG + "Failed to initialize controls..");
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            return rootView;
        }
        public void initUIControls(){
            moreOption=(Button)rootView.findViewById(R.id.moreOption);
            moreoption_lay=(RelativeLayout)rootView.findViewById(R.id.moreOption_layout);
            hideoption_lay=(RelativeLayout)rootView.findViewById(R.id.hideoption_layout);
            articles=(RelativeLayout)rootView.findViewById(R.id.articleView);
            workouts=(RelativeLayout)rootView.findViewById(R.id.workoutView);
            forums=(RelativeLayout)rootView.findViewById(R.id.forumView);
            recipes=(RelativeLayout)rootView.findViewById(R.id.recipeView);
            exclusive=(RelativeLayout)rootView.findViewById(R.id.exclusiveView);
            newsfeed=(RelativeLayout)rootView.findViewById(R.id.newsfeedView);
            story=(RelativeLayout)rootView.findViewById(R.id.storyView);
            photos=(RelativeLayout)rootView.findViewById(R.id.photoView);
            videos=(RelativeLayout)rootView.findViewById(R.id.videosView);
            routines=(RelativeLayout)rootView.findViewById(R.id.routineView);
            meal_plan=(RelativeLayout)rootView.findViewById(R.id.mealplanView);
            lifts=(RelativeLayout)rootView.findViewById(R.id.liftView);
            progress=(RelativeLayout)rootView.findViewById(R.id.progressView);
            moreOption.setOnClickListener(this);
            articles.setOnClickListener(this);
            workouts.setOnClickListener(this);
            forums.setOnClickListener(this);
            recipes.setOnClickListener(this);
            exclusive.setOnClickListener(this);
            newsfeed.setOnClickListener(this);
            story.setOnClickListener(this);
            photos.setOnClickListener(this);
            videos.setOnClickListener(this);
            routines.setOnClickListener(this);
            meal_plan.setOnClickListener(this);
            lifts.setOnClickListener(this);
            progress.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.moreOption:
                    if(!isShown){
                        isShown=true;
                        moreOption.setText(R.string.hide_option);
                        moreoption_lay.setVisibility(View.GONE);
                        slideToTop(hideoption_lay);
                    }
                    else{
                        isShown=false;
                        moreOption.setText(R.string.more_option);
                        hideoption_lay.setVisibility(View.GONE);
                        slideToBottom(moreoption_lay);
                    }
                    break;

                case R.id.articleView:
                    selectItem(1);
                    break;
                case R.id.workoutView:
                    selectItem(2);
                    break;
                case R.id.forumView:
                    selectItem(3);
                    break;
                case R.id.recipeView:
                    selectItem(4);
                    break;
                case R.id.exclusiveView:
                    selectItem(5);
                    break;
                case R.id.storyView:
                    selectItem(6);
                    break;
                case R.id.photoView:
                    selectItem(7);
                    break;
                case R.id.videosView:
                    selectItem(8);
                    break;
                case R.id.routineView:
                    selectItem(9);
                    break;
                case R.id.mealplanView:
                    selectItem(10);
                    break;
                case R.id.liftView:
                    selectItem(11);
                    break;
                case R.id.progressView:
                    selectItem(12);
                    break;

            }
        }
        public void slideToTop(final View view) {


            Animation bottomUp = AnimationUtils.loadAnimation(getActivity(),
                    R.anim.bottom_up);
            bottomUp.setDuration(800);
            view.startAnimation(bottomUp);
            view.setVisibility(View.VISIBLE);
            //setLayoutAnim_slideup();
       /* Animation slideDown = setLayoutAnim_slideup();
        view.startAnimation(slideDown);*/
        }
        public void slideToBottom(final View view) {
            view.setVisibility(View.VISIBLE);

            Animation slideDown = setLayoutAnim_slidedown();
            view.startAnimation(slideDown);
     /*   Animation bottomUp = AnimationUtils.loadAnimation(DashBoardActivity.this,
                R.anim.bottom_down);

        view.startAnimation(bottomUp);*/

        }
        public Animation setLayoutAnim_slidedown() {
            AnimationSet set = new AnimationSet(true);
            Animation animation = new TranslateAnimation(
                    Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                    0.0f, Animation.RELATIVE_TO_SELF, -1.0f,
                    Animation.RELATIVE_TO_SELF, 0.0f);
            animation.setDuration(800);
            animation.setAnimationListener(new Animation.AnimationListener() {

                @Override
                public void onAnimationStart(Animation animation) {
                    // TODO Auto-generated method stub
                    // MapContacts.this.mapviewgroup.setVisibility(View.VISIBLE);

                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    // TODO Auto-generated method stub
                    Log.d("LA", "sliding down ended");

                }
            });
            set.addAnimation(animation);

            LayoutAnimationController controller = new LayoutAnimationController(
                    set, 0.25f);
            return animation;
        }

    }
}
