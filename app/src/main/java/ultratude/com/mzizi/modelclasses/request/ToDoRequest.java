package ultratude.com.mzizi.modelclasses.request;

public class ToDoRequest {

    private String StudentID;
    private String LoginTypeID;
    private String DiaryEntryTypeID;
    private String AppCode;

    public ToDoRequest(String studentID, String loginTypeID, String diaryEntryTypeID, String appCode) {
        StudentID = studentID;
        LoginTypeID = loginTypeID;
        DiaryEntryTypeID = diaryEntryTypeID;
        AppCode = appCode;
    }

    public String getStudentID() {
        return StudentID;
    }

    public String getLoginTypeID() {
        return LoginTypeID;
    }

    public String getDiaryEntryTypeID() {
        return DiaryEntryTypeID;
    }

    public String getAppCode() {
        return AppCode;
    }

    @Override
    public String toString() {
        return "ToDoRequest{" +
                "StudentID='" + StudentID + '\'' +
                ", LoginTypeID='" + LoginTypeID + '\'' +
                ", DiaryEntryTypeID='" + DiaryEntryTypeID + '\'' +
                ", AppCode='" + AppCode + '\'' +
                '}';
    }
}
