package ultratude.com.staff.webservice.RequestModels;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MziziAppVersionControl {

    @SerializedName("ForceUpdate")
    boolean forceAppUpdateStatus;
    @SerializedName("newFeatureUpdates")
    List<String> newFeatureUpdates;

    public List<String> getNewFeatureUpdates() {
        return newFeatureUpdates;
    }

    public void setNewFeatureUpdates(List<String> newFeatureUpdates) {
        this.newFeatureUpdates = newFeatureUpdates;
    }

    public boolean isForceAppUpdateStatus() {
        return forceAppUpdateStatus;
    }

    public void setForceAppUpdateStatus(boolean forceAppUpdateStatus) {
        this.forceAppUpdateStatus = forceAppUpdateStatus;
    }

    @Override
    public String toString() {
        return "MziziAppVersionControl{" +
                "forceAppUpdateStatus=" + forceAppUpdateStatus +
                '}';
    }
}
