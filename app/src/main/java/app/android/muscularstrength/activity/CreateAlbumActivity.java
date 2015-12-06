package app.android.muscularstrength.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import app.android.muscularstrength.R;
import app.android.muscularstrength.model.User;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.session.SessionManager;
import app.android.muscularstrength.webservice.WebServices;

/**
 * Created by laxman singh on 10/29/2015.
 */
public class CreateAlbumActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView actionbarmenu, back_Btn;
    TextView title;
    EditText album_name, album_caption;
    Spinner share_sp;
    String[] shareoption = {"Friends only", "Only me"};
    ProgressDialog pDialog;
    Button createalbumBtn;
    int pos;
    User userObj;
    SessionManager session;
    String errorMessage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_album);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setCustomView(R.layout.toolbar);
        View v = getSupportActionBar().getCustomView();
        actionbarmenu = (ImageView) v.findViewById(R.id.menu_icon);
        back_Btn = (ImageView) v.findViewById(R.id.back_icon);
        title = (TextView) v.findViewById(R.id.titleactionbar);
        title.setText("CREATE ALBUM");
        session = new SessionManager(this);
        Gson gson = new Gson();
        userObj = gson.fromJson(session.getSession(), User.class);
        album_name = (EditText) findViewById(R.id.album_name);
        album_caption = (EditText) findViewById(R.id.album_caption);
        share_sp = (Spinner) findViewById(R.id.shareoption_sp);
        createalbumBtn = (Button) findViewById(R.id.createalbumBtn);
        ArrayAdapter adapter = new ArrayAdapter<String>(this, R.layout.myspinner_style, shareoption);
        adapter.setDropDownViewResource(R.layout.myspinner_style);
        share_sp.setAdapter(adapter);
        actionbarmenu.setVisibility(View.GONE);
        back_Btn.setVisibility(View.VISIBLE);
        back_Btn.setOnClickListener(this);
        Toolbar parent = (Toolbar) v.getParent();//first get parent toolbar of current action bar
        parent.setContentInsetsAbsolute(0, 0);
        pDialog = new ProgressDialog(this);
        createalbumBtn.setOnClickListener(this);
        pDialog.setMessage("loading...");
        share_sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                pos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createalbumBtn:
                if (album_name.getText().toString().trim().length() > 0) {
                    if (album_caption.getText().toString().length() > 0) {
                        createAlbum();
                    } else {
                        album_caption.setError("Enter caption");
                    }
                } else {
                    album_name.setError("Enter Album Name");
                }
                break;
            case R.id.back_icon:
                finish();
            default:
                break;
        }
    }

    private void createAlbum() {
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("userid", "" + userObj.getUserId());
                params.put("title", album_name.getText().toString().trim());
                params.put("description", album_caption.getText().toString().trim());
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.addPhotos, "GET", params);
                try {
                    if (json != null) {
                        if (json.getString("result").equalsIgnoreCase("SUCCESS")) {
                            mainHandler.sendMessage(mainHandler.obtainMessage(1));
                        } else {

                            errorMessage = json.getString("data");
                            mainHandler.sendMessage(mainHandler.obtainMessage(0));
                        }
                    } else {
                        errorMessage = getResources().getString(R.string.errorMessage);
                        mainHandler.sendMessage(mainHandler.obtainMessage(0));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                // uploadFile(selectedFiles.get(countUpload), WebServices.addPhotos, params);

            }

        }).start();

    }

    private Handler mainHandler = new Handler() {
        public void handleMessage(Message message) {
            try {


                pDialog.dismiss();
                pDialog.cancel();
                switch (message.what) {
                    case 0:
                        Toast.makeText(CreateAlbumActivity.this, "" + errorMessage, Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Intent returnIntent = new Intent();
                        //returnIntent.putExtra("result",result);
                        setResult(Activity.RESULT_OK, returnIntent);
                        finish();

                        break;
                    case 2:

                        break;
                }

            } catch (Resources.NotFoundException e) {

            }
        }
    };
}
