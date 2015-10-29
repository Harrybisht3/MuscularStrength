package app.android.muscularstrength.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import app.android.muscularstrength.R;
import app.android.muscularstrength.activity.RegistrationActivity;

/**
 * Created by Bisht Bhawna on 8/27/2015.
 */
public class FirstFragment extends Fragment implements View.OnClickListener{
    View rootView;
    ImageView previous,next;
    Spinner member_spinner;
    String list[]={"Gold Free","PLATINUM $ 7.99 PER MONTH"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.first_fragment, container, false);
        //RegistrationActivity.backBtn.setVisibility(View.GONE);
        previous=(ImageView)rootView.findViewById(R.id.previous);
        next=(ImageView)rootView.findViewById(R.id.next);
        member_spinner=(Spinner)rootView.findViewById(R.id.member_sp);
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.myspinner_style,list);
        adapter.setDropDownViewResource(R.layout.myspinner_style);
        member_spinner.setAdapter(adapter);
        previous.setOnClickListener(this);
        next.setOnClickListener(this);
        member_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
               // ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.previous:
               // RegistrationActivity.regpager.setCurrentItem( RegistrationActivity.regpager.getCurrentItem()-1);
                break;
            case R.id.next:
                RegistrationActivity.regpager.setCurrentItem( RegistrationActivity.regpager.getCurrentItem()+1);
                RegistrationActivity.backBtn.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
}
