package ultratude.com.staff.fromapp_exammeanscoreandhelperclasses;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.List;


import ultratude.com.staff.fragments.ViewExamHistory;
import ultratude.com.staff.R;
import ultratude.com.staff.webservice.ResponseModels.ExamHistory;

/**
 * Created by James on 07/10/2018.
 */


public class MeanScoreInnerDataRecycleViewAdapter extends RecyclerView.Adapter<MeanScoreInnerDataRecycleViewAdapter.DataHolder>{

    WeakReference<ViewExamHistory> fragWeakReference;

    List<ExamHistory> portalResultList;








    public MeanScoreInnerDataRecycleViewAdapter(ViewExamHistory viewExamHistory, List<ExamHistory> portalResultList){
        this.fragWeakReference = new WeakReference<>(viewExamHistory);
        this.portalResultList = portalResultList;



    }




    @Override
    public MeanScoreInnerDataRecycleViewAdapter.DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(fragWeakReference.get().getContext()).inflate(R.layout.meanscore_innerrecycler_items2, parent, false);
        return new DataHolder(view);



    }


    @Override
    public void onBindViewHolder(DataHolder holder, int position) {


            ExamHistory portalStudentResult = portalResultList.get(position);

            // Toast.makeText(fragWeakReference.get().getActivity(), (holder instanceof DataHolder ) ? String.valueOf(true) : String.valueOf(false), Toast.LENGTH_LONG).show();

            DataHolder dataholder =  holder;

            dataholder.bind(portalStudentResult, position);//count will be added 3 times,

    }

    @Override
    public int getItemCount() {
        return portalResultList.size();
    }

    //Inner class
    public class DataHolder extends RecyclerView.ViewHolder{

        //the table should be referenced here

        TextView subjects, score, grade, remarks;
        LinearLayout linearLayout;

        public DataHolder(View itemView){
            super(itemView);

            //totalmarks_text, meanscore_text, meangrade_text, streamposition_text, overalposition_text
            subjects = itemView.findViewById(R.id.subjects_ID);
            score = itemView.findViewById(R.id.scores_ID);
            grade = itemView.findViewById(R.id.grade_ID);
            remarks = itemView.findViewById(R.id.remarks_ID);
            linearLayout = itemView.findViewById(R.id.inner_linearLayout);

        }


        void bind(ExamHistory portalStudentDetailedResults, int position){


                    //should be removed, did not work
//            if(position == 0){
//                linearLayout.setBackgroundColor(( fragWeakReference.get()).getActivity().getResources().getColor(R.color.background_header_table));
//
//                subjects.setText("Subjects: ");
//                subjects.setTextSize(18);
//                subjects.setTextColor(fragWeakReference.get().getResources().getColor(R.color.black));
//
//
//                score.setText("Score: ");
//                score.setTextSize(18);
//                score.setTextColor(fragWeakReference.get().getResources().getColor(R.color.black));
//
//
//                grade.setText("Grade: ");
//                grade.setTextSize(18);
//                grade.setTextColor(fragWeakReference.get().getResources().getColor(R.color.black));
//
//
//                remarks.setText("Remarks: ");
//                remarks.setTextSize(18);
//                remarks.setTextColor(fragWeakReference.get().getResources().getColor(R.color.black));
//            }


                subjects.setText(portalStudentDetailedResults.getSubjects());
                score.setText(portalStudentDetailedResults.getScore() + "%");
                grade.setText(portalStudentDetailedResults.getGrade());
                remarks.setText(portalStudentDetailedResults.getRemark());



        }

    }

}






