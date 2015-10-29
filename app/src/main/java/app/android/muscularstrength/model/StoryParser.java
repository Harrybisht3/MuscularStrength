package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Bisht Bhawna on 8/23/2015.
 */
public class StoryParser {

    @Expose
    private String result;
    @Expose
    private DataStory data;

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
    public DataStory getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(DataStory data) {
        this.data = data;
    }

}
