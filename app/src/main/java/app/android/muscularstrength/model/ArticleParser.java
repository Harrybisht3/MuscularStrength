package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Bisht Bhawna on 8/9/2015.
 */
public class ArticleParser {

    @Expose
    private String result;
    @Expose
    private Data data;

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
    public Data getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(Data data) {
        this.data = data;
    }

}
