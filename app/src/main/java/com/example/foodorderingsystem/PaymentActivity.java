package com.example.foodorderingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    private String rupeesymbol = "\u20B9";

    public static int total_amt,totalquantity;
    public static ArrayList<String> finalorderlist ;
    public static ArrayAdapter<String> adapterPay;
    private ListView finalorder;
    private TextView totalamt ;
    private Button cod;
    private Button op;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        finalorder = findViewById(R.id.finalorderlist);
        totalamt = findViewById(R.id.finalorderamt);
        totalamt.setText(rupeesymbol+Integer.toString(total_amt));
        op = findViewById(R.id.onlinepay);
        cod = findViewById(R.id.cod);

         adapterPay = new ArrayAdapter<>(this,R.layout.cartlistitem,finalorderlist);
        finalorder.setAdapter(adapterPay);
        adapterPay.notifyDataSetChanged();
        cod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaymentActivity.this,TrackActivity.class));
            }
        });
        op.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaymentActivity.this,OnlinePayActivity.class));
            }
        });
    }
}