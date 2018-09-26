package com.google.sample.cloudvision;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class otppage extends AppCompatActivity {

    Button button;
    EditText OTP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otppage);
        button = (Button)findViewById(R.id.login);
        OTP = (EditText)findViewById(R.id.otp);
        Intent i = getIntent();
        final int otpa = i.getIntExtra("otpa",0);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((Integer.valueOf(OTP.getText().toString()).equals(otpa))) {
                    Toast.makeText(getApplicationContext(),
                            R.string.Redirecting, Toast.LENGTH_SHORT).show();
                    Intent toy = new Intent(otppage.this,Problems.class);
                    startActivity(toy);
                }else{
                    Toast.makeText(getApplicationContext(), "Invalid Username or Password ",Toast.LENGTH_SHORT).show();
                }
            }
        });

}}
