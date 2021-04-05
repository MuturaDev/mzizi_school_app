package ultratude.com.mzizi.modelclasses;

public class StudentNotification {

    private String StudentID;
    private String StudentFullNames;
    private String CourseName;
    private String RegistrationNumber;
    private String NotificationCount;


    public StudentNotification(String studentID, String studentFullNames, String courseName, String registrationNumber, String notificationCount) {
        StudentID = studentID;
        StudentFullNames = studentFullNames;
        CourseName = courseName;
        RegistrationNumber = registrationNumber;
        NotificationCount = notificationCount;
    }

    public String getNotificationCount() {
        return NotificationCount;
    }

    public String getStudentID() {
        return StudentID;
    }

    public String getStudentFullNames() {
        return StudentFullNames;
    }

    public String getCourseName() {
        return CourseName;
    }

    public String getRegistrationNumber() {
        return RegistrationNumber;
    }
}
