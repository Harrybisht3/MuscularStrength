package app.android.muscularstrength.adapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
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

/**
 * Created by Bisht Bhawna on 9/24/2015.
 */
public class SelectedImageAdapter extends BaseAdapter {
    Context context;
    private LayoutInflater mInflater;
    DisplayMetrics display;
    int height,width;
    List<String>medifiles;

    public SelectedImageAdapter(Context context,List<String> medifiles) {
        this.context=context;
        this.medifiles=medifiles;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        display= Util.getDisplay((Activity)context);
        height=display.heightPixels;
        width=display.widthPixels;
    }

    public int getCount() {
        return medifiles.size();
    }

    public Object getItem(int position) {
        return medifiles.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
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
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
       // holder.checkbox.setId(position);
        holder.imageview.setId(position);
   /*     holder.checkbox.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
                CheckBox cb = (CheckBox) v;
                int id = cb.getId();
                if (thumbnailsselection[id]){
                    countthumb--;
                    selectedImages.remove(thumbnails[position]);
                    cb.setChecked(false);
                    thumbnailsselection[id] = false;

                } else {
                    if(countthumb<10) {
                        countthumb++;
                        selectedImages.add(thumbnails[position]);
                        cb.setChecked(true);
                        thumbnailsselection[id] = true;
                    }
                    else{
                        cb.setChecked(false);
                        showAlertMax();
                    }
                }
            }
        });*/
        holder.imageview.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                // TODO Auto-generated method stub
              /*  int id = v.getId();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://" + arrPath[id]), "image*//*");
                startActivity(intent);*/
            }
        });
        holder.imageview.setImageURI(Uri.parse(medifiles.get(position)));
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