package app.android.muscularstrength.adapter;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import app.android.muscularstrength.R;
import app.android.muscularstrength.Util.Util;
import app.android.muscularstrength.model.Album;

/**
 * Created by Bisht Bhawna on 9/23/2015.
 */
public class AlbumAdapter extends ArrayAdapter<Album> {
    Context _context;
    DisplayMetrics display;
    int height,width;
    public AlbumAdapter(Context context) {
        super(context, 0);
        this._context=context;
        display= Util.getDisplay((Activity) context);
        height=display.heightPixels;
        width=display.widthPixels;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        HolderView holder = null;
        Album data=getItem(position);
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)_context).getLayoutInflater();
            row = inflater.inflate(R.layout.uservideo_row, parent, false);
            holder = new HolderView();
            holder.image_cover=(ImageView)row.findViewById(R.id.thumbVideo);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(((width/2)-(int)_context.getResources().getDimension(R.dimen._12sdp)), (int)_context.getResources().getDimension(R.dimen._150sdp));
            //  lp.setMargins((int) _context.getResources().getDimension(R.dimen._50sdp), (int) _context.getResources().getDimension(R.dimen._50sdp), 0, 0);
            holder.image_cover.setLayoutParams(lp);
            holder.image_play = (ImageView)row.findViewById(R.id.playbtn);
            holder.image_play.setVisibility(View.GONE);
            holder.image_Title= (TextView)row.findViewById(R.id.video_title);
            row.setTag(holder);

        }
        else
        {
            holder = (HolderView)row.getTag();
        }
        Glide.with(_context).load(data.getCoverImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.image_cover);
        holder.image_Title.setText(data.getTitle());


        return row;
    }
    static class HolderView
    {
        ImageView image_cover;
        ImageView image_play;
        TextView image_Title;
    }
}
