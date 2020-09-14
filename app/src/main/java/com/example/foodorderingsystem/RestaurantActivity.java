package com.example.foodorderingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RestaurantActivity extends AppCompatActivity {

    private static final String TAG = "tag";
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);
        Log.d(TAG, "onCreate: activity restaurant");
        listView = findViewById(R.id.restaurant_list);
        final ArrayList<String> list = new ArrayList<>();
        final ArrayAdapter<String> adapterR = new ArrayAdapter<String>(this,R.layout.list_item,list);
        listView.setAdapter(adapterR);
//        Toast.makeText(RestaurantActivity.this, "initialization", Toast.LENGTH_SHORT).show();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Restaurants");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    list.add(dataSnapshot.getValue().toString());
                }
                adapterR.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    String selectedRestaurant = list.get(position);
                    MenuActivity.getRestaurant(selectedRestaurant);
                    startActivity(new Intent(RestaurantActivity.this,MenuActivity.class));
                }
            });


    }
}