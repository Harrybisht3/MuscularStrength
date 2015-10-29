package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

/**
 * Created by sa on 8/10/2015.
 */
public class Category {

    @Expose
    private String id;
    @Expose
    private String title;

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
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

}