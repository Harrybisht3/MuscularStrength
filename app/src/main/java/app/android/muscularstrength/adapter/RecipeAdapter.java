package app.android.muscularstrength.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import app.android.muscularstrength.R;
import app.android.muscularstrength.model.Recipe;

/**
 * Created by Bisht Bhawna on 8/9/2015.
 */
public class RecipeAdapter extends ArrayAdapter<Recipe> {
    Context _context;
    //List<Float> ratingvalue;
    public RecipeAdapter(Context context) {
        super(context, 0);
        this._context=context;
      //  ratingvalue=new ArrayList<Float>();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row = convertView;
        HolderView holder = null;
        final Recipe data=getItem(position);
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)_context).getLayoutInflater();
            row = inflater.inflate(R.layout.recipe_row, parent, false);
            holder = new HolderView();
            data.setDescription(data.getDescription()+"###"+"0.0");
            holder.image_recipe = (ImageView)row.findViewById(R.id.image_recipe);
            holder.textHeading = (TextView)row.findViewById(R.id.textHeading);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.textDesc = (TextView)row.findViewById(R.id.textDesc);
            holder.ratingbar=(RatingBar)row.findViewById(R.id.rating);
            row.setTag(holder);


         /*   RatingBar.OnRatingBarChangeListener l=
                    new RatingBar.OnRatingBarChangeListener() {
                        public void onRatingChanged(RatingBar ratingBar,
                                                    float rating, boolean fromTouch) {
                            Integer myPosition=(Integer)ratingBar.getTag();
\                            Recipe data1=getItem(myPosition);
                            data1.setDescription(data1.getDescription()+"###"+rating);
                            //notifyDataSetChanged();
                           // model.rating=rating;
                           *//* LinearLayout parent=(LinearLayout)ratingBar.getParent();
                            TextView label=(TextView)parent.findViewById(R.id.label);
                            label.setText(model.toString());*//*
                        }
                    };
            holder.ratingbar.setOnRatingBarChangeListener(l);*/

        }
        else
        {
            holder = (HolderView)row.getTag();
        }

        holder.ratingbar.setTag(position);
        Glide.with(_context).load(data.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.image_recipe);
        holder.textHeading.setText(data.getHeading());
        holder.txtTitle.setText(data.getTitle());
        holder.textDesc.setText(data.getDescription());

        //String rate=data.getDescription().split("###")[1];
        //holder.ratingbar.setRating(Float.parseFloat(rate));
        //if(ratingvalue.size()>0) {

       // }
        return row;
    }
    static class HolderView
    {
        ImageView image_recipe;
        TextView textHeading,txtTitle,textDesc;
        RatingBar ratingbar;
    }
}
