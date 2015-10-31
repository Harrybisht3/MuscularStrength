package app.android.muscularstrength.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import app.android.muscularstrength.R;
import app.android.muscularstrength.Util.Constants;
import app.android.muscularstrength.Util.Util;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.session.SessionManager;
import app.android.muscularstrength.webservice.WebServices;

/**
 * Created by Bisht Bhawna on 7/11/2015.
 */
public class LoginActivity extends Activity implements View.OnClickListener{
    EditText userName_edit,password_edit;
    Button signinBtn;
    ImageView checkbox;
    TextView forget_password,remembertxt,copyrighttxt;
    TextView signupText;
    float density;
    int width;
    int height;
    SessionManager session;
    ProgressDialog pDialog;
    String userName,password;
    String message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);
        session=new SessionManager(this);
        signinBtn=(Button)findViewById(R.id.signinBtn);
        signinBtn.setOnClickListener(this);
        density = Util.getDensity(this);
        pDialog=new ProgressDialog(this);
        pDialog.setMessage("loading...");
        signupText=(TextView)findViewById(R.id.signup_txt);
        forget_password=(TextView)findViewById(R.id.forgottxt);
        remembertxt=(TextView)findViewById(R.id.remembertxt);
        copyrighttxt=(TextView)findViewById(R.id.copyrighttxt);
        userName_edit=(EditText)findViewById(R.id.userName);
        password_edit=(EditText)findViewById(R.id.password);
//set username drwable
        Drawable drawable = ContextCompat.getDrawable(this,R.drawable.icon_username);
        drawable.setBounds(0, 0, (int)(drawable.getIntrinsicWidth()*0.7),
                (int)(drawable.getIntrinsicHeight()*0.7));
        ScaleDrawable sd = new ScaleDrawable(drawable, 0, (int)(50*density), (int)(50*density));
        userName_edit.setCompoundDrawables(sd.getDrawable(), null, null, null);
        //set password drwable
        Drawable drawable1 = ContextCompat.getDrawable(this,R.drawable.icon_password);
        drawable1.setBounds(0, 0, (int)(drawable1.getIntrinsicWidth()*0.7),
                (int)(drawable1.getIntrinsicHeight()*0.7));
        ScaleDrawable sd1 = new ScaleDrawable(drawable1, 0, (int)(50*density), (int)(50*density));
        password_edit.setCompoundDrawables(sd1.getDrawable(), null, null, null);
        setTypeFaces();
        setSpannableString();
        compareString();
       // login_nowBtn.setOnClickListener(this);
        userName_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //boolean isSet=false;
                if(s.length()>0){
                  userName_edit.setBackground(ContextCompat.getDrawable(LoginActivity.this,R.drawable.edittext_bg));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        password_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //boolean isSet=false;
                if(s.length()>0){
                    password_edit.setBackground(ContextCompat.getDrawable(LoginActivity.this,R.drawable.edittext_bg));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signinBtn:
               // callDashBoard();
                validate();
               break;
            case R.id.checkbox:
                break;
            case R.id.forgottxt:
                break;
            case R.id.signup_txt:
                break;
        }
    }
    private void callDashBoard(){
        Intent intent=new Intent(this,DashBoardActivity.class);
        startActivity(intent);
        // finish();
    }
    public void setSpannableString(){
        String clikableString="Sign up";
        final SpannableStringBuilder finalString = new SpannableStringBuilder();
        finalString.append(getResources().getString(R.string.signup_txt)).append(" ");
            final int start = finalString.length();
            finalString.append(clikableString);
            final int end = finalString.length();
            finalString.setSpan(new MyClickableSpan(clikableString),
                    start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            finalString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.color1)), start, end,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        finalString.setSpan(new UnderlineSpan(), start, end, 0);
            finalString.setSpan(new StyleSpan(Typeface.NORMAL),
                    start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        signupText.setText(finalString);
        signupText.setMovementMethod(LinkMovementMethod.getInstance());
        signupText.setTypeface(Util.getTypeFace(LoginActivity.this,Constants.REGULAR));
    }
    class MyClickableSpan extends ClickableSpan {
        String text;

        public MyClickableSpan(String text) {
            this.text = text;
        }

        // clickable span
        public void onClick(View textView) {
            //callGridFragment(text);
           // Toast.makeText(LoginActivity.this,"Cliked spanned",Toast.LENGTH_SHORT).show();
            callRegistrationPage();
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(getResources().getColor(R.color.color1));// set
            // text
            // color
            ds.setUnderlineText(false); // set to false to remove underline
        }
    }
    private void callRegistrationPage(){
        Intent intent=new Intent(this,RegistrationActivity.class);
        startActivity(intent);
        // finish();
    }
    private void setTypeFaces(){
        forget_password.setTypeface(Util.getTypeFace(LoginActivity.this,Constants.MEDIUM));
        remembertxt.setTypeface(Util.getTypeFace(LoginActivity.this,Constants.MEDIUM));
        password_edit.setTypeface(Util.getTypeFace(LoginActivity.this,Constants.MEDIUM));
        userName_edit.setTypeface(Util.getTypeFace(LoginActivity.this,Constants.MEDIUM));
        forget_password.setTypeface(Util.getTypeFace(LoginActivity.this,Constants.MEDIUM));
        copyrighttxt.setTypeface(Util.getTypeFace(LoginActivity.this,Constants.MEDIUM));
        signinBtn.setTypeface(Util.getTypeFace(LoginActivity.this,Constants.MEDIUM));

    }
    private void compareString(){
        String str1="str";
        String str2="str";
        if(str1=="str"){
            Log.i("EQUAL", "TRUE");
        }
        else{
            Log.i("EQUAL","FALSE");
        }
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void validate(){
        userName=userName_edit.getText().toString().trim();
        password=password_edit.getText().toString().trim();
        if(userName.length()>0){
            if(password.length()>0){
                checkLogin();
            }
            else{
                password_edit.setError("Enter Password");
                password_edit.setBackground(ContextCompat.getDrawable(this,R.drawable.edittextwarn_bg));
            }
        }
        else{
            userName_edit.setError("Enter Username");
            userName_edit.setBackground(ContextCompat.getDrawable(this, R.drawable.edittextwarn_bg));
        }

    }
    private void checkLogin(){
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String,String> params=new HashMap<String, String>();
                params.put("user",""+userName);
                params.put("pass",password);
               // params.put("search",quary);
                JSONParser parser = new JSONParser();
                JSONObject json=parser.makeHttpRequest(WebServices.signin,"GET",params);
               // Gson gson = new Gson();
              //  ArticleParser data=gson.fromJson(json.toString(),ArticleParser.class);
                try {
                if(json!=null){
                   /* dataArticle=new ArrayList<Article>();
                    dataArticle.addAll(data.getData().getArticles());*/
                    if(json.has("User Id")) {
                        session.createLoginSession(json.toString());

                        mainHandler.sendMessage(mainHandler.obtainMessage(1));
                    }
                    else
                        if(json.getString("Status").equalsIgnoreCase("Invalid User")||json.getString("Status").equalsIgnoreCase("Invalid Password")){
                            message=json.getString("Status");
                            mainHandler.sendMessage(mainHandler.obtainMessage(2));
                        }

                }

                else{
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


                    pDialog.dismiss();
                    pDialog.cancel();
                    switch (message.what) {
                        case 0:
                            break;
                        case 1:
                            callDashBoard();
                            break;
                        case 2:
                            showError();
                            break;
                    }
            } catch (Resources.NotFoundException e) {

            }
        }
    };
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void showError(){
        if(message.equalsIgnoreCase("Invalid User")){
            userName_edit.setText("");
            userName_edit.setError(message);
            userName_edit.setBackground(ContextCompat.getDrawable(this, R.drawable.edittextwarn_bg));
        }
        else{
            password_edit.setText("");
            password_edit.setError(message);
            password_edit.setBackground(ContextCompat.getDrawable(this,R.drawable.edittextwarn_bg));
        }
    }

}
