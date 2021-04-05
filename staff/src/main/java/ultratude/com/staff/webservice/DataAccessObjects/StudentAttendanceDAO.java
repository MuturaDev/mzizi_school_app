package ultratude.com.staff.webservice.DataAccessObjects;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.staff.adapters.ViewClassAttendanceGridAdapter;
import ultratude.com.staff.fragments.ViewClassAttendance;
import ultratude.com.staff.R;
import ultratude.com.staff.webservice.ResponseModels.StudentClassAttendance;
import ultratude.com.staff.webservice.RequestModels.PortalGetAttendanceRequest;
import ultratude.com.staff.webservice.RetrofitOkHTTP.APIClient;
import ultratude.com.staff.webservice.RetrofitOkHTTP.APIInterface;

/**
 * Created by James on 28/04/2019.
 */

public class StudentAttendanceDAO {

    private Context mContext;

    public StudentAttendanceDAO(Context mContext){

        this.mContext = mContext;
    }


    public void loadGridviewWithData(ViewClassAttendance attendance){

        sendRequest(attendance);
    }


    private void sendRequest(final ViewClassAttendance attendance){

        final APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);//used to instantiate the APIClient.

        final ConnectivityManager cm = (ConnectivityManager) attendance.getActivity().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if ((mobile.getState() == NetworkInfo.State.CONNECTED) || wifi.getState() == NetworkInfo.State.CONNECTED) {

             sendRequestForStudentAttendanceDetils(attendance.getActivity(), apiInterface, attendance);

        }else{


            AlertDialog.Builder accessDeniedAlert = new AlertDialog.Builder(attendance.getActivity());
            LayoutInflater inflater = attendance.getActivity().getLayoutInflater();
            View view  = inflater.inflate(R.layout.no_internet_connection_layout_dialog,null, false);
            accessDeniedAlert.setView(view);
            accessDeniedAlert.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    sendRequest(attendance);
                    //attendance.getActivity().btn_attendance_ID.performClick();
                   // new StudentAttendanceDAO(attendance.getActivity()).loadGridviewWithData(attendance);
                }
            });
            accessDeniedAlert.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                    attendance.getActivity().onBackPressed();
                }
            });
            accessDeniedAlert.show();


        }
    }


    private void sendRequestForStudentAttendanceDetils(final Context mContext, APIInterface apiInterface, final ViewClassAttendance attendance){

        attendance.showProgress(true);
        attendance.no_content_to_display_ID.setVisibility(View.INVISIBLE);

        PortalGetAttendanceRequest portalGetAttendanceRequest = new PortalGetAttendanceRequest(
                attendance.studentRequest.getStudentID(),
                String.valueOf(attendance.yearSelected.getYearID()),
                String.valueOf(attendance.termSelected.getTermID()),
                attendance.studentRequest.getOrganizationID(),
                attendance.studentRequest.getAppcode()
//                    "",
//                    "",
//                    "",
//                    "",
//                    "",
//                    "",
//                "",
//                "",
//                ""
        );


        Call<List<StudentClassAttendance>> userCall = apiInterface.getStudentClassAttendance(portalGetAttendanceRequest);
        userCall.enqueue(new Callback<List<StudentClassAttendance>>() {
                             @Override
                             public void onResponse(Call<List<StudentClassAttendance>> call, Response<List<StudentClassAttendance>> response) {
                                 if(response.isSuccessful()){

                                    if(response.code() == 200){
                                        attendance.showProgress(false);
                                        //attendance.showBtnProgress(false);


                                        final List<StudentClassAttendance> responseAttendance = response.body();
                                        Log.d(mContext.getPackageName().toUpperCase(), response.body().toString());
                                        ViewClassAttendanceGridAdapter adapter = new ViewClassAttendanceGridAdapter(attendance.getActivity(), responseAttendance);
                                        attendance.grid_view_class_attendance_ID.setAdapter(adapter);
                                        attendance.grid_view_class_attendance_ID.setVisibility(View.VISIBLE);
                                        attendance.grid_view_class_attendance_ID.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                            @Override
                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(attendance.getActivity());

                                                LayoutInflater inflater = attendance.getActivity().getLayoutInflater();
                                                View dialogViewLayout = inflater.inflate(R.layout.view_attendance_dialog_layout, null, false);
                                                TextView txt_student_fullnames_ID = dialogViewLayout.findViewById(R.id.txt_student_fullnames_ID);
                                                TextView txt_course_name_ID = dialogViewLayout.findViewById(R.id.txt_course_name_ID);
                                                TextView txt_attendance_status_ID = dialogViewLayout.findViewById(R.id.txt_attendance_status_ID);
                                                ImageView image_no_absent_reason_ID = dialogViewLayout.findViewById(R.id.image_no_absent_reason_ID);
                                                TextView txt_reason_for_absence_ID = dialogViewLayout.findViewById(R.id.txt_reason_for_absence_ID);
                                                TextView txt_date_recorded_ID = dialogViewLayout.findViewById(R.id.txt_date_recorded_ID);
                                                TextView txt_roll_call_session_ID = dialogViewLayout.findViewById(R.id.txt_roll_call_session_ID);
                                                LinearLayout ll_reason_for_absence_layout_ID = dialogViewLayout.findViewById(R.id.ll_reason_for_absence_layout_ID);

                                                StudentClassAttendance studentClassAttendance = responseAttendance.get(position);

                                                if (studentClassAttendance.getStatus().equalsIgnoreCase("PRESENT")) {
                                                    txt_student_fullnames_ID.setText(studentClassAttendance.getStudentName());
                                                    txt_course_name_ID.setText(studentClassAttendance.getCourseName());
                                                    txt_attendance_status_ID.setText(studentClassAttendance.getStatus());
                                                    txt_date_recorded_ID.setText(studentClassAttendance.getDateRecorded());
                                                    txt_roll_call_session_ID.setText(studentClassAttendance.getRollcallSessionName());
                                                    ll_reason_for_absence_layout_ID.setVisibility(View.GONE);//reason for absence and image


                                                }else if (studentClassAttendance.getStatus().equalsIgnoreCase("ABSENT") && TextUtils.isEmpty(responseAttendance.get(position).getRemarks())){
                                                    txt_student_fullnames_ID.setText(studentClassAttendance.getStudentName());
                                                    txt_course_name_ID.setText(studentClassAttendance.getCourseName());
                                                    txt_attendance_status_ID.setText(studentClassAttendance.getStatus());
                                                    txt_date_recorded_ID.setText(studentClassAttendance.getDateRecorded());
                                                    txt_roll_call_session_ID.setText(studentClassAttendance.getRollcallSessionName());
                                                    ll_reason_for_absence_layout_ID.setVisibility(View.VISIBLE);
                                                    image_no_absent_reason_ID.setVisibility(View.VISIBLE);
                                                    txt_reason_for_absence_ID .setVisibility(View.GONE);

                                                }else if (studentClassAttendance.getStatus().equalsIgnoreCase("ABSENT") && !TextUtils.isEmpty(responseAttendance.get(position).getRemarks())){

                                                    txt_student_fullnames_ID.setText(studentClassAttendance.getStudentName());
                                                    txt_course_name_ID.setText(studentClassAttendance.getCourseName());
                                                    txt_attendance_status_ID.setText(studentClassAttendance.getStatus());
                                                    txt_date_recorded_ID.setText(studentClassAttendance.getDateRecorded());
                                                    txt_roll_call_session_ID.setText(studentClassAttendance.getRollcallSessionName());
                                                    ll_reason_for_absence_layout_ID.setVisibility(View.VISIBLE);
                                                    image_no_absent_reason_ID.setVisibility(View.GONE);
                                                    txt_reason_for_absence_ID .setVisibility(View.VISIBLE);

                                                }
                                                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.cancel();
                                                    }
                                                });
                                                alertDialog.setCancelable(false);

                                                alertDialog.setView(dialogViewLayout);
                                                alertDialog.show();

                                            }
                                        });
                                    }else{
                                        attendance.no_content_to_display_ID.setVisibility(View.VISIBLE);
                                    }


                                 }else{
                                     attendance.no_content_to_display_ID.setVisibility(View.VISIBLE);
                                 }
                                     //attendance.showBtnProgress(false);
                                     attendance.showProgress(false);
                                     //you dont have to delete all the data


                             }

                             @Override
                             public void onFailure(Call<List<StudentClassAttendance>> call, Throwable t) {

                                 attendance.showProgress(false);
                                 // Toast.makeText(mContext, "Couldnt connect to server. Make sure your phone has an internet connection and try again.", Toast.LENGTH_SHORT).show();

                                 AlertDialog.Builder accessDeniedAlert = new AlertDialog.Builder(attendance.getActivity());
                                 LayoutInflater inflater = attendance.getActivity().getLayoutInflater();
                                 View view  = inflater.inflate(R.layout.no_internet_connection_layout_dialog,null, false);
                                 accessDeniedAlert.setView(view);
                                 accessDeniedAlert.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialog, int which) {
                                         dialog.cancel();
                                        sendRequest(attendance);
                                     }
                                 });
                                 accessDeniedAlert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialog, int which) {
                                         dialog.cancel();
                                        // onBackPressed();
                                         attendance.getActivity().onBackPressed();
                                     }
                                 });
                                 accessDeniedAlert.show();

                             }
                         }
        );
    }


}
