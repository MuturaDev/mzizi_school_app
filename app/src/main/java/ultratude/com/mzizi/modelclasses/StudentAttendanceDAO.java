package ultratude.com.mzizi.modelclasses;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ultratude.com.mzizi.helperactivityclasses.ViewClassAttendanceGridAdapter;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.uiactivities.SchoolAttendaceFrag;
import ultratude.com.staff.R;
import ultratude.com.staff.fragments.ViewClassAttendance;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.StudentClassAttendance;
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


    public void loadGridviewWithData(final SchoolAttendaceFrag attendance, final YearSpinner year ,final TermSpinner term){
        AsyncTask asyncTask = new AsyncTask() {
            @Override
            protected void onPreExecute()
            {
                attendance.showProgress(true);
                super.onPreExecute();
            }

            @Override
            protected Object doInBackground(Object[] params) {
                ParentMziziDatabase db = ParentMziziDatabase.getInstance(attendance.getActivity());
                String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                if(studentid == null){
                    studentid  = "0";
                }
                List<StudentClassAttendance> list  = db.getStudentClassAttendanceDAO().getStudentClassAttendance(year.getYearFor(),term.getTermFor(), Integer.valueOf(studentid));

                Log.d(attendance.getActivity().getPackageName().toUpperCase(), "Attendance List: " + String.valueOf(list.size()));

                return list;
            }

            @Override
            protected void onPostExecute(Object o) {

                attendance.showProgress(false);
                attendance.no_content_to_display_ID.setVisibility(View.INVISIBLE);

                final List<StudentClassAttendance> responseAttendance = (List<StudentClassAttendance>)o;

               // no_content_to_display_ID

                if(responseAttendance != null){

                    Log.d(mContext.getPackageName().toUpperCase(), "Attendance Count: " + String.valueOf(responseAttendance.size()));

                    if(responseAttendance.size() > 0){
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
                                    SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy");
                                    Date d = new Date(studentClassAttendance.getDateRecorded());

                                    txt_date_recorded_ID.setText(sdf.format(d));
                                    txt_roll_call_session_ID.setText(studentClassAttendance.getRollcallSessionName());
                                    ll_reason_for_absence_layout_ID.setVisibility(View.GONE);//reason for absence and image


                                } else if (studentClassAttendance.getStatus().equalsIgnoreCase("ABSENT") && TextUtils.isEmpty(responseAttendance.get(position).getRemarks())) {
                                    txt_student_fullnames_ID.setText(studentClassAttendance.getStudentName());
                                    txt_course_name_ID.setText(studentClassAttendance.getCourseName());
                                    txt_attendance_status_ID.setText(studentClassAttendance.getStatus());
                                    SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy");
                                    Date d = new Date(studentClassAttendance.getDateRecorded());
                                    txt_date_recorded_ID.setText(sdf.format(d));
                                    txt_roll_call_session_ID.setText(studentClassAttendance.getRollcallSessionName());
                                    ll_reason_for_absence_layout_ID.setVisibility(View.VISIBLE);
                                    image_no_absent_reason_ID.setVisibility(View.VISIBLE);
                                    txt_reason_for_absence_ID.setVisibility(View.GONE);

                                } else if (studentClassAttendance.getStatus().equalsIgnoreCase("ABSENT") && !TextUtils.isEmpty(responseAttendance.get(position).getRemarks())) {

                                    txt_student_fullnames_ID.setText(studentClassAttendance.getStudentName());
                                    txt_course_name_ID.setText(studentClassAttendance.getCourseName());
                                    txt_attendance_status_ID.setText(studentClassAttendance.getStatus());
                                    SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy");
                                    Date d = new Date(studentClassAttendance.getDateRecorded());
                                    txt_date_recorded_ID.setText(sdf.format(d));
                                    txt_roll_call_session_ID.setText(studentClassAttendance.getRollcallSessionName());
                                    ll_reason_for_absence_layout_ID.setVisibility(View.VISIBLE);
                                    image_no_absent_reason_ID.setVisibility(View.GONE);
                                    txt_reason_for_absence_ID.setVisibility(View.VISIBLE);

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
                        attendance.grid_view_class_attendance_ID.setVisibility(View.INVISIBLE);
                    }
                }else{
                    attendance.no_content_to_display_ID.setVisibility(View.VISIBLE);
                    attendance.grid_view_class_attendance_ID.setVisibility(View.INVISIBLE);

                }


                super.onPostExecute(o);
            }
        };
        asyncTask.execute();



    }

}
