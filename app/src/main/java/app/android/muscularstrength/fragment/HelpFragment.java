package app.android.muscularstrength.fragment;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import app.android.muscularstrength.R;
import app.android.muscularstrength.Util.Util;
import app.android.muscularstrength.activity.DashBoardActivity;
import app.android.muscularstrength.activity.YouTubePlayerActivity;
import app.android.muscularstrength.adapter.HelpAdapter;
import app.android.muscularstrength.custom.HorizontalListView;
import app.android.muscularstrength.model.HelpData;
import app.android.muscularstrength.model.HelpParser;
import app.android.muscularstrength.model.User;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.session.SessionManager;
import app.android.muscularstrength.webservice.WebServices;

/**
 * Created by laxman singh on 11/4/2015.
 */
public class HelpFragment extends Fragment {
    View rootView;
    ProgressDialog pDialog;
    SessionManager session;
    User userObj;
    String errorMessage;
    List<HelpData> datahelp;
    HorizontalListView list_help;
    ImageView help_back, help_next;
    HelpAdapter adapter;
    DisplayMetrics display;
    int height, width;
    String wishtoContact, fname, email, issue;
    Spinner sp_contactto;
    EditText edit_fname, edit_email, edit_issue;
    Button submitBtn;
    String contact_options[]={"Customer Service","Affiliates","Scott Herman's Management","Skype Consultations"};

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity.mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("HELP");
        rootView = inflater.inflate(R.layout.help_fragment, container, false);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("loading...");
        session = new SessionManager(getActivity());
        Gson gson = new Gson();
        list_help = (HorizontalListView) rootView.findViewById(R.id.list_help);
        help_back = (ImageView) rootView.findViewById(R.id.help_back);
        help_next = (ImageView) rootView.findViewById(R.id.help_next);
        sp_contactto = (Spinner) rootView.findViewById(R.id.sp_contactto);
        edit_fname = (EditText) rootView.findViewById(R.id.name);
        edit_email = (EditText) rootView.findViewById(R.id.email);
        edit_issue = (EditText) rootView.findViewById(R.id.issue);
        submitBtn = (Button) rootView.findViewById(R.id.submitBtn);
        // list_help.setOnTouchListener(new OnTouch());
        userObj = gson.fromJson(session.getSession(), User.class);
        adapter = new HelpAdapter(getActivity());
        list_help.setAdapter(adapter);
        display = Util.getDisplay(getActivity());
        width = display.widthPixels;
        final ArrayAdapter adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.myspinner_style, contact_options);
        adapter1.setDropDownViewResource(R.layout.myspinner_style);
        sp_contactto.setAdapter(adapter1);
        sp_contactto.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                wishtoContact = contact_options[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
       // help_back.setOnClickListener(listnerLeftArrowButton);
       // help_next.setOnClickListener(listnerRightArrowButton);
        list_help.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
        help_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final  int vp=list_help.getLastVisiblePosition()+1;
                Log.i("VP", "vp=" + vp);
              //  list_help.setSelection();
                list_help.clearFocus();
                list_help.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list_help.setSelection(vp-1);
                        //adapter.notifyDataSetChanged();
                    }
                }, 200);



            }
        });
        help_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               final int vp=list_help.getLastVisiblePosition()-1;
                Log.i("VP","vp="+vp);
               // list_help.setSelection(vp - 1);
                list_help.clearFocus();
                list_help.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        list_help.setSelection(vp -1);
                        //adapter.notifyDataSetChanged();
                    }
                }, 200);



            }
        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fname = edit_fname.getText().toString().trim();
                email = edit_email.getText().toString().trim();
                issue = edit_issue.getText().toString().trim();
                if (fname.length() > 0) {
                    if (email.length() > 0) {
                        if (issue.length() > 0) {
                            postContact();
                        } else {
                            edit_issue.setError("Enter issue here");
                        }

                    } else {
                        edit_email.setError("Enter email");
                    }

                } else {
                    edit_fname.setError("Enter Name");
                }
            }
        });
        list_help.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent ii = new Intent(getActivity(), YouTubePlayerActivity.class);
                HelpData data=adapter.getItem(position);
                ii.putExtra("videoUrl", "" + data.getVideoLink());
                startActivity(ii);
            }
        });
        getHelp();
        return rootView;
    }

    //get articles
    private void getHelp() {
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("userid", "" + userObj.getUserId());
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.help, "GET", params);
                try {
                    if (json != null) {
                        if (json.getString("result").equalsIgnoreCase("SUCCESS")) {
                            Gson gson = new Gson();
                            HelpParser data = gson.fromJson(json.toString(), HelpParser.class);
                            // if (data.getResult().equalsIgnoreCase("SUCCESS")) {
                            datahelp = new ArrayList<HelpData>();
                            datahelp.addAll(data.getData().getWalkThrough());
                            mainHandler.sendMessage(mainHandler.obtainMessage(1));
                       /* } else {

                        }*/
                        } else {
                            mainHandler.sendMessage(mainHandler.obtainMessage(0));
                        }
                    } else {
                        errorMessage = getResources().getString(R.string.errorMessage);
                        mainHandler.sendMessage(mainHandler.obtainMessage(0));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
                            setListAdapter();
                            break;
                        case 2:
                            break;
                        default:
                            break;
                    }
                }
            } catch (Resources.NotFoundException e) {

            }
        }
    };

    private void setListAdapter() {
        for (int i = 0; i < datahelp.size(); i++) {
            adapter.add(datahelp.get(i));
        }
        // Toast.makeText(getActivity(),"COUNT A="+adapter.getCount(),Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();

    }

    private class OnTouch implements View.OnTouchListener {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            return true;
        }
    }

    private void postContact() {
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("wish_to_contact", "" + wishtoContact);
                params.put("fname", "" + fname);
                params.put("email", "" + email);
                params.put("issue", "" + issue);
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.contact, "POST", params);
                try {
                    if (json != null) {
                        if (json.getString("result").equalsIgnoreCase("SUCCESS")) {
                           /* Gson gson = new Gson();
                            HelpParser data = gson.fromJson(json.toString(), HelpParser.class);
                            // if (data.getResult().equalsIgnoreCase("SUCCESS")) {
                            datahelp = new ArrayList<HelpData>();
                            datahelp.addAll(data.getData().getWalkThrough());*/
                            mainHandler.sendMessage(mainHandler.obtainMessage(2));
                       /* } else {

                        }*/
                        } else {
                            mainHandler.sendMessage(mainHandler.obtainMessage(0));
                        }
                    } else {
                        errorMessage = getResources().getString(R.string.errorMessage);
                        mainHandler.sendMessage(mainHandler.obtainMessage(0));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    private View.OnTouchListener listenerScrollViewTouch = new View.OnTouchListener() {

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            showHideViews();
            return false;
        }
    };

    private View.OnClickListener listnerLeftArrowButton = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            list_help.onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, new KeyEvent(0, 0));
        }
    };
    private View.OnClickListener listnerRightArrowButton = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            list_help.onKeyDown(KeyEvent.KEYCODE_DPAD_LEFT, new KeyEvent(0, 0));
        }
    };


    public  void showHideViews() {
        list_help.getFirstVisiblePosition();
        int maxScrollX = list_help.getChildAt(0).getMeasuredWidth()- width;
        Log.e("TestProjectActivity", "scroll X = " +list_help.getScrollX() );
        Log.i("TestProjectActivity", "scroll Width = " +list_help.getMeasuredWidth() );
        Log.d("TestProjectActivity", "Max scroll X = " + maxScrollX);

        if (list_help.getScrollX() == 0) {
            hideLeftArrow();
        } else {
            showLeftArrow();
        }
        if (list_help.getScrollX() == maxScrollX) {
            showRightArrow();
        } else {
            //hideRightArrow();
        }
    }

    private  void hideLeftArrow() {
        help_back.setVisibility(View.GONE);
    }

    private  void showLeftArrow() {
        help_back.setVisibility(View.VISIBLE);
    }

    private  void hideRightArrow() {
        help_next.setVisibility(View.GONE);
    }

    private  void showRightArrow() {
        help_next.setVisibility(View.VISIBLE);
    }
}
