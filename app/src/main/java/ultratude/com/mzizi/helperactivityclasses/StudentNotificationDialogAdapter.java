package ultratude.com.mzizi.helperactivityclasses;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.modelclasses.StudentNotification;
import ultratude.com.mzizi.notificationpg.DisplayNotification.NotificationTopDisplay;
import ultratude.com.mzizi.roomdatabaseclasses.ParentMziziDatabase;
import ultratude.com.mzizi.uiactivities.FloatingActionShow;
import ultratude.com.mzizi.uiactivities.FragTransaction;
import ultratude.com.mzizi.uiactivities.MainActivity;
import ultratude.com.mzizi.uiactivities.NotificationFrag;

public class StudentNotificationDialogAdapter extends RecyclerView.Adapter<StudentNotificationDialogAdapter.ViewHolder> {

    private List<StudentNotification> StudentNotificationList;
    private Context context;
    private FragmentManager fragmentManager;

    CardView exitBtn;



    public StudentNotificationDialogAdapter(List<StudentNotification> StudentNotificationList, Context context, FragmentManager fragmentManager, CardView exitBtn){
        this.StudentNotificationList = StudentNotificationList;
        this.context = context;
        this.fragmentManager = fragmentManager;
        this.exitBtn = exitBtn;
    }


    @Override
    public StudentNotificationDialogAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.choose_student_to_view_notifications_item_layout, parent, false);
        return new StudentNotificationDialogAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final StudentNotificationDialogAdapter.ViewHolder holder, final int position) {
        holder.bind(StudentNotificationList.get(position));
    }


    @Override
    public int getItemCount() {
        return StudentNotificationList.size();
    }

    //this is a class
    //this will take a hold of the views on the item view.
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView siblingName;
        CardView siblingCard;
        TextView regNumber, courseName,txt_notification_count;

        public ViewHolder(View itemView){
            super(itemView);
            siblingCard = itemView.findViewById(R.id.sibling_cardid);
            siblingCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                    new AsyncTask(){
                        @Override
                        protected void onPostExecute(Object o) {
                            NotificationTopDisplay.giveContext(context);
                            NotificationTopDisplay.GetAuthenticatedUser.cancelNotificationDisplay((String)o);

                            exitBtn.callOnClick();

                            Intent intent  = new Intent(context, MainActivity.class);
                            intent.putExtra("OpenNotifications", "OpenNotifications");
                            intent.putExtra("StudentID", (String)o);
                            context.startActivity(intent);
                            ( (MainActivity)context).finish();

                            super.onPostExecute(o);
                        }

                        @Override
                        protected Object doInBackground(Object[] objects) {
                            ParentMziziDatabase db = ParentMziziDatabase.getInstance(context);
                             db.getSwitchSiblingsDAO().setTheMainStudentToThisStudentIDPassed(StudentNotificationList.get(getAdapterPosition()).getStudentID());
                            String studentid = db.getPortalSiblingsDao().getMainStudentFromSibling();
                            if(studentid == null){
                                studentid  = "0";
                            }
                             return studentid;
                        }
                    }.execute();


                }
            });
            imageView = itemView.findViewById(R.id.sibling_image);
            siblingName = itemView.findViewById(R.id.sibling_name);
            regNumber = itemView.findViewById(R.id.reg_num);
            courseName = itemView.findViewById(R.id.course_name);
            txt_notification_count = itemView.findViewById(R.id.txt_notification_count);
        }


        //TODO: For days remaining have the record highlighted in a different color, date should be today
        public void bind(StudentNotification studentNotification){
            imageView.setImageResource(R.drawable.student_registration);
            siblingName.setText("Name: " + studentNotification.getStudentFullNames());
            regNumber.setText("Reg: " + studentNotification.getRegistrationNumber());
            courseName.setText("Course: " + studentNotification.getCourseName());
            txt_notification_count.setText(studentNotification.getNotificationCount());

        }

    }

}