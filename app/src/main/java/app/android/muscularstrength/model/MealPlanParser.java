package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Bisht Bhawna on 8/27/2015.
 */
public class MealPlanParser {

    @Expose
    private String result;
    @Expose
    private DataMealPlan data;

    /**
     *
     * @return
     * The result
     */
    public String getResult() {
        return result;
    }

    /**
     *
     * @param result
     * The result
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     *
     * @return
     * The data
     */
    public DataMealPlan getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(DataMealPlan data) {
        this.data = data;
    }

}