package app.android.muscularstrength.fragment;


import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import app.android.muscularstrength.R;
import app.android.muscularstrength.activity.DashBoardActivity;
import app.android.muscularstrength.adapter.ArticleAdapter;
import app.android.muscularstrength.listner.EndlessScrollListener;
import app.android.muscularstrength.model.Article;
import app.android.muscularstrength.model.ArticleParser;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.webservice.WebServices;

/**
 * Created by logan on 12/7/15.
 */
public class ArticleFragment extends Fragment {
    private static final String TAG="ArticleFragment";
    View rootView;
    int from;
    EditText search_article;
    ListView list_articles;
    ArticleAdapter adapter;
    ArrayList<Article>dataArticle;
    private int page_no=1;
    ProgressDialog pDialog;
    boolean isSearch=false;
    String quary;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.article_fragment, container, false);
       // getActivity().getActionBar().show();
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity. mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("ARTICLES");
        list_articles=(ListView)rootView.findViewById(R.id.list_article);
        search_article=(EditText)rootView.findViewById(R.id.article_search);
        adapter=new ArticleAdapter(getActivity());
        list_articles.setAdapter(adapter);
        pDialog=new ProgressDialog(getActivity());
        pDialog.setMessage("loading...");

      // DashBoardActivity. mainView.setBackgroundColor(getResources().getColor(R.color.tansparent));
        Bundle args = getArguments();
        from=args.getInt("from");
        Log.i(TAG,"FROM="+from);
        /*rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == event.ACTION_UP
                        && keyCode == KeyEvent.KEYCODE_BACK) {
                    if (from == 0) {
                      *//*  FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.contentframe, new DashBoardFragment());
                        ft.commit();*//*
                    } else {
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                }
                return true;
            }
        });*/
        list_articles.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                page_no = page;
                if(!isSearch) {
                    getArticle(page_no);
                }else{
                    getSearchArticle(page_no,quary);
                }
            }
        });
        search_article.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(search_article, InputMethodManager.SHOW_FORCED);
                }else{
                    ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(search_article.getWindowToken(), 0);
                }
            }
        });


        final Drawable x = getResources().getDrawable(android.R.drawable.ic_menu_search);
        x.setBounds(0, 0, x.getIntrinsicWidth(), x.getIntrinsicHeight());
        search_article.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        setEditTextFocus(true);
                        if (search_article.getCompoundDrawables()[2] == null) {
                            return false;
                        }
                        if (event.getAction() != MotionEvent.ACTION_UP) {
                            return false;
                        }
                        if (event.getX() > search_article.getWidth() - search_article.getPaddingRight() - x.getIntrinsicWidth()) {
                           // search_article.setCompoundDrawables(null, null, null, null);
                            quary=search_article.getText().toString().trim();
                           // hideSoftKeyboard(search_article);
                            setEditTextFocus(false);
                            getSearchArticle(1, quary);

                        }
                        return false;
                    }
                });

        getArticle(page_no);
        return  rootView;
    }
    public void setEditTextFocus(boolean isFocused) {
        search_article.setCursorVisible(isFocused);
        search_article.setFocusable(isFocused);
        search_article.setFocusableInTouchMode(isFocused);

        if (isFocused) {
            search_article.requestFocus();
        }
    }

//get articles
    private void getArticle(final int page){
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String,String>params=new HashMap<String, String>();
                params.put("page",""+page);
                params.put("display","15");
                JSONParser parser = new JSONParser();
                JSONObject json=parser.makeHttpRequest(WebServices.article,"GET",params);
                Gson gson = new Gson();
                ArticleParser data=gson.fromJson(json.toString(),ArticleParser.class);
                if(data.getResult().equalsIgnoreCase("SUCCESS")){
                dataArticle=new ArrayList<Article>();
                    dataArticle.addAll(data.getData().getArticles());

                }
                else{
                    mainHandler.sendMessage(mainHandler.obtainMessage(0));
                }
            }
        }).start();
    }
    //search Artciles
    private void getSearchArticle(final int page,final String quary){
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String,String>params=new HashMap<String, String>();
                params.put("page",""+page);
                params.put("display","10");
                params.put("search",quary);
                JSONParser parser = new JSONParser();
                JSONObject json=parser.makeHttpRequest(WebServices.article,"GET",params);
                Gson gson = new Gson();
                ArticleParser data=gson.fromJson(json.toString(),ArticleParser.class);
                if(data.getResult().equalsIgnoreCase("SUCCESS")){
                    dataArticle=new ArrayList<Article>();
                    dataArticle.addAll(data.getData().getArticles());
                    mainHandler.sendMessage(mainHandler.obtainMessage(2));
                }
                else{
                    mainHandler.sendMessage(mainHandler.obtainMessage(0));
                }
            }
        }).start();
    }

    private void setListAdapter(){
        for(int i=0;i<dataArticle.size();i++){
            adapter.add(dataArticle.get(i));
        }
       // Toast.makeText(getActivity(),"COUNT A="+adapter.getCount(),Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();

    }
    private void setSearchedListAdapter(){
        for(int i=0;i<dataArticle.size();i++){
            adapter.add(dataArticle.get(i));
        }
        adapter.notifyDataSetChanged();
        list_articles.setSelection(0);
      //  Toast.makeText(getActivity(),"COUNT S="+adapter.getCount(),Toast.LENGTH_SHORT).show();

    }

    private Handler mainHandler = new Handler() {
        public void handleMessage(Message message) {
            try {

                if (isAdded()) {
                    pDialog.dismiss();
                    pDialog.cancel();
                    switch (message.what) {
                        case 0:
                            break;
                        case 1:
                            isSearch=false;
                            setListAdapter();
                            break;
                        case 2:
                            adapter.clear();
                            isSearch=true;
                            setSearchedListAdapter();
                            break;
                    }
                }
            } catch (Resources.NotFoundException e) {

            }
        }
    };

}
