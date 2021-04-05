package ultratude.com.mzizi.uiactivities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import ultratude.com.mzizi.R;
import ultratude.com.mzizi.BuildConfig;
import ultratude.com.mzizi.crashreport.Catcho;
import ultratude.com.mzizi.report_analytics.ReportAnalytics;
import ultratude.com.mzizi.roomdatabaseclasses.Util.Constants;

/**
 * Created by James on 06/06/2019.
 */

public class AboutMziziApp extends AppCompatActivity {

    private TextView link_ID,version_name_ID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Catcho.Builder(this)
                .recipients(Constants.CRASH_REPORT_EMAIL)
                .build();
        setContentView(R.layout.about_mzizi_layout);

        int versionCode = BuildConfig.VERSION_CODE;
        String versionName = BuildConfig.VERSION_NAME;

        link_ID = findViewById(R.id.link_ID);
        version_name_ID = findViewById(R.id.version_name_ID);

        version_name_ID.setText(versionName);

        link_ID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //was working just needed a new way of dong things
//                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://mzizi.co.ke/mzizi_privacy_policy.pdf"));
//                startActivity(browserIntent);

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("https://mzizi.co.ke/mzizi_privacy_policy.pdf"), "text/html");
                startActivity(intent);
            }
        });

        ReportAnalytics.reportScreenChangeAnalytic(this, "AboutMziziApp Activity");

    }
}
