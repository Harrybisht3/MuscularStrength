package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by laxman singh on 11/16/2015.
 */
public class Lift {
    @SerializedName("id")
@Expose
private String id;
@SerializedName("title")
@Expose
private String title;
@SerializedName("date")
@Expose
private String date;
@SerializedName("image_link")
@Expose
private String imageLink;
@SerializedName("video_link")
@Expose
private String videoLink;
@SerializedName("weight")
@Expose
private String weight;
@SerializedName("motivates")
@Expose
private Integer motivates;

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
         * The date
         */
        public String getDate() {
            return date;
        }

        /**
         *
         * @param date
         * The date
         */
        public void setDate(String date) {
            this.date = date;
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
         * The weight
         */
        public String getWeight() {
            return weight;
        }

        /**
         *
         * @param weight
         * The weight
         */
        public void setWeight(String weight) {
            this.weight = weight;
        }

        /**
         *
         * @return
         * The motivates
         */
        public Integer getMotivates() {
            return motivates;
        }

        /**
         *
         * @param motivates
         * The motivates
         */
        public void setMotivates(Integer motivates) {
            this.motivates = motivates;
        }

}