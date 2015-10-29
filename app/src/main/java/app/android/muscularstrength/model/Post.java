package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by sa on 8/12/2015.
 */
public class Post {

    @Expose
    private String id;
    @SerializedName("user image")
    @Expose
    private String userImage;
    @Expose
    private String Title;
    @Expose
    private String Description;
    @Expose
    private String time;
    @SerializedName("video_link")
    @Expose
    private String videoLink;
    @SerializedName("image_link")
    @Expose
    private String imageLink;
    @Expose
    private String Twitter;
    @Expose
    private String YouTube;
    @Expose
    private String Pinterest;
    @Expose
    private String Instagram;
    @Expose
    private Integer LVL;
    @Expose
    private String Gender;
    @Expose
    private String Location;
    @Expose
    private String Goal;
    @Expose
    private String Posts;
    @SerializedName("Date Joined")
    @Expose
    private String DateJoined;
    @Expose
    private String Membership;

    /**
     *
     * @return
     * The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The userImage
     */
    public String getUserImage() {
        return userImage;
    }

    /**
     *
     * @param userImage
     * The user image
     */
    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    /**
     *
     * @return
     * The Title
     */
    public String getTitle() {
        return Title;
    }

    /**
     *
     * @param Title
     * The Title
     */
    public void setTitle(String Title) {
        this.Title = Title;
    }

    /**
     *
     * @return
     * The Description
     */
    public String getDescription() {
        return Description;
    }

    /**
     *
     * @param Description
     * The Description
     */
    public void setDescription(String Description) {
        this.Description = Description;
    }

    /**
     *
     * @return
     * The time
     */
    public String getTime() {
        return time;
    }

    /**
     *
     * @param time
     * The time
     */
    public void setTime(String time) {
        this.time = time;
    }

    /**
     *
     * @return
     * The videoLink
     */
    public String getVideoLink() {
        return videoLink;
    }

    /**
     *
     * @param videoLink
     * The video_link
     */
    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    /**
     *
     * @return
     * The imageLink
     */
    public String getImageLink() {
        return imageLink;
    }

    /**
     *
     * @param imageLink
     * The image_link
     */
    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    /**
     *
     * @return
     * The Twitter
     */
    public String getTwitter() {
        return Twitter;
    }

    /**
     *
     * @param Twitter
     * The Twitter
     */
    public void setTwitter(String Twitter) {
        this.Twitter = Twitter;
    }

    /**
     *
     * @return
     * The YouTube
     */
    public String getYouTube() {
        return YouTube;
    }

    /**
     *
     * @param YouTube
     * The YouTube
     */
    public void setYouTube(String YouTube) {
        this.YouTube = YouTube;
    }

    /**
     *
     * @return
     * The Pinterest
     */
    public String getPinterest() {
        return Pinterest;
    }

    /**
     *
     * @param Pinterest
     * The Pinterest
     */
    public void setPinterest(String Pinterest) {
        this.Pinterest = Pinterest;
    }

    /**
     *
     * @return
     * The Instagram
     */
    public String getInstagram() {
        return Instagram;
    }

    /**
     *
     * @param Instagram
     * The Instagram
     */
    public void setInstagram(String Instagram) {
        this.Instagram = Instagram;
    }

    /**
     *
     * @return
     * The LVL
     */
    public Integer getLVL() {
        return LVL;
    }

    /**
     *
     * @param LVL
     * The LVL
     */
    public void setLVL(Integer LVL) {
        this.LVL = LVL;
    }

    /**
     *
     * @return
     * The Gender
     */
    public String getGender() {
        return Gender;
    }

    /**
     *
     * @param Gender
     * The Gender
     */
    public void setGender(String Gender) {
        this.Gender = Gender;
    }

    /**
     *
     * @return
     * The Location
     */
    public String getLocation() {
        return Location;
    }

    /**
     *
     * @param Location
     * The Location
     */
    public void setLocation(String Location) {
        this.Location = Location;
    }

    /**
     *
     * @return
     * The Goal
     */
    public String getGoal() {
        return Goal;
    }

    /**
     *
     * @param Goal
     * The Goal
     */
    public void setGoal(String Goal) {
        this.Goal = Goal;
    }

    /**
     *
     * @return
     * The Post
     */
    public String getPosts() {
        return Posts;
    }

    /**
     *
     * @param Posts
     * The Post
     */
    public void setPosts(String Posts) {
        this.Posts = Posts;
    }

    /**
     *
     * @return
     * The DateJoined
     */
    public String getDateJoined() {
        return DateJoined;
    }

    /**
     *
     * @param DateJoined
     * The Date Joined
     */
    public void setDateJoined(String DateJoined) {
        this.DateJoined = DateJoined;
    }

    /**
     *
     * @return
     * The Membership
     */
    public String getMembership() {
        return Membership;
    }

    /**
     *
     * @param Membership
     * The Membership
     */
    public void setMembership(String Membership) {
        this.Membership = Membership;
    }

}