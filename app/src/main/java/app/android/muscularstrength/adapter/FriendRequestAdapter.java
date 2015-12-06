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

import app.android.muscularstrength.R;
import app.android.muscularstrength.model.FriendRequest;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sa on 8/11/2015.
 */
public class FriendRequestAdapter extends ArrayAdapter<FriendRequest> {
    Context _context;
    public FriendRequestAdapter(Context context) {
        super(context, 0);
        this._context=context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        HolderView holder = null;
        FriendRequest data=getItem(position);
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)_context).getLayoutInflater();
            row = inflater.inflate(R.layout.friendrequest_row, parent, false);

            holder = new HolderView();
            holder.userimg = (CircleImageView)row.findViewById(R.id.userimg);
            holder.text_name = (TextView)row.findViewById(R.id.text_name);
            holder.text_user = (TextView)row.findViewById(R.id.text_user);
            holder.accept = (ImageView)row.findViewById(R.id.acceptBtn);
            holder.deny = (ImageView)row.findViewById(R.id.denyBtn);
            row.setTag(holder);

        }
        else
        {
            holder = (HolderView)row.getTag();
        }
        Glide.with(_context).load(data.getImage()).into(holder.userimg);
        holder.text_name.setText(data.getName());
        holder.text_user.setText(data.getUser());
        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.deny.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return row;
    }
    static class HolderView
    {
        CircleImageView userimg;
        TextView text_name,text_user;
        ImageView accept,deny;
    }
}
