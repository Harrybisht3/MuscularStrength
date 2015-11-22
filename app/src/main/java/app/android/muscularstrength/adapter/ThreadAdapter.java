package app.android.muscularstrength.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.*;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.Thread;
import java.util.HashMap;

import app.android.muscularstrength.R;
import app.android.muscularstrength.model.*;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.session.SessionManager;
import app.android.muscularstrength.webservice.WebServices;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sa on 8/12/2015.
 */
public class ThreadAdapter extends ArrayAdapter<Post> {
    Context _context;
    SessionManager session;
    User userObj;
    String msg;
    String postowner;
    public ThreadAdapter(Context context,String postowner) {
        super(context, 0);
        this._context=context;
        this.postowner=postowner;
        session = new SessionManager(context);
        Gson gson = new Gson();
        userObj = gson.fromJson(session.getSession(), User.class);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        HolderView holder = null;
        final Post data=getItem(position);
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)_context).getLayoutInflater();
            row = inflater.inflate(R.layout.thread_row, parent, false);
            holder = new HolderView();
            holder.userimg = (CircleImageView)row.findViewById(R.id.userimg);

            holder.text_user = (TextView)row.findViewById(R.id.text_user);
            holder.text_time = (TextView)row.findViewById(R.id.text_time);
            holder.text_preview = (TextView)row.findViewById(R.id.text_preview);
            holder.replyBtn = (ImageView)row.findViewById(R.id.replyBtn);
            holder.replyQuoteBtn = (ImageView)row.findViewById(R.id.replyQuoteBtn);

            row.setTag(holder);

        }
        else
        {
            holder = (HolderView)row.getTag();
        }
       // Log.i("IMAGE USER",""+data.getUserImage());
        Glide.with(_context).load(WebServices.host+data.getUserImage()).into(holder.userimg);
        holder.text_user.setText(data.getMembership());
        holder.text_time.setText(data.getTime());
        holder.text_preview.setText(Html.fromHtml(data.getDescription()));
        holder.replyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentAlert(data);
            }
        });
        holder.replyQuoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCommentAlert(data);
            }
        });
        return row;
    }
    static class HolderView
    {
        CircleImageView userimg;
        TextView text_user,text_time,text_preview;
        ImageView replyBtn,replyQuoteBtn;
    }
    public void showCommentAlert(final Post data) {
        final Dialog dialog = new Dialog(_context);
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
                    replyPost(postowner, comment_txt.getText().toString().trim(),data.getId());

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
                            mainHandler.sendMessage(mainHandler.obtainMessage(1));
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

    private Handler mainHandler = new Handler() {
        public void handleMessage(android.os.Message message) {
            try {

                // if (_context.isAdded()) {
                //  pDialog.dismiss();
                //pDialog.cancel();
                switch (message.what) {
                    case 0:
                        break;
                    case 1:
                        Toast.makeText(_context, msg, Toast.LENGTH_SHORT).show();
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
}
