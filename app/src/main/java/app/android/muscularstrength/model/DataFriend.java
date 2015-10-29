package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sa on 8/24/2015.
 */
public class DataFriend {

    @Expose
    private String User;
    @Expose
    private List<Friend> friend = new ArrayList<Friend>();

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
     * The friend
     */
    public List<Friend> getFriend() {
        return friend;
    }

    /**
     *
     * @param friend
     * The friend
     */
    public void setFriend(List<Friend> friend) {
        this.friend = friend;
    }

}