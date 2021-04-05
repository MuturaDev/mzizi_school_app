package ultratude.com.staff.spinnermodel;

/**
 * Created by James on 03/05/2019.
 */

public class ClassStreamSpinner {

    public Integer classStreamID;
    public String classStreamName;

    public ClassStreamSpinner(Integer classStreamID, String classStreamName){
        this.classStreamID = classStreamID;
        this.classStreamName = classStreamName;
    }

    @Override
    public String toString() {

        return classStreamName;
    }

    public Integer getClassStreamID() {
        return classStreamID;
    }

    public String getClassStreamName() {
        return classStreamName;
    }

}
