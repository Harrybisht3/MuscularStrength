package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laxman singh on 11/8/2015.
 */
public class HelpMaster {

    @SerializedName("User")
    @Expose
    private String User;
    @SerializedName("walk_through")
    @Expose
    private List<HelpData> walkThrough = new ArrayList<HelpData>();

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
     * The walkThrough
     */
    public List<HelpData> getWalkThrough() {
        return walkThrough;
    }

    /**
     *
     * @param walkThrough
     * The walk_through
     */
    public void setWalkThrough(List<HelpData> walkThrough) {
        this.walkThrough = walkThrough;
    }
}
