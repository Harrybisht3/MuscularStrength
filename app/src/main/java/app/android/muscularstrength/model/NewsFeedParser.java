package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

/**
 * Created by sa on 8/21/2015.
 */
public class NewsFeedParser {

    @Expose
    private String result;
    @Expose
    private DataNewsFeed data;

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
    public DataNewsFeed getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(DataNewsFeed data) {
        this.data = data;
    }

}