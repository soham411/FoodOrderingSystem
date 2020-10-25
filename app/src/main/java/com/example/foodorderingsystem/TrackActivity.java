package com.example.foodorderingsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import static com.example.foodorderingsystem.OrderActivity.restaurantname;
import static com.example.foodorderingsystem.PaymentActivity.finalorderlist;

public class TrackActivity extends AppCompatActivity {
    FirebaseAuth auth;
ListView listView;
Button yes;
Button send;
RatingBar ratingBar;
EditText review;
TextView thanks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track);
       // ;
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.list_item, finalorderlist);
        listView = findViewById(R.id.olist);
        yes = findViewById(R.id.yes);
        send = findViewById(R.id.send);
        ratingBar = findViewById(R.id.ratingBar);
        review = findViewById(R.id.review);
        thanks = findViewById(R.id.textifreceived);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Set<String> hash_Set = new HashSet<String>();
        final ArrayList<String> revrestlist = new ArrayList<>();
        for (int i = 0; i < finalorderlist.size(); i++) {
//                    String index = Integer.toString(i);
            String str = finalorderlist.get(i);
            String strlist[] = str.split(" - ",0);
            hash_Set.add(strlist[1]);
        }
        for (String value : hash_Set){
            revrestlist.add(value);
        }
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                thanks.setText("Thank You");
                yes.setVisibility(View.INVISIBLE);
                ratingBar.setVisibility(View.VISIBLE);
                review.setVisibility(View.VISIBLE);
                send.setVisibility(View.VISIBLE);
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth = FirebaseAuth.getInstance();
                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference();
                                HashMap<String,Object> reg_user = new HashMap<String,Object>();
                                reg_user.put("rating",ratingBar.getRating());
                                reg_user.put("review",review.getText().toString());
                                ref1.updateChildren(reg_user);
                for (String s:revrestlist)
                {
                    ref1.child("Reviews").child(s).child(auth.getCurrentUser().getUid()).updateChildren(reg_user);
                }
                CartFragment.forPayment.clear();
                CartFragment.fname.clear();
                CartFragment.fprice.clear();
                CartFragment.fquantity.clear();
                CartFragment.frestaurant.clear();
                CartFragment.adapterCart.notifyDataSetChanged();
                startActivity(new Intent(TrackActivity.this,HomeActivity.class));
            }
        });

    }
}