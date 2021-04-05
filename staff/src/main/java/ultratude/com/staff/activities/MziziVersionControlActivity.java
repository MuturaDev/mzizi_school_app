package ultratude.com.staff.activities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import io.paperdb.Paper;
import ultratude.com.staff.R;

import ultratude.com.staff.displaygif.GifImageView;
import ultratude.com.staff.utils.Constants;
import ultratude.com.staff.webservice.RequestModels.MziziAppVersionControl;

import static ultratude.com.staff.constantclasses.DisplayContants.PAPER_MZIZIVERSIONCONTROL;

public class MziziVersionControlActivity extends AppCompatActivity {

    private Button update_btn;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Catcho.Builder(this)
//                .recipients(Constants.CRASH_REPORT_EMAIL)
//                .build();
        setContentView(R.layout.mziziapp_version_control_layout);

        Paper.init(this);


        GifImageView gifImageView =  findViewById(R.id.GifImageView);
        gifImageView.setGifImageResource(R.drawable.mzizi_version_upgrade);



        if(Paper.book().contains(PAPER_MZIZIVERSIONCONTROL)){
            MziziAppVersionControl mziziAppVersionControl = Paper.book().read(PAPER_MZIZIVERSIONCONTROL);
            List<String> featureUpdatesList =   mziziAppVersionControl.getNewFeatureUpdates();

            if(featureUpdatesList != null)
            if(featureUpdatesList.size() > 0){
                TextView version_number = findViewById(R.id.version_number);
                version_number.setText(featureUpdatesList.get(0));

                StringBuilder sb = new StringBuilder();
                for(int i = 1; i<featureUpdatesList.size(); i++){
                    String version = featureUpdatesList.get(i);
                    sb.append("- ");
                    sb.append(version);
                    sb.append("\n");
                    sb.append("\n");
                }

                TextView mzizi_app_new_feature = findViewById(R.id.mzizi_app_new_feature);
                mzizi_app_new_feature.setText(sb.toString());
            }

        }


        update_btn = findViewById(R.id.update_btn);

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchPlayStore(MziziVersionControlActivity.this,MziziVersionControlActivity.this.getPackageName().toLowerCase());
            }
        });

    }


    public void launchPlayStore(Context context, String packageName) {
        Intent intent = null;
        try {
            intent = new Intent(Intent.ACTION_VIEW);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(Uri.parse("market://details?id=" + packageName));
            context.startActivity(intent);
            finish();
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + packageName)));
            finish();
        }
    }


}
