package ultratude.com.staff.customer_support;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import ultratude.com.staff.R;
import ultratude.com.staff.customer_support.activities.AdminDashboard;
import ultratude.com.staff.customer_support.activities.CustomerDashboard;
import ultratude.com.staff.customer_support.activities.ProfileFillUpActivity;
import ultratude.com.staff.customer_support.helper.SharedPreferenceHelper;
import ultratude.com.staff.customer_support.helper.UniqueIDGenerator;
import ultratude.com.staff.customer_support.helper.UserRegistration;
import ultratude.com.staff.customer_support.interfaces.UserRegistrationListener;
import ultratude.com.staff.customer_support.util.Config;

public class CustomerSupportTestActivity extends AppCompatActivity implements UserRegistrationListener {
    private final String TAG = getClass().getSimpleName();

    private Button contactSupportBtn, adminBtn;
    private SharedPreferenceHelper sharedPreferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.customer_support_test_activity_layout);

        sharedPreferenceHelper = new SharedPreferenceHelper(this, Config.SP_ROOT_NAME);

        contactSupportBtn = (Button) findViewById(R.id.contactSupportBtn);
        adminBtn = (Button) findViewById(R.id.adminBtn);

        contactSupportBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserRegistration userRegistration = new UserRegistration(CustomerSupportTestActivity.this);
                userRegistration.setUserRegistrationListener(CustomerSupportTestActivity.this);
                userRegistration.isUserRegistered(UniqueIDGenerator.getUniqueID());
            }
        });

        adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sharedPreferenceHelper.putBoolean(Config.SP_ADMIN_LOGIN, true);
                startActivity(new Intent(CustomerSupportTestActivity.this, AdminDashboard.class));
            }
        });
    }

    @Override
    public void registrationStatus(boolean isUserRegistered) {
        if (isUserRegistered) {
            if (sharedPreferenceHelper.getBoolean(Config.SP_ADMIN_LOGIN, false)) {
                sharedPreferenceHelper.putBoolean(Config.SP_ADMIN_LOGIN, false);
            }
            startActivity(new Intent(this, CustomerDashboard.class));
        } else {
            startActivity(new Intent(this, ProfileFillUpActivity.class));
        }
    }
}
