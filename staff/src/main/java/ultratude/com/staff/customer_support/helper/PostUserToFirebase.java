package ultratude.com.staff.customer_support.helper;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import ultratude.com.staff.customer_support.activities.CustomerDashboard;
import ultratude.com.staff.customer_support.models.UserDetail;
import ultratude.com.staff.customer_support.util.Config;
import ultratude.com.staff.customer_support.util.TimeStamp;
import ultratude.com.staff.customer_support.util.UtilityFunctions;

public class PostUserToFirebase {

    private final String TAG = getClass().getSimpleName();
    private Button contactSupportBtn, adminBtn;
    private SharedPreferenceHelper sharedPreferenceHelper;
    private FireBaseReferenceHelper fireBaseReferenceHelper;
    private String userID;
    private Context context;

   public PostUserToFirebase(Context context, boolean isUserRegistered, String username, String password, String schoolCode){
        fireBaseReferenceHelper = new FireBaseReferenceHelper(context);
        this.context = context;

        sharedPreferenceHelper = new SharedPreferenceHelper(context, Config.SP_ROOT_NAME);

        userID = sharedPreferenceHelper.getSharedPreference().getString(Config.SP_USER_ID, null);

        if (isUserRegistered) {
            if (sharedPreferenceHelper.getBoolean(Config.SP_ADMIN_LOGIN, false)) {
                sharedPreferenceHelper.putBoolean(Config.SP_ADMIN_LOGIN, false);
            }

            context.startActivity(new Intent(context, CustomerDashboard.class));
        } else {
            //NEW USERS

            UserDetail userDetail = new UserDetail();
            userDetail.setUserName(UtilityFunctions.encrypt(username));
            userDetail.setUserPassword(UtilityFunctions.encrypt(password));
            userDetail.setUserSchoolCode(UtilityFunctions.encrypt(schoolCode));
            userDetail.setCreatedAt(TimeStamp.getCurrentTimeStamp());
            userDetail.setProfileCompleted("Y");

            if (userID != null && !userID.trim().equals("")) {
                addUser(userDetail);
            } else {
                ToastHelper.showToast(context.getApplicationContext(), "Invalid unique ID");
            }
        }
    }

    private void addUser(UserDetail userDetail) {

        fireBaseReferenceHelper.allUsersReference().child(userID).setValue(userDetail);
        fireBaseReferenceHelper.allUsersReference().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //hideDialog();
                if (dataSnapshot != null) {
                    UserDetail userDetail = dataSnapshot.child(sharedPreferenceHelper.getSharedPreference().getString(Config.SP_USER_ID, "")).getValue(UserDetail.class);

                    Log.e(TAG, "Username : " + userDetail.getUserName());
                    Log.e(TAG, "Password : " + userDetail.getUserPassword());
                    Log.e(TAG, "SchoolCode : " + userDetail.getUserSchoolCode());

                    sharedPreferenceHelper.putString(Config.SP_USER_NAME, userDetail.getUserName());
                    sharedPreferenceHelper.putString(Config.SP_USER_PASSWORD, userDetail.getUserPassword());
                    sharedPreferenceHelper.putString(Config.SP_USER_SCHOOLCODE, userDetail.getUserSchoolCode());
                    sharedPreferenceHelper.putBoolean(Config.SP_USER_DETAILS_STATUS, true);

                    context.startActivity(new Intent(context, CustomerDashboard.class));
                    //finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                ToastHelper.showToast(context.getApplicationContext(), "Couldn't save the profile details. Please try again");
            }
        });
    }
}
