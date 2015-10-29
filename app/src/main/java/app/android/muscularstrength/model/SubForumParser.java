package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sa on 8/11/2015.
 */
public class SubForumParser {

    @Expose
    private String result;
    @Expose
    private List<SubForum> forum = new ArrayList<SubForum>();

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
     * The forum
     */
    public List<SubForum> getForum() {
        return forum;
    }

    /**
     *
     * @param forum
     * The forum
     */
    public void setForum(List<SubForum> forum) {
        this.forum = forum;
    }

}