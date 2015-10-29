package app.android.muscularstrength.fragment;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import app.android.muscularstrength.R;
import app.android.muscularstrength.activity.DashBoardActivity;
import app.android.muscularstrength.adapter.ExclusiveCatAdapter;
import app.android.muscularstrength.model.Category;
import app.android.muscularstrength.model.ExclusiveCatParse;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.webservice.WebServices;

/**
 * Created by logan on 13/7/15.
 */
public class ExclusiveFragment extends Fragment implements AdapterView.OnItemClickListener {
    View rootView;
    int from;
    ListView list_exclusiveCat;
    ExclusiveCatAdapter adapter;
    ArrayList<Category> catExclusive;
    private int page_no=1;
    ProgressDialog pDialog;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity.mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("EXCLUSIVE");
        if(rootView==null) {
            rootView = inflater.inflate(R.layout.exclusive_fragment, container, false);
            // getActivity().getActionBar().show();

            list_exclusiveCat = (ListView) rootView.findViewById(R.id.list_exclusiveCat);
            list_exclusiveCat.setOnItemClickListener(this);
            adapter = new ExclusiveCatAdapter(getActivity());
            list_exclusiveCat.setAdapter(adapter);
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("loading...");

            // DashBoardActivity. mainView.setBackgroundColor(getResources().getColor(R.color.tansparent));
            Bundle args = getArguments();
            from = args.getInt("from");
       /* rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == event.ACTION_UP
                        && keyCode == KeyEvent.KEYCODE_BACK) {
                    if (from == 1) {
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        FragmentTransaction ft = fragmentManager.beginTransaction();
                        ft.replace(R.id.contentframe, new DashBoardFragment());
                        ft.commit();
                    } else {
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                }
                return true;
            }
        });*/



      /*  final Drawable x = getResources().getDrawable(android.R.drawable.ic_menu_search);
        x.setBounds(0, 0, x.getIntrinsicWidth(), x.getIntrinsicHeight());
        search_article.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (search_article.getCompoundDrawables()[2] == null) {
                            return false;
                        }
                        if (event.getAction() != MotionEvent.ACTION_UP) {
                            return false;
                        }
                        if (event.getX() > search_article.getWidth() - search_article.getPaddingRight() - x.getIntrinsicWidth()) {
                            // search_article.setCompoundDrawables(null, null, null, null);
                            getSearchArticle(1,search_article.getText().toString().trim());

                        }
                        return false;
                    }
                });
*/
            getCateogryExclusive(page_no);
        }
       /* list_exclusiveCat.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                page_no = page;
                // getArticle(page_no);
            }
        });*/
        return  rootView;
    }

    //get articles
    private void getCateogryExclusive(final int page){
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String,String> params=new HashMap<String, String>();
               /* params.put("page",""+page);
                params.put("display","15");*/
                JSONParser parser = new JSONParser();
                JSONObject json=parser.makeHttpRequest(WebServices.Exclusive,"GET",params);
                Gson gson = new Gson();
                ExclusiveCatParse data=gson.fromJson(json.toString(),ExclusiveCatParse.class);
                if(data.getResult().equalsIgnoreCase("SUCCESS")){
                    catExclusive=new ArrayList<Category>();
                    catExclusive.addAll(data.getCategories());
                    mainHandler.sendMessage(mainHandler.obtainMessage(1));
                }
                else{
                    mainHandler.sendMessage(mainHandler.obtainMessage(0));
                }
            }
        }).start();
    }
   /* //search Artciles
    private void getSearchArticle(final int page,final String quary){
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String,String>params=new HashMap<String, String>();
                params.put("page",""+page);
                params.put("display","15");
                params.put("search",quary);
                JSONParser parser = new JSONParser();
                JSONObject json=parser.makeHttpRequest(WebServices.article,"GET",params);
                Gson gson = new Gson();
                ArticleParser data=gson.fromJson(json.toString(),ArticleParser.class);
                if(data.getResult().equalsIgnoreCase("SUCCESS")){
                    dataArticle=new ArrayList<Article>();
                    dataArticle.addAll(data.getData().getArticles());
                    mainHandler.sendMessage(mainHandler.obtainMessage(1));
                }
                else{
                    mainHandler.sendMessage(mainHandler.obtainMessage(0));
                }
            }
        }).start();
    }
*/
    private void setListAdapter(){
        for(int i=0;i<catExclusive.size();i++){
            adapter.add(catExclusive.get(i));
        }
        adapter.notifyDataSetChanged();

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
                            setListAdapter();
                            break;
                    }
                }
            } catch (Resources.NotFoundException e) {

            }
        }
    };

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case R.id.list_exclusiveCat:
                Category item=adapter.getItem(position);
                showVideoList(item.getId());
                break;
        }
    }

    private void showVideoList(String id){
      /*  ExclusiveVideoFragment kBDetailFragment=new ExclusiveVideoFragment();
        Bundle args = new Bundle();
        args.putString("id", id);
        kBDetailFragment.setArguments(args);
        FragmentManager fm=getActivity().getSupportFragmentManager();
        fm.beginTransaction()
                .replace(DashBoardFragment.frame.getId(), kBDetailFragment, "videofragment")
                .addToBackStack(null)
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();*/
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        Fragment fragment=new ExclusiveVideoFragment();
        fragment.setArguments(bundle);
        replaceFragment(fragment);
        ft.commit();

    }
    private void replaceFragment (Fragment fragment){
        String backStateName = fragment.getClass().getName();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        boolean fragmentPopped = manager.popBackStackImmediate (backStateName, 0);
        if (!fragmentPopped){ //fragment not in back stack, create it.
            FragmentTransaction ft = manager.beginTransaction();
            ft.replace(R.id.contentframe, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }
}
