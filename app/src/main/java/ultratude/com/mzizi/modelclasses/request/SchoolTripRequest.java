package ultratude.com.mzizi.modelclasses.request;

public class SchoolTripRequest {

    public String StudentID;
    public String ListID;
    public String AddedBy;
    public String AmountCharged;
    public String AppCode;

    public SchoolTripRequest(String studentID, String listID, String addedBy, String amountCharged, String appCode) {
        StudentID = studentID;
        ListID = listID;
        AddedBy = addedBy;
        AmountCharged = amountCharged;
        AppCode = appCode;
    }

    public String getStudentID() {
        return StudentID;
    }

    public String getListID() {
        return ListID;
    }

    public String getAddedBy() {
        return AddedBy;
    }

    public String getAmountCharged() {
        return AmountCharged;
    }

    public String getAppCode() {
        return AppCode;
    }

    @Override
    public String toString() {
        return "SchoolTripRequest{" +
                "StudentID='" + StudentID + '\'' +
                ", ListID='" + ListID + '\'' +
                ", AddedBy='" + AddedBy + '\'' +
                ", AmountCharged='" + AmountCharged + '\'' +
                ", AppCode='" + AppCode + '\'' +
                '}';
    }
}
