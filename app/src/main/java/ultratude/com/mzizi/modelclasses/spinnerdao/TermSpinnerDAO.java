package ultratude.com.mzizi.modelclasses.spinnerdao;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.util.List;

import ultratude.com.mzizi.modelclasses.TermSpinner;
import ultratude.com.mzizi.modelclasses.YearSpinner;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.TermRoom;
import ultratude.com.mzizi.uiactivities.SchoolAttendaceFrag;
import ultratude.com.staff.fragments.ViewClassAttendance;
import ultratude.com.staff.fragments.ViewDisciplinaryCases;

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




        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object[] params) {
                ParentMziziDatabase db = ParentMziziDatabase.getInstance(((SchoolAttendaceFrag) fragmentObject).getActivity());
                String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                if(studentid == null){
                    studentid  = "0";
                }
                List<TermRoom> list  = db.getStudentClassAttendanceDAO().getTermForList(Integer.valueOf(studentid));
                Log.d(((SchoolAttendaceFrag) fragmentObject).getActivity().getPackageName().toUpperCase(), list.toString());

                return list;
            }

            @Override
            protected void onPostExecute(Object o) {

                ArrayList<TermSpinner> termSpinnerArrayList = new ArrayList<>();
                TermSpinner termSpinner = new TermSpinner(
                        0,
                        "Select Term"
                );
                termSpinnerArrayList.add(termSpinner);
                int count = 1;
                for(TermRoom termFor : ((List<TermRoom>) o)){
                    TermSpinner termSpinner1 = new TermSpinner(
                            count,
                            termFor.getTerm()
                    );
                    termSpinnerArrayList.add(termSpinner1);
                    count++;
                }

                ArrayAdapter<TermSpinner> termSpinnerArrayAdapter = new ArrayAdapter<TermSpinner>(((SchoolAttendaceFrag) fragmentObject).getActivity(), android.R.layout.simple_spinner_dropdown_item, termSpinnerArrayList);
                ((SchoolAttendaceFrag) fragmentObject).sp_term_ID.setAdapter(termSpinnerArrayAdapter);

                super.onPostExecute(o);
            }
        };
        asyncTask.execute();


//        for(int termID = 0; termID <=3; termID++){
//            if(termID == 0){
//                TermSpinner termSpinner = new TermSpinner(termID, "Select Term");
//                termSpinnerList.add(termSpinner);
//            }else{
//                TermSpinner termSpinner = new TermSpinner(termID, "Term "+ termID);
//                termSpinnerList.add(termSpinner);
//            }
//
//        }



        return termSpinnerList;
    }
}
