package app.android.muscularstrength.fragment;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import app.android.muscularstrength.R;
import app.android.muscularstrength.activity.DashBoardActivity;
import app.android.muscularstrength.model.User;
import app.android.muscularstrength.session.SessionManager;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by laxman singh on 11/4/2015.
 */
public class LiftFragment extends Fragment{
    View rootView;
    ProgressDialog pDialog;
    CircleImageView userProfileImg;
    TextView user,account_type,level;
    String errorMessage;
    ListView list_lift;
    SessionManager session;
    User userObj;;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        DashBoardActivity.actionBar.show();
        DashBoardActivity.menuView.setVisibility(View.GONE);
        DashBoardActivity. mainView.setBackground(null);
        DashBoardActivity.actiontitle.setText("LIFTS");
        rootView=inflater.inflate(R.layout.lift_fragment,container,false);
       init();
        return rootView;
    }
    private void init(){
        session=new SessionManager(getActivity());
        Gson gson=new Gson();
        userObj=gson.fromJson(session.getSession(),User.class);
        //header View
        View headerlayout= rootView.findViewById(R.id.header);
        userProfileImg=(CircleImageView)headerlayout.findViewById(R.id.profileImg);
        user = (TextView)headerlayout.findViewById(R.id.user);
        account_type = (TextView)headerlayout.findViewById(R.id.account_type);
        level = (TextView)headerlayout.findViewById(R.id.level);
       // list_message.setAdapter(adapter);
        Glide.with(getActivity()).load(userObj.getFullImage()).into(userProfileImg);
        user.setText(userObj.getFirstName() + "" + userObj.getLastName());
        account_type.setText(userObj.getAccountType());
        level.setText(userObj.getUserLevel());
        pDialog=new ProgressDialog(getActivity());
        pDialog.setMessage("loading...");

    }

}
