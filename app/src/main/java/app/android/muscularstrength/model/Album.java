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
public class Album implements Parcelable{
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

    protected Album(Parcel in) {
        id = in.readString();
        coverImage = in.readString();
        title = in.readString();
        photos=new ArrayList<PhotoData>();
        in.readTypedList(photos,PhotoData.CREATOR);
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(coverImage);
        dest.writeString(title);
        dest.writeTypedList(photos);
    }
}
