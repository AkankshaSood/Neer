package com.google.sample.cloudvision;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by hp on 3/26/2018.
 */

public class HomePage extends AppCompatActivity {

    LinearLayout guestUser;
    LinearLayout authorityLogin, recentupdates,dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coordinate);
        ((CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout)).setTitle("Neer");
        guestUser = (LinearLayout) findViewById(R.id.GuestUser);
        authorityLogin = (LinearLayout) findViewById(R.id.AuthorityLogin);

        guestUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,Main4Activity.class);
                startActivity(intent);
            }
        });

        authorityLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(HomePage.this,Auth_login.class);
                startActivity(intent2);
            }
        });

        recentupdates = (LinearLayout)findViewById(R.id.RecentUpdates);
        recentupdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomePage.this,UserTimeline.class);
                startActivity(intent);
            }
        });

        dashboard = (LinearLayout)findViewById(R.id.dashboard);
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomePage.this,NavigationMenu.class);
                startActivity(i);
            }
        });
    }

}
