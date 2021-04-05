package ultratude.com.mzizi.tableviewimplementation.tableviewexample;

import android.os.Bundle;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.crashreport.Catcho;
import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;


public class SampleTableViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Catcho.Builder(this)
                .recipients(Constants.CRASH_REPORT_EMAIL)
                .build();

        setContentView(R.layout.sample_tableview_activity_layout);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.activity_container, new
                    OrderItemsFragment2(), OrderItemsFragment2.class.getSimpleName()).commit();
        }
    }
}

