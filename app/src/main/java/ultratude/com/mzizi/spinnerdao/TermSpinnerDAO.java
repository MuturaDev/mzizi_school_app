package ultratude.com.mzizi.spinnerdao;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import ultratude.com.mzizi.modelclasses.NewCarriculumExamDAO;
import ultratude.com.mzizi.notificationpg.NotificationSQDB.LocalDBContract;
import ultratude.com.staff.spinnermodel.TermSpinner;

/**
 * Created by James on 29/04/2019.
 */

public class TermSpinnerDAO {

    private Context mContext;


    public TermSpinnerDAO(Context mContext){
        this.mContext = mContext;

    }

    public ArrayList<TermSpinner> getTermSpinner(String year){
        Cursor cursor = new NewCarriculumExamDAO(mContext).getPortalExamProgressReportTermCursor( year);
        ArrayList<TermSpinner> termSpinnerArrayList = new ArrayList<>();
        TermSpinner termSpinner1 = new TermSpinner(0, "Select Term.");
        termSpinnerArrayList.add(termSpinner1);

        if(cursor != null){
            int count = 1;
            while(cursor.moveToNext()){

                TermSpinner termSpinner = new TermSpinner(
                        count,
                        cursor.getString(cursor.getColumnIndex(LocalDBContract.NewCurriculumExamFormat.TERM_NAME))
                );
                termSpinnerArrayList.add(termSpinner);
                count++;
            }

        }

        return termSpinnerArrayList;
    }


    public int getTermSpinnerPosition(String year,String termName){
      ArrayList<TermSpinner> list =  getTermSpinner(year);

        int count = 0;
        for(TermSpinner term : list){

            if(term.getTermFor().equals(termName)){
                break;
            }
            count++;
        }

        return count;
    }
}
