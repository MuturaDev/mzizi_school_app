package ultratude.com.mzizi.helperactivityclasses;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import java.util.List;
import java.util.Random;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.TimeTableResponse;


public class TimeTableRecyclerAdapter extends RecyclerView.Adapter<TimeTableRecyclerAdapter.ViewHolder> {

    private List<List<TimeTableResponse>> TimeTableResponseListList;
    private Context context;

    public TimeTableRecyclerAdapter(List<List<TimeTableResponse>> TimeTableResponseList, Context context){
        this.TimeTableResponseListList = TimeTableResponseList;
        this.context = context;
    }


    @Override
    public TimeTableRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.time_table_view_recyclerview_item, parent, false);
        return new TimeTableRecyclerAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final TimeTableRecyclerAdapter.ViewHolder holder, final int position) {
        //https://stackoverflow.com/questions/42794443/prevent-recyclerview-from-recycling
        holder.setIsRecyclable(false);

        holder.bind(TimeTableResponseListList.get(position));

        holder.populateTime(position);
    }


    @Override
    public int getItemCount() {
        return TimeTableResponseListList.size();
    }

    //this is a class
    //this will take a hold of the views on the item view.
    public class ViewHolder extends RecyclerView.ViewHolder{

        private LinearLayout the_restof_timetable_layout;
        private TextView start_time_rest, end_time_rest;
        private TextView monday_rest, tuesday_rest,wednesday_rest,thursday_rest,friday_rest;
        private TextView monday_break, tuesday_break, wednesday_break, thursday_break,friday_break;

        private LinearLayout timetable_last_layout;
        private TextView start_time_last, end_time_last;
        private TextView monday_last, tuesday_last,wednesday_last,thursday_last,friday_last;



        public ViewHolder(View itemView){
            super(itemView);
            the_restof_timetable_layout = itemView.findViewById(R.id.the_restof_timetable_layout);
            start_time_rest = itemView.findViewById(R.id.start_time_rest);
            end_time_rest = itemView.findViewById(R.id.end_time_rest);

            monday_rest = itemView.findViewById(R.id.monday_rest);
            tuesday_rest = itemView.findViewById(R.id.tuesday_rest);
            wednesday_rest = itemView.findViewById(R.id.wednesday_rest);
            thursday_rest = itemView.findViewById(R.id.thursday_rest);
            friday_rest = itemView.findViewById(R.id.friday_rest);

            monday_break = itemView.findViewById(R.id.monday_break);
            tuesday_break = itemView.findViewById(R.id.tuesday_break);
            wednesday_break = itemView.findViewById(R.id.wednesday_break);
            thursday_break = itemView.findViewById(R.id.thursday_break);
            friday_break = itemView.findViewById(R.id.friday_break);

            timetable_last_layout = itemView.findViewById(R.id.timetable_last_layout);
            start_time_last = itemView.findViewById(R.id.start_time_last);
            end_time_last = itemView.findViewById(R.id.end_time_last);

            monday_last = itemView.findViewById(R.id.monday_last);
            tuesday_last = itemView.findViewById(R.id.tuesday_last);
            wednesday_last = itemView.findViewById(R.id.wednesday_last);
            thursday_last = itemView.findViewById(R.id.thursday_last);
            friday_last = itemView.findViewById(R.id.friday_last);

        }


        private boolean checkTimeToAddTimeTableDataForDay(int position,String hour){

            if(hour.equalsIgnoreCase(getHour(position))){
                return true;
            }else{
                return false;
            }
        }

        private String getHour(int position){
            switch(position){
                case 0:
                    return ("07");
                case 1:
                   return ("08");
                case 2:
                    return ("09");
                case 3:
                   return ("10");
                case 4:
                    return ("11");
                case 5:
                    return( "12");
                case 6:
                    return ("13");
                case 7:
                    return ("14");
                case 8:
                    return ("15");
                case 9:
                    return ("16");
                case 10:
                    return ("17");
                case 11:
                    return ("18");
                  default:
                      return "19";
            }
        }


        private void checkIfTextViewEmpty(TextView txtView){
            String time = getHour(getAdapterPosition());
            String content = txtView.getText().toString();

            //here i was trying to come up with a way to prevent recycler view from recycling views, it did not work
            //https://blog.mindorks.com/how-does-recyclerview-work-internally
            //https://stackoverflow.com/questions/42794443/prevent-recyclerview-from-recycling
           // holder.setIsRecyclable(false);

            if(txtView.getText().toString().isEmpty()){
                txtView.setText("");
                txtView.setBackgroundColor(context.getResources().getColor(R.color.white));
            }
        }


        public void bind(List<TimeTableResponse> timetableList){

            if(timetableList.size() > 0){

                for(TimeTableResponse timetable : timetableList){
                    Log.d(context.getPackageName().toUpperCase(), "Adapter position: " + (getAdapterPosition() == 11));
                    if(getAdapterPosition() == (TimeTableResponseListList.size() - 1)){
                        Log.d(context.getPackageName().toUpperCase(), "Adapter position: " + getAdapterPosition());
                        timetable_last_layout.setVisibility(View.VISIBLE);
                        the_restof_timetable_layout.setVisibility(View.GONE);



                        if(timetable.getDay().equalsIgnoreCase("Monday")){
                            if(checkTimeToAddTimeTableDataForDay((TimeTableResponseListList.size() - 1),timetable.getStartTime().split(":")[0])){
                                StringBuilder sb = new StringBuilder();
                                sb.append(timetable.getSubject());
                                sb.append("\n");
//                                sb.append("Class: " + timetable.getClass_());
//                                sb.append("\n");
                                sb.append("Venue: " +  timetable.getClassRoom());
                                sb.append("\n");
                                sb.append("Teacher: " + timetable.getTeacher());
                                monday_last.setText(sb.toString());
                                monday_last.setBackgroundColor(Color.parseColor(timetable.getUnitColor()));
                            }/*else{
                                monday_last.setText("");
                                monday_last.setBackgroundColor(context.getResources().getColor(R.color.white));
                            }*/

                        }else
                        {
                            checkIfTextViewEmpty(monday_last);
                        }

                         if(timetable.getDay().equalsIgnoreCase("Tuesday")){
                            if(checkTimeToAddTimeTableDataForDay((TimeTableResponseListList.size() - 1),timetable.getStartTime().split(":")[0])){
                                StringBuilder sb = new StringBuilder();
                                sb.append(timetable.getSubject());
                                sb.append("\n");
//                                sb.append("Class: " + timetable.getClass_());
//                                sb.append("\n");
                                sb.append("Venue: " +  timetable.getClassRoom());
                                sb.append("\n");
                                sb.append("Teacher: " + timetable.getTeacher());
                                tuesday_last.setText(sb.toString());
                                tuesday_last.setBackgroundColor(Color.parseColor(timetable.getUnitColor()));
                            }/*else{
                                tuesday_last.setText("");
                                tuesday_last.setBackgroundColor(context.getResources().getColor(R.color.white));
                            }*/
                        }else {
                             checkIfTextViewEmpty(tuesday_last);
                         }

                        if(timetable.getDay().equalsIgnoreCase("Wednesday")){
                            if(checkTimeToAddTimeTableDataForDay((TimeTableResponseListList.size() - 1),timetable.getStartTime().split(":")[0])){
                                StringBuilder sb = new StringBuilder();
                                sb.append(timetable.getSubject());
                                sb.append("\n");
//                                sb.append("Class: " + timetable.getClass_());
//                                sb.append("\n");
                                sb.append("Venue: " +  timetable.getClassRoom());
                                sb.append("\n");
                                sb.append("Teacher: " + timetable.getTeacher());
                                wednesday_last.setText(sb.toString());
                                wednesday_last.setBackgroundColor(Color.parseColor(timetable.getUnitColor()));
                            }/*else{
                                wednesday_last.setText("");
                                wednesday_last.setBackgroundColor(context.getResources().getColor(R.color.white));
                            }*/
                        }else {
                            checkIfTextViewEmpty(wednesday_last);
                        }
                        if(timetable.getDay().equalsIgnoreCase("Thursday")){
                            if(checkTimeToAddTimeTableDataForDay((TimeTableResponseListList.size() - 1),timetable.getStartTime().split(":")[0])){
                                StringBuilder sb = new StringBuilder();
                                sb.append(timetable.getSubject());
                                sb.append("\n");
//                                sb.append("Class: " + timetable.getClass_());
//                                sb.append("\n");
                                sb.append("Venue: " +  timetable.getClassRoom());
                                sb.append("\n");
                                sb.append("Teacher: " + timetable.getTeacher());
                                thursday_last.setText(sb.toString());
                                thursday_last.setBackgroundColor(Color.parseColor(timetable.getUnitColor()));
                            }/*else{
                                thursday_last.setText("");
                                thursday_last.setBackgroundColor(context.getResources().getColor(R.color.white));
                            }*/
                        }else {
                            checkIfTextViewEmpty(thursday_last);
                        }
                         if(timetable.getDay().equalsIgnoreCase("Friday")){
                            if(checkTimeToAddTimeTableDataForDay((TimeTableResponseListList.size() - 1),timetable.getStartTime().split(":")[0])){
                                StringBuilder sb = new StringBuilder();
                                sb.append(timetable.getSubject());
                                sb.append("\n");
//                                sb.append("Class: " + timetable.getClass_());
//                                sb.append("\n");
                                sb.append("Venue: " +  timetable.getClassRoom());
                                sb.append("\n");
                                sb.append("Teacher: " + timetable.getTeacher());
                                friday_last.setText(sb.toString());
                                friday_last.setBackgroundColor(Color.parseColor(timetable.getUnitColor()));
                            }/*else{
                                friday_last.setText("");
                                friday_last.setBackgroundColor(context.getResources().getColor(R.color.white));
                            }*/
                        }else{
                             checkIfTextViewEmpty(friday_last);
                         }



                    }else{
                        the_restof_timetable_layout.setVisibility(View.VISIBLE);
                        timetable_last_layout.setVisibility(View.GONE);

                        int position = getAdapterPosition();
                        String hour = timetable.getStartTime().split(":")[0];

                        String getHour = getHour(position);

                        if(timetable.getDay().equalsIgnoreCase("Monday")){
                            if(checkTimeToAddTimeTableDataForDay(getAdapterPosition(),timetable.getStartTime().split(":")[0])){
                                StringBuilder sb = new StringBuilder();

                                sb.append(timetable.getSubject());
                                sb.append("\n");
//                                sb.append("Class: " + timetable.getClass_());
//                                sb.append("\n");
                                sb.append("Venue: " +  timetable.getClassRoom());
                                sb.append("\n");
                                sb.append("Teacher: " + timetable.getTeacher());
                                monday_rest.setText(sb.toString());
                                monday_rest.setBackgroundColor(Color.parseColor(timetable.getUnitColor()));
                            }/*else{
                                monday_rest.setText("");
                                monday_rest.setBackgroundColor(context.getResources().getColor(R.color.white));
                            }*/
                        }else{
                            checkIfTextViewEmpty(monday_rest);
                        }
                        if(timetable.getDay().equalsIgnoreCase("Tuesday")){
                            if(checkTimeToAddTimeTableDataForDay(getAdapterPosition(),timetable.getStartTime().split(":")[0])){
                                StringBuilder sb = new StringBuilder();
                                sb.append(timetable.getSubject());
                                sb.append("\n");
//                                sb.append("Class: " + timetable.getClass_());
//                                sb.append("\n");
                                sb.append("Venue: " +  timetable.getClassRoom());
                                sb.append("\n");
                                sb.append("Teacher: " + timetable.getTeacher());
                                tuesday_rest.setText(sb.toString());
                                tuesday_rest.setBackgroundColor(Color.parseColor(timetable.getUnitColor()));
                            }else{
                                checkIfTextViewEmpty(tuesday_rest);
                            }
                        }/*else {
                            tuesday_rest.setText("");
                            tuesday_rest.setBackgroundColor(context.getResources().getColor(R.color.white));
                        }*/
                         if(timetable.getDay().equalsIgnoreCase("Wednesday")){
                            if(checkTimeToAddTimeTableDataForDay(getAdapterPosition(),timetable.getStartTime().split(":")[0])){
                                StringBuilder sb = new StringBuilder();
                                sb.append(timetable.getSubject());
                                sb.append("\n");
//                                sb.append("Class: " + timetable.getClass_());
//                                sb.append("\n");
                                sb.append("Venue: " +  timetable.getClassRoom());
                                sb.append("\n");
                                sb.append("Teacher: " + timetable.getTeacher());
                                wednesday_rest.setText(sb.toString());
                                wednesday_rest.setBackgroundColor(Color.parseColor(timetable.getUnitColor()));
                            }/*else{
                                wednesday_rest.setText("");
                                wednesday_rest.setBackgroundColor(context.getResources().getColor(R.color.white));
                            }*/
                        }else {
                             checkIfTextViewEmpty(wednesday_rest);
                         }
                         if(timetable.getDay().equalsIgnoreCase("Thursday")){
                            if(checkTimeToAddTimeTableDataForDay(getAdapterPosition(),timetable.getStartTime().split(":")[0])){
                                StringBuilder sb = new StringBuilder();
                                sb.append(timetable.getSubject());
                                sb.append("\n");
//                                sb.append("Class: " + timetable.getClass_());
//                                sb.append("\n");
                                sb.append("Venue: " +  timetable.getClassRoom());
                                sb.append("\n");
                                sb.append("Teacher: " + timetable.getTeacher());
                                thursday_rest.setText(sb.toString());
                                thursday_rest.setBackgroundColor(Color.parseColor(timetable.getUnitColor()));
                            }/*else{
                                thursday_rest.setText("");
                                thursday_rest.setBackgroundColor(context.getResources().getColor(R.color.white));
                            }*/
                        }else {
                             checkIfTextViewEmpty(thursday_rest);
                         }

                        if(timetable.getDay().equalsIgnoreCase("Friday")){
                            if(checkTimeToAddTimeTableDataForDay(getAdapterPosition(),timetable.getStartTime().split(":")[0])){
                                StringBuilder sb = new StringBuilder();
                                sb.append(timetable.getSubject());
                                sb.append("\n");
//                                sb.append("Class: " + timetable.getClass_());
//                                sb.append("\n");
                                sb.append("Venue: " +  timetable.getClassRoom());
                                sb.append("\n");
                                sb.append("Teacher: " + timetable.getTeacher());
                                friday_rest.setText(sb.toString());
                                friday_rest.setBackgroundColor(Color.parseColor(timetable.getUnitColor()));
                            }/*else{
                                friday_rest.setText("");
                                friday_rest.setBackgroundColor(context.getResources().getColor(R.color.white));
                            }*/
                        }else{
                            checkIfTextViewEmpty(friday_rest);
                        }

                    }
                }

            }else{
                monday_rest.setText("");
                monday_rest.setBackgroundColor(context.getResources().getColor(R.color.white));
                tuesday_rest.setText("");
                tuesday_rest.setBackgroundColor(context.getResources().getColor(R.color.white));
                wednesday_rest.setText("");
                wednesday_rest.setBackgroundColor(context.getResources().getColor(R.color.white));
                thursday_rest.setText("");
                thursday_rest.setBackgroundColor(context.getResources().getColor(R.color.white));
                friday_rest.setText("");
                friday_rest.setBackgroundColor(context.getResources().getColor(R.color.white));

                monday_last.setText("");
                monday_last.setBackgroundColor(context.getResources().getColor(R.color.white));
                tuesday_last.setText("");
                tuesday_last.setBackgroundColor(context.getResources().getColor(R.color.white));
                wednesday_last.setText("");
                wednesday_last.setBackgroundColor(context.getResources().getColor(R.color.white));
                thursday_last.setText("");
                thursday_last.setBackgroundColor(context.getResources().getColor(R.color.white));
                friday_last.setText("");
                friday_last.setBackgroundColor(context.getResources().getColor(R.color.white));
            }
        }

        public void populateTime(int adapterposition){
            setRandomColor();
            switch(adapterposition){
                case 0:
                    start_time_rest.setText("7:00 am");
                    end_time_rest.setText(":45");
                    break;
                case 1:
                    start_time_rest.setText("8:00 am");
                    end_time_rest.setText(":45");
                    break;
                case 2:
                    start_time_rest.setText("9:00 am");
                    end_time_rest.setText(":45");
                    break;
                case 3:
                    start_time_rest.setText("10:00 am");
                    end_time_rest.setText(":45");
                    break;
                case 4:
                    start_time_rest.setText("11:00 am");
                    end_time_rest.setText(":45");
                    break;
                case 5:
                    start_time_rest.setText("12:00 pm");
                    end_time_rest.setText(":45");
                    break;
                case 6:
                    start_time_rest.setText("1:00 pm");
                    end_time_rest.setText(":45");
                    break;
                case 7:
                    start_time_rest.setText("2:00 pm");
                    end_time_rest.setText(":45");
                    break;
                case 8:
                    start_time_rest.setText("3:00 pm");
                    end_time_rest.setText(":45");
                    break;
                case 9:
                    start_time_rest.setText("4:00 pm");
                    end_time_rest.setText(":45");
                    break;
                case 10:
                    start_time_rest.setText("5:00 pm");
                    end_time_rest.setText(":45");
                    break;
                case 11:
                    start_time_last.setText("6:00 pm");
                    end_time_last.setText(":45");
                    break;
            }
        }


        private void setRandomColor(){
            int[] colorArray =
                    {
                            Color.rgb(207, 54, 126),
                            Color.rgb(135, 21, 211),
                            Color.rgb(207, 58, 54 ),
                            Color.rgb(21, 102, 211),

                            Color.rgb(207, 135, 54),
                            Color.rgb(126, 207, 54),
                            Color.rgb(21, 211, 35 ),
                            Color.rgb(207, 148, 54),

                            Color.rgb(54, 207, 59 ),
                            Color.rgb(202, 207, 54),
                            Color.rgb(207, 54, 190),
                            Color.rgb(43, 132, 112),


                            Color.rgb(244, 122, 66),
                            Color.rgb(234, 202, 42),
                            Color.rgb(181, 229, 71),
                            Color.rgb(64, 193, 34),

                            Color.rgb(7, 84, 2),
                            Color.rgb(9, 193, 138),
                            Color.rgb(9, 127, 196),
                            Color.rgb(9, 41, 226),

                            Color.rgb(166, 8, 224),
                            Color.rgb(242, 36, 197),
                            Color.rgb(29, 73, 37),
                            Color.rgb(204, 12, 50)
                    };


            int upperBound = 23;
            int lowerBound = 0;

            Random random = new Random();

            if(getAdapterPosition() == 11){
                timetable_last_layout.setVisibility(View.VISIBLE);
                the_restof_timetable_layout.setVisibility(View.GONE);

//                monday_last.setBackgroundColor(colorArray[random.nextInt(upperBound - lowerBound) + lowerBound]);
//                tuesday_last.setBackgroundColor(colorArray[random.nextInt(upperBound - lowerBound) + lowerBound]);
//                wednesday_last.setBackgroundColor(colorArray[random.nextInt(upperBound - lowerBound) + lowerBound]);
//                thursday_last.setBackgroundColor(colorArray[random.nextInt(upperBound - lowerBound) + lowerBound]);
//                friday_last.setBackgroundColor(colorArray[random.nextInt(upperBound - lowerBound) + lowerBound]);




            }else{
                the_restof_timetable_layout.setVisibility(View.VISIBLE);
                timetable_last_layout.setVisibility(View.GONE);

//                monday_rest.setBackgroundColor(colorArray[random.nextInt(upperBound - lowerBound) + lowerBound]);
//                tuesday_rest.setBackgroundColor(colorArray[random.nextInt(upperBound - lowerBound) + lowerBound]);
//                wednesday_rest.setBackgroundColor(colorArray[random.nextInt(upperBound - lowerBound) + lowerBound]);
//                thursday_rest.setBackgroundColor(colorArray[random.nextInt(upperBound - lowerBound) + lowerBound]);
//                friday_rest.setBackgroundColor(colorArray[random.nextInt(upperBound - lowerBound) + lowerBound]);



            }
        }

    }

}