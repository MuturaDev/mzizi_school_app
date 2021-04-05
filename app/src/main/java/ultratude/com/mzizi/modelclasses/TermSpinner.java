package ultratude.com.mzizi.modelclasses;



/**
 * Created by James on 29/04/2019.
 */

public class TermSpinner {


    Integer termID;
    String termFor;


    public TermSpinner(Integer termID,
                       String termFor){
        this.termID = termID;
        this.termFor = termFor;
    }

    @Override
    public String toString() {
        return termFor;
    }

    public Integer getTermID() {
        return termID;
    }

    public String getTermFor() {
        return termFor;
    }
}
