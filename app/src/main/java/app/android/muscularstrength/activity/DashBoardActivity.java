package app.android.muscularstrength.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import app.android.muscularstrength.R;
import app.android.muscularstrength.adapter.MenuListAdapter;
import app.android.muscularstrength.fragment.ArticleFragment;
import app.android.muscularstrength.fragment.CustomizeAvatarFragment;
import app.android.muscularstrength.fragment.FragmentHome;
import app.android.muscularstrength.fragment.FriendRequestFragment;
import app.android.muscularstrength.fragment.FriendsFragment;
import app.android.muscularstrength.fragment.HelpFragment;
import app.android.muscularstrength.fragment.MessageFragment;
import app.android.muscularstrength.fragment.NewsFeedFragment;
import app.android.muscularstrength.fragment.NotificationFragment;
import app.android.muscularstrength.fragment.PhotoFragment;
import app.android.muscularstrength.fragment.ProfileFragment;
import app.android.muscularstrength.fragment.RecipesFragment;
import app.android.muscularstrength.fragment.UserVideoFragment;
import app.android.muscularstrength.model.User;
import app.android.muscularstrength.session.SessionManager;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Bisht Bhawna on 7/12/2015.
 */
public class DashBoardActivity extends AppCompatActivity implements OnItemClickListener {
    private static final String TAG = "DashBoardActivity";
    public static ActionBar actionBar;
    public static RelativeLayout menuView;
    public static RelativeLayout mainView;
    public static DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    public static TextView actiontitle;
    ImageView menu_icon, help_icon, addfriend_icon, friends_icon, message_icon, notification_icon;
    public static ImageView actionbarmenu, back_Btn;
    CircleImageView userProfileImg;
    TextView user;
    SessionManager session;
    User userObj;

    int[] icons = {R.drawable.icon_home,R.drawable.icon_newsfeeds,R.drawable.icon_profile,R.drawable.icon_my_account,R.drawable.icon_my_content,R.drawable.icon_lifts,R.drawable.icon_customize_avatar,R.drawable.icon_edit_profile,R.drawable.icon_account_setting,R.drawable.icon_friends,R.drawable.icon_manage_photos,R.drawable.icon_manage_videos, R.drawable.icon_logout};

    // private ActionBarDrawerToggle mDrawerToggle;
//Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // requestWindowFeature(Window.FEATURE_ACTION_BAR);
        setContentView(R.layout.main);
        session = new SessionManager(this);
        Gson gson = new Gson();
        userObj = gson.fromJson(session.getSession(), User.class);
        mainView = (RelativeLayout) findViewById(R.id.headerView);
        menuView = (RelativeLayout) findViewById(R.id.actionbar_layout);
        menu_icon = (ImageView) findViewById(R.id.menu_icon);
        help_icon = (ImageView) findViewById(R.id.help_icon);
        friends_icon = (ImageView) findViewById(R.id.profile_icon);
        addfriend_icon = (ImageView) findViewById(R.id.addfriend_icon);
        message_icon = (ImageView) findViewById(R.id.message_icon);
        notification_icon = (ImageView) findViewById(R.id.notification_icon);
        // toolbar = (Toolbar) findViewById(R.id.toolbar);
        // setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        View v = getSupportActionBar().getCustomView();
        actionbarmenu = (ImageView) v.findViewById(R.id.menu_icon);
        back_Btn = (ImageView) v.findViewById(R.id.back_icon);
        actiontitle = (TextView) v.findViewById(R.id.titleactionbar);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(R.color.actionbar_color));
        Toolbar parent = (Toolbar) v.getParent();//first get parent toolbar of current action bar
        parent.setContentInsetsAbsolute(0, 0);// set padding programmatically to 0dp
        moveDrawerToTop();
        //initActionBar() ;
        initDrawer();
        actionBar = getSupportActionBar();
        actionBar.hide();
        //Quick cheat: Add Fragment 1 to default view


        menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        });

        actionbarmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        });
        notification_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(null, null, 17, 0);
            }
        });
        message_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(null, null, 14, 0);
            }
        });

        friends_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(null, null, 10, 0);
            }
        });
        addfriend_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(null, null, 15, 0);
            }
        });

      /*  moreOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  mDrawerLayout.openDrawer(mDrawerList);
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
            }
        });*/
        help_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* new Thread(new Runnable() {
                    public void run() {
                        downloadFile();
                    }
                }).start();*/
                onItemClick(null, null, 16, 0);
            }
        });
        onItemClick(null, null, 1, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // mDrawerToggle.syncState();
    }

    private void moveDrawerToTop() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        DrawerLayout drawer = (DrawerLayout) inflater.inflate(R.layout.drawer_layout, null); // "null" is important.
        // HACK: "steal" the first child of decor view
        ViewGroup decor = (ViewGroup) getWindow().getDecorView();
        View child = decor.getChildAt(0);
        decor.removeView(child);
        LinearLayout container = (LinearLayout) drawer.findViewById(R.id.drawer_content); // This is the container we defined just now.
        container.addView(child, 0);
        drawer.findViewById(R.id.drawer).setPadding(0, getStatusBarHeight(), 0, 0);

        // Make the drawer replace the first child
        decor.addView(drawer);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    private int getContentIdResource() {
        return getResources().getIdentifier("content", "id", "android");
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }*/
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
      //  mDrawerToggle.syncState();

        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/

   /* private void initActionBar() {
        actionBar = getSupportActionBar();
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.actionbar_layout, null);
        actionBar.setCustomView(mCustomView);
        actionBar.setDisplayShowCustomEnabled(true);
        //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#330000ff")));
        actionBar.setStackedBackgroundDrawable(new ColorDrawable(Util.getColorWithAlpha(Color.BLACK,0.2f)));
        ImageView imageButton = (ImageView) mCustomView
                .findViewById(R.id.menu_icon);
        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                mDrawerLayout.openDrawer(mDrawerList);
            }
        });
        *//*actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);*//*
    }*/

    private void initDrawer() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.drawer);
        View headerlayout = View.inflate(DashBoardActivity.this, R.layout.menulist_header, null);
        mDrawerList.addHeaderView(headerlayout, null, false);
        userProfileImg = (CircleImageView) headerlayout.findViewById(R.id.profileImg);
        user = (TextView) headerlayout.findViewById(R.id.user);
        Glide.with(this).load(userObj.getFullImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(userProfileImg);
        user.setText(userObj.getFirstName() + "" + userObj.getLastName());
        headerlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClick(null,null,0,0);
            }
        });
        mDrawerList.setAdapter(new MenuListAdapter(this, getResources().getStringArray(R.array.nav_items), icons));
        mDrawerList.setOnItemClickListener(this);
    }

  /*  private DrawerListener createDrawerToggle() {
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerStateChanged(int state) {
            }
        };
        return mDrawerToggle;
    }*/

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        mDrawerLayout.closeDrawer(mDrawerList);
        Log.i(TAG, "pos=" + position);
        clearBackStack();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction ftx = fragmentManager.beginTransaction();
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        bundle.putInt("from", 0);
        if (position == 0) {
            fragment = new ProfileFragment();
            bundle.putString("userid",userObj.getUserId());
            fragment.setArguments(bundle);
        } else if (position == 1) {
            DashBoardActivity.actionBar.hide();
            DashBoardActivity.menuView.setVisibility(View.VISIBLE);
            DashBoardActivity.mainView.setBackgroundResource(R.drawable.dash_bg);
            back_Btn.setVisibility(View.GONE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            actionbarmenu.setVisibility(View.VISIBLE);
            fragment = new FragmentHome();
        } else if (position == 2) {
            fragment=new NewsFeedFragment();
            fragment.setArguments(bundle);
        } else if (position == 3) {
            fragment = new ProfileFragment();
            bundle.putString("userid",userObj.getUserId());
            fragment.setArguments(bundle);
        } else if (position == 4) {
            fragment=new ArticleFragment();
            fragment.setArguments(bundle);
        } else if (position == 5) {
            fragment=new RecipesFragment();
            fragment.setArguments(bundle);
        } else if (position == 6) {
          //  fragment=new Lif();
            //fragment.setArguments(bundle);
        } else if (position == 7) {
         fragment=new CustomizeAvatarFragment();
        } else if (position == 8) {
            editProfile();

        } else if (position == 9) {

        } else if (position == 10) {
            fragment = new FriendsFragment();
        } else if (position == 11) {
            fragment=new PhotoFragment();
            fragment.setArguments(bundle);
        }
        else if (position == 12) {
            fragment = new UserVideoFragment();
            fragment.setArguments(bundle);
        }else if (position == 13) {
            logout();
            /**/
        } else if (position == 14) {
            fragment = new MessageFragment();
            fragment.setArguments(bundle);
        } else if (position == 15) {
            fragment = new FriendRequestFragment();
            fragment.setArguments(bundle);
        }
        else if(position==16){
            fragment=new HelpFragment();
        }
        else if(position==17){
            fragment = new NotificationFragment();
            fragment.setArguments(bundle);
        }

        if(position!=8||position!=13) {
            replaceFragment(fragment);
            ftx.commit();
        }
      /*  if(position==13){

        }
        else*/

    }


    public Animation setLayoutAnim_slideup() {

        AnimationSet set = new AnimationSet(true);

        Animation animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, -1.0f);
        animation.setDuration(800);
        animation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // TODO Auto-generated method stub
               /* RelativeLayout bodyView = (RelativeLayout)findViewById(R.id.bodyView);
                RelativeLayout myView = (RelativeLayout)findViewById(R.id.my_view);
                addListingView.clearAnimation();
                bodyView.removeView(myView);*/
            }
        });
        set.addAnimation(animation);

        LayoutAnimationController controller = new LayoutAnimationController(
                set, 0.25f);

        return animation;

    }

    private void clearBackStack() {
        final FragmentManager fragmentManager = getSupportFragmentManager();
        while (fragmentManager.getBackStackEntryCount() != 0) {
            fragmentManager.popBackStackImmediate();
        }
    }

    private void replaceFragment(Fragment fragment) {
        if (fragment != null) {
            String backStateName = fragment.getClass().getName();
            FragmentManager manager = getSupportFragmentManager();
            boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
            if (!fragmentPopped) { //fragment not in back stack, create it.
                FragmentTransaction ft = manager.beginTransaction();
                ft.replace(R.id.contentframe, fragment);
                ft.addToBackStack(backStateName);
                ft.commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() == 1) {
            Log.i("Last", "backstack=" + manager.getBackStackEntryAt(0).getName());
            Log.i("Real", "class frag=" + FragmentHome.class.getName());
            if (manager.getBackStackEntryAt(0).getName().equalsIgnoreCase(FragmentHome.class.getName())) {
                finish();
            } else {
                onItemClick(null, null, 1, 0);

            }
        } else {
            DashBoardActivity.actionBar.hide();
            DashBoardActivity.menuView.setVisibility(View.VISIBLE);
            DashBoardActivity.mainView.setBackgroundResource(R.drawable.dash_bg);
            mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            back_Btn.setVisibility(View.GONE);
            actionbarmenu.setVisibility(View.VISIBLE);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            super.onBackPressed();
        }
    }
   // IconGenerator icon=new Icong
    String dwnload_file_path = "http://coderzheaven.com/sample_folder/sample_file.png";
    void downloadFile(){
        try {
            URL url = new URL(dwnload_file_path);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(true);

            //connect
            urlConnection.connect();

            //set the path where we want to save the file
            File SDCardRoot = Environment.getExternalStorageDirectory();
            //create a new file, to save the downloaded file
            File fname=new File(SDCardRoot,getResources().getString(R.string.app_name));
            if(!fname.exists()){
                fname.mkdir();
            }
            File finner=new File(fname,"Album");
            if(!finner.exists()){
                finner.mkdir();
            }
            File file = new File(finner,"harryCode.png");

            FileOutputStream fileOutput = new FileOutputStream(file);

            //Stream used for reading the data from the internet
            InputStream inputStream = urlConnection.getInputStream();

            //this is the total size of the file which we are downloading
            //totalSize = urlConnection.getContentLength();

            runOnUiThread(new Runnable() {
                public void run() {
                  //  pb.setMax(totalSize);
                }
            });

            //create a buffer...
            byte[] buffer = new byte[1024];
            int bufferLength = 0;

            while ( (bufferLength = inputStream.read(buffer)) > 0 ) {
                fileOutput.write(buffer, 0, bufferLength);
               // downloadedSize += bufferLength;
                // update the progressbar //
                runOnUiThread(new Runnable() {
                    public void run() {
                       // pb.setProgress(downloadedSize);
                       // float per = ((float)downloadedSize/totalSize) * 100;
                       // cur_val.setText("Downloaded " + downloadedSize + "KB / " + totalSize + "KB (" + (int)per + "%)" );
                    }
                });
            }
            //close the output stream when complete //
            fileOutput.close();
            runOnUiThread(new Runnable() {
                public void run() {
                    // pb.dismiss(); // if you want close it..
                }
            });

        } catch (final MalformedURLException e) {
            showError("Error : MalformedURLException " + e);
            e.printStackTrace();
        } catch (final IOException e) {
            showError("Error : IOException " + e);
            e.printStackTrace();
        }
        catch (final Exception e) {
            showError("Error : Please check your internet connection " + e);
        }
    }

    void showError(final String err){
        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(DashBoardActivity.this, err, Toast.LENGTH_LONG).show();
            }
        });
    }
    private void editProfile(){
        Intent it=new Intent(DashBoardActivity.this, EditProfileActivity.class);
        startActivity(it);

    }
    private void logout(){
        session.logoutUser();
       /* Intent it=new Intent(DashBoardActivity.this, LoginActivity.class);
        startActivity(it);*/
        finish();

    }

}