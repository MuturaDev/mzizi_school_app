package ultratude.com.staff.customer_support.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import ultratude.com.staff.R;
import ultratude.com.staff.customer_support.fragments.ComplaintsFragment;
import ultratude.com.staff.customer_support.fragments.FeedbackFragment;
import ultratude.com.staff.customer_support.fragments.SuggestionsFragment;
import ultratude.com.staff.customer_support.helper.ProgressDialogHelper;
import ultratude.com.staff.customer_support.helper.SharedPreferenceHelper;
import ultratude.com.staff.customer_support.util.Config;

public class AdminDashboard extends AppCompatActivity {
    private final String TAG = AdminDashboard.class.getSimpleName();

    private LinearLayout complaintsTab, suggestionsTab, feedbackTab;
    private TextView complaintsLabel, suggestionsLabel, feedbackLabel, screenTitle;

    private ProgressDialogHelper progressDialogHelper;
    private ProgressDialog progressDialog;

    private SharedPreferenceHelper sharedPreferenceHelper;

    public static String selectedTab;
    private Dialog logOutDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);

        sharedPreferenceHelper = new SharedPreferenceHelper(this, Config.SP_ROOT_NAME);

        progressDialogHelper = new ProgressDialogHelper(this, true, true);
        progressDialog = progressDialogHelper.getProgressDialog();
        progressDialog.setMessage("Loading...");

        complaintsTab = (LinearLayout) findViewById(R.id.complaintsTab);
        suggestionsTab = (LinearLayout) findViewById(R.id.suggestionsTab);
        feedbackTab = (LinearLayout) findViewById(R.id.feedbackTab);


        complaintsLabel = (TextView) findViewById(R.id.complaintsLabel);
        suggestionsLabel = (TextView) findViewById(R.id.suggestionsLabel);
        feedbackLabel = (TextView) findViewById(R.id.feedbackLabel);
        screenTitle = (TextView) findViewById(R.id.screenTitle);
        screenTitle.setText("Customer Support");

        complaintsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab = "Complaints";
                highlightComplaintsTab();
                FragmentManager fm = getSupportFragmentManager();
                final FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.container, ComplaintsFragment.newInstance("Complaint")).commit();
            }
        });

        suggestionsTab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedTab = "Suggestions";
                highlightSuggestionsTab();
                FragmentManager fm = getSupportFragmentManager();
                final FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.container, SuggestionsFragment.newInstance("Suggestion")).commit();
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

        selectedTab = "Complaints";
        FragmentManager fm = getSupportFragmentManager();
        final FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.container, ComplaintsFragment.newInstance("Complaint")).commit();
        highlightComplaintsTab();
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
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            showLogoutPopUp();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showDialog() {
        if (!progressDialog.isShowing()) {
            progressDialog.show();
        }
    }

    private void hideDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    private void showLogoutPopUp() {
        AlertDialog.Builder builder = new AlertDialog.Builder(AdminDashboard.this);
        builder.setMessage("Do you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sharedPreferenceHelper.putBoolean(Config.SP_ADMIN_LOGIN, false);
                finish();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logOutDialog.dismiss();
            }
        });

        logOutDialog = builder.create();
        logOutDialog.setCancelable(false);
        logOutDialog.setCanceledOnTouchOutside(false);
        logOutDialog.show();
    }
}
