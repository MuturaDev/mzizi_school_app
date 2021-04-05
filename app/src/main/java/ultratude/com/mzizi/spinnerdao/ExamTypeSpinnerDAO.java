package ultratude.com.mzizi.spinnerdao;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;

import ultratude.com.mzizi.modelclasses.NewCarriculumExamDAO;
import ultratude.com.mzizi.notificationpg.NotificationSQDB.LocalDBContract;
import ultratude.com.staff.spinnermodel.ExamTypeSpinner;

/**
 * Created by James on 19/05/2019.
 */

public class ExamTypeSpinnerDAO {

   private Context mContext;

    public ExamTypeSpinnerDAO(Context mContext){
        this.mContext = mContext;
    }


    public ArrayList<ExamTypeSpinner> getExamTypeSpinner(String term, String year){
        Cursor cursor = new NewCarriculumExamDAO(mContext).getPortalExamProgressReportExamTypeCursor(term,year);
        ArrayList<ExamTypeSpinner> examTypeSpinnerArrayList = new ArrayList<>();
        ExamTypeSpinner examTypeSpinner1 = new ExamTypeSpinner(0, "Select ExamType.");
        examTypeSpinnerArrayList.add(examTypeSpinner1);
        if(cursor != null){
            int count = 1;
            while(cursor.moveToNext()){
                ExamTypeSpinner examTypeSpinner = new ExamTypeSpinner(
                        Integer.valueOf(count),
                        cursor.getString(cursor.getColumnIndex(LocalDBContract.NewCurriculumExamFormat.EXAMTYPEDESC))
                );

                examTypeSpinnerArrayList.add(examTypeSpinner);

                count++;
            }

        }

        return examTypeSpinnerArrayList;
    }


    public int getExamSpinnerPosition(String term,String year, String examType){
        ArrayList<ExamTypeSpinner> list =  getExamTypeSpinner(term,year);

        int count = 0;
        for(ExamTypeSpinner exam : list){

            if(exam.getExamTypeName().equals(examType)){
                break;
            }
            count++;
        }

        return count;
    }
}
