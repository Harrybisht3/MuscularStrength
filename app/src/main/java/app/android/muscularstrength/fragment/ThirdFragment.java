package app.android.muscularstrength.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import app.android.muscularstrength.R;
import app.android.muscularstrength.activity.RegistrationActivity;

/**
 * Created by Bisht Bhawna on 8/27/2015.
 */
public class ThirdFragment extends Fragment implements View.OnClickListener{
    View rootView;
    ImageView previous,next;
    ArrayList<String> countries;
    Spinner country_spinner;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView= inflater.inflate(R.layout.third_fragment, container, false);
        previous = (ImageView) rootView.findViewById(R.id.previous);
        next = (ImageView) rootView.findViewById(R.id.next);
        country_spinner=(Spinner)rootView.findViewById(R.id.country_sp);
        previous.setOnClickListener(this);
        next.setOnClickListener(this);
        getCountires();
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
                RegistrationActivity.regpager.setCurrentItem( RegistrationActivity.regpager.getCurrentItem()+1);
                RegistrationActivity.backBtn.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
    }
    private void getCountires(){
        Locale[] locales = Locale.getAvailableLocales();
        countries = new ArrayList<String>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length()>0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries);
        for (String country : countries) {
            System.out.println(country);
        }
        System.out.println("# countries found: " + countries.size());
        setCountrySpinner();
    }
    private void setCountrySpinner(){
        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(), R.layout.myspinner_style,countries);
        adapter.setDropDownViewResource(R.layout.myspinner_style);
        country_spinner.setAdapter(adapter);
    }
}