package app.android.muscularstrength.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.android.muscularstrength.R;
import app.android.muscularstrength.activity.DashBoardActivity;

/**
 * Created by laxman singh on 11/4/2015.
 */
public class HelpFragment extends Fragment {
    View rootView;
    ProgressDialog pDialog;
    String errorMessage;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity. mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("HELP");
        rootView=inflater.inflate(R.layout.help_fragment,container,false);
        pDialog=new ProgressDialog(getActivity());
        pDialog.setMessage("loading...");
        return rootView;
    }
}
