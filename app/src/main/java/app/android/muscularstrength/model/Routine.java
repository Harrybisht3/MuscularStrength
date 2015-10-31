package app.android.muscularstrength.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by laxman singh on 10/31/2015.
 */
public class Routine {

    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("Routine")
    @Expose
    private List<Routine_> Routine = new ArrayList<Routine_>();

    /**
     *
     * @return
     * The day
     */
    public String getDay() {
        return day;
    }

    /**
     *
     * @param day
     * The day
     */
    public void setDay(String day) {
        this.day = day;
    }

    /**
     *
     * @return
     * The Routine
     */
    public List<Routine_> getRoutine() {
        return Routine;
    }

    /**
     *
     * @param Routine
     * The Routine
     */
    public void setRoutine(List<Routine_> Routine) {
        this.Routine = Routine;
    }

}
