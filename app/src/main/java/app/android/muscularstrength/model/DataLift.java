package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laxman singh on 11/16/2015.
 */
public class DataLift {
    @SerializedName("User")
    @Expose
    private String User;
    @SerializedName("lifts")
    @Expose
    private List<Lift> lifts = new ArrayList<Lift>();

    /**
     * @return The User
     */
    public String getUser() {
        return User;
    }

    /**
     * @param User The User
     */
    public void setUser(String User) {
        this.User = User;
    }

    /**
     * @return The lifts
     */
    public List<Lift> getLifts() {
        return lifts;
    }

    /**
     * @param lifts The lifts
     */
    public void setLifts(List<Lift> lifts) {
        this.lifts = lifts;
    }
}