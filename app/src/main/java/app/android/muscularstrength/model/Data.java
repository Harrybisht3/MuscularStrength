package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bisht Bhawna on 8/8/2015.
 */
public class Data {

    @Expose
    private String page;
    @SerializedName("total_articles")
    @Expose
    private String totalArticles;
    @Expose
    private String display;
    @Expose
    private List<Article> articles = new ArrayList<Article>();

    /**
     *
     * @return
     * The page
     */
    public String getPage() {
        return page;
    }

    /**
     *
     * @param page
     * The page
     */
    public void setPage(String page) {
        this.page = page;
    }

    /**
     *
     * @return
     * The totalArticles
     */
    public String getTotalArticles() {
        return totalArticles;
    }

    /**
     *
     * @param totalArticles
     * The total_articles
     */
    public void setTotalArticles(String totalArticles) {
        this.totalArticles = totalArticles;
    }

    /**
     *
     * @return
     * The display
     */
    public String getDisplay() {
        return display;
    }

    /**
     *
     * @param display
     * The display
     */
    public void setDisplay(String display) {
        this.display = display;
    }

    /**
     *
     * @return
     * The articles
     */
    public List<Article> getArticles() {
        return articles;
    }

    /**
     *
     * @param articles
     * The articles
     */
    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

}