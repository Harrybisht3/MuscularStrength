package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by laxman singh on 11/16/2015.
 */
public class LiftParser {
    @SerializedName("result")
@Expose
private String result;
@SerializedName("data")
@Expose
private DataLift data;

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
        public DataLift getData() {
            return data;
        }

        /**
         *
         * @param data
         * The data
         */
        public void setData(DataLift data) {
            this.data = data;
        }

}