package app.android.muscularstrength.adapter;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import app.android.muscularstrength.R;
import app.android.muscularstrength.model.Post;
import app.android.muscularstrength.webservice.WebServices;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sa on 8/12/2015.
 */
public class ThreadAdapter extends ArrayAdapter<Post> {
    Context _context;
    public ThreadAdapter(Context context) {
        super(context, 0);
        this._context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        HolderView holder = null;
        Post data=getItem(position);
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
        return row;
    }
    static class HolderView
    {
        CircleImageView userimg;
        TextView text_user,text_time,text_preview;
        ImageView replyBtn,replyQuoteBtn;
    }
}
