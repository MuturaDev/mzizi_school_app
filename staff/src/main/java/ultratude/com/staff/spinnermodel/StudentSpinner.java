package ultratude.com.staff.spinnermodel;

/**
 * Created by James on 13/01/2019.
 */

public class StudentSpinner {

    private Integer studentID;
    private String studentFullName;


    public StudentSpinner(Integer studentID, String studentFullName){
        this.studentID = studentID;
        this.studentFullName = studentFullName;
    }

    //this method is important as it is responsible for displaying the data in spinner,
        //you can modify it as per your need.
    @Override
    public String toString() {
        return studentFullName;
    }


    public Integer getStudentID() {
        return studentID;
    }

    public String getStudentFullName() {
        return studentFullName;
    }
}
