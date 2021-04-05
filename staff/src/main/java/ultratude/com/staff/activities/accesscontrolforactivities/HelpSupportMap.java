package ultratude.com.staff.activities.accesscontrolforactivities;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.staff.webservice.DataAccessObjects.StaffOperationsDAO;
import ultratude.com.staff.webservice.ResponseModels.Help;
import ultratude.com.staff.webservice.ResponseModels.StaffOperation;

/**
 * Created by James on 23/05/2019.
 */

public class HelpSupportMap {

    private Context mContext;

    public HelpSupportMap(Context mContext){
        this.mContext = mContext;
    }

    public List<Help>  requestAccessToThisHelpSupport(List<Help> helpList){
        //holds all the questions the user is allowed to see
        List<Help> returnHelpList = new ArrayList<>();
      List<StaffOperation> staffOperationList = new StaffOperationsDAO(mContext).getStaffOperationsList();//new ScreenDAO(mContext).getOperations();

        for(Help help : helpList){

            //we are searching throught the logged in staffOperations, and showing him/her the questions, as assigned to the operations he can do
            for(StaffOperation staffOperation : staffOperationList){
                if(help.getOperationName().equalsIgnoreCase(staffOperation.getOperations())){
                    //the staff has this operation, you can display this question
                    returnHelpList.add(help);
                    break;
                }//else dont display this question
            }
        }

        return returnHelpList;

    }

}
