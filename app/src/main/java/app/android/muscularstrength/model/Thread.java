package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sa on 8/11/2015.
 */
public class Thread {

    @Expose
    private String id;
    @Expose
    private String title;
    @Expose
    private String description;
    @Expose
    private String Views;
    @Expose
    private String Posts;
    @SerializedName("Post By")
    @Expose
    private String PostBy;
    @Expose
    private String time;
    @SerializedName("user image")
    @Expose
    private String userImage;

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

    /**
     *
     * @return
     * The description
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @param description
     * The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     *
     * @return
     * The Views
     */
    public String getViews() {
        return Views;
    }

    /**
     *
     * @param Views
     * The Views
     */
    public void setViews(String Views) {
        this.Views = Views;
    }

    /**
     *
     * @return
     * The Post
     */
    public String getPosts() {
        return Posts;
    }

    /**
     *
     * @param Posts
     * The Post
     */
    public void setPosts(String Posts) {
        this.Posts = Posts;
    }

    /**
     *
     * @return
     * The PostBy
     */
    public String getPostBy() {
        return PostBy;
    }

    /**
     *
     * @param PostBy
     * The Post By
     */
    public void setPostBy(String PostBy) {
        this.PostBy = PostBy;
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
     * The userImage
     */
    public String getUserImage() {
        return userImage;
    }

    /**
     *
     * @param userImage
     * The user image
     */
    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

}