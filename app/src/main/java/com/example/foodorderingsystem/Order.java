package com.example.foodorderingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Order extends AppCompatActivity {
    public static String restaurantname ="";
    ListView ol;

    final ArrayList<String> orderlist = new ArrayList<>();
    final ArrayAdapter<String> adapterR = new ArrayAdapter<String>(this,R.layout.list_item,orderlist);
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        ol = findViewById(R.id.manorderlist);
        auth = FirebaseAuth.getInstance();
        ol.setAdapter(adapterR);
//        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Orders");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Manager").child(auth.getCurrentUser().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    if(Objects.equals(dataSnapshot.getKey(), "Restaurant"))
                    {
                        restaurantname = dataSnapshot.getValue().toString();
//                        setname(restaurantname);
                        final DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Orders").child(restaurantname);
                        ref1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                recieveorder(snapshot);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ol.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                OrderDetails.uid= orderlist.get(position);
                startActivity(new Intent(Order.this,OrderDetails.class));
            }
        });

        if(restaurantname.equals("")) Toast.makeText(Order.this, "not working", Toast.LENGTH_SHORT).show();
    }

    private void recieveorder(DataSnapshot snapshot) {
        for(DataSnapshot dataSnapshot1 : snapshot.getChildren())
        {
            orderlist.add(dataSnapshot1.getKey());
        }
        adapterR.notifyDataSetChanged();
    }
//
}