package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bisht Bhawna on 8/9/2015.
 */
public class ForumParser {

    @Expose
    private String result;
    @Expose
    private List<Forum> forums = new ArrayList<Forum>();

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
     * The forums
     */
    public List<Forum> getForums() {
        return forums;
    }

    /**
     *
     * @param forums
     * The forums
     */
    public void setForums(List<Forum> forums) {
        this.forums = forums;
    }

}
