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
import app.android.muscularstrength.model.Video;

/**
 * Created by laxman singh on 11/9/2015.
 */
public class MemberRoutineAdapter extends ArrayAdapter<Video> {

    Context _context;
    DisplayMetrics display;
    int height, width;

    public MemberRoutineAdapter(Context context) {
        super(context, 0);
        this._context = context;
        display = Util.getDisplay((Activity) context);
        width = display.widthPixels;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        HolderView holder = null;
        Video data = getItem(position);
        if (row == null) {
            LayoutInflater inflater = ((Activity) _context).getLayoutInflater();
            row = inflater.inflate(R.layout.uservideo_row, parent, false);
            holder = new HolderView();
            holder.image_video = (ImageView) row.findViewById(R.id.thumbVideo);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(((width / 2) - (int) _context.getResources().getDimension(R.dimen._12sdp)), (width / 2) - (int) _context.getResources().getDimension(R.dimen._12sdp));
            //  lp.setMargins((int) _context.getResources().getDimension(R.dimen._50sdp), (int) _context.getResources().getDimension(R.dimen._50sdp), 0, 0);
            holder.image_video.setLayoutParams(lp);
            holder.image_play = (ImageView) row.findViewById(R.id.playbtn);
            //RelativeLayout.LayoutParams lp1 = new RelativeLayout.LayoutParams(((width/2)-(int)_context.getResources().getDimension(R.dimen._12sdp)), RelativeLayout.LayoutParams.WRAP_CONTENT);
            //lp1.setMargins(0,(width/2)-(int)_context.getResources().getDimension(R.dimen._12sdp),(int)_context.getResources().getDimension(R.dimen._5sdp),0);
            holder.video_title = (TextView) row.findViewById(R.id.video_title);
            // holder.video_title.setLayoutParams(lp1);
            row.setTag(holder);

        } else {
            holder = (HolderView) row.getTag();
        }
        // String lasturl=data.getVideoLink().substring(data.getVideoLink().lastIndexOf("/"));

        //String img_url="http://img.youtube.com/vi/"+data.getVideoLink().substring(data.getVideoLink().lastIndexOf("/")+1)+"/0.jpg";
        // Log.i("URL THUMB", "utt=" + img_url);
        Glide.with(_context).load(data.getImageLink()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.image_video);
        holder.video_title.setText(data.getTitle());


        return row;
    }

    static class HolderView {
        ImageView image_video;
        ImageView image_play;
        TextView video_title;
    }
}

