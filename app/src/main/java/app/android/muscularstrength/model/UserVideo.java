package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

/**
 * Created by sa on 8/24/2015.
 */
public class UserVideo {

    @Expose
    private String id;
    @Expose
    private String title;
    @Expose
    private String image;
    @Expose
    private String video;

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
     * The video
     */
    public String getVideo() {
        return video;
    }

    /**
     *
     * @param video
     * The video
     */
    public void setVideo(String video) {
        this.video = video;
    }

}