package ultratude.com.mzizi.tableviewimplementation.preference;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import androidx.annotation.NonNull;

/**
 * Created by evrencoskun on 4.03.2018.
 */

public class SavedState extends View.BaseSavedState {

    public Preferences preferences;

    public SavedState(Parcelable superState) {
        super(superState);
    }

    private SavedState(Parcel in) {
        super(in);
        preferences = in.readParcelable(Preferences.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeParcelable(preferences, flags);
    }

    @NonNull
    public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable
            .Creator<SavedState>() {
        @NonNull
        public SavedState createFromParcel(Parcel in) {
            return new SavedState(in);
        }

        @NonNull
        public SavedState[] newArray(int size) {
            return new SavedState[size];
        }
    };
}
