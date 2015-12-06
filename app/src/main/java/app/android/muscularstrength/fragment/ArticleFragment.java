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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
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
    private static final String TAG = "ArticleFragment";
    View rootView;
    int from;
    EditText search_article;
    ListView list_articles;
    ArticleAdapter adapter;
    ArrayList<Article> dataArticle;
    private int page_no = 0;
    ProgressDialog pDialog;
    boolean isSearch = false;
    String quary, errorMessage;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.article_fragment, container, false);
        // getActivity().getActionBar().show();
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity.mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("ARTICLES");
        list_articles = (ListView) rootView.findViewById(R.id.list_article);
        search_article = (EditText) rootView.findViewById(R.id.article_search);
        adapter = new ArticleAdapter(getActivity());
        list_articles.setAdapter(adapter);
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("loading...");
        dataArticle = new ArrayList<Article>();
        // DashBoardActivity. mainView.setBackgroundColor(getResources().getColor(R.color.tansparent));
        Bundle args = getArguments();
        from = args.getInt("from");
        Log.i(TAG, "FROM=" + from);

        list_articles.setOnScrollListener(new EndlessScrollListener(15) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                page_no = page;
                Log.i(TAG,"PAGE NO="+page_no);
                if (!isSearch) {
                    getArticle(page_no);
                } else {
                    getSearchArticle(page_no, quary);
                }
            }
        });
        search_article.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(search_article, InputMethodManager.SHOW_FORCED);
                } else {
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
                            quary = search_article.getText().toString().trim();
                            // hideSoftKeyboard(search_article);
                            setEditTextFocus(false);
                            if (quary.length() > 0) {
                                isSearch=true;
                                getSearchArticle(1, quary);
                            } else {
                                search_article.setError("Enter search text");
                            }

                        }
                        return false;
                    }
                });
        search_article.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    quary=search_article.getText().toString().trim();
                    if (quary.length() > 0) {
                        getSearchArticle(1, quary);
                    } else {
                        search_article.setError("Enter search text");
                    }
                    handled = true;
                }
                return handled;
            }
        });
       /* search_article.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_SEARCH)) {
                    if (quary.length() > 0) {
                        getSearchArticle(1, quary);
                    } else {
                        search_article.setError("Enter search text");
                    }
                    // Perform action on key press
                   // Toast.makeText(HelloFormStuff.this, edittext.getText(), Toast.LENGTH_SHORT).show();
                    return true;
                }
                return false;
            }
        });*/
        search_article.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //boolean isSet=false;
                if (s.length() > 0) {
                    search_article.setError(null);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        getArticle(page_no);
        return rootView;
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
    private void getArticle(final int page) {
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("page", "" + page);
                params.put("display", "15");
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.article, "GET", params);
                try {
                    if (json != null) {
                        if (json.getString("result").equalsIgnoreCase("SUCCESS")) {
                            Gson gson = new Gson();
                            ArticleParser data = gson.fromJson(json.toString(), ArticleParser.class);
                            // if (data.getResult().equalsIgnoreCase("SUCCESS")) {
                            dataArticle.addAll(data.getData().getArticles());
                            mainHandler.sendMessage(mainHandler.obtainMessage(1));
                       /* } else {

                        }*/
                        } else {
                            mainHandler.sendMessage(mainHandler.obtainMessage(0));
                        }
                    } else {
                        errorMessage = getResources().getString(R.string.errorMessage);
                        mainHandler.sendMessage(mainHandler.obtainMessage(0));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    //search Artciles
    private void getSearchArticle(final int page, final String quary) {
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("page", "" + page);
                params.put("display", "10");
                params.put("search", quary);
                JSONParser parser = new JSONParser();
                JSONObject json = parser.makeHttpRequest(WebServices.article, "GET", params);
                try {
                    if (json != null) {
                        if (json.getString("result").equalsIgnoreCase("SUCCESS")) {
                            Gson gson = new Gson();
                            ArticleParser data = gson.fromJson(json.toString(), ArticleParser.class);
                            // if (data.getResult().equalsIgnoreCase("SUCCESS")) {
                            dataArticle.clear();
                           // adapter.clear();
                            //adapter.notifyDataSetChanged();
                            //dataArticle = new ArrayList<Article>();
                            dataArticle.addAll(data.getData().getArticles());
                            mainHandler.sendMessage(mainHandler.obtainMessage(2));
                       /* } else {
                            mainHandler.sendMessage(mainHandler.obtainMessage(0));
                        }*/
                        } else {
                            mainHandler.sendMessage(mainHandler.obtainMessage(0));
                        }
                    } else {
                        errorMessage = getResources().getString(R.string.errorMessage);
                        mainHandler.sendMessage(mainHandler.obtainMessage(0));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void setListAdapter() {
        for (int i = 0; i < dataArticle.size(); i++) {
            adapter.add(dataArticle.get(i));
        }
        // Toast.makeText(getActivity(),"COUNT A="+adapter.getCount(),Toast.LENGTH_SHORT).show();
        adapter.notifyDataSetChanged();

    }

    private void setSearchedListAdapter() {
        for (int i = 0; i < dataArticle.size(); i++) {
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
                            isSearch = false;
                           // adapter.clear();
                           // adapter.notifyDataSetChanged();
                            setListAdapter();
                            break;
                        case 2:
                            adapter.clear();
                            adapter.notifyDataSetChanged();
                            isSearch = true;
                            page_no=0;
                            setSearchedListAdapter();
                            break;
                    }
                }
            } catch (Resources.NotFoundException e) {

            }
        }
    };

}
