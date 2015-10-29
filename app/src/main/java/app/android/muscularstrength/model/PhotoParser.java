package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

/**
 * Created by Bisht Bhawna on 8/23/2015.
 */
public class PhotoParser{

    @Expose
    private String result;
    @Expose
    private PhotoMaster data;

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
    public PhotoMaster getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(PhotoMaster data) {
        this.data = data;
    }

}