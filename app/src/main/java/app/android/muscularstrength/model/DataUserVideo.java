package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sa on 8/24/2015.
 */
public class DataUserVideo {

    @Expose
    private String User;
    @Expose
    private List<UserVideoMaster> Video = new ArrayList<UserVideoMaster>();

    /**
     *
     * @return
     * The User
     */
    public String getUser() {
        return User;
    }

    /**
     *
     * @param User
     * The User
     */
    public void setUser(String User) {
        this.User = User;
    }

    /**
     *
     * @return
     * The Video
     */
    public List<UserVideoMaster> getVideo() {
        return Video;
    }

    /**
     *
     * @param Video
     * The Video
     */
    public void setVideo(List<UserVideoMaster> Video) {
        this.Video = Video;
    }

}