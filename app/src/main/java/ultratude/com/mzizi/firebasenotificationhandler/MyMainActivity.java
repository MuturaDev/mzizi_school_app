package ultratude.com.mzizi.firebasenotificationhandler;


import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import ultratude.com.mzizi.R;

public class MyMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_main_activity_layout);
        //FirebaseMessaging.getInstance().subscribeToTopic("ServiceNow");

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            setContentView(R.layout.activity_main);
            TextView message = findViewById(R.id.message);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("******MESSAGE SENT FROM THE FIREBASE CONSOLE*****");
            stringBuilder.append("\n");
            stringBuilder.append("Title: " + extras.getString("Title", "Title"));
            stringBuilder.append("\n");
            stringBuilder.append("Message: " + extras.getString("Message", "Message"));

            message.setText(stringBuilder.toString());
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



    }




    @Override
    protected void onNewIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        if(extras != null){
            setContentView(R.layout.activity_main);
            TextView message = findViewById(R.id.message);

            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("******MESSAGE SENT FROM THE FIREBASE CONSOLE*****");
            stringBuilder.append("\n");
            stringBuilder.append("Title: " + extras.getString("Title", "Title"));
            stringBuilder.append("\n");
            stringBuilder.append("Message: " + extras.getString("Message", "Message"));

            message.setText(stringBuilder.toString());
        }
        super.onNewIntent(intent);
    }
}
