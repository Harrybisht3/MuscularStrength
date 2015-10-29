package app.android.muscularstrength.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

import app.android.muscularstrength.R;
import app.android.muscularstrength.model.Forum;
import app.android.muscularstrength.model.Forum_;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Bisht Bhawna on 8/9/2015.
 */
public class ForumAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private ArrayList<Forum> data_forums;
    public ForumAdapter(Context context, ArrayList<Forum> data_forums) {
        this._context = context;
        this.data_forums = data_forums;
    }

    @Override
    public int getGroupCount() {
        return data_forums.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
       List<Forum_> productList = data_forums.get(groupPosition).getForum();
        return productList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data_forums.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        List<Forum_> productList = data_forums.get(groupPosition).getForum();
        return productList.get(childPosition);
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
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        Forum headerInfo = (Forum) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.forum_parent, null);
        }

        TextView heading = (TextView) convertView.findViewById(R.id.textHeading);
        heading.setText(headerInfo.getHeading());
        TextView textCategories = (TextView) convertView.findViewById(R.id.textCategories);
        textCategories.setText(headerInfo.getCategories());
        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        Forum_ detailInfo = (Forum_) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.forum_child, null);
        }

        TextView texttitle = (TextView) convertView.findViewById(R.id.texttitle);
        texttitle.setText(detailInfo.getTitle());
        TextView textcreated = (TextView) convertView.findViewById(R.id.textcreated);
        textcreated.setText("Created by "+detailInfo.getCreatedBy());
        TextView textthread_post = (TextView) convertView.findViewById(R.id.textthread_post);
        textthread_post.setText("Threads: "+detailInfo.getThreads()+" | "+"Post: "+detailInfo.getPosts());
        TextView textposted_time = (TextView) convertView.findViewById(R.id.textposted_time);
        textposted_time.setText("Posted By. " + detailInfo.getPostedBy() + "," + detailInfo.getTime());
        CircleImageView user_image=(CircleImageView)convertView.findViewById(R.id.user_image);
       Glide.with(_context).load(detailInfo.getUserImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(user_image);
       // View line=(View)convertView.findViewById(R.id.line);
       // Glide.with(_context).load(data.getImage()).into(holder.image_article);
       /* if(childPosition==getChildrenCount(groupPosition)-1){
            line.setVisibility(View.GONE);
        }*/
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
