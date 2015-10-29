package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sa on 8/10/2015.
 */
public class DataNotification {

    @Expose
    private String User;
    @Expose
    private List<Notification> notification = new ArrayList<Notification>();

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
     * The notification
     */
    public List<Notification> getNotification() {
        return notification;
    }

    /**
     *
     * @param notification
     * The notification
     */
    public void setNotification(List<Notification> notification) {
        this.notification = notification;
    }

}