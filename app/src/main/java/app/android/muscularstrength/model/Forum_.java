package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bisht Bhawna on 8/9/2015.
 */
public class Forum_ {

    @Expose
    private String id;
    @Expose
    private String title;
    @SerializedName("Created by")
    @Expose
    private String CreatedBy;
    @Expose
    private String Threads;
    @Expose
    private Integer Posts;
    @SerializedName("Posted By")
    @Expose
    private String PostedBy;
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
     * The CreatedBy
     */
    public String getCreatedBy() {
        return CreatedBy;
    }

    /**
     *
     * @param CreatedBy
     * The Created by
     */
    public void setCreatedBy(String CreatedBy) {
        this.CreatedBy = CreatedBy;
    }

    /**
     *
     * @return
     * The Threads
     */
    public String getThreads() {
        return Threads;
    }

    /**
     *
     * @param Threads
     * The Threads
     */
    public void setThreads(String Threads) {
        this.Threads = Threads;
    }

    /**
     *
     * @return
     * The Post
     */
    public Integer getPosts() {
        return Posts;
    }

    /**
     *
     * @param Posts
     * The Post
     */
    public void setPosts(Integer Posts) {
        this.Posts = Posts;
    }

    /**
     *
     * @return
     * The PostedBy
     */
    public String getPostedBy() {
        return PostedBy;
    }

    /**
     *
     * @param PostedBy
     * The Posted By
     */
    public void setPostedBy(String PostedBy) {
        this.PostedBy = PostedBy;
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