package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sa on 8/11/2015.
 */
public class DataFriendRequest{

    @Expose
    private String User;
    @SerializedName("friend_request")
    @Expose
    private List<FriendRequest> friendRequest = new ArrayList<FriendRequest>();

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
     * The friendRequest
     */
    public List<FriendRequest> getFriendRequest() {
        return friendRequest;
    }

    /**
     *
     * @param friendRequest
     * The friend_request
     */
    public void setFriendRequest(List<FriendRequest> friendRequest) {
        this.friendRequest = friendRequest;
    }

}