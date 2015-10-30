package app.android.muscularstrength.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

/**
 * Created by Bisht Bhawna on 8/23/2015.
 */
public class PhotoParser implements Parcelable{

    @Expose
    private String result;
    @Expose
    private PhotoMaster data;

    protected PhotoParser(Parcel in) {
        result = in.readString();
        data = in.readParcelable(PhotoMaster.class.getClassLoader());
    }

    public static final Creator<PhotoParser> CREATOR = new Creator<PhotoParser>() {
        @Override
        public PhotoParser createFromParcel(Parcel in) {
            return new PhotoParser(in);
        }

        @Override
        public PhotoParser[] newArray(int size) {
            return new PhotoParser[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(result);
        dest.writeParcelable(data, flags);
    }
}