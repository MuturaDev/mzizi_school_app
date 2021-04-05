package ultratude.com.mzizi.modelclasses.spinnerdao;


import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.mzizi.modelclasses.YearSpinner;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;


import ultratude.com.mzizi.uiactivities.SchoolAttendaceFrag;


/**
 * Created by James on 29/04/2019.
 */

public class YearSpinnerDAO {

    private Context mContext;


    public YearSpinnerDAO(Context mContext){
        this.mContext = mContext;
    }




    public void loadDataForYearSpinner(final Object fragmentObject){


        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected void onPreExecute() {
                ((SchoolAttendaceFrag) fragmentObject).showSpinnerProgress(true);
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object[] params) {
                ParentMziziDatabase db = ParentMziziDatabase.getInstance(((SchoolAttendaceFrag) fragmentObject).getActivity());
                String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                if(studentid == null){
                    studentid  = "0";
                }
                List<ultratude.com.mzizi.roomdatabaseclasses.RoomModel.YearRoom> list  = db.getStudentClassAttendanceDAO().getYearForList(Integer.valueOf(studentid));

                Log.d(((SchoolAttendaceFrag) fragmentObject).getActivity().getPackageName().toUpperCase(), list.toString());
                return list;
            }

            @Override
            protected void onPostExecute(Object o) {
                ((SchoolAttendaceFrag) fragmentObject).showSpinnerProgress(false);
                ArrayList<YearSpinner> yearSpinnerArrayList = new ArrayList<>();
                YearSpinner yearSpinner = new YearSpinner(
                        0,
                        "Select Year"
                );
                yearSpinnerArrayList.add(yearSpinner);
                int count = 1;
                for(ultratude.com.mzizi.roomdatabaseclasses.RoomModel.YearRoom yearFor : ((List<ultratude.com.mzizi.roomdatabaseclasses.RoomModel.YearRoom>) o)){


                    YearSpinner year = new YearSpinner(
                            count,
                            yearFor.getYear()
                    );
                    yearSpinnerArrayList.add(year);
                    count++;
                }

                ArrayAdapter<YearSpinner> classesAdapter = new ArrayAdapter<YearSpinner>(mContext, android.R.layout.simple_spinner_dropdown_item,yearSpinnerArrayList );
                ((SchoolAttendaceFrag) fragmentObject).sp_year_ID.setAdapter(classesAdapter);


                super.onPostExecute(o);
            }
        };
        asyncTask.execute();

    }


//    private List<YearSpinner> generateListOfYearsFromDateOfAdmission(String dateOfAdmission){
//
//        //Current date
//        Calendar currentDate = Calendar.getInstance();
//        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
//
//        String currentDateString = formatter.format(currentDate.getTime());
//        String dateSentString;
//
//        final Date currentd = new Date(currentDateString);
//
//
//        //Notification date/privious date
//        Date notificaionDateSent;
//
//        Date dateSent;
//
//        //WORKS FOR BOTH, COOL
//        //this is the date you should set
//       // notificaionDateSent = new Date("30 Oct 2018");
//        //notificaionDateSent = new Date("01/01/2018");
//        notificaionDateSent = new Date(dateOfAdmission);
//        dateSentString = formatter.format(notificaionDateSent);
//        dateSent = new Date(dateSentString);
//
////        //CALCULATION
////        long diff = Math.abs(currentd.getTime() - dateSent.getTime());
////
////
////        final long days = diff / (24 * 60 * 60 * 1000);
//
//        //  System.out.println("Days between the dates: " + days);
//
//
//
//        Calendar startCalDate = Calendar.getInstance();
//        startCalDate.setTime(dateSent);//is the previous date
//
//        Calendar endCalDate = Calendar.getInstance();
//        endCalDate.setTime(currentd);
//        int endYear = endCalDate.get(Calendar.YEAR);
//
//        List<YearSpinner> returnYearList = new ArrayList<>();
//        YearSpinner yearSpinner1 = new YearSpinner(0, "Select Year");
//        returnYearList.add(0, yearSpinner1);
//
//
//        //CALCULATION
//        if(startCalDate.before(endCalDate)){
//            for( int startYear = startCalDate.get(Calendar.YEAR); startYear <= endYear; startYear++ ){
//
//                int nextyear = startYear;
//                YearSpinner yearSpinner = new YearSpinner(nextyear, String.valueOf(nextyear));
//                returnYearList.add(yearSpinner);
//
//            }
//        }
//
//
//        Log.d(mContext.getPackageName().toUpperCase(), returnYearList.toString());
//
//
//        return returnYearList;
//
//    }
}
