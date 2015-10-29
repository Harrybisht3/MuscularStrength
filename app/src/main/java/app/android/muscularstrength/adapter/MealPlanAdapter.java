package app.android.muscularstrength.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import app.android.muscularstrength.R;
import app.android.muscularstrength.model.MealPlanMaster;

/**
 * Created by Bisht Bhawna on 8/27/2015.
 */
public class MealPlanAdapter extends ArrayAdapter<MealPlanMaster> {
    Context _context;

    public MealPlanAdapter(Context context) {
        super(context, 0);
        this._context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        HolderView holder = null;
        MealPlanMaster data = getItem(position);
        if (row == null) {
            LayoutInflater inflater = ((Activity) _context).getLayoutInflater();
            row = inflater.inflate(R.layout.meal_plan_row, parent, false);
            holder = new HolderView();
            holder.title = (TextView) row.findViewById(R.id.title);
            row.setTag(holder);

        } else {
            holder = (HolderView) row.getTag();
        }
        holder.title.setText(data.getTitle());
        return row;
    }

    static class HolderView {
        TextView title;
    }
}
