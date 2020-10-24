package com.example.foodorderingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class OrderDetails extends AppCompatActivity {
public static String uid;
public static String restantname;
ListView listView;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        auth = FirebaseAuth.getInstance();
        listView = findViewById(R.id.itemlist);
        final ArrayList<String> orderlist = new ArrayList<>();
        final ArrayAdapter<String> adapterR = new ArrayAdapter<String>(this,R.layout.list_item,orderlist);
        listView.setAdapter(adapterR);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Manager").child(auth.getCurrentUser().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    if(Objects.equals(dataSnapshot.getKey(), "Restaurant"))
                    {
                        restantname = dataSnapshot.getValue().toString();
                        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Orders").child(restantname).child(uid);
                        ref1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot dataSnapshot1 : snapshot.getChildren())
                                {
                                    orderlist.add(dataSnapshot1.getKey()+" "+dataSnapshot1.getValue().toString());
                                }
                                adapterR.notifyDataSetChanged();

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

    }
}