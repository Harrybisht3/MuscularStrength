package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sa on 8/21/2015.
 */
public class Newsfeed {

    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String time;
    @Expose
    private String comment;
    @Expose
    private String image;
    @Expose
    private List<Childcomment> childcomment = new ArrayList<Childcomment>();

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
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The time
     */
    public String getTime() {
        return time;
    }

    /**
     *
     * @param time
     * The time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     *
     * @return
     * The comment
     */
    public String getComment() {
        return comment;
    }

    /**
     *
     * @param comment
     * The comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     *
     * @return
     * The image
     */
    public String getImage() {
        return image;
    }

    /**
     *
     * @param image
     * The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     *
     * @return
     * The childcomment
     */
    public List<Childcomment> getChildcomment() {
        return childcomment;
    }

    /**
     *
     * @param childcomment
     * The childcomment
     */
    public void setChildcomment(List<Childcomment> childcomment) {
        this.childcomment = childcomment;
    }

}