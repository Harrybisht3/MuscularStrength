package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sa on 8/10/2015.
 */
public class ExclusiveCatParse {

    @Expose
    private String result;
    @Expose
    private List<Category> categories = new ArrayList<Category>();

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
     * The categories
     */
    public List<Category> getCategories() {
        return categories;
    }

    /**
     *
     * @param categories
     * The categories
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

}
