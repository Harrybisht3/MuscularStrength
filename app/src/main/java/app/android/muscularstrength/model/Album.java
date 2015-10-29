package app.android.muscularstrength.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bisht Bhawna on 9/23/2015.
 */
public class Album {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("cover_image")
    @Expose
    private String coverImage;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("photos")
    @Expose
    private List<PhotoData> photos = new ArrayList<PhotoData>();

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
     * The coverImage
     */
    public String getCoverImage() {
        return coverImage;
    }

    /**
     *
     * @param coverImage
     * The cover_image
     */
    public void setCoverImage(String coverImage) {
        this.coverImage = coverImage;
    }

    /**
     *
     * @return
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The photos
     */
    public List<PhotoData> getPhotos() {
        return photos;
    }

    /**
     *
     * @param photos
     * The photos
     */
    public void setPhotos(List<PhotoData> photos) {
        this.photos = photos;
    }



}
