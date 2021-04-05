package ultratude.com.staff.adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ultratude.com.staff.R;
import ultratude.com.staff.webservice.ResponseModels.StudentDisciplinaryCase;

/**
 * Created by James on 28/04/2019.
 */

public class ViewDisciplinaryCasesAdapter extends RecyclerView.Adapter<ViewDisciplinaryCasesAdapter.ViewHolder>{

    private List<StudentDisciplinaryCase> studentDisciplinaryCaseList;
    private Context mContext;

    public ViewDisciplinaryCasesAdapter(Context mContext, List<StudentDisciplinaryCase> studentDisciplinaryCaseList){
        this.mContext = mContext;
        this.studentDisciplinaryCaseList = studentDisciplinaryCaseList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txt_year_ID,txt_term_ID,txt_date_reported_ID;
        private TextView txt_offence_ID,txt_penaulty_ID,txt_reportedby_ID;

        public ViewHolder(View view){
            super(view);

            txt_year_ID = view.findViewById(R.id.txt_year_ID);
            txt_term_ID = view.findViewById(R.id.txt_term_ID);
            txt_date_reported_ID = view.findViewById(R.id.txt_date_reported_ID);

            txt_offence_ID = view.findViewById(R.id.txt_offence_ID);
            txt_penaulty_ID = view.findViewById(R.id.txt_penaulty_ID);

            txt_reportedby_ID = view.findViewById(R.id.txt_reportedby_ID);

        }

        public final void bind(StudentDisciplinaryCase studentDisciplinaryCase){

            txt_year_ID.setText(studentDisciplinaryCase.getYearID());
            txt_term_ID.setText(studentDisciplinaryCase.getTermID());
            txt_date_reported_ID.setText(studentDisciplinaryCase.getDateCommited());

            txt_offence_ID.setText(studentDisciplinaryCase.getOffenceDescription());
            txt_penaulty_ID.setText(studentDisciplinaryCase.getPenaultyDescription());

            txt_reportedby_ID.setText(studentDisciplinaryCase.getReportedBy());

        }
    }



    @Override
    public ViewDisciplinaryCasesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.disciplinary_cases_item_layout, parent, false);
        return new ViewDisciplinaryCasesAdapter.ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewDisciplinaryCasesAdapter.ViewHolder holder, int position) {
        holder.bind(studentDisciplinaryCaseList.get(position));
    }


    @Override
    public int getItemCount() {
        return studentDisciplinaryCaseList.size();
    }

}