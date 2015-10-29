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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import app.android.muscularstrength.R;
import app.android.muscularstrength.activity.DashBoardActivity;
import app.android.muscularstrength.adapter.RecipeAdapter;
import app.android.muscularstrength.listner.EndlessScrollListener;
import app.android.muscularstrength.model.Recipe;
import app.android.muscularstrength.model.RecipeParser;
import app.android.muscularstrength.network.JSONParser;
import app.android.muscularstrength.webservice.WebServices;

/**
 * Created by logan on 13/7/15.
 */
public class RecipesFragment extends Fragment {
    View rootView;
    int from;
   // EditText search_article;
    ListView list_recipe;
    RecipeAdapter adapter;
    ArrayList<Recipe> dataRecipe;
    private int page_no=1;
    ProgressDialog pDialog;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.recipes_fragment, container, false);
        // getActivity().getActionBar().show();
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity. mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("RECIPES");
        list_recipe=(ListView)rootView.findViewById(R.id.list_recipe);
        adapter=new RecipeAdapter(getActivity());
        list_recipe.setAdapter(adapter);
        pDialog=new ProgressDialog(getActivity());
        pDialog.setMessage("loading...");

        // DashBoardActivity. mainView.setBackgroundColor(getResources().getColor(R.color.tansparent));
        Bundle args = getArguments();
        from=args.getInt("from");
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
        list_recipe.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                page_no = page;
                getRecipe(page_no);
            }
        });




        getRecipe(page_no);
        return  rootView;
    }

    //get articles
    private void getRecipe(final int page){
        pDialog.show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String,String> params=new HashMap<String, String>();
                params.put("page",""+page);
                params.put("display","10");
                JSONParser parser = new JSONParser();
                JSONObject json=parser.makeHttpRequest(WebServices.Recipes,"GET",params);
                Gson gson = new Gson();
                RecipeParser data=gson.fromJson(json.toString(),RecipeParser.class);
                if(data.getResult().equalsIgnoreCase("SUCCESS")){
                    dataRecipe=new ArrayList<Recipe>();
                    dataRecipe.addAll(data.getData().getRecipes());
                    mainHandler.sendMessage(mainHandler.obtainMessage(1));
                }
                else{
                    mainHandler.sendMessage(mainHandler.obtainMessage(0));
                }
            }
        }).start();
    }


    private void setListAdapter(){
        for(int i=0;i<dataRecipe.size();i++){
            adapter.add(dataRecipe.get(i));
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

}
