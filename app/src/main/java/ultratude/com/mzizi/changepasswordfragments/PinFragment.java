package ultratude.com.mzizi.changepasswordfragments;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kevalpatel.passcodeview.PinView;
import com.kevalpatel.passcodeview.authenticator.PasscodeViewPinAuthenticator;
import com.kevalpatel.passcodeview.indicators.CircleIndicator;
import com.kevalpatel.passcodeview.interfaces.AuthenticationListener;
import com.kevalpatel.passcodeview.keys.KeyNamesBuilder;
import com.kevalpatel.passcodeview.keys.RoundKey;

import ultratude.com.mzizi.R;

public class PinFragment extends Fragment {

    public PinFragment(){

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.barchart_fragment_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        // TODO: 2020-04-26 Removed coz it started crashing on android 10
        //registerPin();
        super.onViewCreated(view, savedInstanceState);
    }


    private PinView mPinView;

    private void registerPin(){
        mPinView = getActivity().findViewById(R.id.pin_view);
        mPinView.setIsFingerPrintEnable(true);
        mPinView.setTactileFeedback(false);


        //Set the correct pin code.
        //REQUIRED
        final int[] correctPattern = new int[]{1, 2, 3, 4};
        mPinView.setPinAuthenticator(new PasscodeViewPinAuthenticator(correctPattern));

        //Build the desired key shape and pass the theme parameters.
        //REQUIRED
        mPinView.setKey(new RoundKey.Builder(mPinView)
                .setKeyPadding(R.dimen.key_padding)
                .setKeyStrokeColorResource(R.color.colorAccent)
                .setKeyStrokeWidth(R.dimen.key_stroke_width)
                .setKeyTextColorResource(R.color.colorAccent)
                .setKeyTextSize(R.dimen.key_text_size));

        //Build the desired indicator shape and pass the theme attributes.
        //REQUIRED
        mPinView.setIndicator(new CircleIndicator.Builder(mPinView)
                .setIndicatorRadius(R.dimen.indicator_radius)
                .setIndicatorFilledColorResource(R.color.colorAccent)
                .setIndicatorStrokeColorResource(R.color.colorAccent)
                .setIndicatorStrokeWidth(R.dimen.indicator_stroke_width));

        mPinView.setPinLength(PinView.DYNAMIC_PIN_LENGTH);

        //Set the name of the keys based on your locale.
        //OPTIONAL. If not passed key names will be displayed based on english locale.
        mPinView.setKeyNames(new KeyNamesBuilder()
                .setKeyOne(getActivity(), R.string.key_1)
                .setKeyTwo(getActivity(), R.string.key_2)
                .setKeyThree(getActivity()  , R.string.key_3)
                .setKeyFour(getActivity() , R.string.key_4)
                .setKeyFive(getActivity() , R.string.key_5)
                .setKeySix(getActivity() , R.string.key_6)
                .setKeySeven(getActivity()  , R.string.key_7)
                .setKeyEight(getActivity() , R.string.key_8)
                .setKeyNine(getActivity() , R.string.key_9)
                .setKeyZero(getActivity() , R.string.key_0));

        mPinView.setTitle("Enter the PIN");







        mPinView.setAuthenticationListener(new AuthenticationListener() {

            @Override
            public void onAuthenticationSuccessful() {
                //User authenticated successfully.
                //Navigate to secure screens.
//                startActivity(new Intent(getActivity(), AboutMziziApp.class));
//                getActivity().finish();
                if(mPinView.isFingerPrintEnable()){
                    Toast.makeText(getActivity(), "Successful", Toast.LENGTH_SHORT).show();
                }

                int[] pins =  mPinView.getCurrentTypedPin();

                Log.d(getActivity().getPackageName().toUpperCase(), "Pins Number: " + pins.toString());


            }

            @Override
            public void onAuthenticationFailed() {
                //Calls whenever authentication is failed or user is unauthorized.
                //Do something
            }
        });
    }


}
