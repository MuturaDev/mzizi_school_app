package ultratude.com.staff.datepickerfragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

/**
 * Created by James on 05/01/2019.
 */

public class DatePickerFragment_DateFrom extends DialogFragment {


    private DatePickerFragment_DateFromInteractionListener mListener;


    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            Toast.makeText(getActivity(), "DatePickerFrom is " + year + " / " + (month + 1) + " / " + dayOfMonth, Toast.LENGTH_LONG).show();

            mListener.onDatePickerFragment_DateFromInteraction(year, (month), dayOfMonth);
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


        if (context instanceof DatePickerFragment_DateFromInteractionListener) {
            mListener = (DatePickerFragment_DateFromInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement DatePickerFragment_DateFromInteractionListener");
        }
    }


    public interface DatePickerFragment_DateFromInteractionListener {
        // TODO: Update argument type and name
        void onDatePickerFragment_DateFromInteraction(int year, int month, int day);
    }

}