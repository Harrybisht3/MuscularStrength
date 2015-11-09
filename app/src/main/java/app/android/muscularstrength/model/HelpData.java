package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by laxman singh on 11/8/2015.
 */
public class HelpData {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
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