package app.android.muscularstrength.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import app.android.muscularstrength.R;
import app.android.muscularstrength.model.Childcomment;
import app.android.muscularstrength.model.Newsfeed;
import app.android.muscularstrength.model.User;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.session.SessionManager;
import app.android.muscularstrength.webservice.WebServices;

/**
 * Created by sa on 8/21/2015.
 */
public class NewsFeedAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private ArrayList<Newsfeed> data_newsfeed;
    String msg;
    SessionManager session;
    User userObj;
    public NewsFeedAdapter(Context context, ArrayList<Newsfeed> data_newsfeed) {
        this._context = context;
        this.data_newsfeed = data_newsfeed;
        session=new SessionManager(context);
        Gson gson=new Gson();
        userObj=gson.fromJson(session.getSession(),User.class);
    }
    @Override
    public int getGroupCount() {
        return data_newsfeed.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(data_newsfeed.get(groupPosition).getChildcomment()==null){
            return 0;
        }
        else {
            return data_newsfeed.get(groupPosition).getChildcomment().size();
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data_newsfeed.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data_newsfeed.get(groupPosition).getChildcomment().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, final ViewGroup parent) {
      final  Newsfeed headerInfo = (Newsfeed) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.newsfeed_parent, null);
        }
        ImageView userimg=(ImageView)convertView.findViewById(R.id.userimg);
        ImageView indicator=(ImageView)convertView.findViewById(R.id.indicator);
        indicator.setVisibility(View.GONE);
        TextView heading = (TextView) convertView.findViewById(R.id.text_name);
        heading.setText(headerInfo.getName());
        TextView textcontent = (TextView) convertView.findViewById(R.id.text_content);
        textcontent.setText(Html.fromHtml(headerInfo.getComment()));
        TextView textlike = (TextView) convertView.findViewById(R.id.likeTxt);
        TextView textreply = (TextView) convertView.findViewById(R.id.text_reply);
        TextView timeago = (TextView) convertView.findViewById(R.id.time_ago);
        timeago.setText(headerInfo.getTime());
        Glide.with(_context).load(headerInfo.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(userimg);
        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);
       // Util.setListViewHeight(mExpandableListView,groupPosition);
        if (isExpanded) {

            indicator.setImageResource( R.drawable.nav_up);
        } else {
            indicator.setImageResource(R.drawable.nav_down);
        }
        textlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hitLike(headerInfo.getId());
            }
        });
        textreply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentAlert(headerInfo.getId());
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
       final  Childcomment childinfo = (Childcomment) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.newsfeed_parent, null);
        }
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((int)_context.getResources().getDimension(R.dimen._50sdp), (int)_context.getResources().getDimension(R.dimen._50sdp));
        lp.setMargins((int) _context.getResources().getDimension(R.dimen._50sdp), (int) _context.getResources().getDimension(R.dimen._50sdp), 0, 0);
        ImageView userimg=(ImageView)convertView.findViewById(R.id.userimg);
        userimg.setLayoutParams(lp);
        ImageView indicator=(ImageView)convertView.findViewById(R.id.indicator);
        indicator.setVisibility(View.GONE);
        TextView heading = (TextView) convertView.findViewById(R.id.text_name);
        heading.setText(childinfo.getName());
        TextView textcontent = (TextView) convertView.findViewById(R.id.text_content);
        textcontent.setText(Html.fromHtml(childinfo.getComment()));
        TextView textlike = (TextView) convertView.findViewById(R.id.likeTxt);
        TextView textreply = (TextView) convertView.findViewById(R.id.text_reply);
        TextView timeago = (TextView) convertView.findViewById(R.id.time_ago);
        timeago.setText(childinfo.getTime());
        Glide.with(_context).load(childinfo.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(userimg);
       /* ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);
        Util.setListViewHeight(mExpandableListView,groupPosition);*/
        textlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hitLike(childinfo.getId());
            }
        });
        textreply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentAlert(childinfo.getId());
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
    //get articles
    private void hitLike(final String postid) {
       // pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("userid", "" + userObj.getUserId());
                  params.put("id",postid);
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.newsFeedlike, "GET", params);
                try {
                    if (json.getString("result").equalsIgnoreCase("SUCCESS")) {
                       // datanewsFeed.addAll(data.getData().getNewsfeed());
                        msg=json.getString("data");
                        mainHandler.sendMessage(mainHandler.obtainMessage(1));
                    } else {
                        mainHandler.sendMessage(mainHandler.obtainMessage(0));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private void replyPost(final String postid,final String comment) {
        // pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("userid", "" + userObj.getUserId());
                params.put("id",postid);
                params.put("postowner","147430");
                params.put("comment",comment);
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.newsFeedReply, "GET", params);
                try {
                    if (json.getString("result").equalsIgnoreCase("SUCCESS")) {
                        // datanewsFeed.addAll(data.getData().getNewsfeed());
                        msg=json.getString("data");
                        mainHandler.sendMessage(mainHandler.obtainMessage(1));
                    } else {
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

               // if (_context.isAdded()) {
                  //  pDialog.dismiss();
                    //pDialog.cancel();
                    switch (message.what) {
                        case 0:
                            break;
                        case 1:
                            Toast.makeText(_context,msg,Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            // isSearch=true;
                           // setListAdapter();
                            break;
                    }
                //}
            } catch (Resources.NotFoundException e) {

            }
        }
    };
    public void showCommentAlert(final String postid) {
        final Dialog dialog = new Dialog(_context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.comment_box);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        wlp.gravity = Gravity.BOTTOM;
        window.setAttributes(wlp);
        dialog.show();
        final EditText comment_txt = (EditText) dialog
                .findViewById(R.id.comment_txt);
        ImageView add_comment = (ImageView) dialog
                .findViewById(R.id.add_comment);
        add_comment.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (comment_txt.getText().toString().length() != 0) {
                    dialog.dismiss();
                  //  new SendComment().execute(comment_txt.getText().toString());
                    replyPost(postid,comment_txt.getText().toString().trim());

                } else {
                    comment_txt.setError(Html
                            .fromHtml("<font color='#ff0000'> Please Add Comment.</font>"));
                }
            }
        });
    }


}
