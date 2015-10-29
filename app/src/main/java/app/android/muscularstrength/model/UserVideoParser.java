package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

/**
 * Created by sa on 8/24/2015.
 */
public class UserVideoParser {

    @Expose
    private String result;
    @Expose
    private DataUserVideo data;

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
    public DataUserVideo getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(DataUserVideo data) {
        this.data = data;
    }

}