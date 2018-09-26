package com.google.sample.cloudvision;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class Auth_login extends AppCompatActivity {
    Button login;
    EditText username,password;
    TextView guest;
    GMailSender sender;
    int rand=13852;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth_login);
        //init();
        login = (Button) findViewById(R.id.generateotp);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        guest = (TextView)findViewById(R.id.guest);
        sender = new GMailSender("smartindiahackathon.sih@gmail.com", "laughing1@");
        //sender.addAttachment(mFilePath);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.
                Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//
//            public void onClick(View v) {
//
//                // TODO Auto-generated method stub
//                try {
//                    new MyAsyncClass().execute();
//                } catch (Exception ex) {
//                    Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
//                }
//            }
//        });

        login.setOnClickListener(new View.OnClickListener() {

            //double x = (Math.random()*((99999-10000)+1))+10000;
            //int rand = (int) Math.floor(x);
           // rand2=rand;
            //rand = getRandomDoubleBetweenRange(1001,9999);
//            GMailSender sender = new GMailSender("smartindiahackathon.sih@gmail.com", "update1.0");
//            //sender.addAttachment(mFilePath);
//            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.
//                    Builder().permitAll().build();
//                    StrictMode.setThreadPolicy(policy);
            @Override
            public void onClick(View view) {
                if (username.getText().toString().equals("admin") &&
                        password.getText().toString().equals("admin")) {

                    try {
                        //rand=rand+132;

                        new MyAsyncClass().execute();
                    } catch (Exception ex) {
                        Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
                    }
                    // TODO Auto-generated method stub
//                            try {
//                                new MyAsyncClass().execute();
//                            } catch (Exception ex) {
//                                Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
//                            }


                    Toast.makeText(getApplicationContext(),
                            R.string.Redirecting, Toast.LENGTH_SHORT).show();

                    Intent toy = new Intent(Auth_login.this, otppage.class);
                    toy.putExtra("otpa", rand);
                    startActivity(toy);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Username or Password ", Toast.LENGTH_SHORT).show();


                }
            }
        });


        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent click = new Intent(Auth_login.this,MainActivity.class);
                startActivity(click);
            }
        });


    }

//  void init() {
//        login = (Button) findViewById(R.id.login);
//        username = (EditText) findViewById(R.id.username);
//        password = (EditText) findViewById(R.id.password);
//        guest = (TextView)findViewById(R.id.guest);
//
//        login.setOnClickListener(new View.OnClickListener() {
//
////            double x = (Math.random()*((99999-10000)+1))+10000;
////            int rand = (int) Math.floor(x);
////            rand2=rand;
//            //rand = getRandomDoubleBetweenRange(1001,9999);
//            @Override
//            public void onClick(View view) {
//
//
//                if(username.getText().toString().equals("admin") &&
//                        password.getText().toString().equals("admin")) {
//                    Toast.makeText(getApplicationContext(),
//                            R.string.Redirecting, Toast.LENGTH_SHORT).show();
//                    try {
//                        new MyAsyncClass().execute();
//                    } catch (Exception ex) {
//                        Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
//                    }
//
//
//                    Intent toy = new Intent(Auth_login.this,otppage.class);
//                    //toy.putExtra("otpa",rand);
//                    startActivity(toy);
//                }else{
//                    Toast.makeText(getApplicationContext(), "Invalid Username or Password ",Toast.LENGTH_SHORT).show();
//
//
//                }
//            }
//        });
//
//
//        guest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent click = new Intent(Auth_login.this,MainActivity.class);
//                startActivity(click);
//            }
//        });
//
//
//    }


    class MyAsyncClass extends AsyncTask<Void, Void, Void> {

        ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            Random ran = new Random();
            super.onPreExecute();
            rand = rand+ran.nextInt(100);
            pDialog = new ProgressDialog(Auth_login.this);
            pDialog.setMessage(String.valueOf(R.string.Please_Wait));
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... mApi) {
            try {

                // Add subject, Body, your mail Id, and receiver mail Id.
//                double x = (Math.random()*((99999-10000)+1))+10000;
//           int rand = (int) Math.floor(x);
    //      rand2=rand;

                sender.sendMail("OTPA",String.valueOf(rand), "smartindiahackathon.sih@gmail.com", "akankshasud.as@gmail.com");
            }
            catch (Exception ex) {

            }
            return null;
        }



        @Override

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog.cancel();
            Toast.makeText(getApplicationContext(), "OTP sent", Toast.LENGTH_LONG).show();
        }

    }



}
