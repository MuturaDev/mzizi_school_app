package ultratude.com.staff.activities.accesscontrolforactivities;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ultratude.com.staff.webservice.DataAccessObjects.StaffDao;
import ultratude.com.staff.webservice.ResponseModels.Staff;
import ultratude.com.staff.webservice.ResponseModels.StaffOperation;

/**
 * Created by James on 02/05/2019.
 */

public class ScreenDAO {

    private Context mContext;

    public ScreenDAO(Context mContext){
        this.mContext = mContext;
    }

    public boolean requestAccessToThisScreen(List<StaffOperation> staffOperations, ScreenNameEnums screenName){

        boolean access = false;

        Map<ScreenNameEnums, List<StaffOperation>> screenAccessOperationMap =  getScreenMapToOperation();
        List<StaffOperation> screenOperations = screenAccessOperationMap.get(screenName);

        //contains a list of all the operations the screen permits.
        for(StaffOperation screenOperation : screenOperations){//2

            //contains a list of all the operations the staff must have
            //loop through to see if the staff has this permission,if he or she has the permission allow.
            //if there are two operations here we can allow if he has one of this
            for(StaffOperation staffOperation : staffOperations){
                if(screenOperation.getOperations().equalsIgnoreCase(staffOperation.getOperations())){
                     access = true;
                    return access;
                }else{
                    continue;
                }
            }

        }


        return false;


    }

//    DUTY_ROSTER,0
//    HOME_SCREEN,1
//    MANAGE_ATTENDANCE,2
//    MANAGE_FLEET_SERVICE,3
//    MANAGE_FLEET_FUEL,4
//    MANAGE_DISCPLINARY,5
//    MANAGE_TRANSPORT,6
//    STUDENT_ENQUIRY;7



    //now assigning to the screens
    private Map<ScreenNameEnums, List<StaffOperation>> getScreenMapToOperation(){
        Map<ScreenNameEnums, List<StaffOperation>> screenAccessOperationMap = new HashMap<>();

        List<StaffOperation> staffOperationList = getOperations();



        List<StaffOperation> accessOperation = new ArrayList<>();
        accessOperation.add(staffOperationList.get(5));//Free_Access
        screenAccessOperationMap.put(ScreenNameEnums.APPLY_LEAVE, accessOperation);


        List<StaffOperation> accessOperation1 = new ArrayList<>();
        accessOperation1.add(staffOperationList.get(5));//Free_Access
        screenAccessOperationMap.put(ScreenNameEnums.DUTY_ROSTER,accessOperation1 );

        List<StaffOperation> accessOperation2 = new ArrayList<>();
        accessOperation2.add(staffOperationList.get(5));//Free_Access
        screenAccessOperationMap.put(ScreenNameEnums.HOME_SCREEN,accessOperation2 );

        List<StaffOperation> accessOperation3 = new ArrayList<>();
        accessOperation3.add(staffOperationList.get(2));//Enter Attendance Records
        screenAccessOperationMap.put(ScreenNameEnums.MANAGE_ATTENDANCE,accessOperation3 );

        List<StaffOperation> accessOperation4 = new ArrayList<>();
        accessOperation4.add(staffOperationList.get(4));//Manage Fleet
       // accessOperation4.add(staffOperationList.get(3));//Manage Transport
        screenAccessOperationMap.put(ScreenNameEnums.MANAGE_FLEET_SERVICE,accessOperation4 );

        List<StaffOperation> accessOperation5 = new ArrayList<>();
        accessOperation5.add(staffOperationList.get(4));//Manage Fleet
       // accessOperation5.add(staffOperationList.get(3));//Manage Transport
        screenAccessOperationMap.put(ScreenNameEnums.MANAGE_FLEET_FUEL, accessOperation5);

        List<StaffOperation> accessOperation6 = new ArrayList<>();
        accessOperation6.add(staffOperationList.get(1));
        screenAccessOperationMap.put(ScreenNameEnums.MANAGE_DISCPLINARY, accessOperation6);

//        List<StaffOperation> accessOperation7 = new ArrayList<>();
//        accessOperation7.add(staffOperationList.get(3));//Manage Transport
//        screenAccessOperationMap.put(ScreenNameEnums.MANAGE_TRANSPORT,accessOperation7 );

        List<StaffOperation> accessOperation8 = new ArrayList<>();
        accessOperation8.add(staffOperationList.get(0));
        screenAccessOperationMap.put(ScreenNameEnums.STUDENT_ENQUIRY, accessOperation8);

        List<StaffOperation> accessOperation9 = new ArrayList<>();
        accessOperation9.add(staffOperationList.get(5));//Free_Access
        screenAccessOperationMap.put(ScreenNameEnums.VIEW_PAYSLIP,accessOperation9);

        List<StaffOperation> accessOperation10 = new ArrayList<>();
        accessOperation10.add(staffOperationList.get(3));
        screenAccessOperationMap.put(ScreenNameEnums.DAILTY_TRANSPORT, accessOperation10);

        List<StaffOperation> accessOperation11 = new ArrayList<>();
        accessOperation11.add(staffOperationList.get(6));
        screenAccessOperationMap.put(ScreenNameEnums.TRIP_TRANSPORT, accessOperation11);



        return screenAccessOperationMap;
    }


    //for the screens//all operations
    public List<StaffOperation> getOperations(){
        Staff staff = new StaffDao(mContext).getUserThatSignedUp();

        StaffOperation operationStudentEnquiry = new StaffOperation(staff.getStaff_ID(), "Student Enquiry Access");
        StaffOperation operationDisciplinaryCases = new StaffOperation(staff.getStaff_ID(), "Enter Discipline Cases");
        StaffOperation operationAttendanceRecords = new StaffOperation(staff.getStaff_ID(), "Enter Attendance Records");
        StaffOperation operationStudentTransportTracking = new StaffOperation(staff.getStaff_ID(), "Manage Student Transport Tracking");//Manage Transport
        StaffOperation operationManageFleet = new StaffOperation(staff.getStaff_ID(), "Manage Fleet");
        StaffOperation operationFreeAccess = new StaffOperation(staff.getStaff_ID(), "View Standard Reports");
        StaffOperation operationVehicleRouteTracking = new StaffOperation(staff.getStaff_ID(), "Manage Vehicle Route Tracking");


        List<StaffOperation> staffOperationList = new ArrayList<>();
        staffOperationList.add(operationStudentEnquiry);//0
        staffOperationList.add(operationDisciplinaryCases);//1
        staffOperationList.add(operationAttendanceRecords);//2
        staffOperationList.add(operationStudentTransportTracking);//3
        staffOperationList.add(operationManageFleet);//4
        staffOperationList.add(operationFreeAccess);//5
        staffOperationList.add(operationVehicleRouteTracking);//6

        return staffOperationList;

    }


}
