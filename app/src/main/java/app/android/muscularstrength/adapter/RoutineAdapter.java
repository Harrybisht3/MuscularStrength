package app.android.muscularstrength.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import java.util.List;

import app.android.muscularstrength.R;
import app.android.muscularstrength.model.Routine;

/**
 * Created by laxman singh on 10/31/2015.
 */
public class RoutineAdapter extends BaseExpandableListAdapter {
    private Context _context;
    private List<Routine> data_Routine;
    public RoutineAdapter(Context context, List<Routine> data_Routine) {
        this._context = context;
        this.data_Routine = data_Routine;
        //session=new SessionManager(context);
       // Gson gson=new Gson();
       // userObj=gson.fromJson(session.getSession(),User.class);
    }

    @Override
    public int getGroupCount() {
        return data_Routine.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(data_Routine.get(groupPosition).getRoutine()==null) {
            return 0;
        }else{
            return data_Routine.get(groupPosition).getRoutine().size();
        }
    }

    @Override
    public Object getGroup(int groupPosition) {
        return data_Routine.get(groupPosition).getDay();
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data_Routine.get(groupPosition).getRoutine().get(childPosition).getTitle();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
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
        final String headerInfo = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.forum_parent, null);
        }
        TextView heading = (TextView) convertView.findViewById(R.id.textHeading);
        heading.setText(headerInfo);
        heading.setAllCaps(false);
        heading.setPadding(20,10,10,10);
        TextView textcontent = (TextView) convertView.findViewById(R.id.textCategories);
        textcontent.setVisibility(View.GONE);
        ExpandableListView mExpandableListView = (ExpandableListView) parent;
        mExpandableListView.expandGroup(groupPosition);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String childInfo = (String) getChild(groupPosition,childPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) _context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.forum_parent, null);
        }
        TextView heading = (TextView) convertView.findViewById(R.id.textHeading);
        heading.setVisibility(View.GONE);
        TextView textcontent = (TextView) convertView.findViewById(R.id.textCategories);
        View divider=(View)convertView.findViewById(R.id.divider);
        divider.setVisibility(View.VISIBLE);
      // divider.setm
        textcontent.setPadding(20,5,10,5);
        textcontent.setTextColor(_context.getResources().getColor(R.color.white));

        textcontent.setText(childInfo);
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
