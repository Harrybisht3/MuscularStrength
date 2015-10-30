package app.android.muscularstrength.model;

/**
 * Created by laxman singh on 10/30/2015.
 */
public class ImageModel {
    String path;
    String caption;
    public String getpath() {
        return path;
    }

    /**
     *
     * @param path
     * The id
     */
    public void setpath(String path) {
        this.path = path;
    }

    /**
     *
     * @return
     * The title
     */
    public String getCaption() {
        return caption;
    }

    /**
     *
     * @param caption
     * The title
     */
    public void setCaption(String caption) {
        this.caption = caption;
    }

}
