package ultratude.com.staff.spinnermodel;

/**
 * Created by James on 19/05/2019.
 */

public class ExamTypeSpinner {

    private Integer examTypeID;
    private String examTypeName;

    public ExamTypeSpinner(Integer examTypeID, String examTypeName){
        this.examTypeID = examTypeID;
        this.examTypeName = examTypeName;
    }

    @Override
    public String toString() {
        return examTypeName;
    }

    public Integer getExamTypeID() {
        return examTypeID;
    }

    public String getExamTypeName() {
        return examTypeName;
    }
}
