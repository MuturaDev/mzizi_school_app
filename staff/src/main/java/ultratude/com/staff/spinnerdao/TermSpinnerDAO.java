package ultratude.com.staff.spinnerdao;

import android.content.Context;
import android.util.Log;
import java.util.ArrayList;

import ultratude.com.staff.fragments.ViewClassAttendance;
import ultratude.com.staff.fragments.ViewDisciplinaryCases;
import ultratude.com.staff.spinnermodel.TermSpinner;

/**
 * Created by James on 29/04/2019.
 */

public class TermSpinnerDAO {

    private Context mContext;


    public TermSpinnerDAO(Context mContext){
        this.mContext = mContext;

    }

    public ArrayList<TermSpinner> loadDataForTermSpinner(final Object fragmentObject){
        ArrayList<TermSpinner> termSpinnerList =  new ArrayList<>();

        if(fragmentObject instanceof ViewDisciplinaryCases) {
            ((ViewDisciplinaryCases) fragmentObject).showSpinnerProgress(true);

        }
        if(fragmentObject instanceof ViewClassAttendance) {
            ((ViewClassAttendance) fragmentObject).showSpinnerProgress(true);

        }


        for(int termID = 0; termID <=3; termID++){
            if(termID == 0){
                TermSpinner termSpinner = new TermSpinner(termID, "Select Term");
                termSpinnerList.add(termSpinner);
            }else{
                TermSpinner termSpinner = new TermSpinner(termID, "Term "+ termID);
                termSpinnerList.add(termSpinner);
            }

        }

        Log.d(mContext.getPackageName().toUpperCase(), termSpinnerList.toString());
        if (fragmentObject instanceof ViewDisciplinaryCases) {
            ((ViewDisciplinaryCases) fragmentObject).showSpinnerProgress(false);

        }
        if (fragmentObject instanceof ViewClassAttendance) {
            ((ViewClassAttendance) fragmentObject).showSpinnerProgress(false);

        }

        return termSpinnerList;
    }
}
