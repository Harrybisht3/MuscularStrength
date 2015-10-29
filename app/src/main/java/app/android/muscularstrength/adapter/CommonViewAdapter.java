package app.android.muscularstrength.adapter;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.android.muscularstrength.R;
import app.android.muscularstrength.model.PhotoData;
import app.android.muscularstrength.model.UserVideoMaster;

/**
 * Created by Bisht Bhawna on 8/21/2015.
 */
public class CommonViewAdapter extends BaseExpandableListAdapter {
    private Context _context;
  private  ViewPager  viewPager;
    private String[] data;
    String story;
    List<PhotoData> photodata;
    List<UserVideoMaster> Video;

    public CommonViewAdapter(Context context, String[] data) {
        this._context = context;
        this.data = data;
    }
    public CommonViewAdapter(Context context, String[] data,String story) {
        this._context = context;
        this.data = data;
        this.story=story;
    }

    public CommonViewAdapter(Context context, String[] data,List<PhotoData> photodata) {
        this._context = context;
        this.data = data;
        this.photodata=photodata;
    }

    public CommonViewAdapter(Context context, String[] data,List<UserVideoMaster> Video,int type) {
        this._context = context;
        this.data = data;
        this.Video=Video;
    }

  /*  public CommonViewAdapter(Context context, String[] data) {
        this._context = context;
        this.data = data;
    }

    public CommonViewAdapter(Context context, String[] data) {
        this._context = context;
        this.data = data;
    }

    public CommonViewAdapter(Context context, String[] data) {
        this._context = context;
        this.data = data;
    }*/


    @Override
    public int getGroupCount() {
        //  Log.i("Group count..",data.length+"");
        return data.length;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        return 1;
    }


    @Override
    public Object getGroup(int groupPosition) {
        return data[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerInfo = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.parent, null);
        }
        Button heading = (Button) convertView.findViewById(R.id.commonBtn);
        heading.setText(headerInfo);
        // ExpandableListView mExpandableListView = (ExpandableListView) parent;
        //mExpandableListView.expandGroup(groupPosition);
        //Util.setExpendableListViewHeight(mExpandableListView,groupPosition);
        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        // String headerInfo = (String) getGroup(groupPosition);
        // LayoutInflater inf = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       // if (convertView == null) {
        LayoutInflater inf = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (groupPosition == 0) {
                convertView = inf.inflate(R.layout.routine_view, null);
               // Toast.makeText(_context, "Gpostion" + groupPosition, Toast.LENGTH_SHORT).show();
            } else if (groupPosition == 1) {
                convertView = inf.inflate(R.layout.story_view, null);
                TextView storyTxt=(TextView)convertView.findViewById(R.id.story_txt);
                storyTxt.setText(Html.fromHtml(story));
               // Toast.makeText(_context, "Gpostion" + groupPosition, Toast.LENGTH_SHORT).show();
            } else if (groupPosition == 2) {
                convertView = inf.inflate(R.layout.parent, null);
            } else if (groupPosition == 3) {
                convertView = inf.inflate(R.layout.video_view, null);
            viewPager = (ViewPager)convertView.findViewById(R.id.videopager);
              //  mIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
                // Pass results to ViewPagerAdapter Class
                final TextView albumTxt=(TextView)convertView.findViewById(R.id.albumTxt);
                //VideoViewPagerAdapter  adapter = new VideoViewPagerAdapter(_context,Video,albumTxt);
                // Binds the Adapter to the ViewPager
               // viewPager.setAdapter(adapter);
                ImageView previous=(ImageView)convertView.findViewById(R.id.previous);
                ImageView next=(ImageView)convertView.findViewById(R.id.next);

               // albumTxt.setText(adapter.get);
                Button addBtn=(Button)convertView.findViewById(R.id.addVideo);
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       // if(viewPager.getChildCount()) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem()+1, true);
                           // albumTxt.setText("ALBUM-"));
                       // }
                    }
                });
                previous.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                      //  if (getItem(-1)>0) {
                            viewPager.setCurrentItem(viewPager.getCurrentItem()-1, true);
                           // albumTxt.setText("ALBUM-" + getItem(-1));
                       // }
                    }
                });
            } else if (groupPosition == 4) {
                convertView = inf.inflate(R.layout.routine_view, null);
            } else if (groupPosition == 5) {
                convertView = inf.inflate(R.layout.meal_planview, null);
            } else if (groupPosition == 6) {
                convertView = inf.inflate(R.layout.parent, null);
            } else if (groupPosition == 7) {
                convertView = inf.inflate(R.layout.parent, null);
            }
        //}


        return convertView;
    }
    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
