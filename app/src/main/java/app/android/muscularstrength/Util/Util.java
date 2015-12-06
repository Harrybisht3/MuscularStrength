package app.android.muscularstrength.Util;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import app.android.muscularstrength.R;
import app.android.muscularstrength.activity.DashBoardActivity;
import app.android.muscularstrength.fragment.EditProfileFragment;
import app.android.muscularstrength.fragment.FragmentHome;
import app.android.muscularstrength.fragment.FriendsFragment;
import app.android.muscularstrength.fragment.MessageFragment;
import app.android.muscularstrength.fragment.NotificationFragment;

/**
 * Created by degree on 7/13/15.
 */
public class Util {
    public static int getColorWithAlpha(int color, float ratio) {
        int newColor = 0;
        int alpha = Math.round(Color.alpha(color) * ratio);
        int r = Color.red(color);
        int g = Color.green(color);
        int b = Color.blue(color);
        newColor = Color.argb(alpha, r, g, b);
        return newColor;
    }

    public static Typeface getTypeFace(Context context, String type) {
        Typeface typeface;
        if (type.equals(Constants.BOLD)) {
            typeface = Typeface.createFromAsset(context.getAssets(), "Roboto-Bold.ttf");
        } else if (type.equals(Constants.LIGHT)) {
            typeface = Typeface.createFromAsset(context.getAssets(), "Roboto-Light.ttf");
        } else if (type.equals(Constants.MEDIUM)) {
            typeface = Typeface.createFromAsset(context.getAssets(), "Roboto-Medium.ttf");
        } else {
            typeface = Typeface.createFromAsset(context.getAssets(), "Roboto-Regular.ttf");
        }
        return typeface;
    }

    public static void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.setTag("0");
        listView.requestLayout();
    }

    public static void setListViewHeight(ExpandableListView listView, int group
    ) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
       /* int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);*/

        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(0, 0);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(0, 0);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    public static void setExpendableListViewHeight(ExpandableListView listView,
                                                   int group) {
        ExpandableListAdapter listAdapter = (ExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    public static float getDensity(Activity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics.density;
    }

    public static DisplayMetrics getDisplay(Activity context) {
        Display display = context.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return metrics;
    }

    public static Date strtodate(String date) {
        Date cdate = null;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        //String dateInString = "07/06/2013";

        try {

            cdate = formatter.parse(date);
       /* System.out.println(date);
        System.out.println(formatter.format(date));
*/
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cdate;
    }

    public static void setFragment(FragmentManager fragmentManager, int pos) {
        FragmentTransaction ftx = fragmentManager.beginTransaction();
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        bundle.putInt("from", 0);
        switch (pos) {
            case Constants.FRIEND:
                fragment = new FriendsFragment();
                break;
            case Constants.MESSAGE:
                fragment = new MessageFragment();
                fragment.setArguments(bundle);
                break;
            case Constants.NOTIFICATION:
                fragment = new NotificationFragment();
                fragment.setArguments(bundle);
                break;
            case Constants.EDITPROFILE:
                fragment = new EditProfileFragment();
                bundle.putString("Type", "Fragment");
                fragment.setArguments(bundle);
                break;
            case Constants.DASHHOME:
                DashBoardActivity.actionBar.hide();
                DashBoardActivity.menuView.setVisibility(View.VISIBLE);
                DashBoardActivity.mainView.setBackgroundResource(R.drawable.dash_bg);
                DashBoardActivity.back_Btn.setVisibility(View.GONE);
                //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                DashBoardActivity.actionbarmenu.setVisibility(View.VISIBLE);
                fragment = new FragmentHome();
                break;

            default:
                break;
        }
        replaceFragment(fragment, fragmentManager);
        ftx.commit();
    }

    public static void replaceFragment(Fragment fragment, FragmentManager fragmentManager) {
        String backStateName = fragment.getClass().getName();
        // FragmentManager manager = fragment.getActivity().getSupportFragmentManager();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStateName, 0);
        if (!fragmentPopped) { //fragment not in back stack, create it.
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.contentframe, fragment);
            ft.addToBackStack(backStateName);
            ft.commit();
        }
    }

    public static String pad(String value) {
        if (value.length() == 1) {
            value = "0" + value;
        }
        return value;
    }
}
