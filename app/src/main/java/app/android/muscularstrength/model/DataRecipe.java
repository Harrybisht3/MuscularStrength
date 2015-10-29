package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bisht Bhawna on 8/9/2015.
 */
public class DataRecipe{

    @Expose
    private String page;
    @SerializedName("total_recipes")
    @Expose
    private String totalRecipes;
    @Expose
    private String display;
    @Expose
    private List<Recipe> recipes = new ArrayList<Recipe>();

    /**
     *
     * @return
     * The page
     */
    public String getPage() {
        return page;
    }

    /**
     *
     * @param page
     * The page
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     *
     * @return
     * The totalRecipes
     */
    public String getTotalRecipes() {
        return totalRecipes;
    }

    /**
     *
     * @param totalRecipes
     * The total_recipes
     */
    public void setTotalRecipes(String totalRecipes) {
        this.totalRecipes = totalRecipes;
    }

    /**
     *
     * @return
     * The display
     */
    public String getDisplay() {
        return display;
    }

    /**
     *
     * @param display
     * The display
     */
    public void setDisplay(String display) {
        this.display = display;
    }

    /**
     *
     * @return
     * The recipes
     */
    public List<Recipe> getRecipes() {
        return recipes;
    }

    /**
     *
     * @param recipes
     * The recipes
     */
    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
    }

}