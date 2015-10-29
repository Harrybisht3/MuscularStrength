package app.android.muscularstrength.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.android.muscularstrength.R;
import app.android.muscularstrength.activity.DashBoardActivity;

/**
 * Created by Bisht Bhawna on 7/19/2015.
 */
public class ProfileFragment extends Fragment {
    View rootView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.common_fragment, container, false);
        // getActivity().getActionBar().show();
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity. mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("PROFILE");
     /*   rootView.setFocusableInTouchMode(true);
        rootView.requestFocus();
        rootView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == event.ACTION_UP
                        && keyCode == KeyEvent.KEYCODE_BACK) {

                  *//*  FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.contentframe, new DashBoardFragment());
                    ft.commit();*//*
                    getActivity().getSupportFragmentManager().popBackStack();

                }
                return true;
            }
        });*/
        return rootView;
    }
}
