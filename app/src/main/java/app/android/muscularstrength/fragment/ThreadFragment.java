package app.android.muscularstrength.fragment;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.Thread;
import java.util.ArrayList;
import java.util.HashMap;

import app.android.muscularstrength.R;
import app.android.muscularstrength.activity.DashBoardActivity;
import app.android.muscularstrength.adapter.ThreadAdapter;
import app.android.muscularstrength.model.*;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.session.SessionManager;
import app.android.muscularstrength.webservice.WebServices;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sa on 8/12/2015.
 */
public class ThreadFragment extends Fragment {
    View rootView;
    app.android.muscularstrength.model.Thread datath;
    ListView list_thread;
   // TextView txtheading,txtcategories;
    //String heading,category;
    ThreadAdapter adapter;
    ArrayList<Post> datathread;
    private int page_no=1;
    ProgressDialog pDialog;
    String errorMessage;
    CircleImageView userimg;
    TextView text_user,text_time,text_preview;
    ImageView replyBtn,replyQuoteBtn;
    String msg;
    SessionManager session;
    User userObj;
    TextView nodatatxt;
    View threadView;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.thread_fragment, container, false);
        // getActivity().getActionBar().show();

        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity. mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("DISCUSSION BOARD");
        DashBoardActivity.actionbarmenu.setVisibility(View.GONE);
        DashBoardActivity.back_Btn.setVisibility(View.VISIBLE);
        session = new SessionManager(getActivity());
        Gson gson = new Gson();
        userObj = gson.fromJson(session.getSession(), User.class);
        //DashBoardActivity.mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        list_thread=(ListView)rootView.findViewById(R.id.list_thread);
        userimg = (CircleImageView)rootView.findViewById(R.id.userimg);
        threadView=(View)rootView.findViewById(R.id.threadView);
        nodatatxt= (TextView) rootView.findViewById(R.id.nodataTxt);
        text_user = (TextView)rootView.findViewById(R.id.text_user);
       text_time = (TextView)rootView.findViewById(R.id.text_time);
       text_preview = (TextView)rootView.findViewById(R.id.text_preview);
        replyBtn = (ImageView)rootView.findViewById(R.id.replyBtn);
        replyQuoteBtn = (ImageView)rootView.findViewById(R.id.replyQuoteBtn);
        Bundle args = getArguments();
        datath=args.getParcelable("threadBundle");
        Log.i("threadID=", "" + datath.getId());
        Glide.with(getActivity()).load(datath.getUserImage()).into(userimg);
        text_user.setText(datath.getPostBy());
        text_time.setText(datath.getTime());
        text_preview.setText(Html.fromHtml(datath.getDescription()));

        adapter=new ThreadAdapter(getActivity(),datath.getId());
        list_thread.setAdapter(adapter);
        pDialog=new ProgressDialog(getActivity());
        pDialog.setMessage("loading...");

        // DashBoardActivity. mainView.setBackgroundColor(getResources().getColor(R.color.tansparent));


        DashBoardActivity.back_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashBoardActivity.actionbarmenu.setVisibility(View.VISIBLE);
                DashBoardActivity.back_Btn.setVisibility(View.GONE);
                getActivity().getSupportFragmentManager().popBackStack();
               // DashBoardActivity.mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
            }
        });
        getThreads(datath.getId());
        replyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentAlert();
            }
        });
        replyQuoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentAlert();
            }
        });
        return  rootView;
    }

    //get articles
    private void getThreads(final String threadID){
        pDialog.show();
        new java.lang.Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String,String> params=new HashMap<String, String>();
                params.put("threadID",threadID);
                JSONParser parser = new JSONParser();
                JSONObject json=parser.makeHttpRequest(WebServices.Forums,"GET",params);
                try {
                    if(json!=null){
                    if(json.getString("result").equalsIgnoreCase("SUCCESS")){
                    Gson gson = new Gson();
                    ThreadParser data=gson.fromJson(json.toString(),ThreadParser.class);
                   // if(data.getResult().equalsIgnoreCase("SUCCESS")){
                        datathread=new ArrayList<Post>();
                        //System.out.println("SIZE="+data.getForum().size());
                        datathread.addAll(data.getPosts());
                       // heading=data.getForum().get(0).getHeading();
                       // category=data.getForum().get(0).getCategories();
                        mainHandler.sendMessage(mainHandler.obtainMessage(1));
                    }
                    else{
                        errorMessage=json.getJSONObject("posts").getString("data");
                        mainHandler.sendMessage(mainHandler.obtainMessage(3));
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


    private void setListAdapter(){
        //txtheading.setText(heading);
       // txtcategories.setText(category);
        for(int i=0;i<datathread.size();i++){
            adapter.add(datathread.get(i));
        }
        adapter.notifyDataSetChanged();

    }

    private Handler mainHandler = new Handler() {
        public void handleMessage(android.os.Message message) {
            try {

                if (isAdded()) {
                    pDialog.dismiss();
                    pDialog.cancel();
                    list_thread.setVisibility(View.VISIBLE);
                    nodatatxt.setVisibility(View.GONE);
                    threadView.setVisibility(View.VISIBLE);
                    switch (message.what) {
                        case 0:
                            Toast.makeText(getActivity(), "" + errorMessage, Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            setListAdapter();
                            break;
                        case 2:
                            Toast.makeText(getActivity(), "" + errorMessage, Toast.LENGTH_SHORT).show();
                            break;
                        case 3:
                            list_thread.setVisibility(View.GONE);
                            nodatatxt.setVisibility(View.VISIBLE);
                            threadView.setVisibility(View.GONE);
                            nodatatxt.setText(errorMessage);
                            break;
                        default:
                            break;
                    }
                }
            } catch (Resources.NotFoundException e) {

            }
        }
    };
    public void showCommentAlert() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.comment_box);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);
        dialog.show();
        final EditText comment_txt = (EditText) dialog
                .findViewById(R.id.comment_txt);
        ImageView close = (ImageView) dialog
                .findViewById(R.id.close);
        Button submit=(Button)dialog.findViewById(R.id.add_comment);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (comment_txt.getText().toString().length() != 0) {
                    dialog.dismiss();
                    //  new SendComment().execute(comment_txt.getText().toString());
                    replyPost(datath.getId(), comment_txt.getText().toString().trim(),datath.getId());

                } else {
                    comment_txt.setError(Html
                            .fromHtml("<font color='#ff0000'> Please Add Comment.</font>"));
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
    }
    private void replyPost(final String postid, final String comment,final String postwoner) {
        // pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("userid", "" + userObj.getUserId());
                params.put("id", postid);
                params.put("postowner", postwoner);
                params.put("comment", comment);
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.newsFeedReply, "GET", params);
                try {
                    if (json != null) {
                        if (json.getString("result").equalsIgnoreCase("SUCCESS")) {
                            // datanewsFeed.addAll(data.getData().getNewsfeed());
                            msg = json.getString("data");
                            mainHandler.sendMessage(mainHandler.obtainMessage(2));
                        } else {
                            mainHandler.sendMessage(mainHandler.obtainMessage(0));
                        }
                    } else {
                        mainHandler.sendMessage(mainHandler.obtainMessage(0));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
