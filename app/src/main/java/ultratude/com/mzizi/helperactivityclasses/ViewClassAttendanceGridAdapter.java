package ultratude.com.mzizi.helperactivityclasses;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.StudentClassAttendance;
import ultratude.com.staff.R;


/**
 * Created by James on 01/05/2019.
 */

public class ViewClassAttendanceGridAdapter extends BaseAdapter{

    private Context mContext;
    private List<StudentClassAttendance> studentClassAttendanceList;

    public ViewClassAttendanceGridAdapter(Context mContext, List<StudentClassAttendance> studentClassAttendanceList){
        this.mContext = mContext;
        this.studentClassAttendanceList = studentClassAttendanceList;
    }

    @Override
    public int getCount() {
        return studentClassAttendanceList.size();
    }

    @Override
    public Object getItem(int position) {
        return studentClassAttendanceList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.attendance_history_grid_item_layout, null, false);

        ImageView image_attendance_ID = view.findViewById(R.id.image_attendance_ID);
        if (studentClassAttendanceList.get(position).getStatus().equalsIgnoreCase("PRESENT")) {
            image_attendance_ID.setImageResource(R.drawable.student_present_icon2);
        }else  if (studentClassAttendanceList.get(position).getStatus().equalsIgnoreCase("ABSENT") && TextUtils.isEmpty(studentClassAttendanceList.get(position).getRemarks())){
            image_attendance_ID.setImageResource(R.drawable.student_absent_icon2);
        }else  if (studentClassAttendanceList.get(position).getStatus().equalsIgnoreCase("ABSENT") && !TextUtils.isEmpty(studentClassAttendanceList.get(position).getRemarks())){
            image_attendance_ID.setImageResource(R.drawable.student_absent_icon2);
        }

        TextView day_of_week_ID = view.findViewById(R.id.day_of_week_ID);
        SimpleDateFormat sdf = new SimpleDateFormat("EEE");
        Date d = new Date(studentClassAttendanceList.get(position).getDateRecorded());
        day_of_week_ID.setText(sdf.format(d));

        TextView txt_dateRecorded_ID = view.findViewById(R.id.txt_dateRecorded_ID);
        txt_dateRecorded_ID.setText(studentClassAttendanceList.get(position).getDateRecorded());

        return view;
    }
}
