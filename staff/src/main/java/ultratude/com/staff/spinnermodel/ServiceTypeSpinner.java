package ultratude.com.staff.spinnermodel;

/**
 * Created by James on 13/01/2019.
 */

public class ServiceTypeSpinner {

    private Integer serviceTypeID;
    private String serviceTypeName;



    public ServiceTypeSpinner(Integer serviceTypeID, String serviceTypeName){
        this.serviceTypeID = serviceTypeID;
        this.serviceTypeName = serviceTypeName;
    }


    @Override
    public String toString() {
        return serviceTypeName;
    }


    public Integer getServiceTypeID() {
        return serviceTypeID;
    }

    public String getServiceTypeName() {
        return serviceTypeName;
    }
}
