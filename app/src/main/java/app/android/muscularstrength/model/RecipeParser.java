package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Bisht Bhawna on 8/9/2015.
 */
public class RecipeParser {

    @Expose
    private String result;
    @Expose
    private DataRecipe data;

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
    public DataRecipe getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(DataRecipe data) {
        this.data = data;
    }

}
