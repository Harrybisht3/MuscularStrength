package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sa on 8/24/2015.
 */
public class UserVideoMaster{

    @Expose
    private String id;
    @Expose
    private String title;
    @Expose
    private String image;
    @Expose
    private String CoverVideo;
    @Expose
    private List<UserVideo> videos = new ArrayList<UserVideo>();

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
     * The CoverVideo
     */
    public String getCoverVideo() {
        return CoverVideo;
    }

    /**
     *
     * @param CoverVideo
     * The CoverVideo
     */
    public void setCoverVideo(String CoverVideo) {
        this.CoverVideo = CoverVideo;
    }

    /**
     *
     * @return
     * The videos
     */
    public List<UserVideo> getVideos() {
        return videos;
    }

    /**
     *
     * @param videos
     * The videos
     */
    public void setVideos(List<UserVideo> videos) {
        this.videos = videos;
    }

}