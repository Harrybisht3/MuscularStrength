package app.android.muscularstrength.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import app.android.muscularstrength.R;

/**
 * Created by Bisht Bhawna on 7/11/2015.
 */
public class ViewPagerAdapter extends PagerAdapter {
    // Declare Variables
    Context context;
    String[] title;
    String[] tag;
    int[] bgimg;
    int[] flag;
    LayoutInflater inflater;

    public ViewPagerAdapter(Context context, String[] title,String[] tag, int[] bgimg) {
        this.context = context;
        this.title = title;
        this.tag=tag;
        this.bgimg=bgimg;

    }

    @Override
    public int getCount() {
        return title.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        // Declare Variables
        RelativeLayout pager_main;
        TextView txt_one;
        TextView txt_two;


        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.viewpager_item, container,
                false);

        // Locate the TextViews in viewpager_item.xml
        pager_main=(RelativeLayout)itemView.findViewById(R.id.pager_main);
        txt_one = (TextView) itemView.findViewById(R.id.txt_one);
        txt_two = (TextView) itemView.findViewById(R.id.txt_two);


        // Capture position and set to the TextViews
        pager_main.setBackgroundResource(bgimg[position]);
        txt_one.setText(Html.fromHtml(title[position]));
        txt_two.setText(Html.fromHtml(tag[position]));
        if(position==0){
            txt_two.setTextSize(context.getResources().getDimension(R.dimen._8sdp));
        }
        else {
            txt_one.setTextSize(context.getResources().getDimension(R.dimen._8sdp));
        }




        // Add viewpager_item.xml to ViewPager
        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        // Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}
