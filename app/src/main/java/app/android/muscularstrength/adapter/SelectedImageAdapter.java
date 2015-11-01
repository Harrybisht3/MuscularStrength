package app.android.muscularstrength.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

import app.android.muscularstrength.R;
import app.android.muscularstrength.Util.Util;
import app.android.muscularstrength.activity.AddPhotoActivity;

/**
 * Created by Bisht Bhawna on 9/24/2015.
 */
public class SelectedImageAdapter extends BaseAdapter {
    Context context;
    private LayoutInflater mInflater;
    DisplayMetrics display;
    int height,width;
    //List<String>medifiles;
    List<Bitmap>bitmaplist;

    public SelectedImageAdapter(Context context,List<Bitmap>bitmaplist) {
        this.context=context;
       // this.medifiles=medifiles;
        this.bitmaplist=bitmaplist;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        display= Util.getDisplay((Activity)context);
        height=display.heightPixels;
        width=display.widthPixels;
    }

    public int getCount() {
        return bitmaplist.size();
    }

    public Object getItem(int position) {
        return bitmaplist.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
       // final ImageModel model=new ImageModel();
        if (convertView == null) {
            holder = new ViewHolder();
           // model.setpath(medifiles.get(position));
            //model.setCaption(null);
            convertView = mInflater.inflate(
                    R.layout.uservideo_row, null);
            holder.imageview = (ImageView) convertView.findViewById(R.id.thumbVideo);
            holder.play = (ImageView) convertView.findViewById(R.id.playbtn);
            holder.title=(TextView)convertView.findViewById(R.id.video_title);
            holder.play.setVisibility(View.GONE);
            holder.title.setVisibility(View.GONE);
            holder.caption_edit = (EditText) convertView.findViewById(R.id.caption_edit);
            holder.caption_edit.setVisibility(View.VISIBLE);
            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(((width/2)-(int)context.getResources().getDimension(R.dimen._10sdp)), ((width/2)-(int)context.getResources().getDimension(R.dimen._10sdp)));
            //  lp.setMargins((int) _context.getResources().getDimension(R.dimen._50sdp), (int) _context.getResources().getDimension(R.dimen._50sdp), 0, 0);
            holder.imageview.setLayoutParams(lp);
            holder.imageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
            convertView.setTag(holder);
            holder.caption_edit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.toString().trim().length() > 0) {
                       AddPhotoActivity.imagesmodel.get(position).setCaption(s.toString());
                    } else {
                        AddPhotoActivity.imagesmodel.get(position).setCaption(null);
                    }
                    // model.setpath(medifiles.get(position));

                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
        //AddPhotoActivity.imagesmodel.add(position, model);
       // holder.checkbox.setId(position);
        holder.imageview.setId(position);

       /* holder.imageview.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
              *//*  int id = v.getId();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://" + arrPath[id]), "image*//**//*");
                startActivity(intent);*//*
            }
        });*/
       // holder.imageview.setImageURI(Uri.parse(medifiles.get(position)));
        holder.imageview.setImageBitmap(bitmaplist.get(position));
      //  Glide.with(context).
                //Glide.with(context).load(Uri.parse(medifiles.get(position))).asBitmap().into(holder.imageview);

       // holder.checkbox.setChecked(thumbnailsselection[position]);
       // holder.id = position;
        return convertView;
    }
}
class ViewHolder {
    ImageView imageview;
    ImageView play;
    TextView title;
    EditText caption_edit;
    int id;
}