package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

/**
 * Created by sa on 8/11/2015.
 */
public class FriendRequestParser {

    @Expose
    private String result;
    @Expose
    private DataFriendRequest data;

    /**
     *
     * @return
     * The result
     */
    public String getResult() {
        return result;
    }

    /**
     *
     * @param result
     * The result
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     *
     * @return
     * The data
     */
    public DataFriendRequest getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(DataFriendRequest data) {
        this.data = data;
    }

}