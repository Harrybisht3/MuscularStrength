package app.android.muscularstrength.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bisht Bhawna on 8/23/2015.
 */
public class PhotoMaster implements Parcelable{

    @Expose
    private String User;
    @Expose
    private List<Album> data = new ArrayList<Album>();

    protected PhotoMaster(Parcel in) {
        User = in.readString();
        data = in.createTypedArrayList(Album.CREATOR);
    }

    public static final Creator<PhotoMaster> CREATOR = new Creator<PhotoMaster>() {
        @Override
        public PhotoMaster createFromParcel(Parcel in) {
            return new PhotoMaster(in);
        }

        @Override
        public PhotoMaster[] newArray(int size) {
            return new PhotoMaster[size];
        }
    };

    /**
     *
     * @return
     * The User
     */
    public String getUser() {
        return User;
    }

    /**
     *
     * @param User
     * The User
     */
    public void setUser(String User) {
        this.User = User;
    }

    /**
     *
     * @return
     * The data
     */
    public List<Album> getData() {
        return data;
    }

    /**
     *
     * @param data
     * The data
     */
    public void setData(List<Album> data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(User);
        dest.writeTypedList(data);
    }
}

