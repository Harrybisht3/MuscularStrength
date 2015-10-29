package app.android.muscularstrength.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.android.muscularstrength.R;

/**
 * Created by Bisht Bhawna on 7/19/2015.
 */
public class MenuListAdapter extends BaseAdapter {
    Context context;
    String[] mTitle;
    int[] mIcons;
    LayoutInflater inflater;
    public static int selection = 0;
   // Typeface typeface;
    public static int count = 0;

    public MenuListAdapter(Context context, String[] title, int[] icons) {
        this.context = context;
        this.mTitle = title;
        this.mIcons = icons;
       // typeface = Typeface.createFromAsset(context.getAssets(), "TAHOMA.TTF");
    }

    @Override
    public int getCount() {
        return mTitle.length;
    }

    @Override
    public Object getItem(int position) {
        return mTitle[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        TextView txtTitle;
        ImageView imgIcon;
        RelativeLayout layout_count;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.drawerlist_row, parent, false);
        txtTitle = (TextView) itemView.findViewById(R.id.menu_name);
        imgIcon = (ImageView) itemView.findViewById(R.id.menu_img);
       // txtTitle.setTypeface(typeface);
        txtTitle.setText(mTitle[position]);
        imgIcon.setImageResource(mIcons[position]);
        return itemView;
    }
}
