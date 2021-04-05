package ultratude.com.staff.spinnermodel;

/**
 * Created by James on 29/04/2019.
 */

public class YearSpinner {

    Integer yearID;
    String yearFor;

    public YearSpinner(Integer yearID,
                       String yearFor){
        this.yearID = yearID;
        this.yearFor = yearFor;
    }


    @Override
    public String toString() {
        return yearFor;
    }

    public Integer getYearID() {
        return yearID;
    }

    public String getYearFor() {
        return yearFor;
    }
}
