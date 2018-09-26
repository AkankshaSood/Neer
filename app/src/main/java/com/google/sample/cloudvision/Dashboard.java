package com.google.sample.cloudvision;

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Dashboard extends AppCompatActivity {

    LinearLayout guestUser;
    LinearLayout authorityLogin,recentupdates,dashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ((CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout)).setTitle("Neer");

        guestUser = (LinearLayout) findViewById(R.id.GuestUser);
        guestUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,Main4Activity.class);
                startActivity(intent);
            }
        });

        authorityLogin = (LinearLayout) findViewById(R.id.AuthorityLogin);
        authorityLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Dashboard.this,Auth_login.class);
                startActivity(intent2);
            }
        });

        recentupdates = (LinearLayout)findViewById(R.id.RecentUpdates);
        recentupdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,UserTimeline.class);
                startActivity(intent);
            }
        });

        dashboard = (LinearLayout)findViewById(R.id.dashboard);
        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,NavigationMenu.class);
                startActivity(intent);
            }
        });


    }
}
