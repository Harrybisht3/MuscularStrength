package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sa on 8/12/2015.
 */
public class ThreadParser {

    @Expose
    private String result;
    @Expose
    private List<Post> posts = new ArrayList<Post>();

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
     * The posts
     */
    public List<Post> getPosts() {
        return posts;
    }

    /**
     *
     * @param posts
     * The posts
     */
    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

}

