package com.example.a800361.shifterapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.craftman.cardform.Card;
import com.craftman.cardform.CardForm;
import com.craftman.cardform.OnPayBtnClickListner;

public class CardPayment extends AppCompatActivity {

    double  total_calculation;
    double km_travelled;
    String pick_location,destination_location;
    String number_of_seats;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_payment);

        CardForm cardForm = (CardForm)findViewById(R.id.card_form);
        TextView txtDes= (TextView)findViewById(R.id.payment_amount);
        Button btnPay  =(Button)findViewById(R.id.btn_pay);

        //From complete form
        Bundle bundle = getIntent().getExtras();
        total_calculation = bundle.getDouble("calculation");
        txtDes.setText("R"+total_calculation);
        btnPay.setText(String.format("Payer "+txtDes.getText()));
        km_travelled = bundle.getDouble("distance");
        pick_location = bundle.getString("location");
        destination_location =bundle.getString("destination");


        cardForm.setPayBtnClickListner(new OnPayBtnClickListner() {
            @Override
            public void onClick(Card card) {

                Intent i = new Intent(getApplicationContext(),complete_form.class);
                i.putExtra("location",pick_location);
                i.putExtra("destination",destination_location);
                i.putExtra("seats",number_of_seats);
                i.putExtra("calculation",total_calculation);
                startActivity(i);

            }

        });
    }
}
