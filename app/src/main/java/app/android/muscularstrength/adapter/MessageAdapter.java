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
import app.android.muscularstrength.model.Message;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Bisht Bhawna on 8/10/2015.
 */
public class MessageAdapter extends ArrayAdapter<Message> {
    Context _context;
    public MessageAdapter(Context context) {
        super(context, 0);
        this._context=context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        HolderView holder = null;
        Message data=getItem(position);
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)_context).getLayoutInflater();
            row = inflater.inflate(R.layout.message_row, parent, false);

            holder = new HolderView();
            holder.userimg = (CircleImageView)row.findViewById(R.id.userimg);
            holder.text_user = (TextView)row.findViewById(R.id.text_user);
            holder.text_time = (TextView)row.findViewById(R.id.text_time);
            holder.text_fullName = (TextView)row.findViewById(R.id.text_fullName);
            holder.text_preview = (TextView)row.findViewById(R.id.text_preview);
            row.setTag(holder);

        }
        else
        {
            holder = (HolderView)row.getTag();
        }
        Glide.with(_context).load(data.getImage()).into(holder.userimg);
        holder.text_user.setText(data.getUser());
        holder.text_time.setText(data.getTimestamp());
        holder.text_fullName.setText(data.getSenderFullName());
        holder.text_preview.setText(data.getPreview());
        return row;
    }
    static class HolderView
    {
        CircleImageView userimg;
        TextView text_user,text_time,text_fullName,text_preview;
    }
}
