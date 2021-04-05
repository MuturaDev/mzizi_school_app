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
 * Created by James on 07/01/2019.
 */



public class DatePickerFragment_AttendanceDate extends DialogFragment {



    private DatePickerFragment_AttendanceDateInteractionListener mListener;

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            // Toast.makeText(getActivity(), "Date selected", Toast.LENGTH_LONG).show();


            Calendar cal = Calendar.getInstance();
            cal.set(year, month, dayOfMonth);

            long milli=cal.getTimeInMillis();

            SimpleDateFormat format=new SimpleDateFormat("dd MMM yyyy");
            String datePassed=format.format(milli);


            mListener.onDatePickerFragment_AttendanceDateInteraction(datePassed, milli );
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


        if (context instanceof DatePickerFragment_AttendanceDateInteractionListener) {
            mListener = (DatePickerFragment_AttendanceDateInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DatePickerFragment_DateFueledInteractionListener");
        }
    }


    public interface DatePickerFragment_AttendanceDateInteractionListener {
        // TODO: Update argument type and name
        void onDatePickerFragment_AttendanceDateInteraction(String datePassed, long milli);
    }
}