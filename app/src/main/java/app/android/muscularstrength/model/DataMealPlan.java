package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bisht Bhawna on 8/27/2015.
 */
public class DataMealPlan {

    @Expose
    private String User;
    @Expose
    private List<MealPlanMaster> data = new ArrayList<MealPlanMaster>();

    /**
     *
     * @return
     * The User
     */
    public String getUser() {
        return User;
    }

    /**
     *
     * @param User
     * The User
     */
    public void setUser(String User) {
        this.User = User;
    }

    /**
     *
     * @return
     * The data
     */
    public List<MealPlanMaster> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<MealPlanMaster> data) {
        this.data = data;
    }

}