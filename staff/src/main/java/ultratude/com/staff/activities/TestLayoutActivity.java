package ultratude.com.staff.activities;


import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ultratude.com.staff.R;
import ultratude.com.staff.activities.accesscontrolforactivities.HomeScreen;
import ultratude.com.staff.utils.UtilityFunctions;

public class TestLayoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity_layout);
        UtilityFunctions.activateQuickActions(this, 5, HomeScreen.CurrentScreenKey);

    }



}
