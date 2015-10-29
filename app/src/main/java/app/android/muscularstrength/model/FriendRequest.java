package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Bisht Bhawna on 8/10/2015.
 */
public class FriendRequest {

    @Expose
    private String id;
    @Expose
    private String name;
    @Expose
    private String user;
    @Expose
    private String image;
    @SerializedName("accept-url")
    @Expose
    private String acceptUrl;
    @SerializedName("deny-url")
    @Expose
    private String denyUrl;

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
     * The acceptUrl
     */
    public String getAcceptUrl() {
        return acceptUrl;
    }

    /**
     *
     * @param acceptUrl
     * The accept-url
     */
    public void setAcceptUrl(String acceptUrl) {
        this.acceptUrl = acceptUrl;
    }

    /**
     *
     * @return
     * The denyUrl
     */
    public String getDenyUrl() {
        return denyUrl;
    }

    /**
     *
     * @param denyUrl
     * The deny-url
     */
    public void setDenyUrl(String denyUrl) {
        this.denyUrl = denyUrl;
    }

}


