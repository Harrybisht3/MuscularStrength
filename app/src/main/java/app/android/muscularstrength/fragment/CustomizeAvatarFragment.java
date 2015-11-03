package app.android.muscularstrength.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;

import app.android.muscularstrength.R;
import app.android.muscularstrength.activity.DashBoardActivity;
import app.android.muscularstrength.model.User;
import app.android.muscularstrength.session.SessionManager;
import app.android.muscularstrength.webservice.WebServices;

/**
 * Created by laxman singh on 11/3/2015.
 */
public class CustomizeAvatarFragment extends Fragment{
    View rootView;
    WebView webView;
    SessionManager session;
    User userObj;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity.mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("CUSTOMIZE AVATAR");
        rootView =inflater.inflate(R.layout.customize_avatar_fragment,container,false);
        session = new SessionManager(getActivity());
        Gson gson = new Gson();
        userObj = gson.fromJson(session.getSession(), User.class);
        webView=(WebView)rootView.findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        startWebView(WebServices.customize_avatar+userObj.getUserId());
        //webView.loadUrl(WebServices.customize_avatar+userObj.getUserId());
        return rootView;
    }
    private void startWebView(String url) {

        //Create new webview Client to show progress dialog
        //When opening a url or click on link

        webView.setWebViewClient(new WebViewClient() {
            ProgressDialog progressDialog;

            //If you will not use this method url links are opeen in new brower not in webview
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            //Show loader on url load
            public void onLoadResource (WebView view, String url) {
                if (progressDialog == null) {
                    // in standard case YourActivity.this
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Loading...");
                    progressDialog.show();
                }
            }
            public void onPageFinished(WebView view, String url) {
                try{
                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                        progressDialog = null;
                    }
                }catch(Exception exception){
                    exception.printStackTrace();
                }
            }

        });

        // Javascript inabled on webview
        webView.getSettings().setJavaScriptEnabled(true);

        // Other webview options
        /*
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setBuiltInZoomControls(true);
        */

        /*
         String summary = "<html><body>You scored <b>192</b> points.</body></html>";
         webview.loadData(summary, "text/html", null);
         */

        //Load url in webview
        webView.loadUrl(url);


    }
}
