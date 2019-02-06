package com.example.a800361.shifterapp;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.util.Calendar;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class complete_form extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    Button btnDate,btnTime;
    TextView txtDate,txtTime,amout_to_be_paid;

    Calendar calendar;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    RadioButton rdbnt4Seats,rdbnt7Seats,rdbnt13Seats,rdbnt22Seats;
    double km_travelled;
   Button btnPickupRequest,btnCard_;
   String pick_location,destination_location;
   String number_of_seats;
   RelativeLayout completeRelativeLayout;
   double total_calculation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Arkhip_font.ttf")
                .setFontAttrId(R.attr.fontPath).build());
        setContentView(R.layout.activity_complete_form);
          //Money to pay

        btnDate =(Button)findViewById(R.id.btnPickDate);
        btnTime =(Button)findViewById(R.id.btnPickTime);
        txtDate =(TextView)findViewById(R.id.txtPickDate);
        txtTime =(TextView)findViewById(R.id.txtPickTime);

        initialTimeDate();
         //From home Activity
         Bundle bundle = getIntent().getExtras();
         km_travelled = bundle.getDouble("distance");
         pick_location = bundle.getString("location");
         destination_location =bundle.getString("destination");




        rdbnt4Seats =(RadioButton)findViewById(R.id.rdio4Seats);
        rdbnt7Seats =(RadioButton)findViewById(R.id.rdio7Seats);
        rdbnt13Seats =(RadioButton)findViewById(R.id.rdio13Seats);
        rdbnt22Seats = (RadioButton)findViewById(R.id.rdio22Seats);

        rdbnt4Seats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calculator_4Seats calculator_4Seats =  new Calculator_4Seats();
                double calculations =  calculator_4Seats.getInitial()+(calculator_4Seats.getPrice_per_mile()*km_travelled)+calculator_4Seats.getService_cost()
                        +calculator_4Seats.getPrice_per_minute();
                double calculation = (double) Math.round(calculations*100)/100;
                number_of_seats = "4";
                amout_to_be_paid.setText("R"+calculation);
                total_calculation = calculation;

            }
        });

        rdbnt7Seats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calculator_7Seats calculator_7Seats =  new Calculator_7Seats();
                double calculations = calculator_7Seats .getInitial()+(calculator_7Seats .getPrice_per_mile()*km_travelled)+calculator_7Seats .getService_cost()
                        +calculator_7Seats .getPrice_per_minute();
                double calculation = (double) Math.round(calculations*100)/100;
                number_of_seats = "7";
                amout_to_be_paid.setText("R"+calculation);
                total_calculation = calculation;

            }
        });

        rdbnt13Seats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calculator_13Seats calculator_13Seats =  new Calculator_13Seats();
                double calculations =  calculator_13Seats.getInitial()+(calculator_13Seats .getPrice_per_mile()*km_travelled)+calculator_13Seats.getService_cost()
                        +calculator_13Seats.getPrice_per_minute();
                double calculation = (double) Math.round(calculations*100)/100;
                number_of_seats = "13";
                amout_to_be_paid.setText("R"+calculation);
                total_calculation = calculation;

            }
        });

        rdbnt22Seats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calculator_22Seats calculator_22Seats =  new Calculator_22Seats();
                double calculations =  calculator_22Seats.getInitial()+(calculator_22Seats .getPrice_per_mile()*km_travelled)+calculator_22Seats.getService_cost()
                        +calculator_22Seats.getPrice_per_minute();
                double calculation = (double) Math.round(calculations*100)/100;
                number_of_seats = "22";
                amout_to_be_paid.setText("R"+calculation);
                total_calculation = calculation;

            }
        });

        //Button card killed for card
//        btnCard_ =(Button)findViewById(R.id.btnCard);
//        btnCard_.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(getApplicationContext(),CardPayment.class);
//                i.putExtra("calculation",total_calculation);
//                startActivity(i);
//            }
//        });

        completeRelativeLayout =(RelativeLayout)findViewById(R.id.completeRelativeLayout);
        btnPickupRequest = (Button)findViewById(R.id.btnPickupRequest);
        btnPickupRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(checkClidcked()){

                    Snackbar.make(completeRelativeLayout,"Click on how many seats you want",Snackbar.LENGTH_LONG).show();
                    return;
                }

               Intent i = new Intent(getApplicationContext(),confirmation.class);
               i.putExtra("location",pick_location);
               i.putExtra("destination",destination_location);
               i.putExtra("date",txtDate.getText().toString());
               i.putExtra("time",txtTime.getText().toString());
               i.putExtra("amount",amout_to_be_paid.getText().toString());
               i.putExtra("seats",number_of_seats);
               startActivity(i);
            }
        });


        amout_to_be_paid =(TextView)findViewById(R.id.moneyToPay);





        btnTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();

                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minutes = calendar.get(Calendar.MINUTE);
                timePickerDialog = new TimePickerDialog(complete_form.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(hourOfDay > 11){
                            txtTime.setText(String.format("%02d:%02d",hourOfDay,minute)+"PM");
                        }else {
                            txtTime.setText(String.format("%02d:%02d",hourOfDay,minute)+"AM");
                        }
                    }
                },hour,minutes,false);
                timePickerDialog.show();

            }
        });


        btnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                calendar = Calendar.getInstance();
                int date = calendar.get(Calendar.DAY_OF_MONTH);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);

                datePickerDialog = new DatePickerDialog(complete_form.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int myear, int mmonth, int dayOfMonth) {
                          txtDate.setText(dayOfMonth+"/"+(mmonth+1)+"/"+myear);
                    }
                },date,month,year);
                datePickerDialog.show();
            }
        });



    }

    private boolean checkClidcked() {

        if(!rdbnt4Seats.isChecked() && !rdbnt7Seats.isChecked() && !rdbnt22Seats.isChecked() && !rdbnt13Seats.isChecked()){
            return  true;
        }
        return  false;

    }

    private void initialTimeDate() {
        calendar = Calendar.getInstance();
        int date = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);
        txtDate.setText(date+"/"+(month+1)+"/"+year);
        if(hour > 11){
            txtTime.setText(String.format("%02d:%02d",hour,minutes)+"PM");
        }else {
            txtTime.setText(String.format("%02d:%02d",hour,minutes)+"AM");
        }

    }


}
