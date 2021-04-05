package ultratude.com.mzizi.modelclasses.request;

public class OrderItemRequest {

    private String StudentID;
    private String ItemID;
    private String Quantity;
    private String ChargeAmount;
    private String Remarks;
    private String AppCode;
    private String OrganizationID;

    public OrderItemRequest(String studentID, String itemID, String quantity, String chargeAmount, String remarks, String appCode, String organizationID) {
        StudentID = studentID;
        ItemID = itemID;
        Quantity = quantity;
        ChargeAmount = chargeAmount;
        Remarks = remarks;
        AppCode = appCode;
        OrganizationID = organizationID;
    }

    public String getStudentID() {
        return StudentID;
    }

    public String getItemID() {
        return ItemID;
    }

    public String getQuantity() {
        return Quantity;
    }

    public String getChargeAmount() {
        return ChargeAmount;
    }

    public String getRemarks() {
        return Remarks;
    }

    public String getAppCode() {
        return AppCode;
    }

    public String getOrganizationID() {
        return OrganizationID;
    }

    @Override
    public String toString() {
        return "OrderItemRequest{" +
                "StudentID='" + StudentID + '\'' +
                ", ItemID='" + ItemID + '\'' +
                ", Quantity='" + Quantity + '\'' +
                ", ChargeAmount='" + ChargeAmount + '\'' +
                ", Remarks='" + Remarks + '\'' +
                ", AppCode='" + AppCode + '\'' +
                ", OrganizationID='" + OrganizationID + '\'' +
                '}';
    }
}
