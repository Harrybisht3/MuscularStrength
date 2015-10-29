package app.android.muscularstrength.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import app.android.muscularstrength.R;
import app.android.muscularstrength.activity.RegistrationActivity;

/**
 * Created by Bisht Bhawna on 8/27/2015.
 */
public class SecondFragment extends Fragment implements View.OnClickListener {
    View rootView;
    ImageView previous, next;
    String genders[]={"Male","Female"};
    String goals[]={"Goal"};
    Spinner gender_spinner,goal_spinner;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.second_fragment, container, false);
        previous = (ImageView) rootView.findViewById(R.id.previous);
        next = (ImageView) rootView.findViewById(R.id.next);
        gender_spinner=(Spinner)rootView.findViewById(R.id.gender_sp);
        goal_spinner=(Spinner)rootView.findViewById(R.id.goal_sp);
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.myspinner_style,genders);
        adapter.setDropDownViewResource(R.layout.myspinner_style);
        gender_spinner.setAdapter(adapter);
        ArrayAdapter adapter1 = new ArrayAdapter<String>(getActivity(), R.layout.myspinner_style,goals);
        adapter1.setDropDownViewResource(R.layout.myspinner_style);
        goal_spinner.setAdapter(adapter1);
        previous.setOnClickListener(this);
        next.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.previous:
                RegistrationActivity.backBtn.setVisibility(View.GONE);
                RegistrationActivity.regpager.setCurrentItem(RegistrationActivity.regpager.getCurrentItem() - 1);
                break;
            case R.id.next:
                RegistrationActivity.regpager.setCurrentItem(RegistrationActivity.regpager.getCurrentItem() + 1);
                RegistrationActivity.backBtn.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }

}