package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bisht Bhawna on 8/23/2015.
 */
public class PhotoMaster {

    @Expose
    private String User;
    @Expose
    private List<Album> data = new ArrayList<Album>();

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
    public List<Album> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<Album> data) {
        this.data = data;
    }

}

