package ultratude.com.staff.fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import ultratude.com.staff.R;

/**
 * Created by James on 04/05/2019.
 */

public class ChipDialogFragment extends AppCompatDialogFragment {

    public static ChipDialogFragment newInstance(int num){
        ChipDialogFragment f = new ChipDialogFragment();

        //supply num input as an argument
        Bundle args = new Bundle();
        args.putInt("num", num);
        f.setArguments(args);

        return f;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.chip_dialog_fragment_layout, container, false);
    }
}
