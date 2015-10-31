package app.android.muscularstrength.fragment;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import app.android.muscularstrength.R;
import app.android.muscularstrength.Util.Constants;
import app.android.muscularstrength.Util.Util;
import app.android.muscularstrength.activity.DashBoardActivity;
import app.android.muscularstrength.adapter.ForumAdapter;
import app.android.muscularstrength.model.Forum;
import app.android.muscularstrength.model.ForumParser;
import app.android.muscularstrength.model.Forum_;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.webservice.WebServices;

/**
 * Created by logan on 13/7/15.
 */
public class ForumsFragment extends Fragment implements View.OnClickListener {
    View rootView;
    int from;
    int viewPost;
    private int pYear;
    private int pMonth;
    private int pDay;
    Date default_date;
    Date selected_date;
    String post_date;
    float density;
    EditText search_forum;
    ExpandableListView list_forum;
    ForumAdapter adapter;
    ArrayList<Forum> dataforum;
    private int page_no = 1;
    ProgressDialog pDialog;
    TextView my_post, todays_post, past_post;
    DatePickerDialog datePickerDialog;
    boolean isSearch=false;
    String quary;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity.mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("FORUM");
        if(rootView==null) {
            rootView = inflater.inflate(R.layout.forum_fragment, container, false);
            // getActivity().getActionBar().show();

            list_forum = (ExpandableListView) rootView.findViewById(R.id.list_forum);

            search_forum = (EditText) rootView.findViewById(R.id.forum_search);
            my_post = (TextView) rootView.findViewById(R.id.my_post);
            todays_post = (TextView) rootView.findViewById(R.id.todays_post);
            past_post = (TextView) rootView.findViewById(R.id.past_post);
            my_post.setOnClickListener(this);
            todays_post.setOnClickListener(this);
            past_post.setOnClickListener(this);

            //set drwavle right
            density = Util.getDensity(getActivity());
            final Drawable drawable = ContextCompat.getDrawable(getActivity(), R.drawable.forum_calendar_icon);
            drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * 0.5),
                    (int) (drawable.getIntrinsicHeight() * 0.5));
            ScaleDrawable sd = new ScaleDrawable(drawable, 0, (int) (30 * density), (int) (30 * density));
            past_post.setCompoundDrawables(null, null, sd.getDrawable(), null);
            //past_post.setco
            dataforum = new ArrayList<Forum>();
            adapter = new ForumAdapter(getActivity(), dataforum);
            list_forum.setAdapter(adapter);
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("loading...");
            getCurrentDate();

            // DashBoardActivity. mainView.setBackgroundColor(getResources().getColor(R.color.tansparent));
            Bundle args = getArguments();
            from = args.getInt("from");
       /* rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == event.ACTION_UP
                        && keyCode == KeyEvent.KEYCODE_BACK) {
                    if (from == 1) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.contentframe, new DashBoardFragment());
                        ft.commit();
                    } else {
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                }
                return true;
            }
        });*/

            getForum(page_no);
        }
        list_forum.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                // Doing nothing
                return true;
            }
        });
        list_forum.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Forum_ data = dataforum.get(groupPosition).getForum().get(childPosition);
                showSubForum(data.getId());
                return false;
            }
        });


        final Drawable x = ContextCompat.getDrawable(getActivity(), android.R.drawable.ic_menu_search);
        x.setBounds(0, 0, x.getIntrinsicWidth(), x.getIntrinsicHeight());
        search_forum.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        setEditTextFocus(true);
                        if (search_forum.getCompoundDrawables()[2] == null) {
                            return false;
                        }
                        if (event.getAction() != MotionEvent.ACTION_UP) {
                            return false;
                        }
                        if (event.getX() > search_forum.getWidth() - search_forum.getPaddingRight() - x.getIntrinsicWidth()) {
                            // search_article.setCompoundDrawables(null, null, null, null);
                            quary = search_forum.getText().toString().trim();
                            setEditTextFocus(false);
                            getSearchforum(quary);

                        }
                        return false;
                    }
                });
        search_forum.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(search_forum, InputMethodManager.SHOW_FORCED);
                } else {
                    ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(search_forum.getWindowToken(), 0);
                }
            }
        });
        return rootView;
    }
    public void setEditTextFocus(boolean isFocused) {
        search_forum.setCursorVisible(isFocused);
        search_forum.setFocusable(isFocused);
        search_forum.setFocusableInTouchMode(isFocused);

        if (isFocused) {
            search_forum.requestFocus();
        }
    }

    //get current date
    private void getCurrentDate() {
        /** Get the current date */
        Calendar cal = Calendar.getInstance();
        pYear = cal.get(Calendar.YEAR);
        pMonth = cal.get(Calendar.MONTH);
        //cal.add(Calendar.DATE, 1);
        pDay = cal.get(Calendar.DAY_OF_MONTH);
        String strdate = new StringBuilder()
                .append(pDay).append("-")
                .append(pMonth + 1).append("-")
                .append(pYear).toString();

        default_date = Util.strtodate(strdate);
        Log.d("FORUM", "CURRENT DATE=" + default_date);
    }

    //get articles
    private void getForum(final int page) {
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
             /*   params.put("page",""+page);
                params.put("display","15");*/
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.Forums, "GET", params);
                try {
                    if(json.getString("result").equalsIgnoreCase("SUCCESS")) {
                        Gson gson = new Gson();
                        ForumParser data = gson.fromJson(json.toString(), ForumParser.class);
                       // if (data.getResult().equalsIgnoreCase("SUCCESS")) {

                            dataforum.addAll(data.getForums());
                            mainHandler.sendMessage(mainHandler.obtainMessage(1));
                       /* } else {
                            mainHandler.sendMessage(mainHandler.obtainMessage(0));
                        }*/
                    } else {
                        mainHandler.sendMessage(mainHandler.obtainMessage(0));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //search Forum
    private void getSearchforum(final String quary) {
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("search", quary);
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.Forums, "GET", params);
                try {
                    if (json.getString("result").equalsIgnoreCase("SUCCESS")) {
                        Gson gson = new Gson();
                        ForumParser data = gson.fromJson(json.toString(), ForumParser.class);
                        // if (data.getResult().equalsIgnoreCase("SUCCESS")) {
                        // dataforum=new ArrayList<Article>();
                        dataforum.clear();
                        dataforum.addAll(data.getForums());
                        mainHandler.sendMessage(mainHandler.obtainMessage(2));

                    } else {
                        mainHandler.sendMessage(mainHandler.obtainMessage(0));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            }).start();
    }

    private void setListAdapter() {
        adapter.notifyDataSetChanged();
        list_forum.setSelection(0);

    }

    private Handler mainHandler = new Handler() {
        public void handleMessage(Message message) {
            try {

                if (isAdded()) {
                    pDialog.dismiss();
                    pDialog.cancel();
                    switch (message.what) {
                        case 0:
                            break;
                        case 1:
                            isSearch=false;
                            search_forum.setText("");
                            setListAdapter();
                            break;
                        case 2:
                            isSearch=true;
                            setListAdapter();
                            break;
                    }
                }
            } catch (Resources.NotFoundException e) {

            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.my_post:
                viewPost = Constants.MYPOSTS;
                getSelectedPosts();
                break;
            case R.id.todays_post:
                viewPost = Constants.TODAYS;
                getSelectedPosts();
                break;
            case R.id.past_post:
                viewPost = Constants.PASTPOSTS;
                showDatePicker();
                break;
            default:
                break;
        }
    }

    private void getSelectedPosts() {
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
                if (viewPost == Constants.MYPOSTS) {
                    params.put("search", "mypost");
                    params.put("user_id", "142338");
                } else if (viewPost == Constants.TODAYS) {
                    params.put("search", "today");
                } else if (viewPost == Constants.PASTPOSTS) {
                    params.put("search", "");
                    params.put("date", post_date);
                }
               /* params.put("page",""+page);
                params.put("display","15");
                params.put("search",quary);*/
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.Forums, "GET", params);
                Gson gson = new Gson();
                ForumParser data = gson.fromJson(json.toString(), ForumParser.class);
                if (data.getResult().equalsIgnoreCase("SUCCESS")) {
                    //  dataForumPast=new ArrayList<ForumPast>();
                    dataforum.clear();
                    dataforum.addAll(data.getForums());
                    mainHandler.sendMessage(mainHandler.obtainMessage(1));
                } else {
                    mainHandler.sendMessage(mainHandler.obtainMessage(0));
                }
            }
        }).start();
    }

    private void showDatePicker() {
        new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        pYear = year;
                        pMonth = monthOfYear;
                        pDay = dayOfMonth;
                        String strdate = new StringBuilder()
                                .append(pDay).append("-")
                                .append(pMonth + 1).append("-")
                                .append(pYear).toString();
                        selected_date = Util.strtodate(strdate);
                        post_date = new StringBuilder().append(pDay).append("-").append(Constants.months[pMonth]).append("-").append(pYear).toString();
                        Log.d("FORUM", "Selected DATE=" + selected_date);
                        if (selected_date.after(default_date)) {
                            Toast.makeText(getActivity(), "Select past date", Toast.LENGTH_SHORT).show();
                        } else {
                            getSelectedPosts();
                        }


                    }
                },
                pYear, pMonth, pDay).show();
    }
  /*  DatePickerDialog pickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
    pickerDialog.getDatePicker().setMaxDate(maxDate);
    pickerDialog.getDatePicker().setMinDate(minDate);*/
   // return pickerDialog;

    private void showSubForum(String id) {
     /*   SubForumFragment kBDetailFragment = new SubForumFragment();
        Bundle args = new Bundle();
        args.putString("subForumID", id);
        kBDetailFragment.setArguments(args);
        FragmentManager fm = getActivity().getSupportFragmentManager();
        //fm.beginTransaction().detach(this).commit();
        fm.beginTransaction()
                .replace(DashBoardFragment.frame.getId(), kBDetailFragment, "subForumfragment")
                .addToBackStack(null)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();*/
        //new code
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("subForumID", id);
        Fragment fragment=new SubForumFragment();
        fragment.setArguments(bundle);
        replaceFragment(fragment);
        ft.commit();

    }
    private void replaceFragment (Fragment fragment){
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);
        if (!fragmentPopped){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.contentframe, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }
}
