package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sa on 8/10/2015.
 */
public class Video {

    @Expose
    private String id;
    @Expose
    private String title;
    @SerializedName("image_link")
    @Expose
    private String imageLink;
    @SerializedName("video_link")
    @Expose
    private String videoLink;

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
     * The imageLink
     */
    public String getImageLink() {
        return imageLink;
    }

    /**
     *
     * @param imageLink
     * The image_link
     */
    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    /**
     *
     * @return
     * The videoLink
     */
    public String getVideoLink() {
        return videoLink;
    }

    /**
     *
     * @param videoLink
     * The video_link
     */
    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

}