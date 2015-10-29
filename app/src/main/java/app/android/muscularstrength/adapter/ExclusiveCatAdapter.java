package app.android.muscularstrength.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import app.android.muscularstrength.R;
import app.android.muscularstrength.model.Category;

/**
 * Created by sa on 8/10/2015.
 */
public class ExclusiveCatAdapter extends ArrayAdapter<Category> {
    Context _context;

    public ExclusiveCatAdapter(Context context) {
        super(context, 0);
        this._context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        HolderView holder = null;
        Category data = getItem(position);
        if (row == null) {
            LayoutInflater inflater = ((Activity) _context).getLayoutInflater();
            row = inflater.inflate(R.layout.exclusive_row, parent, false);
            holder = new HolderView();
            holder.textCategory = (TextView) row.findViewById(R.id.textCategory);
            row.setTag(holder);

        } else {
            holder = (HolderView) row.getTag();
        }
        //Glide.with(_context).load(data.getImage()).into(holder.image_recipe);
        holder.textCategory.setText(data.getTitle());

        return row;
    }

    static class HolderView {
        // ImageView image_recipe;
        TextView textCategory;
    }
}

