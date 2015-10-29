package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Bisht Bhawna on 8/23/2015.
 */
public class DataStory {

    @Expose
    private String User;
    @Expose
    private String data;

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
     * The data
     */
    public String getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(String data) {
        this.data = data;
    }

}