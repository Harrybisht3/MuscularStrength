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
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import app.android.muscularstrength.R;
import app.android.muscularstrength.model.Article;

/**
 * Created by Bisht Bhawna on 8/9/2015.
 */
public class ArticleAdapter extends ArrayAdapter<Article> {
    Context _context;
    public ArticleAdapter(Context context) {
        super(context, 0);
        this._context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        HolderView holder = null;
        Article data=getItem(position);
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)_context).getLayoutInflater();
            row = inflater.inflate(R.layout.article_row, parent, false);

            holder = new HolderView();
            holder.image_article = (ImageView)row.findViewById(R.id.image_article);
           // holder.image_article  = (SimpleDraweeView)row. findViewById(R.id.image_article);
            holder.textHeading = (TextView)row.findViewById(R.id.textHeading);
            holder.txtTitle = (TextView)row.findViewById(R.id.txtTitle);
            holder.textDesc = (TextView)row.findViewById(R.id.textDesc);
            row.setTag(holder);

        }
        else
        {
            holder = (HolderView)row.getTag();
        }
       // Uri uri=Uri.parse(data.getImage());
        Glide.with(_context).load(data.getImage()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.image_article);
       // holder.image_article.setImageURI(uri);
        holder.textHeading.setText(data.getHeading());
        holder.txtTitle.setText(data.getTitle());
        holder.textDesc.setText(data.getDescription());

        return row;
    }
    static class HolderView
    {
        ImageView image_article;
       // SimpleDraweeView image_article;
        TextView textHeading,txtTitle,textDesc;
    }
}
