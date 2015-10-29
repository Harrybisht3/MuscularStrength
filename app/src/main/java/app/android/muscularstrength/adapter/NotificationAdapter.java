package app.android.muscularstrength.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import app.android.muscularstrength.R;
import app.android.muscularstrength.model.Notification;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by sa on 8/10/2015.
 */
public class NotificationAdapter extends ArrayAdapter<Notification> {
    Context _context;
    public NotificationAdapter(Context context) {
        super(context, 0);
        this._context=context;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        HolderView holder = null;
        Notification data=getItem(position);
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)_context).getLayoutInflater();
            row = inflater.inflate(R.layout.notification_row, parent, false);

            holder = new HolderView();
            holder.userimg = (CircleImageView)row.findViewById(R.id.userimg);
            holder.notification_txt = (TextView)row.findViewById(R.id.notification_txt);
            holder.notification_time = (TextView)row.findViewById(R.id.notification_time);

            row.setTag(holder);

        }
        else
        {
            holder = (HolderView)row.getTag();
        }
        Glide.with(_context).load(data.getUserImg()).into(holder.userimg);
      //  holder.notification_txt.setText(Html.fromHtml(data.getMessage()));
        Document doc= Jsoup.parse(data.getMessage());
        String first=doc.select("a").first().ownText();
        String last=doc.select("a").last().ownText();
        String middle=doc.text();
        middle=middle.replace(first,"");
        middle=middle.replace(last,"");
        middle=middle.replace(".","");
        String urlfirst = doc.select("a").first().attr("abs:href");
        String urllast = doc.select("a").last().attr("abs:href");

       // Log.i("URl 1=",""+urlfirst);
       // Log.i("URL 2=",""+urllast);
       // Log.i("plainText=", "" + middle);
        final SpannableStringBuilder finalString = new SpannableStringBuilder();
        finalString.append(first);
        finalString.setSpan(new MyClickableSpan(urlfirst),
                0, finalString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        finalString.setSpan(new ForegroundColorSpan(_context.getResources().getColor(R.color.red)), 0, finalString.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        finalString.append(" ").append(middle).append(" ");
        final int start=finalString.length();
        finalString.append(last);
        finalString.setSpan(new MyClickableSpan(urllast),
               start, finalString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        finalString.setSpan(new ForegroundColorSpan(_context.getResources().getColor(R.color.red)), start, finalString.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        holder.notification_txt.setText(finalString);
        holder.notification_txt.setMovementMethod(LinkMovementMethod.getInstance());
        holder.notification_time.setText(data.getTime());

        return row;
    }
    static class HolderView
    {
        CircleImageView userimg;
        TextView notification_txt,notification_time;
    }
    class MyClickableSpan extends ClickableSpan {
        String text;

        public MyClickableSpan(String text) {
            this.text = text;
        }

        // clickable span
        public void onClick(View textView) {
            //callGridFragment(text);
            //Toast.makeText(_context, "Cliked spanned", Toast.LENGTH_SHORT).show();
            Intent httpIntent = new Intent(Intent.ACTION_VIEW);
            httpIntent.setData(Uri.parse(text));

            _context.startActivity(httpIntent);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(_context.getResources().getColor(R.color.red));// set
            // text
            // color
            ds.setUnderlineText(false); // set to false to remove underline
        }
    }

}
