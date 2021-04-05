package ultratude.com.staff.expandablelist;

import java.util.ArrayList;

/**
 * Created by James on 12/05/2019.
 */

public class GroupInfo {

    private String entitlement;
    private ArrayList<ChildInfo> childInfoList = new ArrayList<>();

    public String getEntitlement() {
        return entitlement;
    }

    public void setEntitlement(String entitlement) {
        this.entitlement = entitlement;
    }

    public ArrayList<ChildInfo> getChildInfoList() {
        return childInfoList;
    }

    public void setChildInfoList(ArrayList<ChildInfo> childInfoList) {
        this.childInfoList = childInfoList;
    }
}
