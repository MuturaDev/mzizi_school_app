package ultratude.com.staff.datepickerfragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by James on 05/01/2019.
 */

public class DatePickeRFragment_DateServiced extends DialogFragment {


    private DatePickerFragment_DateServiedInteractionListener mListener;


    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
           // Toast.makeText(getActivity(), "Date selected", Toast.LENGTH_LONG).show();

            Calendar cal = Calendar.getInstance();
            cal.set(year, month, dayOfMonth);

            long mili=cal.getTimeInMillis();

            SimpleDateFormat format=new SimpleDateFormat("dd MMM yyyy");
            String date=format.format(mili);

            mListener.onDatePickerFragment_DateServiedInteraction(date, mili);
        }
    };


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {


        final Calendar cal = Calendar.getInstance();

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);



        return new DatePickerDialog(getActivity(), dateSetListener, year, month, day);
    }





    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


        if (context instanceof DatePickerFragment_DateServiedInteractionListener) {
            mListener = (DatePickerFragment_DateServiedInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DatePickerFragment_DateServiedInteractionListener");
        }
    }


    public interface DatePickerFragment_DateServiedInteractionListener {
        // TODO: Update argument type and name
        void onDatePickerFragment_DateServiedInteraction(String date,long mili);
    }






}
