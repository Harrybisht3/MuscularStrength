package app.android.muscularstrength.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import app.android.muscularstrength.R;
import app.android.muscularstrength.fragment.ProfileFragment;
import app.android.muscularstrength.model.Friend;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by laxman singh on 11/2/2015.
 */
public class FriendAdapter extends ArrayAdapter<Friend> {
    Context _context;
    Fragment fragmentcontext;
    public FriendAdapter(Context context,Fragment fragmentcontext) {
        super(context,0);
        this._context=context;
        this.fragmentcontext=fragmentcontext;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        HolderView holder = null;
        final Friend data=getItem(position);
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)_context).getLayoutInflater();
            row = inflater.inflate(R.layout.friendrequest_row, parent, false);

            holder = new HolderView();
            holder.userimg = (CircleImageView)row.findViewById(R.id.userimg);
            holder.text_name = (TextView)row.findViewById(R.id.text_name);
            holder.text_user = (TextView)row.findViewById(R.id.text_user);
            holder.text_accept = (TextView)row.findViewById(R.id.accept_txt);
            holder.text_deny = (TextView)row.findViewById(R.id.deny_txt);
            holder.accept = (ImageView)row.findViewById(R.id.acceptBtn);
            holder.deny = (ImageView)row.findViewById(R.id.denyBtn);
            holder.view=(View)row.findViewById(R.id.v_div);
            holder.view.setVisibility(View.VISIBLE);
            holder.accept.setVisibility(View.GONE);
            holder.deny.setVisibility(View.GONE);
            row.setTag(holder);

        }
        else
        {
            holder = (HolderView)row.getTag();
        }
        Glide.with(_context).load(data.getImage()).into(holder.userimg);
        holder.text_name.setText(data.getName());
        holder.text_name.setTextColor(_context.getResources().getColor(R.color.cat_color));
        //holder.text_user.setText(data.getUser());
        doColorSpanForFirstString("User: ",data.getUser(),holder.text_user);
        holder.text_accept.setText("View Profile");
        holder.text_accept.setTextColor(_context.getResources().getColor(R.color.red));
        holder.text_deny.setText("Unfriend");
        holder.text_deny.setTextColor(_context.getResources().getColor(R.color.red));
        holder.text_accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProfile(data);
               // Toast.makeText(_context,"Clicked",Toast.LENGTH_SHORT).show();
            }
        });

        return row;
    }
    static class HolderView
    {
        CircleImageView userimg;
        TextView text_name,text_user;
        TextView text_accept,text_deny;
        ImageView accept,deny;
        View view;
    }
    private void doColorSpanForFirstString(String firstString, String lastString, TextView txtSpan) {
        SpannableStringBuilder finalString = new SpannableStringBuilder();
        finalString.append(firstString);
        finalString.setSpan(new ForegroundColorSpan(_context.getResources()
                .getColor(R.color.cat_color)), 0, finalString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        int start = finalString.length();
        finalString.append(lastString);
        finalString.setSpan(new ForegroundColorSpan(_context.getResources()
                .getColor(R.color.red)), start, finalString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        txtSpan.setText(finalString);
        // txtSpan.setMovementMethod(LinkMovementMethod.getInstance());

    }
    private void showProfile(final Friend data) {
        FragmentTransaction ft = fragmentcontext.getChildFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putInt("from", 1);
        bundle.putString("userid", data.getId());
        bundle.putString("username", data.getName());
        Fragment fragment = new ProfileFragment();
        fragment.setArguments(bundle);
        replaceFragment(fragment);
        ft.commit();

    }

    private void replaceFragment(Fragment fragment) {
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = fragmentcontext.getActivity().getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate(backStateName, 0);
        if (!fragmentPopped) { //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.contentframe, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }
}
