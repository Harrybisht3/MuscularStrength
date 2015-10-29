package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

/**
 * Created by sa on 8/10/2015.
 */
public class NotificationParser {

    @Expose
    private String result;
    @Expose
    private DataNotification data;

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
    public DataNotification getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(DataNotification data) {
        this.data = data;
    }

}