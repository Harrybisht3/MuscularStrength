package app.android.muscularstrength.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import app.android.muscularstrength.R;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sa on 8/11/2015.
 */
public class SubForumAdapter extends ArrayAdapter<app.android.muscularstrength.model.Thread> {
    private Context _context;
    public SubForumAdapter(Context context) {
        super(context, 0);
        this._context=context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        HolderView holder = null;
        app.android.muscularstrength.model.Thread data=getItem(position);
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)_context).getLayoutInflater();
            row = inflater.inflate(R.layout.forum_child, parent, false);
            holder = new HolderView();
            holder.user_img=(CircleImageView)row.findViewById(R.id.user_image);
            holder.texttitle = (TextView)row.findViewById(R.id.texttitle);
            holder.textcreated = (TextView)row.findViewById(R.id.textcreated);
            holder.textcreated.setVisibility(View.GONE);
            holder.textthread_post = (TextView)row.findViewById(R.id.textthread_post);
            holder.textposted_time = (TextView)row.findViewById(R.id.textposted_time);
            row.setTag(holder);

        }
        else
        {
            holder = (HolderView)row.getTag();
        }
        Glide.with(_context).load(data.getUserImage()).into(holder.user_img);
        holder.texttitle.setText(data.getTitle());
        holder.textthread_post.setText("Views: "+data.getViews()+" | "+"Post: "+data.getPosts());
        holder.textposted_time.setText("Posted By. " + data.getPostBy() + "," + data.getTime());

        return row;
    }
    static class HolderView
    {
        CircleImageView user_img;
        TextView texttitle,textcreated,textthread_post,textposted_time;
    }
}
