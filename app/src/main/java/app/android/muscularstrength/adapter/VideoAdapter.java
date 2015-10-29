package app.android.muscularstrength.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import app.android.muscularstrength.R;
import app.android.muscularstrength.model.Video;

/**
 * Created by sa on 8/10/2015.
 */
public class VideoAdapter extends ArrayAdapter<Video> {
    Context _context;
    public VideoAdapter(Context context) {
        super(context, 0);
        this._context=context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        HolderView holder = null;
        Video data=getItem(position);
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)_context).getLayoutInflater();
            row = inflater.inflate(R.layout.exclusive_videorow, parent, false);
            holder = new HolderView();
            holder.image_video=(ImageView)row.findViewById(R.id.video_img);
            holder.text_title = (TextView)row.findViewById(R.id.text_title);
            row.setTag(holder);

        }
        else
        {
            holder = (HolderView)row.getTag();
        }
        Glide.with(_context).load(data.getImageLink()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.image_video);
        holder.text_title.setText(data.getTitle());


        return row;
    }
    static class HolderView
    {
         ImageView image_video;
        TextView text_title;
    }
}
