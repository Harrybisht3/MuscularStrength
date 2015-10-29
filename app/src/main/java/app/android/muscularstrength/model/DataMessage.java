package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bisht Bhawna on 8/10/2015.
 */
public class DataMessage {

    @Expose
    private String User;
    @Expose
    private List<Message> Messages = new ArrayList<Message>();

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
     * The Messages
     */
    public List<Message> getMessages() {
        return Messages;
    }

    /**
     *
     * @param Messages
     * The Messages
     */
    public void setMessages(List<Message> Messages) {
        this.Messages = Messages;
    }

}
