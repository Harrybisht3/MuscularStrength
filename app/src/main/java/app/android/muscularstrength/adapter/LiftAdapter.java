package app.android.muscularstrength.adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import app.android.muscularstrength.R;
import app.android.muscularstrength.model.Lift;
import app.android.muscularstrength.model.User;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.session.SessionManager;
import app.android.muscularstrength.webservice.WebServices;

/**
 * Created by laxman singh on 11/17/2015.
 */
public class LiftAdapter extends ArrayAdapter<Lift> {
    Context _context;
    String msg;
    SessionManager session;
    User userObj;
    String value_year,value_month,value_day,unit;
    public LiftAdapter(Context context) {
        super(context, 0);
        this._context=context;
        session = new SessionManager(context);
        Gson gson = new Gson();
        userObj = gson.fromJson(session.getSession(), User.class);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        HolderView holder = null;
        final Lift data=getItem(position);
        if(row == null)
        {
            LayoutInflater inflater = ((Activity)_context).getLayoutInflater();
            row = inflater.inflate(R.layout.lift_row, parent, false);

            holder = new HolderView();
            holder.image = (ImageView)row.findViewById(R.id.image);
            holder.edit=(ImageView)row.findViewById(R.id.edit);
            holder.title = (TextView)row.findViewById(R.id.title);
            holder.weight = (TextView)row.findViewById(R.id.weight);
            holder.date = (TextView)row.findViewById(R.id.date);
            holder.motivate_value = (TextView)row.findViewById(R.id.motivate_txt);
            row.setTag(holder);

        }
        else
        {
            holder = (HolderView)row.getTag();
        }
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showEditAlert(data);
            }
        });
        Glide.with(_context).load(data.getImageLink()).diskCacheStrategy(DiskCacheStrategy.ALL).into(holder.image);
        holder.title.setText(data.getTitle());
        holder.weight.setText(data.getWeight());
        holder.date.setText(data.getDate());
        holder.motivate_value.setText(String.valueOf(data.getMotivates()));
        return row;
    }
    static class HolderView
    {
        ImageView image,edit;
        TextView title,weight,date,motivate,motivate_value;
    }
    public void showEditAlert(final Lift data) {
        final Dialog dialog = new Dialog(_context);
       final List<String> year = new ArrayList<String>();
        final List<String> months = new ArrayList<String>();
        final List<String> days = new ArrayList<String>();
        final List<String>  w_unit = new ArrayList<String>();
        w_unit.add("lbs");
        w_unit.add("kg");
        w_unit.add("st");
        int thisYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = 1925; i <= thisYear; i++) {
            year.add(Integer.toString(i));
        }

            for (int i = 1; i < 32; i++) {
                if (i < 13) {
                    months.add(Integer.toString(i));
                }
                days.add(Integer.toString(i));
            }

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.edit_lift);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.width = RelativeLayout.LayoutParams.MATCH_PARENT;
        wlp.gravity = Gravity.CENTER;
        window.setAttributes(wlp);
        dialog.show();
        final EditText weight_txt = (EditText) dialog
                .findViewById(R.id.weight_txt);
        String intValue = data.getWeight().replaceAll("[^0-9]", "");
        weight_txt.setText(""+intValue);
        final EditText example_edit = (EditText) dialog
                .findViewById(R.id.example_edit);
        ImageView close = (ImageView) dialog
                .findViewById(R.id.close);
        Spinner year_sp = (Spinner)dialog. findViewById(R.id.sp_year);
        Spinner  month_sp = (Spinner) dialog.findViewById(R.id.sp_month);
        Spinner day_sp = (Spinner)dialog. findViewById(R.id.sp_day);
        Spinner  sp_weight = (Spinner)dialog. findViewById(R.id.sp_weight);
    String unit_v=data.getWeight().replace(""+intValue,"").trim();
        int index_u=w_unit.indexOf(unit_v);
        settingSpinner(year,year_sp);
        settingSpinner(months,month_sp);
        settingSpinner(days,day_sp);
        settingSpinner(w_unit, sp_weight);
        sp_weight.setSelection(index_u);
        Button submit=(Button)dialog.findViewById(R.id.add_comment);
        submit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (weight_txt.getText().toString().trim().length() != 0) {
                    if(example_edit.getText().toString().trim().length() != 0){
                        postEditLift(data.getId(),weight_txt.getText().toString().trim(),example_edit.getText().toString().trim(),data.getTitle());
                    }
                    else{
                        example_edit.setError(Html
                                .fromHtml("<font color='#ff0000'> Please Add Video Id.</font>"));
                    }
                    dialog.dismiss();
                    //  new SendComment().execute(comment_txt.getText().toString());
                    //replyPost(postid, comment_txt.getText().toString().trim(),header.getId());
                    //

                } else {
                    weight_txt.setError(Html
                            .fromHtml("<font color='#ff0000'> Add weight.</font>"));
                }
            }
        });
        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        sp_weight.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                unit=w_unit.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        year_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
            value_year=year.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        month_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
           value_month=months.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        day_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
              value_day=days.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    private void settingSpinner(List<String> list, Spinner spinner) {
        ArrayAdapter adapter = new ArrayAdapter<String>(_context, R.layout.myspinner_style, list);
        adapter.setDropDownViewResource(R.layout.myspinner_style);
        spinner.setAdapter(adapter);

    }
    private void postEditLift(final String recordId, final String weight, final String videoId, final String section){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("action", "" +"UPDATE_LIFT" );
                params.put("rmRecordID", recordId);
                params.put("frmWeight", weight);
                params.put("frmWeightUnit", unit);
                params.put("datepicker",value_month+"/"+ value_day+"/"+value_year);
                params.put("frmVideoID", videoId);
                params.put("frmUserID", userObj.getUserId());
                params.put("frmSection", section);
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.Update_lift, "POST", params);
                try {
                    if (json != null) {
                        if (json.getString("result").equalsIgnoreCase("SUCCESS")) {
                            // datanewsFeed.addAll(data.getData().getNewsfeed());
                            msg = json.getString("data");
                            mainHandler.sendMessage(mainHandler.obtainMessage(1));
                        } else {
                            mainHandler.sendMessage(mainHandler.obtainMessage(0));
                        }
                    } else {
                        mainHandler.sendMessage(mainHandler.obtainMessage(0));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
    private Handler mainHandler = new Handler() {
        public void handleMessage(Message message) {
            try {

                // if (_context.isAdded()) {
                //  pDialog.dismiss();
                //pDialog.cancel();
                switch (message.what) {
                    case 0:
                        break;
                    case 1:
                        Toast.makeText(_context, msg, Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        // isSearch=true;
                        // setListAdapter();
                        break;
                }
                //}
            } catch (Resources.NotFoundException e) {

            }
        }
    };


}
