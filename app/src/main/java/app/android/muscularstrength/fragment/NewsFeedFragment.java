package app.android.muscularstrength.fragment;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
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
import java.util.List;

import app.android.muscularstrength.R;
import app.android.muscularstrength.Util.Constants;
import app.android.muscularstrength.Util.Util;
import app.android.muscularstrength.activity.DashBoardActivity;
import app.android.muscularstrength.model.Childcomment;
import app.android.muscularstrength.model.NewsFeedParser;
import app.android.muscularstrength.model.Newsfeed;
import app.android.muscularstrength.model.User;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.session.SessionManager;
import app.android.muscularstrength.webservice.WebServices;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by logan on 13/7/15.
 */
public class NewsFeedFragment extends Fragment {
    View rootView;
    int from;
    float density;
    ExpandableListView list_newsfeed;
    NewsFeedAdapter adapter;
   List<Newsfeed> datanewsFeed;
    //private int page_no = 1;
    ProgressDialog pDialog;
    CircleImageView userProfileImg;
    TextView user, account_type, level;
    SessionManager session;
    User userObj;
    String errorMessage;
    FragmentManager fragmentManager;
    ImageView profile,message,notification;
    TextView likeView;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity.mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("News Feed");
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.newsfeed_fragment, container, false);
            list_newsfeed = (ExpandableListView) rootView.findViewById(R.id.list_newsfeed);
            density = Util.getDensity(getActivity());
            datanewsFeed = new ArrayList<Newsfeed>();
            adapter = new NewsFeedAdapter(getActivity(), datanewsFeed);
            // list_newsfeed.setIndicatorBounds(Util.getDisplay(getActivity()).widthPixels - GetDipsFromPixel(50), Util.getDisplay(getActivity()).widthPixels - GetDipsFromPixel(10));
            fragmentManager=getActivity().getSupportFragmentManager();
            session = new SessionManager(getActivity());
            Gson gson = new Gson();
            userObj = gson.fromJson(session.getSession(), User.class);
            View headerlayout = View.inflate(getActivity(), R.layout.header_layout, null);
            list_newsfeed.addHeaderView(headerlayout, null, false);
            View view1 = View.inflate(getActivity(), R.layout.footer_layout, null);
            list_newsfeed.addFooterView(view1, null, false);
            list_newsfeed.setAdapter(adapter);
            profile=(ImageView)headerlayout.findViewById(R.id.profile);
            message=(ImageView)headerlayout.findViewById(R.id.message);
            notification=(ImageView)headerlayout.findViewById(R.id.notification);
            // View headerlayout= rootView.findViewById(R.id.header);
            userProfileImg = (CircleImageView) headerlayout.findViewById(R.id.profileImg);
            user = (TextView) headerlayout.findViewById(R.id.user);
            account_type = (TextView) headerlayout.findViewById(R.id.account_type);
            level = (TextView) headerlayout.findViewById(R.id.level);
            Glide.with(getActivity()).load(userObj.getFullImage()).into(userProfileImg);
            user.setText(userObj.getFirstName() + "" + userObj.getLastName());
            account_type.setText(userObj.getAccountType());
            level.setText(userObj.getUserLevel());
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("loading...");
            Bundle args = getArguments();
            from = args.getInt("from");
            getNewsfeed();
        }
        list_newsfeed.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                //Util.setExpendableListViewHeight(parent, groupPosition);
                //return false;
                return true;
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.setFragment(fragmentManager, Constants.FRIEND);
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.setFragment(fragmentManager, Constants.MESSAGE);
            }
        });
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Util.setFragment(fragmentManager, Constants.NOTIFICATION);
            }
        });


        return rootView;
    }

    public int GetDipsFromPixel(float pixels) {
        // Get the screen's density scale
        final float scale = getResources().getDisplayMetrics().density;
        // Convert the dps to pixels, based on density scale
        return (int) (pixels * scale + 0.5f);
    }

    //get articles
    private void getNewsfeed() {
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("userid", "" + userObj.getUserId());
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.newsFeed, "GET", params);
                try {
                    if (json != null) {
                        if (json.getString("result").equalsIgnoreCase("SUCCESS")) {
                            Gson gson = new Gson();
                            NewsFeedParser data = gson.fromJson(json.toString(), NewsFeedParser.class);
                            // if (data.getResult().equalsIgnoreCase("SUCCESS")) {
                            if (data.getData().getNewsfeed() != null) {
                                datanewsFeed.addAll(data.getData().getNewsfeed());
                                mainHandler.sendMessage(mainHandler.obtainMessage(1));
                            } else {
                                errorMessage = "No News Feed.";
                                mainHandler.sendMessage(mainHandler.obtainMessage(0));
                            }
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

    private void setListAdapter() {
        adapter.notifyDataSetChanged();
        list_newsfeed.setSelection(0);
        // Util.setExpendableListViewHeight(list_newsfeed, 0);
    }

    private Handler mainHandler = new Handler() {
        public void handleMessage(Message message) {
            try {

                if (isAdded()) {
                    pDialog.dismiss();
                    pDialog.cancel();
                    switch (message.what) {
                        case 0:
                            Toast.makeText(getActivity(), "" + errorMessage, Toast.LENGTH_SHORT).show();
                            break;
                        case 1:
                            //  isSearch=false;
                            // search_forum.setText("");
                            setListAdapter();
                            break;
                        case 2:
                            // isSearch=true;
                            setListAdapter();
                            break;
                        case 4:
                            likeView.setText("Liked");
                            likeView.setTextColor(getResources().getColor(R.color.cat_color));
                            //Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
                            break;
                        case 5:
                            datanewsFeed.clear();
                            adapter.notifyDataSetChanged();
                            getNewsfeed();
                            break;
                    }
                }
            } catch (Resources.NotFoundException e) {

            }
        }
    };
    public class NewsFeedAdapter extends BaseExpandableListAdapter {
        private Context _context;
        private List<Newsfeed> data_newsfeed;
        String msg;
        //SessionManager session;
      //  User userObj;
        //TextView textView;


        public NewsFeedAdapter(Context context, List<Newsfeed> data_newsfeed) {
            this._context = context;
            this.data_newsfeed = data_newsfeed;
            session = new SessionManager(context);
            Gson gson = new Gson();
            userObj = gson.fromJson(session.getSession(), User.class);
        }

        @Override
        public int getGroupCount() {
            return data_newsfeed.size();
        }

        @Override
        public int getChildrenCount(int groupPosition) {
            if (data_newsfeed.get(groupPosition).getChildcomment() == null) {
                return 0;
            } else {
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
            final Newsfeed headerInfo = (Newsfeed) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater inf = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inf.inflate(R.layout.newsfeed_parent, null);
            }
            ImageView userimg = (ImageView) convertView.findViewById(R.id.userimg);
            ImageView indicator = (ImageView) convertView.findViewById(R.id.indicator);
            indicator.setVisibility(View.GONE);
            TextView heading = (TextView) convertView.findViewById(R.id.text_name);
            heading.setText(headerInfo.getName());
            TextView textcontent = (TextView) convertView.findViewById(R.id.text_content);
            textcontent.setText(Html.fromHtml(headerInfo.getComment()));
            final TextView textlike = (TextView) convertView.findViewById(R.id.likeTxt);
            TextView textreply = (TextView) convertView.findViewById(R.id.text_reply);
            TextView timeago = (TextView) convertView.findViewById(R.id.time_ago);
            timeago.setText(headerInfo.getTime());
            Glide.with(_context).load(headerInfo.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(userimg);
            ExpandableListView mExpandableListView = (ExpandableListView) parent;
            mExpandableListView.expandGroup(groupPosition);
            // Util.setListViewHeight(mExpandableListView,groupPosition);
            if (isExpanded) {

                indicator.setImageResource(R.drawable.nav_up);
            } else {
                indicator.setImageResource(R.drawable.nav_down);
            }
            textlike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    likeView=textlike;
                    hitLike(headerInfo.getId());
                }
            });
            textreply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCommentAlert(headerInfo.getId(),headerInfo);
                }
            });
            return convertView;
        }

        @Override
        public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
            final Childcomment childinfo = (Childcomment) getChild(groupPosition, childPosition);
            final Newsfeed headerInfo = (Newsfeed) getGroup(groupPosition);
            if (convertView == null) {
                LayoutInflater inf = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inf.inflate(R.layout.newsfeed_parent, null);
            }
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams((int) _context.getResources().getDimension(R.dimen._50sdp), (int) _context.getResources().getDimension(R.dimen._50sdp));
            lp.setMargins((int) _context.getResources().getDimension(R.dimen._50sdp), (int) _context.getResources().getDimension(R.dimen._50sdp), 0, 0);
            ImageView userimg = (ImageView) convertView.findViewById(R.id.userimg);
            userimg.setLayoutParams(lp);
            ImageView indicator = (ImageView) convertView.findViewById(R.id.indicator);
            indicator.setVisibility(View.GONE);
            TextView heading = (TextView) convertView.findViewById(R.id.text_name);
            heading.setText(childinfo.getName());
            TextView textcontent = (TextView) convertView.findViewById(R.id.text_content);
            textcontent.setText(Html.fromHtml(childinfo.getComment()));
            final TextView textlike = (TextView) convertView.findViewById(R.id.likeTxt);
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
                    likeView=textlike;
                    hitLike(childinfo.getId());
                }
            });
            textreply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showCommentAlert(childinfo.getId(),headerInfo);
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
                    params.put("id", postid);
                    JSONParser parser = new JSONParser();
                    JSONObject json = parser.makeHttpRequest(WebServices.newsFeedlike, "GET", params);
                    try {
                        if (json != null) {
                            if (json.getString("result").equalsIgnoreCase("SUCCESS")) {
                                // datanewsFeed.addAll(data.getData().getNewsfeed());
                                msg = json.getString("data");
                                mainHandler.sendMessage(mainHandler.obtainMessage(4));
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
                                mainHandler.sendMessage(mainHandler.obtainMessage(5));
                            } else {
                                errorMessage=json.getString("data");
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

       /* private Handler mainHandler = new Handler() {
            public void handleMessage(Message message) {
                try {

                    // if (_context.isAdded()) {
                    //  pDialog.dismiss();
                    //pDialog.cancel();
                    switch (message.what) {
                        case 0:
                            break;
                        case 1:
                            textView.setText("Liked");
                            textView.setTextColor(_context.getResources().getColor(R.color.cat_color));
                            Toast.makeText(_context, msg, Toast.LENGTH_SHORT).show();
                            break;
                        case 2:
                            // isSearch=true;
                            // setListAdapter();
                            Toast.makeText(_context, msg, Toast.LENGTH_SHORT).show();
                            break;
                    }
                    //}
                } catch (Resources.NotFoundException e) {

                }
            }
        };*/

        public void showCommentAlert(final String postid,final Newsfeed header) {
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
                        replyPost(postid, comment_txt.getText().toString().trim(),header.getId());

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


    }
}
