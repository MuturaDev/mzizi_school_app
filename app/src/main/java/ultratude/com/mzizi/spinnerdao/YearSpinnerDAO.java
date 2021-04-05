package ultratude.com.mzizi.spinnerdao;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import ultratude.com.mzizi.modelclasses.NewCarriculumExamDAO;
import ultratude.com.mzizi.notificationpg.NotificationSQDB.LocalDBContract;
import ultratude.com.staff.spinnermodel.YearSpinner;

/**
 * Created by James on 29/04/2019.
 */

public class YearSpinnerDAO {

    private Context mContext;


    public YearSpinnerDAO(Context mContext){
        this.mContext = mContext;
    }

    public ArrayList<YearSpinner> getYearSpinner(){
        Cursor cursor = new NewCarriculumExamDAO(mContext).getPortalExamProgressReportYearCursor();
        ArrayList<YearSpinner> yearSpinnerArrayList = new ArrayList<>();
        YearSpinner yearSpinner2 = new YearSpinner(0,"Select Year.");
        yearSpinnerArrayList.add(yearSpinner2);

        if(cursor != null){


            while(cursor.moveToNext()){

                YearSpinner yearSpinner = new YearSpinner(
                        cursor.getInt(cursor.getColumnIndex(LocalDBContract.NewCurriculumExamFormat.YEAID)),
                        cursor.getString(cursor.getColumnIndex(LocalDBContract.NewCurriculumExamFormat.YEAID))
                );

                yearSpinnerArrayList.add(yearSpinner);

            }
        }

        return yearSpinnerArrayList;

    }



    public int getYearSpinnerPosition(String yearName){
        ArrayList<YearSpinner> list =  getYearSpinner();

        int count = 0;
        for(YearSpinner year : list){

            if(year.getYearFor().equals(yearName)){
                break;
            }
            count++;
        }

        return count;
    }

}
