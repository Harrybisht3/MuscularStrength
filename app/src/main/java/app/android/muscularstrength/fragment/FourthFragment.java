package app.android.muscularstrength.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import app.android.muscularstrength.R;
import app.android.muscularstrength.activity.RegistrationActivity;

/**
 * Created by Bisht Bhawna on 8/27/2015.
 */
public class FourthFragment extends Fragment implements View.OnClickListener{
    View rootView;
    ImageView previous,next;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.fourth_fragment, container, false);
       // RegistrationActivity.backBtn.setVisibility(View.VISIBLE);
        previous = (ImageView) rootView.findViewById(R.id.previous);
        next = (ImageView) rootView.findViewById(R.id.next);
        previous.setOnClickListener(this);
        next.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.previous:
                RegistrationActivity.regpager.setCurrentItem( RegistrationActivity.regpager.getCurrentItem()-1);
                RegistrationActivity.backBtn.setVisibility(View.VISIBLE);
                break;
            case R.id.next:
               // RegistrationActivity.regpager.setCurrentItem( RegistrationActivity.regpager.getCurrentItem()+1);
                break;
            default:
                break;
        }
    }
}