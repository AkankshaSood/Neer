package com.google.sample.cloudvision;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInstaller;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaCas;
import android.media.tv.TvInputService;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.service.textservice.SpellCheckerService;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.cast.framework.Session;
import com.google.api.services.vision.v1.Vision;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.PasswordAuthentication;
import java.net.URI;
import java.security.Security;
import java.util.EnumMap;
import java.util.Properties;

import javax.sql.DataSource;

public class EmailActivity extends AppCompatActivity {


    //public static final String TAG = "HII";
    TextView message;
    Button button;
    String msg;
    Bitmap bitmap;
    Button mapping;
    GMailSender sender;
    public static final String TAG="l";
    CallbackManager callbackManager;
    LoginButton loginButton;
    TextView facebook;
    String labelmsg1,labelmsg2,labelmsg3,labelmsg4;
    Button ratingbutton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.setApplicationId("221239738455521");
//        loginButton=(LoginButton)findViewById(R.id.login_button);

        super.onCreate(savedInstanceState);

        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_email);
        facebook=(TextView)findViewById(R.id.facebook);
        ratingbutton=(Button)findViewById(R.id.ratingbutton1);

        ratingbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(EmailActivity.this,RiverRating.class);
                startActivity(i);

            }
        });


        mapping=(Button)findViewById(R.id.mapping);
        mapping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(EmailActivity.this,MapActivity.class);
                startActivity(intent1);
            }
        });
       mapping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(EmailActivity.this,MapActivity.class);
                startActivity(i);
            }
        });
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(this.openFileInput("myimage1"));
            bitmap.compress(Bitmap.CompressFormat.JPEG,90, openFileOutput("myimage1",MODE_PRIVATE));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        //setContentView(R.layout.activity_email);
        int count=1;
        message = (TextView)findViewById(R.id.message);
        button = (Button)findViewById(R.id.btn);
        Intent intent = getIntent();
        msg = intent.getStringExtra("message");
        //Log.d(TAG, "message: "+msg);
            labelmsg1 = intent.getStringExtra("1data");
        //Log.d(TAG, "onCreatedd: "+labelmsg1);
          labelmsg2 = intent.getStringExtra("image2data");
          labelmsg3 = intent.getStringExtra("image3data");
          labelmsg4 = intent.getStringExtra("image4data");

        msg = msg+"\nThe analysis of images obtained after image processing are as follows: ";

        if(labelmsg1!=null)
        {
            msg+="\nImage "+ count +"\n"+labelmsg1;
            count++;
        }
        if(labelmsg2!=null) {
            msg += "\nImage " + count + "\n" + labelmsg2;
            count++;
        }
        if(labelmsg3!=null) {
            msg += "\nImage " + count + "\n" + labelmsg3;
            count++;
        }
        if(labelmsg4!=null) {
            msg += "\nImage " + count + "\n" + labelmsg4;
            count++;
        }

        //msg=msg+"\n\n"+labelmsg1+"\n\n"+labelmsg2+"\n\n"+labelmsg3+"\n\n"+labelmsg4;

        String latitude = intent.getStringExtra("latitude");
        String longitude = intent.getStringExtra("longitude");
        message.setText(intent.getStringExtra("message"));
        //button = (Button) findViewById(R.id.mybtn);

        // Add your mail Id and Password

        sender = new GMailSender("smartindiahackathon.sih@gmail.com", "update1.0");
        //sender.addAttachment(mFilePath);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.
                Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        button.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                // TODO Auto-generated method stub
                try {
                    new MyAsyncClass().execute();
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
                }
            }
        });
        initilialiseControl();
        loginwithfb();

    }
    private void initilialiseControl(){
        callbackManager=CallbackManager.Factory.create();
        loginButton=(LoginButton)findViewById(R.id.login_button);

    }
    private void loginwithfb(){
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                facebook.setText("Loggin was succesfull " +loginResult.getAccessToken());


            }

            @Override
            public void onCancel() {

//                facebook.setText("The loggin was failed");
            }

            @Override
            public void onError(FacebookException error) {

                facebook.setText("Their was an eror"+error.getMessage() ) ;
            }
        });

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }






//    @Override
//
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        // Inflate the menu; this adds items to the action bar if it //     is present.
//
//        getMenuInflater().inflate(R.menu.main, menu);
//
//        return true;
//
//    }

    class MyAsyncClass extends AsyncTask<Void, Void, Void> {

        ProgressDialog pDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(EmailActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.show();
        }
        @Override
        protected Void doInBackground(Void... mApi) {
            try {
                // Add subject, Body, your mail Id, and receiver mail Id.
                sender.sendMail("River Complaint", msg, "smartindiahackathon.sih@gmail.com", "akankshasud.as@gmail.com");
            }
            catch (Exception ex) {

            }
            return null;
        }



        @Override

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pDialog.cancel();
            Toast.makeText(getApplicationContext(), "Email sent", Toast.LENGTH_LONG).show();
        }

    }
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
////                Intent emailintent2 = new Intent(Intent.ACTION_SEND);
////                emailintent2.setData(Uri.parse("mailto:"));
////
////                emailintent2.setType("*photo/text*");
////                emailintent2.putExtra(Intent.EXTRA_SUBJECT,"Comaplaint regarding river");
////                emailintent2.putExtra(Intent.EXTRA_TEXT,msg);
////
////
////                if(emailintent2.resolveActivity(getPackageManager())!=null)
////                {
////                    startActivity(emailintent2);
////                    //Log.d(TAG, "onClick: hii");
////                }
//                sending(view);
//
//
//            }
//        });
//    }

//sender.addAttachment(mFilePath);



}
