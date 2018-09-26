package com.google.sample.cloudvision;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.RatingBar;
import android.widget.TextView;

public class RiverRating extends AppCompatActivity {
    RatingBar ratingBar1,ratingBar2,ratingBar3,ratingBar4;
    TextView value1,value2,value3,value4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_river_rating);
        ratingBar1=(RatingBar)findViewById(R.id.ratingbar1);
        ratingBar2=(RatingBar)findViewById(R.id.ratingbar2);
        ratingBar3=(RatingBar)findViewById(R.id.ratingbar3);

        value1=(TextView)findViewById(R.id.value1);
        value2=(TextView)findViewById(R.id.value2);
        value3=(TextView)findViewById(R.id.value3);



        ratingBar1.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                value1.setText("Value is" +rating);


            }
        });
        ratingBar2.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                value2.setText("Value is" +rating);


            }
        });
        ratingBar3.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                value3.setText("Value is" +rating);


            }
        });
        ratingBar4.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                value4.setText("Value is" +rating);


            }
        });
    }
}
