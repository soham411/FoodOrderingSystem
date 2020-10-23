package com.example.foodorderingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity {

    private String rupeesymbol = "\u20B9";
 FirebaseAuth auth ;
    DatabaseReference ref;
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
              ref = FirebaseDatabase.getInstance().getReference().child("Orders");
////                DatabaseReference myref = FirebaseDatabase.getInstance().getReference().
                auth = FirebaseAuth.getInstance();
                for (int i = 0; i < finalorderlist.size(); i++) {
//                    String index = Integer.toString(i);
                    String str = finalorderlist.get(i);
                    String strlist[] = str.split(" - ",0);
                    ref.child(strlist[1]).child(auth.getCurrentUser().getUid()).child(strlist[0]).setValue(strlist[2]+" "+strlist[3]);
//
                }
                startActivity(new Intent(PaymentActivity.this,TrackActivity.class));
            }
        });
        op.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ref = FirebaseDatabase.getInstance().getReference().child("Orders");
                auth = FirebaseAuth.getInstance();
                for (int i = 0; i < finalorderlist.size(); i++) {
                    String index = Integer.toString(i);
                    String str = finalorderlist.get(i);
                    String strlist[] = str.split(" - ",0);
                    ref.child(strlist[1]).child(auth.getCurrentUser().getUid()).child(strlist[0]).setValue(strlist[2]+" "+strlist[3]);

                }
                startActivity(new Intent(PaymentActivity.this,OnlinePayActivity.class));
            }
        });
    }
}