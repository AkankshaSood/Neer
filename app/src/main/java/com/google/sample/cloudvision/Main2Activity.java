package com.google.sample.cloudvision;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Main2Activity.this.startActivity(new Intent(Main2Activity.this,HomePage.class));
                Main2Activity.this.finish();
            }
        },3000);
    }

}
