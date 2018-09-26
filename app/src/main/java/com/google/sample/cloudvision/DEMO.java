package com.google.sample.cloudvision;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.client.Firebase;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class DEMO extends AppCompatActivity {

    private FusedLocationProviderClient client;
    public static final String TAG = "l";
    TextView msg;
    CheckBox wasteDumping, encroachment, RRR;
    String message;
    private Button proceed;
    private ImageView image1, image2, image3, image4;
    ArrayList<Bitmap> B;
    Bitmap b1, b2, b3, b4;
    Button Button2;
    String latitude,lon;

    private DatabaseReference dbref;
    private StorageReference stref;
    private Firebase mref;
    String Storage_Path = "All_Image_Uploads/";
    int i;
    Uri pickedUri;
    Uri downloadUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        dbref= FirebaseDatabase.getInstance().getReference();
        stref= FirebaseStorage.getInstance().getReference();

        wasteDumping = (CheckBox) findViewById(R.id.wasteDumping);
        encroachment = (CheckBox) findViewById(R.id.encroachment);
        RRR = (CheckBox) findViewById(R.id.rrr);
        //msg = (TextView) findViewById(R.id.msg);
        proceed = (Button) findViewById(R.id.proceed);
        image1 = (ImageView) findViewById(R.id.image1);
        image2 = (ImageView) findViewById(R.id.image2);
        image3 = (ImageView) findViewById(R.id.image3);
        image4 = (ImageView) findViewById(R.id.image4);
        Button2 = (Button) findViewById(R.id.getLocation);
        Intent i = getIntent();
        String str=i.getStringExtra("Uri");
        if(str!=null)
            pickedUri=Uri.parse(str);
        final int determine[] = i.getIntArrayExtra("DetermineArray");
        final String labelmsg1 = i.getStringExtra("image1data");
        final String labelmsg2 = i.getStringExtra("image2data");
        final String labelmsg3 = i.getStringExtra("image3data");
        final String labelmsg4 = i.getStringExtra("image4data");




        if(determine[1]==1){
            try {

                Bitmap bitmap=BitmapFactory.decodeStream(this.openFileInput("myimage1"));
                image1.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else
        {
            image1.setImageResource(R.drawable.ic_report_problem_white_24dp);
        }
        if(determine[2]==1){
            try {
                Bitmap bitmap=BitmapFactory.decodeStream(this.openFileInput("myimage2"));
                image2.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else
        {
            image2.setImageResource(R.drawable.ic_report_problem_white_24dp);
        }
        if(determine[5]==1){
            try {
                Bitmap bitmap=BitmapFactory.decodeStream(this.openFileInput("myimage5"));
                image3.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else
        {
            image3.setImageResource(R.drawable.ic_report_problem_white_24dp);
        }
        if(determine[7]==1){
            try {
                Bitmap bitmap=BitmapFactory.decodeStream(this.openFileInput("myimage7"));
                image4.setImageBitmap(bitmap);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        else
        {
            image4.setImageResource(R.drawable.ic_report_problem_white_24dp);
        }


        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Boolean allIncorrect=true;

                for(int j=1;j<5;j++)
                {
                    if(determine[j]==1)
                        allIncorrect=false;
                }
                if(allIncorrect)
                {
                    Toast toast=Toast.makeText(getApplicationContext(),"All the images are invalid..you are being directed back to the homepage",Toast.LENGTH_LONG);
                    toast.setMargin(50,50);
                    toast.show();
                    Intent intent = new Intent(DEMO.this,MainActivity.class);
                    startActivity(intent);
                }

                message = "Respected Sir/Ma'am, \n I am hereby uploading photographs of my location : \n Latitude : " +
                        latitude + "\nLongitude : "+lon + "\nThe site has following problems : ";
                if(wasteDumping.isChecked())
                {
                    message = message + "\nWaste Dumping";
                }
                if(encroachment.isChecked())
                {
                    message = message + "\nEncroachment";
                }
                if(RRR.isChecked())
                {
                    message = message + "\nRRR";
                }
                //msg.setText(message);

                UploadDataFileToFirebaseStorage();

                Intent intent = new Intent(DEMO.this,EmailActivity.class);
                intent.putExtra("message",message);
                Log.d(TAG, "onClickmsg:"+message);
                intent.putExtra("latitude",latitude);
                intent.putExtra("longitude",lon);
                intent.putExtra("1data",labelmsg1);
                //Log.d(TAG, "onClick:"+labelmsg1);
                intent.putExtra("iamge2data",labelmsg2);
                intent.putExtra("image3data",labelmsg3);
                intent.putExtra("image4data",labelmsg4);
                startActivity(intent);
            }
        });


//        message = "Respected Sir/Ma'am,\n I wish to report the following problems at the location \n I hereby upload the following phototes";
//        //msg.append(message);
//        //msg.append(message);
//
//        wasteDumping.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b == true) {
//                    editmessage = "\nWaste Dumping";
//
//
//                }
//                if (b == false) {
//                    editmessage = "";
//
//
//                }
//                message = message + editmessage;
//                msg.setText(message);
//            }
//
//        });
//        message = message + editmessage;
//        msg.setText(message);
//
//        encroachment.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b == true) {
//                    editmessage = "\nEnchroachment needed";
//                }
//                if (b == false) {
//                    editmessage = "";
//
//                }
//                message = message + editmessage;
//                msg.setText(message);
//            }
//
//        });
//
//
//        RRR.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (b == true) {
//                    editmessage = "\nThe river needs RRR";
//
//                }
//                if (b == false) {
//                    editmessage = "";
//
//                }
//                message = message + editmessage;
//                msg.setText(message);
//            }
//        });
      RequestPermission();
        client = LocationServices.getFusedLocationProviderClient(this);
        Button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivityCompat.checkSelfPermission(DEMO.this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                client.getLastLocation().addOnSuccessListener(DEMO.this, new OnSuccessListener<Location>() {


                    @Override
                    public void onSuccess(Location location) {

                        Double lat=location.getLatitude();
                        Double longt=location.getLongitude();
                        if(location!=null){
                            TextView textView=(TextView)findViewById(R.id.location);
                            textView.setText(lat.toString()+" "+longt.toString());
                            latitude = lat.toString();
                            lon = longt.toString();

                        }

                    }
                });
            }
        });

    }
    private void RequestPermission(){

        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);
    }

    public void UploadDataFileToFirebaseStorage() {

        final HashMap<String, String> map = new HashMap<>();
        map.put("Prob", message);
        map.put("Location", latitude + " " + lon);
        map.put("Date", DateFormat.getDateTimeInstance().format(new Date()));
        map.put("Status", "Pending");

        if (pickedUri != null) {
            StorageReference stref2 = stref.child(Storage_Path + DateFormat.getDateTimeInstance().format(new Date())+System.currentTimeMillis()+pickedUri.getLastPathSegment());

            stref2.putFile(pickedUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Toast.makeText(getApplicationContext(), "Image Uploaded Successfully ", Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            Toast.makeText(DEMO.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });
        } else{

        }


        map.put("Image", String.valueOf(pickedUri));
        dbref.push().setValue(map);
    }
}
