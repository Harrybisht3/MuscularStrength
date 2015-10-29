package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sa on 8/21/2015.
 */
public class DataNewsFeed {

    @Expose
    private String User;
    @Expose
    private List<Newsfeed> Newsfeed = new ArrayList<Newsfeed>();

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
     * The Newsfeed
     */
    public List<Newsfeed> getNewsfeed() {
        return Newsfeed;
    }

    /**
     *
     * @param Newsfeed
     * The Newsfeed
     */
    public void setNewsfeed(List<Newsfeed> Newsfeed) {
        this.Newsfeed = Newsfeed;
    }

}