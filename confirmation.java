package com.example.a800361.shifterapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class confirmation extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    TextView location_pickUp,destination_location_,date_topick,time_topick,amount_charged,number_of_seats_;

Button request_driver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Arkhip_font.ttf")
                .setFontAttrId(R.attr.fontPath).build());
        setContentView(R.layout.activity_confirmation);


        location_pickUp =(TextView)findViewById(R.id.txtPickDate) ;
        destination_location_ =(TextView)findViewById(R.id.txtPickTime);
        date_topick =(TextView)findViewById(R.id.date_txt_view);
        time_topick  =(TextView)findViewById(R.id.time_txt_view);
        amount_charged  =(TextView)findViewById(R.id.amount_txt_view);
        number_of_seats_ =(TextView)findViewById(R.id.seats_txt_view);
        request_driver =(Button)findViewById(R.id.btnPickupRequest_);

        request_driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request_driver.setText("Waiting for driver ........");
            }
        });

        Bundle bundle = getIntent().getExtras();
        String pick_location = bundle.getString("location");
        String destination_location = bundle.getString("destination");
        String date = bundle.getString("date");
        String time = bundle.getString("time");
        String amout_to_be_paid = bundle.getString("amount");
        String number_of_seats = bundle.getString("seats");

        location_pickUp.setText(pick_location);
        date_topick.setText(date);
        destination_location_.setText(destination_location);
        time_topick.setText(time);
        amount_charged.setText(amout_to_be_paid);
        number_of_seats_.setText(number_of_seats);
    }
}
