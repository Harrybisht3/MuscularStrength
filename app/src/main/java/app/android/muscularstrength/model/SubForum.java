package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sa on 8/11/2015.
 */
public class SubForum {

    @Expose
    private String id;
    @Expose
    private String heading;
    @Expose
    private String categories;
    @Expose
    private List<Thread> threads = new ArrayList<Thread>();

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
     * The threads
     */
    public List<Thread> getTh() {
        return threads;
    }

    /**
     *
     * @param threads
     * The threads
     */
    public void setTh(List<Thread> threads) {
        this.threads = threads;
    }

}