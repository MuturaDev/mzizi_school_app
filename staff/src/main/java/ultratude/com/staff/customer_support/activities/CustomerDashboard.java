package ultratude.com.staff.customer_support.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ultratude.com.staff.R;
import ultratude.com.staff.customer_support.fragments.ComplaintsFragment;
import ultratude.com.staff.customer_support.fragments.FeedbackFragment;
import ultratude.com.staff.customer_support.fragments.SuggestionsFragment;

public class CustomerDashboard extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();

    private RelativeLayout complaintsTab, suggestionsTab, feedbackTab;
    private FrameLayout container;
    private TextView fab;
    private TextView complaintsLabel, suggestionsLabel, feedbackLabel, screenTitle;

    private String selectedTab = "";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_dashboard);

        container = (FrameLayout) findViewById(R.id.container);

        complaintsLabel = (TextView) findViewById(R.id.complaintsLabel);
        suggestionsLabel = (TextView) findViewById(R.id.suggestionsLabel);
        feedbackLabel = (TextView) findViewById(R.id.feedbackLabel);
        screenTitle = (TextView) findViewById(R.id.screenTitle);
        screenTitle.setText("Mzizi Support");

        complaintsTab =  findViewById(R.id.complaintsTab);
        suggestionsTab =  findViewById(R.id.suggestionsTab);
        feedbackTab = findViewById(R.id.feedbackTab);

        fab = (TextView) findViewById(R.id.fab);

        complaintsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab = "Complaints";
                highlightComplaintsTab();
                FragmentManager fm = getSupportFragmentManager();
                final FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.container, ComplaintsFragment.newInstance("Complaints")).commit();
            }
        });

        suggestionsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab = "Suggestions";
                highlightSuggestionsTab();
                FragmentManager fm = getSupportFragmentManager();
                final FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.container, SuggestionsFragment.newInstance("Suggestions")).commit();
            }
        });

        feedbackTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab = "Feedback";
                highlightFeedbackTab();
                FragmentManager fm = getSupportFragmentManager();
                final FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.container, FeedbackFragment.newInstance("Feedback")).commit();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CustomerDashboard.this, AddCSFActivity.class);
                switch (selectedTab) {
                    case "Complaints":
                        intent.putExtra("selectedTab", selectedTab);
                        startActivity(intent);
                        break;
                    case "Suggestions":
                        intent.putExtra("selectedTab", selectedTab);
                        startActivity(intent);
                        break;
                    case "Feedback":
                        intent.putExtra("selectedTab", selectedTab);
                        startActivity(intent);
                        break;
                }
            }
        });

        selectedTab = "Complaints";
        highlightComplaintsTab();
        FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, ComplaintsFragment.newInstance("Complaints")).commit();

    }

    private void highlightComplaintsTab() {
        complaintsLabel.setTextColor(getResources().getColor(R.color.colorPrimary));
        suggestionsLabel.setTextColor(Color.parseColor("#FFFFFF"));
        feedbackLabel.setTextColor(Color.parseColor("#FFFFFF"));

        complaintsTab.setBackgroundColor(Color.parseColor("#FFFFFF"));
        suggestionsTab.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        feedbackTab.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    private void highlightSuggestionsTab() {
        complaintsLabel.setTextColor(Color.parseColor("#FFFFFF"));
        suggestionsLabel.setTextColor(getResources().getColor(R.color.colorPrimary));
        feedbackLabel.setTextColor(Color.parseColor("#FFFFFF"));

        complaintsTab.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        suggestionsTab.setBackgroundColor(Color.parseColor("#FFFFFF"));
        feedbackTab.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
    }

    private void highlightFeedbackTab() {
        complaintsLabel.setTextColor(Color.parseColor("#FFFFFF"));
        suggestionsLabel.setTextColor(Color.parseColor("#FFFFFF"));
        feedbackLabel.setTextColor(getResources().getColor(R.color.colorPrimary));

        complaintsTab.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        suggestionsTab.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        feedbackTab.setBackgroundColor(Color.parseColor("#FFFFFF"));
    }

}
