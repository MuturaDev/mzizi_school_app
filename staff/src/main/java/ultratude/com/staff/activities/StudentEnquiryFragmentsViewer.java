package ultratude.com.staff.activities;

import android.os.Bundle;

import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import ultratude.com.staff.constantclasses.DisplayContants;

import ultratude.com.staff.fragments.FragTransaction;
import ultratude.com.staff.fragments.ViewClassAttendance;
import ultratude.com.staff.fragments.ViewDisciplinaryCases;
import ultratude.com.staff.fragments.ViewExamHistory;
import ultratude.com.staff.R;
import ultratude.com.staff.webservice.ResponseModels.StudentRequest;

/**
 * Created by James on 28/04/2019.
 */

public class StudentEnquiryFragmentsViewer extends AppCompatActivity {


    private FrameLayout fragment_transaction;
    public static FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Catcho.Builder(this)
//                .recipients(Constants.CRASH_REPORT_EMAIL)
//                .build();
        setContentView(R.layout.student_enquiry_fragments_viewer_layout);

        ActionBar actionBar = (ActionBar) getSupportActionBar();
        if(actionBar != null){
            actionBar.setHomeAsUpIndicator(R.drawable.back_icon3);
            actionBar.setDisplayHomeAsUpEnabled(true);



        }


        fragment_transaction = findViewById(R.id.fragment_transaction);

        fragmentManager = getSupportFragmentManager();

       String display =  getIntent().getStringExtra(DisplayContants.DISPLAY);
        StudentRequest studentRequest = (StudentRequest) getIntent().getSerializableExtra(DisplayContants.STUDENT_REQUEST);

        if(display.equalsIgnoreCase(DisplayContants.DISPLAY_CLASS_ATTENDANCE)){

            FragTransaction.dislayFragment(ViewClassAttendance.class, fragmentManager, studentRequest);
            getSupportActionBar().setTitle("Class Attendance");

        }else if(display.equalsIgnoreCase(DisplayContants.DISPLAY_DISCIPLINARY_CASES)){


            FragTransaction.dislayFragment(ViewDisciplinaryCases.class, fragmentManager, studentRequest);
            getSupportActionBar().setTitle("Disciplinary Cases");

        }else if(display.equalsIgnoreCase(DisplayContants.DISPLAY_EXAM_HISTORY)){


            FragTransaction.dislayFragment(ViewExamHistory.class, fragmentManager, studentRequest);
            getSupportActionBar().setTitle("Exam History");

        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
