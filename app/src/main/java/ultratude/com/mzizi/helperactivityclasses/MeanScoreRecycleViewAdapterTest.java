package ultratude.com.mzizi.helperactivityclasses;

/**
 * Created by James on 07/10/2018.
 */

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.lang.ref.WeakReference;
import java.util.List;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentDetailedResults;
import ultratude.com.mzizi.uiactivities.MeanScoreFrag;

/**
 * Created by James on 18/07/2018.
 */

public class MeanScoreRecycleViewAdapterTest extends RecyclerView.Adapter<MeanScoreRecycleViewAdapterTest.DataHolder>{

    WeakReference<MeanScoreFrag> fragWeakReference;

    List<String> uniquePeriodHeaders;

    List<List<PortalStudentDetailedResults>> headerResultList;

    int draws = 0;

    public MeanScoreRecycleViewAdapterTest(MeanScoreFrag meanScoreFrag,List<String> uniquePeriodHeaders, List<List<PortalStudentDetailedResults>> headerResultList){
        this.fragWeakReference = new WeakReference<>(meanScoreFrag);
        this.headerResultList = headerResultList;
        this.uniquePeriodHeaders = uniquePeriodHeaders;
    }


    @Override
    public MeanScoreRecycleViewAdapterTest.DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View    view = LayoutInflater.from(fragWeakReference.get().getContext()).inflate(R.layout.meanscore_recycler_item_layout, parent, false);
        return new DataHolder(view);
    }


    @Override
    public void onBindViewHolder(DataHolder holder, int position) {

            // if(draws <= (uniquePeriodHeaders.size() + 2)){

            List<PortalStudentDetailedResults> resultsTermx =   headerResultList.get(position);//1,3,5 as position for results for term one , term two , term three


            // Toast.makeText(fragWeakReference.get().getActivity(), (holder instanceof DataHolder ) ? String.valueOf(true) : String.valueOf(false), Toast.LENGTH_LONG).show();

            DataHolder dataholder = (DataHolder) holder;



            dataholder.bind(resultsTermx, headerResultList.get(position).size()-1, uniquePeriodHeaders, position);//count will be added 3 times,



            //here there is alot of work to be dome





    }

    @Override
    public int getItemCount() {
        return uniquePeriodHeaders.size();
    }

    //Inner class
    public class DataHolder extends RecyclerView.ViewHolder{

        //the table should be referenced here
        TableLayout tableLayout;
        TextView totalmarks_text, meanscore_text, meangrade_text, streamposition_text, overalposition_text;
        TextView period;
        private TextView txt_head_teacher_comment_ID,txt_grade_facilitator_comment;
        private TextView label_grade_facilitator_comment,label_head_teacher_comment;

        RecyclerView inner_recycler_view;
        MeanScoreInnerDataRecycleViewAdapter meanScoreInnerDataRecycleViewAdapter;
        LinearLayoutManager linearLayoutManagerInner;


        public DataHolder(View itemView){
            super(itemView);

            //tableLayout = itemView.findViewById(R.id.tablelayout);

            //totalmarks_text, meanscore_text, meangrade_text, streamposition_text, overalposition_text
            totalmarks_text = itemView.findViewById(R.id.totalmarks_text);
            meanscore_text = itemView.findViewById(R.id.meanscore_text);
            meangrade_text = itemView.findViewById(R.id.meangrade_text);
            streamposition_text = itemView.findViewById(R.id.streamposition_text);
            overalposition_text = itemView.findViewById(R.id.overalposition_text);
            inner_recycler_view = itemView.findViewById(R.id.inner_recycler_view);

            txt_head_teacher_comment_ID = itemView.findViewById(R.id.txt_head_teacher_comment_ID);
            txt_grade_facilitator_comment = itemView.findViewById(R.id.txt_grade_facilitator_comment);

            label_grade_facilitator_comment = itemView.findViewById(R.id.label_grade_facilitator_comment);
            label_head_teacher_comment = itemView.findViewById(R.id.label_head_teacher_comment);

            period = itemView.findViewById(R.id.period);
        }


        void bind(List<PortalStudentDetailedResults> resultsTermx,int summaryCount, List<String> uniquePeriodHeaders, int position){

            //So this has to be repeated for every period
            //this is so that results are displayed



            //remember your filling in a table


            //start


            //here there must be a decision if its term one , two or three

           // Toast.makeText(fragWeakReference.get().getContext(), String.valueOf(uniquePeriodHeaders.size()), Toast.LENGTH_LONG).show();

            totalmarks_text.setText((resultsTermx.get(summaryCount)).getTotalMarks());
            meanscore_text.setText((resultsTermx.get(summaryCount)).getMeanScore());
            meangrade_text.setText((resultsTermx.get(summaryCount)).getMeanGrade());
            streamposition_text.setText((resultsTermx.get(summaryCount)).getStreamPosition());
            overalposition_text.setText((resultsTermx.get(summaryCount)).getOverallPosition());
            if(!TextUtils.isEmpty(resultsTermx.get(summaryCount).getCtComments())){
                txt_grade_facilitator_comment.setText(resultsTermx.get(summaryCount).getCtComments());
                label_grade_facilitator_comment.setVisibility(View.VISIBLE);
                txt_grade_facilitator_comment.setVisibility(View.VISIBLE);
            }else if(!TextUtils.isEmpty(resultsTermx.get(summaryCount).getHmComments())){
                txt_head_teacher_comment_ID.setText(resultsTermx.get(summaryCount).getHmComments());
                label_head_teacher_comment.setVisibility(View.VISIBLE);
                txt_head_teacher_comment_ID.setVisibility(View.VISIBLE);
            }



            // period.setText((resultsTermx.get(summaryCount)).getPeriod());
            period.setText(uniquePeriodHeaders.get(position));


            linearLayoutManagerInner = new LinearLayoutManager(fragWeakReference.get().getContext(), LinearLayoutManager.VERTICAL, false);
            inner_recycler_view.setLayoutManager(linearLayoutManagerInner);

            meanScoreInnerDataRecycleViewAdapter = new MeanScoreInnerDataRecycleViewAdapter(((MeanScoreFrag) fragWeakReference.get()) ,headerResultList.get(position));

           // Toast.makeText(fragWeakReference.get().getContext(), String.valueOf(headerResultList.get(position).size()),Toast.LENGTH_LONG).show();


           inner_recycler_view.setAdapter(meanScoreInnerDataRecycleViewAdapter);
            inner_recycler_view.scrollToPosition(0);

            inner_recycler_view.setVisibility(View.VISIBLE  );



        }

    }

}





