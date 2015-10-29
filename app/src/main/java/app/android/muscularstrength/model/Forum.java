package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bisht Bhawna on 8/9/2015.
 */
 public class Forum {

    @Expose
    private String id;
    @Expose
    private String heading;
    @Expose
    private String categories;
    @Expose
    private List<Forum_> forum = new ArrayList<Forum_>();

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The heading
     */
    public String getHeading() {
        return heading;
    }

    /**
     *
     * @param heading
     * The heading
     */
    public void setHeading(String heading) {
        this.heading = heading;
    }

    /**
     *
     * @return
     * The categories
     */
    public String getCategories() {
        return categories;
    }

    /**
     *
     * @param categories
     * The categories
     */
    public void setCategories(String categories) {
        this.categories = categories;
    }

    /**
     *
     * @return
     * The forum
     */
    public List<Forum_> getForum() {
        return forum;
    }

    /**
     *
     * @param forum
     * The forum
     */
    public void setForum(List<Forum_> forum) {
        this.forum = forum;
    }

}
