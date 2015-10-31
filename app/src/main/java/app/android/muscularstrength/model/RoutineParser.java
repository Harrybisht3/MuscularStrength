package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laxman singh on 10/31/2015.
 */
public class RoutineParser {

    @SerializedName("result")
    @Expose
    private String result;
    @SerializedName("Routines")
    @Expose
    private List<Routine> Routines = new ArrayList<Routine>();

    /**
     * @return The result
     */
    public String getResult() {
        return result;
    }

    /**
     * @param result The result
     */
    public void setResult(String result) {
        this.result = result;
    }

    /**
     * @return The Routines
     */
    public List<Routine> getRoutines() {
        return Routines;
    }

    /**
     * @param Routines The Routines
     */
    public void setRoutines(List<Routine> Routines) {
        this.Routines = Routines;
    }
}