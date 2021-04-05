package ultratude.com.staff.spinnerdao;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.staff.R;
import ultratude.com.staff.fragments.ViewClassAttendance;
import ultratude.com.staff.fragments.ViewDisciplinaryCases;
import ultratude.com.staff.spinnermodel.YearSpinner;
import ultratude.com.staff.webservice.ResponseModels.PortalStudentInfo;
import ultratude.com.staff.webservice.ResponseModels.StudentRequest;
import ultratude.com.staff.webservice.RetrofitOkHTTP.APIClient;
import ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface;

/**
 * Created by James on 29/04/2019.
 */

public class YearSpinnerDAO {

    private Context mContext;


    public YearSpinnerDAO(Context mContext){
        this.mContext = mContext;
    }

    public void loadDataForYearSpinner(StudentRequest student, final Spinner sp_year_ID, final Object fragmentObject){



        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        if(fragmentObject instanceof ViewDisciplinaryCases) {
            ((ViewDisciplinaryCases) fragmentObject).showSpinnerProgress(true);

        }
        if(fragmentObject instanceof ViewClassAttendance) {
            ((ViewClassAttendance) fragmentObject).showSpinnerProgress(true);

        }



        Call<PortalStudentInfo> userCall = apiInterface.getStudentInfo(student);
        userCall.enqueue(new Callback<PortalStudentInfo>() {
            @Override
            public void onResponse(Call<PortalStudentInfo> call, Response<PortalStudentInfo> response) {

                if (response.isSuccessful()) {


                    if(response.code() == 200) {


                        //you have to have a method that calculates dates from the time this student was admitted, the data we want, date of admission
                        PortalStudentInfo studentInfo = response.body();


                        ArrayAdapter<YearSpinner> classesAdapter = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_dropdown_item, generateListOfYearsFromDateOfAdmission(studentInfo.getDateOfAdmission()));
                        sp_year_ID.setAdapter(classesAdapter);



                    }


                }
                    if(fragmentObject instanceof ViewDisciplinaryCases) {
                        ((ViewDisciplinaryCases) fragmentObject).showSpinnerProgress(false);
                    }else if(fragmentObject instanceof ViewClassAttendance) {
                        ((ViewClassAttendance) fragmentObject).showSpinnerProgress(false);

                    }



            }

            @Override
            public void onFailure(Call<PortalStudentInfo> call, Throwable t) {
               // Toast.makeText(context, "Couldnt connect to server. Make sure your phone has an internet connection and try again!" + t.getMessage(), Toast.LENGTH_SHORT).show();
                if(fragmentObject instanceof ViewDisciplinaryCases) {

                    AlertDialog.Builder accessDeniedAlert = new AlertDialog.Builder(((ViewDisciplinaryCases) fragmentObject).getActivity());
                    LayoutInflater inflater =  ((ViewDisciplinaryCases) fragmentObject).getLayoutInflater();
                    View view  = inflater.inflate(R.layout.no_internet_connection_layout_dialog,null, false);
                    accessDeniedAlert.setView(view);
//                    accessDeniedAlert.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                          //  btn_disciplinary_cases_ID.performClick();
//
//                        }
//                    });
                    accessDeniedAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            ((ViewDisciplinaryCases) fragmentObject).onDestroy();
                        }
                    });
                    accessDeniedAlert.show();

                    ((ViewDisciplinaryCases) fragmentObject).showSpinnerProgress(false);

                    //handle the button
                }else if(fragmentObject instanceof ViewClassAttendance) {

                    AlertDialog.Builder accessDeniedAlert = new AlertDialog.Builder(((ViewClassAttendance) fragmentObject).getActivity());
                    LayoutInflater inflater =  ((ViewClassAttendance) fragmentObject).getLayoutInflater();
                    View view  = inflater.inflate(R.layout.no_internet_connection_layout_dialog,null, false);
                    accessDeniedAlert.setView(view);
//                    accessDeniedAlert.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//                            dialog.cancel();
//                          //  btn_disciplinary_cases_ID.performClick();
//
//                        }
//                    });
                    accessDeniedAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                           ((ViewClassAttendance) fragmentObject).onDestroy();
                        }
                    });
                    accessDeniedAlert.show();

                    ((ViewClassAttendance) fragmentObject).showSpinnerProgress(false);

                }





                                                    /*No internet connection. Make sure that Wi-Fi or mobile data is turned on, then try again.
                                                     Couldnt connect to server. Make sure your phone has an internet connection and try again.*/

            }
        });

    }


    private List<YearSpinner> generateListOfYearsFromDateOfAdmission(String dateOfAdmission){

        //Current date
        Calendar currentDate = Calendar.getInstance();
        SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

        String currentDateString = formatter.format(currentDate.getTime());
        String dateSentString;

        final Date currentd = new Date(currentDateString);


        //Notification date/privious date
        Date notificaionDateSent;

        Date dateSent;

        //WORKS FOR BOTH, COOL
        //this is the date you should set
       // notificaionDateSent = new Date("30 Oct 2018");
        //notificaionDateSent = new Date("01/01/2018");
        notificaionDateSent = new Date(dateOfAdmission);
        dateSentString = formatter.format(notificaionDateSent);
        dateSent = new Date(dateSentString);

//        //CALCULATION
//        long diff = Math.abs(currentd.getTime() - dateSent.getTime());
//
//
//        final long days = diff / (24 * 60 * 60 * 1000);

        //  System.out.println("Days between the dates: " + days);



        Calendar startCalDate = Calendar.getInstance();
        startCalDate.setTime(dateSent);//is the previous date

        Calendar endCalDate = Calendar.getInstance();
        endCalDate.setTime(currentd);
        int endYear = endCalDate.get(Calendar.YEAR);

        List<YearSpinner> returnYearList = new ArrayList<>();
        YearSpinner yearSpinner1 = new YearSpinner(0, "Select Year");
        returnYearList.add(0, yearSpinner1);


        //CALCULATION
        if(startCalDate.before(endCalDate)){
            for( int startYear = startCalDate.get(Calendar.YEAR); startYear <= endYear; startYear++ ){

                int nextyear = startYear;
                YearSpinner yearSpinner = new YearSpinner(nextyear, String.valueOf(nextyear));
                returnYearList.add(yearSpinner);

            }
        }


        Log.d(mContext.getPackageName().toUpperCase(), returnYearList.toString());


        return returnYearList;

    }
}
