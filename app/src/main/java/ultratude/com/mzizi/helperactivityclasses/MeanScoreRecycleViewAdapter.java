package ultratude.com.mzizi.helperactivityclasses;

import android.graphics.Typeface;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.lang.ref.WeakReference;
import java.util.List;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.roomdatabaseclasses.RoomModel.PortalStudentDetailedResults;
import ultratude.com.mzizi.uiactivities.MeanScoreFrag;

/**
 * Created by James on 18/07/2018.
 */

public class MeanScoreRecycleViewAdapter extends RecyclerView.Adapter<MeanScoreRecycleViewAdapter.DataHolder>{

    WeakReference<MeanScoreFrag> fragWeakReference;

    List<String> uniquePeriodHeaders;

    List<List<PortalStudentDetailedResults>> headerResultList;

     int draws = 0;






    public MeanScoreRecycleViewAdapter(MeanScoreFrag meanScoreFrag,List<String> uniquePeriodHeaders, List<List<PortalStudentDetailedResults>> headerResultList){
        this.fragWeakReference = new WeakReference<>(meanScoreFrag);
        this.headerResultList = headerResultList;
        this.uniquePeriodHeaders = uniquePeriodHeaders;

        Toast.makeText(meanScoreFrag.getActivity(), String.valueOf(headerResultList.get(0).size()), Toast.LENGTH_LONG).show();
       //this.headerResultList.clear();
        //this.uniquePeriodHeaders.clear();

    }




    @Override
    public MeanScoreRecycleViewAdapter.DataHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View    view = LayoutInflater.from(fragWeakReference.get().getContext()).inflate(R.layout.meanscore_recycler_item_layout, parent, false);
        return new DataHolder(view);



    }


    @Override
    public void onBindViewHolder(DataHolder holder, int position) {

            //this has to change to the 2 + the number of viewholders
        if(draws <=(uniquePeriodHeaders.size() + (uniquePeriodHeaders.size()-2))){

       // if(draws <= (uniquePeriodHeaders.size() + 2)){

           List<PortalStudentDetailedResults> resultsTermx =   headerResultList.get(position);//1,3,5 as position for results for term one , term two , term three



                // Toast.makeText(fragWeakReference.get().getActivity(), (holder instanceof DataHolder ) ? String.valueOf(true) : String.valueOf(false), Toast.LENGTH_LONG).show();

                DataHolder dataholder = (DataHolder) holder;



                dataholder.bind(resultsTermx, headerResultList.get(position).size()-1, uniquePeriodHeaders, position);//count will be added 3 times,

                draws++;

            //here there is alot of work to be dome

        }else{
            //do nothing
        }



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


        public DataHolder(View itemView){
            super(itemView);

            //tableLayout = itemView.findViewById(R.id.tablelayout);

            //totalmarks_text, meanscore_text, meangrade_text, streamposition_text, overalposition_text
            totalmarks_text = itemView.findViewById(R.id.totalmarks_text);
            meanscore_text = itemView.findViewById(R.id.meanscore_text);
            meangrade_text = itemView.findViewById(R.id.meangrade_text);
            streamposition_text = itemView.findViewById(R.id.streamposition_text);
            overalposition_text = itemView.findViewById(R.id.overalposition_text);

            period = itemView.findViewById(R.id.period);
        }


        void bind(List<PortalStudentDetailedResults> resultsTermx,int summaryCount, List<String> uniquePeriodHeaders, int position){

            //So this has to be repeated for every period
                //this is so that results are displayed



            //remember your filling in a table


            //start


            //here there must be a decision if its term one , two or three


            totalmarks_text.setText((resultsTermx.get(summaryCount)).getTotalMarks());
            meanscore_text.setText((resultsTermx.get(summaryCount)).getMeanScore());
            meangrade_text.setText((resultsTermx.get(summaryCount)).getMeanGrade());
            streamposition_text.setText((resultsTermx.get(summaryCount)).getStreamPosition());
            overalposition_text.setText((resultsTermx.get(summaryCount)).getOverallPosition());



           // period.setText((resultsTermx.get(summaryCount)).getPeriod());
            period.setText(uniquePeriodHeaders.get(position));





            //adding rows dynamically
            //column data



            String[] subjectValues = new String[resultsTermx.size()];
            for(int i = 0; i< resultsTermx.size(); i++){
                String subject = resultsTermx.get(i).getSubjects();
                subjectValues[i] = subject;
            }

            String [] scoreValues = new String[resultsTermx.size()];
            for(int i = 0; i< resultsTermx.size(); i++){
                String score = resultsTermx.get(i).getScore();
                scoreValues[i] = score;
            }

            String[]   gradeValues= new String[resultsTermx.size()];
            for(int i = 0; i< resultsTermx.size(); i++){
                String grade = resultsTermx.get(i).getGrade();
                gradeValues[i] = grade;
            }

            String[] remarksValeus  = new String[resultsTermx.size()];
            for(int i = 0; i< resultsTermx.size(); i++){
                String remark = resultsTermx.get(i).getRemark();
                remarksValeus[i] = remark;
            }




            // TableLayout tablelayout = ( (ViewExamHistory) fragWeakReference.get()).tableLayout;

            TableRow tbrow;
            TextView subjectText, scoreText, gradeText, remarksText;
            //SET HEADERS
            //already set

            //        //create a table row dynamically
            tbrow = new TableRow(( fragWeakReference.get()).getActivity());
            tbrow.setBackgroundColor(( fragWeakReference.get()).getActivity().getResources().getColor(R.color.background_header_table));
            tbrow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            //ItemReference heading
            //subjectText, scoreValues, meanGrade, remarksHeader;
            TextView subjectHeader = new TextView(( fragWeakReference.get()).getActivity());
            subjectHeader.setText("Subjects");
            subjectHeader.setTextSize(18);
            subjectHeader.setGravity(Gravity.START);
            subjectHeader.setTextColor(( fragWeakReference.get()).getActivity().getResources().getColor(R.color.fee_column_header_color));
            subjectHeader.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            subjectHeader.setPadding(5,5,5,0);
            tbrow.addView(subjectHeader);

            TextView scoreHeader = new TextView(( fragWeakReference.get()).getActivity());
            scoreHeader.setText("Score");
            scoreHeader.setGravity(Gravity.CENTER);
            scoreHeader.setTextSize(18);
            scoreHeader.setTextColor(( fragWeakReference.get()).getActivity().getResources().getColor(R.color.fee_column_header_color));
            scoreHeader.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            scoreHeader.setPadding(5,5,5,0);
            tbrow.addView(scoreHeader);

            TextView gradeHeader = new TextView((fragWeakReference.get()).getActivity());
            gradeHeader.setText("Grade");
            gradeHeader.setTextSize(18);
            gradeHeader.setGravity(Gravity.CENTER);
            gradeHeader.setTextColor(( fragWeakReference.get()).getActivity().getResources().getColor(R.color.fee_column_header_color));
            gradeHeader.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            gradeHeader.setPadding(5,5,5,0);
            tbrow.addView(gradeHeader);

            TextView remarksHeader = new TextView(( fragWeakReference.get()).getActivity());
            remarksHeader.setText("Remarks");
            remarksHeader.setTextSize(18);
            remarksHeader.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
            remarksHeader.setGravity(Gravity.END);
            remarksHeader.setTextColor((fragWeakReference.get()).getActivity().getResources().getColor(R.color.fee_column_header_color));
            remarksHeader.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            remarksHeader.setPadding(5,5,5,0);
            tbrow.addView(remarksHeader);



            tableLayout.addView(tbrow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

            //the two textview below are for the divider
//            tbrow = new TableRow(((ViewExamHistory) fragWeakReference.get()).getActivity());
//            tbrow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
//
//           // Divider color
//            View divider = new View(((ViewExamHistory) fragWeakReference.get()).getActivity());
//            divider.setBackgroundColor(Color.BLACK);
//            divider.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 3));
//
////            tbrow.addView(divider);
//
//            tablelayout.addView(divider, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));





            //SET DATA
            int rows = 0;
            for (int i = 0; i < resultsTermx.size(); i++) {
                //create a tablerow dynamically




                tbrow = new TableRow(( fragWeakReference.get()).getActivity());
                tbrow.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));

                //subjectText, scoreValues, meanGrade, remarksHeader;
                subjectText = new TextView(( fragWeakReference.get()).getActivity());
                subjectText.setText(subjectValues[i]);
                subjectText.setTextSize(16);
                subjectText.setGravity(Gravity.START);
                remarksHeader.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_START);
                subjectText.setTextColor(( fragWeakReference.get()).getActivity().getResources().getColor(R.color.fee_column_value_color));
                subjectText.setTypeface(Typeface.DEFAULT);
                //subjectText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                subjectText.setPadding(5, 5, 5, 5);
                tbrow.addView(subjectText);//add view to the table row


                scoreText = new TextView(( fragWeakReference.get()).getActivity());
                scoreText.setText(scoreValues[i] + "%");
                scoreText.setTextSize(16);
                // scoreValues.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                scoreText.setGravity(Gravity.CENTER);
                scoreText.setTextColor(( fragWeakReference.get()).getActivity().getResources().getColor(R.color.fee_column_value_color));
                scoreText.setTypeface(Typeface.DEFAULT);
                scoreText.setPadding(5, 5, 5, 5);
                tbrow.addView(scoreText);//add view to the table row

                gradeText = new TextView(( fragWeakReference.get()).getActivity());
                gradeText.setText(gradeValues[i]);
                gradeText.setTextSize(16);
                //gradeText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                gradeText.setGravity(Gravity.CENTER);
                gradeText.setTextColor((fragWeakReference.get()).getActivity().getResources().getColor(R.color.fee_column_value_color));
                gradeText.setTypeface(Typeface.DEFAULT);
                gradeText.setPadding(5, 5, 5, 5);
                tbrow.addView(gradeText);//add view to the table row


                remarksText = new TextView(( fragWeakReference.get()).getActivity());
                remarksText.setText(remarksValeus[i]);
                remarksText.setTextSize(16);
                //remarksText.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                remarksText.setGravity(Gravity.START);
                remarksText.setTextColor(( fragWeakReference.get()).getActivity().getResources().getColor(R.color.fee_column_value_color));
                remarksText.setTypeface(Typeface.DEFAULT);
                remarksText.setPadding(5, 5, 5, 5);
                tbrow.addView(remarksText);//add view to the table row


                //add the tablerow to the table layout
                tableLayout.addView(tbrow, new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableLayout.LayoutParams.WRAP_CONTENT));
                rows++;
            }

          //  Toast.makeText(( fragWeakReference.get()).getActivity(), "Number of Rows: " + rows, Toast.LENGTH_LONG).show();
            //end
        }


    }


}




