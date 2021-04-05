package ultratude.com.mzizi.videoimagefragments.videoimageutil;

import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import ultratude.com.mzizi.R;

public class TestingMedia extends AppCompatActivity {
    TextView tv,tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testing_media_layout);
        tv = findViewById(R.id.launch_image);
        tv1 = findViewById(R.id.launch_video);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestingMedia.this,ImageSliderDemo.class));
            }
        });
        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(TestingMedia.this,VideoSliderDemo.class));
            }
        });
    }
}

