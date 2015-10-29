package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Bisht Bhawna on 8/10/2015.
 */
public class Message {

    @Expose
    private String senderFullName;
    @Expose
    private String user;
    @Expose
    private String preview;
    @Expose
    private String timestamp;
    @Expose
    private String image;

    /**
     *
     * @return
     * The senderFullName
     */
    public String getSenderFullName() {
        return senderFullName;
    }

    /**
     *
     * @param senderFullName
     * The senderFullName
     */
    public void setSenderFullName(String senderFullName) {
        this.senderFullName = senderFullName;
    }

    /**
     *
     * @return
     * The user
     */
    public String getUser() {
        return user;
    }

    /**
     *
     * @param user
     * The user
     */
    public void setUser(String user) {
        this.user = user;
    }

    /**
     *
     * @return
     * The preview
     */
    public String getPreview() {
        return preview;
    }

    /**
     *
     * @param preview
     * The preview
     */
    public void setPreview(String preview) {
        this.preview = preview;
    }

    /**
     *
     * @return
     * The timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     *
     * @param timestamp
     * The timestamp
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
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
}