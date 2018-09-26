/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.sample.cloudvision;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.vision.v1.Vision;
import com.google.api.services.vision.v1.VisionRequest;
import com.google.api.services.vision.v1.VisionRequestInitializer;
import com.google.api.services.vision.v1.model.AnnotateImageRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesRequest;
import com.google.api.services.vision.v1.model.BatchAnnotateImagesResponse;
import com.google.api.services.vision.v1.model.EntityAnnotation;
import com.google.api.services.vision.v1.model.Feature;
import com.google.api.services.vision.v1.model.Image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Main4Activity extends AppCompatActivity {
    private static final String CLOUD_VISION_API_KEY = "AIzaSyDBC08XsrdNXUJigUJmjl2qG-CktrZzFhc";
    public static final String FILE_NAME = "temp.jpg";
    private static final String ANDROID_CERT_HEADER = "X-Android-Cert";
    private static final String ANDROID_PACKAGE_HEADER = "X-Android-Package";

    private static final String TAG = Main4Activity.class.getSimpleName();
    private static final int GALLERY_PERMISSIONS_REQUEST = 0;
    private static final int GALLERY_IMAGE_REQUEST = 1;
    public static final int CAMERA_PERMISSIONS_REQUEST = 2;
    public static final int CAMERA_IMAGE_REQUEST = 3;
    public static final int PROGRESS_BAR_TYPE=0;

    private TextView textView1,textView5,textView6,textView7,textView8,textView9,textView10;
    private ImageView imageView1,imageView5,imageView6,imageView7,imageView8,imageView9,imageView10;
    private TextView textView2;
    private ImageView imageView2;
    private TextView textView3;
    private ImageView imageView3;
    private TextView textView4;
    private ImageView imageView4;
    LinearLayout ll1,ll2,ll3,ll4,ll5,ll6,ll7,ll8,ll9,ll10;
    int[] determine=new int[10];
    int count=0;
    int imagenumber=0;
    int i=0;
    //I am declaring variables for progress bar;
    int read=0;
    int lenght=0;
    private ProgressDialog progressDialog;//Declaring the progress dialog


    int counter=0;
    Button button;
    Bitmap[] bitmaps=new Bitmap[100];
    Bitmap b;
    Image image;
    FilterImage filter;
    String message;
    String labelMsg1,labelMsg2,labelMsg3,labelMsg4;
    Uri[] photoarray=new Uri[10];
    Uri photoUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

       // setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                builder
//                        .setMessage(R.string.dialog_select_prompt)
//                        .setPositiveButton(R.string.dialog_select_gallery, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                startGalleryChooser();
//                                counter+=1;
//                            }
//                        })
//                        .setNegativeButton(R.string.dialog_select_camera, new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                startCamera();
//                                counter+=1;
//                            }
//                        });
//                builder.create().show();
//            }
//        });

        textView1 = (TextView) findViewById(R.id.textView1);
        imageView1 = (ImageView) findViewById(R.id.imageview1);
        textView2 = (TextView) findViewById(R.id.textView2);
        imageView2 = (ImageView) findViewById(R.id.imageview2);
        textView3 = (TextView) findViewById(R.id.textView3);
        imageView3 = (ImageView) findViewById(R.id.imageview3);
        textView4 = (TextView) findViewById(R.id.textView4);
        imageView4 = (ImageView) findViewById(R.id.imageview4);
        textView5 = (TextView) findViewById(R.id.textView5);
        imageView5 = (ImageView) findViewById(R.id.imageview5);
        textView6= (TextView) findViewById(R.id.textView6);
        imageView6 = (ImageView) findViewById(R.id.imageview6);
        textView7= (TextView) findViewById(R.id.textView7);
        imageView7 = (ImageView) findViewById(R.id.imageview7);
        textView8= (TextView) findViewById(R.id.textView8);
        imageView8 = (ImageView) findViewById(R.id.imageview8);
        imageView9 = (ImageView) findViewById(R.id.imageview9);
        textView9= (TextView) findViewById(R.id.textView9);
        imageView10 = (ImageView) findViewById(R.id.imageView10);
        textView10= (TextView) findViewById(R.id.textView10);
        button = (Button)findViewById(R.id.Report);

        ll1 = (LinearLayout)findViewById(R.id.ll1);
        ll2 = (LinearLayout)findViewById(R.id.ll2);
        ll3 = (LinearLayout)findViewById(R.id.ll3);
        ll4 = (LinearLayout)findViewById(R.id.ll4);
        ll5 = (LinearLayout)findViewById(R.id.ll5);
        ll6 = (LinearLayout)findViewById(R.id.ll6);
        ll7 = (LinearLayout)findViewById(R.id.ll7);
        ll8 = (LinearLayout)findViewById(R.id.ll8);
        ll9 = (LinearLayout)findViewById(R.id.ll9);
        ll10 = (LinearLayout)findViewById(R.id.ll10);

        ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Main4Activity.this);
                builder
                        .setMessage(R.string.dialog_select_prompt)
                        .setPositiveButton(R.string.dialog_select_gallery, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startGalleryChooser();
                                counter+=1;
                            }
                        })
                        .setNegativeButton(R.string.dialog_select_camera, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startCamera();
                                counter+=1;
                            }
                        });
                builder.create().show();


            }
        });

        ll2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Main4Activity.this);
                builder
                        .setMessage(R.string.dialog_select_prompt)
                        .setPositiveButton(R.string.dialog_select_gallery, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startGalleryChooser();
                                counter+=1;
                            }
                        })
                        .setNegativeButton(R.string.dialog_select_camera, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startCamera();
                                counter+=1;
                            }
                        });
                builder.create().show();


            }
        });

        ll3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Main4Activity.this);
                builder
                        .setMessage(R.string.dialog_select_prompt)
                        .setPositiveButton(R.string.dialog_select_gallery, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startGalleryChooser();
                                counter+=1;
                            }
                        })
                        .setNegativeButton(R.string.dialog_select_camera, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startCamera();
                                counter+=1;
                            }
                        });
                builder.create().show();


            }
        });

        ll4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Main4Activity.this);
                builder
                        .setMessage(R.string.dialog_select_prompt)
                        .setPositiveButton(R.string.dialog_select_gallery, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startGalleryChooser();
                                counter+=1;
                            }
                        })
                        .setNegativeButton(R.string.dialog_select_camera, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startCamera();
                                counter+=1;
                            }
                        });
                builder.create().show();


            }
        });

        ll5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Main4Activity.this);
                builder
                        .setMessage(R.string.dialog_select_prompt)
                        .setPositiveButton(R.string.dialog_select_gallery, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startGalleryChooser();
                                counter+=1;
                            }
                        })
                        .setNegativeButton(R.string.dialog_select_camera, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startCamera();
                                counter+=1;
                            }
                        });
                builder.create().show();


            }
        });

        ll6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Main4Activity.this);
                builder
                        .setMessage(R.string.dialog_select_prompt)
                        .setPositiveButton(R.string.dialog_select_gallery, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startGalleryChooser();
                                counter+=1;
                            }
                        })
                        .setNegativeButton(R.string.dialog_select_camera, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startCamera();
                                counter+=1;
                            }
                        });
                builder.create().show();


            }
        });

        ll7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Main4Activity.this);
                builder
                        .setMessage(R.string.dialog_select_prompt)
                        .setPositiveButton(R.string.dialog_select_gallery, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startGalleryChooser();
                                counter+=1;
                            }
                        })
                        .setNegativeButton(R.string.dialog_select_camera, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startCamera();
                                counter+=1;
                            }
                        });
                builder.create().show();


            }
        });

        ll8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Main4Activity.this);
                builder
                        .setMessage(R.string.dialog_select_prompt)
                        .setPositiveButton(R.string.dialog_select_gallery, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startGalleryChooser();
                                counter+=1;
                            }
                        })
                        .setNegativeButton(R.string.dialog_select_camera, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startCamera();
                                counter+=1;
                            }
                        });
                builder.create().show();


            }
        });

        ll9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Main4Activity.this);
                builder
                        .setMessage(R.string.dialog_select_prompt)
                        .setPositiveButton(R.string.dialog_select_gallery, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startGalleryChooser();
                                counter+=1;
                            }
                        })
                        .setNegativeButton(R.string.dialog_select_camera, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startCamera();
                                counter+=1;
                            }
                        });
                builder.create().show();


            }
        });

        ll10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Main4Activity.this);
                builder
                        .setMessage(R.string.dialog_select_prompt)
                        .setPositiveButton(R.string.dialog_select_gallery, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startGalleryChooser();
                                counter+=1;
                            }
                        })
                        .setNegativeButton(R.string.dialog_select_camera, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startCamera();
                                counter+=1;
                            }
                        });
                builder.create().show();


            }
        });




        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Log.d(TAG, labelMsg1);
                Intent i=new Intent(Main4Activity.this,DEMO.class);
                i.putExtra("DetermineArray",determine);
                if(determine[1]==1)
                    i.putExtra("image1data",labelMsg1);
                if(determine[2]==1)
                    i.putExtra("image2data",labelMsg2);
                if(determine[3]==1)
                    i.putExtra("image3data",labelMsg3);
                if(determine[4]==1)
                    i.putExtra("image4data",labelMsg4);
                if(photoUri!=null)
                    i.putExtra("Uri",photoUri.toString());
                startActivity(i);
            }
        });
    }

    public void startGalleryChooser() {
        if (PermissionUtils.requestPermission(this, GALLERY_PERMISSIONS_REQUEST, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);

            startActivityForResult(Intent.createChooser(intent, "Select a photo") ,
                    GALLERY_IMAGE_REQUEST);
        }
    }

    public void startCamera() {
        if (PermissionUtils.requestPermission(
                this,
                CAMERA_PERMISSIONS_REQUEST,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA)) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
             photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile());
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivityForResult(intent, CAMERA_IMAGE_REQUEST);
        }
    }

    public File getCameraFile() {
        File dir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        return new File(dir, FILE_NAME);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            uploadImage(data.getData());
        } else if (requestCode == CAMERA_IMAGE_REQUEST && resultCode == RESULT_OK) {
            photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile());
            uploadImage(photoUri);
        }
    }

    @Override
    public void onRequestPermissionsResult(
            int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, CAMERA_PERMISSIONS_REQUEST, grantResults)) {
                    startCamera();
                }
                break;
            case GALLERY_PERMISSIONS_REQUEST:
                if (PermissionUtils.permissionGranted(requestCode, GALLERY_PERMISSIONS_REQUEST, grantResults)) {
                    startGalleryChooser();
                }
                break;
        }
    }

    public void uploadImage(Uri uri) {
        if (uri != null) {
            try {
                i=i+1;
                photoarray[i]=uri;

                // scale the image to save on bandwidth
                Bitmap bitmap =
                        scaleBitmapDown(
                                MediaStore.Images.Media.getBitmap(getContentResolver(), uri),
                                1200);

                callCloudVision(bitmap);



                switch (counter){
                    case 1:
                        imageView1.setImageBitmap(bitmap);
                        createImageFromBitmap1(bitmap);
                        break;
                    case 2:
                        imageView2.setImageBitmap(bitmap);
                        createImageFromBitmap2(bitmap);
                        break;
                    case 3:
                        imageView3.setImageBitmap(bitmap);
                        createImageFromBitmap3(bitmap);
                        break;
                    case 4:
                        imageView4.setImageBitmap(bitmap);
                        createImageFromBitmap4(bitmap);
                        break;
                    case 5:
                        imageView5.setImageBitmap(bitmap);
                        createImageFromBitmap5(bitmap);
                        break;
                    case 6:
                        imageView6.setImageBitmap(bitmap);
                        createImageFromBitmap6(bitmap);
                        break;
                    case 7:
                        imageView7.setImageBitmap(bitmap);
                        createImageFromBitmap7(bitmap);
                        break;
                    case 8:
                        imageView8.setImageBitmap(bitmap);
                        createImageFromBitmap8(bitmap);
                        break;
                    case 9:
                        imageView9.setImageBitmap(bitmap);
                        createImageFromBitmap9(bitmap);
                        break;
                    case 10:
                        imageView10.setImageBitmap(bitmap);
                        createImageFromBitmap10(bitmap);
                        break;




                }


            } catch (IOException e) {
                Log.d(TAG, "Image picking failed because " + e.getMessage());
                Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d(TAG, "Image picker gave us a null image.");
            Toast.makeText(this, R.string.image_picker_error, Toast.LENGTH_LONG).show();
        }
    }

    private void callCloudVision(final Bitmap bitmap) throws IOException {
        switch (counter){
            case 1:
                textView1.setText(R.string.loading_message);
                break;
            case 2:
                textView2.setText(R.string.loading_message);
                break;
            case 3:
                textView3.setText(R.string.loading_message);
                break;
            case 4:
                textView4.setText(R.string.loading_message);
                break;
            case 5:
                textView5.setText(R.string.loading_message);
                break;
            case 6:
                textView6.setText(R.string.loading_message);
                break;
            case 7:
                textView7.setText(R.string.loading_message);
                break;
            case 8:
                textView8.setText(R.string.loading_message);
                break;
            case 9:
                textView9.setText(R.string.loading_message);
                break;
            case 10:
                textView10.setText(R.string.loading_message);
                break;


        }
        // Switch text to loading


        // Do the real work in an async task, because we need to use the network anyway
        new AsyncTask<Object, Void, String>() {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showDialog(PROGRESS_BAR_TYPE);
            }

            @Override
            protected String doInBackground(Object... params) {
                try {
                    HttpTransport httpTransport = AndroidHttp.newCompatibleTransport();
                    JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

                    VisionRequestInitializer requestInitializer =
                            new VisionRequestInitializer(CLOUD_VISION_API_KEY) {

                                @Override
                                protected void initializeVisionRequest(VisionRequest<?> visionRequest)
                                        throws IOException {
                                    super.initializeVisionRequest(visionRequest);

                                    String packageName = getPackageName();
                                    visionRequest.getRequestHeaders().set(ANDROID_PACKAGE_HEADER, packageName);

                                    String sig = PackageManagerUtils.getSignature(getPackageManager(), packageName);

                                    visionRequest.getRequestHeaders().set(ANDROID_CERT_HEADER, sig);
                                    while(read!=lenght){
                                        publishProgress();
                                        read++;
                                    }
                                    read=0;
                                }
                            };

                    Vision.Builder builder = new Vision.Builder(httpTransport, jsonFactory, null);
                    builder.setVisionRequestInitializer(requestInitializer);

                    Vision vision = builder.build();

                    BatchAnnotateImagesRequest batchAnnotateImagesRequest =
                            new BatchAnnotateImagesRequest();
                    batchAnnotateImagesRequest.setRequests(new ArrayList<AnnotateImageRequest>() {{
                        AnnotateImageRequest annotateImageRequest = new AnnotateImageRequest();

                        // Add the image
                        Image base64EncodedImage = new Image();

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
                        byte[] imageBytes = byteArrayOutputStream.toByteArray();


                        base64EncodedImage.encodeContent(imageBytes);
                        annotateImageRequest.setImage(base64EncodedImage);

                        // add the features we want
                        annotateImageRequest.setFeatures(new ArrayList<Feature>() {{
                            Feature labelDetection = new Feature();
                            labelDetection.setType("LABEL_DETECTION");
                            labelDetection.setMaxResults(10);
                            add(labelDetection);
                        }});


                        add(annotateImageRequest);
                    }});

                    Vision.Images.Annotate annotateRequest =
                            vision.images().annotate(batchAnnotateImagesRequest);

                    annotateRequest.setDisableGZipContent(true);
                    Log.d(TAG, "created Cloud Vision request object, sending request");

                    BatchAnnotateImagesResponse response = annotateRequest.execute();
                    return convertResponseToString(response).getLabels();

                } catch (GoogleJsonResponseException e) {
                    Log.d(TAG, "failed to make API request because " + e.getContent());
                } catch (IOException e) {
                    Log.d(TAG, "failed to make API request because of other IOException " +
                            e.getMessage());
                }
                return "Cloud Vision API request failed. Check logs for details.";
            }

            protected void onPostExecute(String result) {
                switch (counter){

                    case 1:
                        dismissDialog(PROGRESS_BAR_TYPE);
                        textView1.setText(result);
                        break;
                    case 2:
                        dismissDialog(PROGRESS_BAR_TYPE);
                        textView2.setText(result);
                        break;
                    case 3:
                        dismissDialog(PROGRESS_BAR_TYPE);
                        textView3.setText(result);
                        break;
                    case 4:
                        dismissDialog(PROGRESS_BAR_TYPE);
                        textView4.setText(result);
                        break;
                    case 5:
                        dismissDialog(PROGRESS_BAR_TYPE);
                        textView5.setText(result);
                        break;
                    case 6:
                        dismissDialog(PROGRESS_BAR_TYPE);
                        textView6.setText(result);
                        break;
                    case 7:
                        dismissDialog(PROGRESS_BAR_TYPE);
                        textView7.setText(result);
                        break;
                    case 8:
                        dismissDialog(PROGRESS_BAR_TYPE);
                        textView8.setText(result);
                        break;
                    case 9:
                        dismissDialog(PROGRESS_BAR_TYPE);
                        textView9.setText(result);
                        break;
                    case 10:
                        dismissDialog(PROGRESS_BAR_TYPE);
                        textView10.setText(result);
                        break;

                }

            }
        }.execute();
    }

    public Bitmap scaleBitmapDown(Bitmap bitmap, int maxDimension) {

        int originalWidth = bitmap.getWidth();
        int originalHeight = bitmap.getHeight();
        int resizedWidth = maxDimension;
        int resizedHeight = maxDimension;

        if (originalHeight > originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = (int) (resizedHeight * (float) originalWidth / (float) originalHeight);
        } else if (originalWidth > originalHeight) {
            resizedWidth = maxDimension;
            resizedHeight = (int) (resizedWidth * (float) originalHeight / (float) originalWidth);
        } else if (originalHeight == originalWidth) {
            resizedHeight = maxDimension;
            resizedWidth = maxDimension;
        }
        return Bitmap.createScaledBitmap(bitmap, resizedWidth, resizedHeight, false);
    }

    private FilterImage convertResponseToString(BatchAnnotateImagesResponse response) {
        message = "After processing the image the following results have been obtained :\n\n";
        Boolean isCorrect = false;
        FilterImage filter =null;
        count=count+1;
        imagenumber=+1;

        List<EntityAnnotation> labels = response.getResponses().get(0).getLabelAnnotations();
        if (labels != null) {
            for (EntityAnnotation label : labels) {

                String l = label.getDescription();
                if(imagenumber==1)
                {
                    if(labelMsg1!=null)
                        labelMsg1=labelMsg1+label.getDescription()+"\n";
                    else
                        labelMsg1=l;
                }
                else if(imagenumber==2)
                {
                    if(labelMsg2!=null)
                        labelMsg2=labelMsg2+label.getDescription()+"\n";
                    else
                        labelMsg2=l;
                }
                else if(imagenumber==3)
                {
                    if(labelMsg3!=null)
                        labelMsg3=labelMsg3+label.getDescription()+"\n";
                    else
                        labelMsg3=l;
                }
                else if(imagenumber==4)
                {
                    if(labelMsg4!=null)
                        labelMsg4=labelMsg4+label.getDescription()+"\n";
                    else
                        labelMsg4=l;
                }

                if(l.equals("construction") ||l.equals("waste") || l.equals("garbage") || l.equals("pollution")  ||l.equals("litter") ||l.equals("bridge")  )
                {
                    isCorrect = true;
                }
                message += String.format(Locale.US, "%.3f: %s", label.getScore(), label.getDescription());
                message += "\n";
                //message+="\n"+imagenumber;

            }
            if(isCorrect)
            {
                filter = new FilterImage(message,true);
                determine[count]=1;
            }
            else
            {
                message = "Invalid image";
                filter = new FilterImage(message,false);
                determine[count]=0;

            }
        } else {
            message += "nothing";
            filter = new FilterImage("",false);
        }

        return filter;


    }

    private String createImageFromBitmap1(Bitmap bitmap){

        String Filename="myimage1";
        String a=textView1.getText().toString();
        try{

            ByteArrayOutputStream bytes=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
            FileOutputStream fo=openFileOutput(Filename, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            fo.close();
        }
        catch (IOException e){
            e.printStackTrace();
            Filename=null;


        }
        return Filename;
    }
    private String createImageFromBitmap2(Bitmap bitmap){
        String Filename="myimage2";
        try{

            ByteArrayOutputStream bytes=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
            FileOutputStream fo=openFileOutput(Filename, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            fo.close();
        }
        catch (IOException e){
            e.printStackTrace();
            Filename=null;


        }
        return Filename;
    }
    private String createImageFromBitmap3(Bitmap bitmap){
        String Filename="myimage3";
        try{

            ByteArrayOutputStream bytes=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
            FileOutputStream fo=openFileOutput(Filename, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            fo.close();
        }
        catch (IOException e){
            e.printStackTrace();
            Filename=null;


        }
        return Filename;
    }
    private String createImageFromBitmap4(Bitmap bitmap){
        String Filename="myimage4";
        try{

            ByteArrayOutputStream bytes=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
            FileOutputStream fo=openFileOutput(Filename, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            fo.close();
        }
        catch (IOException e){
            e.printStackTrace();
            Filename=null;


        }
        return Filename;
    }
    protected Dialog onCreateDialog(int id){
        switch (id){
            case PROGRESS_BAR_TYPE:
                progressDialog=new ProgressDialog(this);
                progressDialog.setMessage("Please wait ,while we analyse your image\n");
                progressDialog.setIndeterminate(false);
                progressDialog.setMax(100);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.setCancelable(true);
                progressDialog.show();
                return progressDialog;
            default:
                return null;
        }
    }
    private String createImageFromBitmap5(Bitmap bitmap){
        String Filename="myimage5";
        try{

            ByteArrayOutputStream bytes=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
            FileOutputStream fo=openFileOutput(Filename, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            fo.close();
        }
        catch (IOException e){
            e.printStackTrace();
            Filename=null;


        }
        return Filename;
    }
    private String createImageFromBitmap6(Bitmap bitmap){
        String Filename="myimage6";
        try{

            ByteArrayOutputStream bytes=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
            FileOutputStream fo=openFileOutput(Filename, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            fo.close();
        }
        catch (IOException e){
            e.printStackTrace();
            Filename=null;


        }
        return Filename;
    }
    private String createImageFromBitmap7(Bitmap bitmap){
        String Filename="myimage7";
        try{

            ByteArrayOutputStream bytes=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
            FileOutputStream fo=openFileOutput(Filename, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            fo.close();
        }
        catch (IOException e){
            e.printStackTrace();
            Filename=null;


        }
        return Filename;
    }
    private String createImageFromBitmap8(Bitmap bitmap){
        String Filename="myimage8";
        try{

            ByteArrayOutputStream bytes=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
            FileOutputStream fo=openFileOutput(Filename, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            fo.close();
        }
        catch (IOException e){
            e.printStackTrace();
            Filename=null;


        }
        return Filename;
    }
    private String createImageFromBitmap9(Bitmap bitmap){
        String Filename="myimage9";
        try{

            ByteArrayOutputStream bytes=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
            FileOutputStream fo=openFileOutput(Filename, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            fo.close();
        }
        catch (IOException e){
            e.printStackTrace();
            Filename=null;


        }
        return Filename;
    }
    private String createImageFromBitmap10(Bitmap bitmap){
        String Filename="myimage10";
        try{

            ByteArrayOutputStream bytes=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,bytes);
            FileOutputStream fo=openFileOutput(Filename, Context.MODE_PRIVATE);
            fo.write(bytes.toByteArray());
            fo.close();
        }
        catch (IOException e){
            e.printStackTrace();
            Filename=null;


        }
        return Filename;
    }

}

