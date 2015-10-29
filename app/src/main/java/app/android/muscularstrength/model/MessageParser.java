package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Bisht Bhawna on 8/10/2015.
 */
public class MessageParser {

    @Expose
    private String result;
    @Expose
    private DataMessage data;

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
    public DataMessage getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(DataMessage data) {
        this.data = data;
    }

}
