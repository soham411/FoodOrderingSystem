package com.example.foodorderingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

public class ManagerActivity extends AppCompatActivity {

    private Button addfooditem ;
    private Button dispatched;
    private Button neworders;
    private Button logoutman;
    String restaurantname ="";
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        addfooditem = findViewById(R.id.addfooditem);
        dispatched = findViewById(R.id.dispatchedorders);
        neworders = findViewById(R.id.neworders);
        logoutman = findViewById(R.id.logoutman);
        neworders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ManagerActivity.this,OrderActivity.class));
                auth = FirebaseAuth.getInstance();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Manager").child(auth.getCurrentUser().getUid());
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren())
                        {
                            if(Objects.equals(dataSnapshot.getKey(), "Restaurant"))
                            {
                                restaurantname = dataSnapshot.getValue().toString();
                                OrderActivity.restaurantname = restaurantname;
//                                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Menu").child(restaurantname);
//                                HashMap<String,Object> reg_user = new HashMap<String,Object>();
//                                reg_user.put(newfoodname.getText().toString(),newfoodprice.getText().toString());
//                                ref1.updateChildren(reg_user);
//                                Toast.makeText(AddFoodDialog.this, "", Toast.LENGTH_SHORT).show();
//                                break;

                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        addfooditem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog();
            }
        });
        logoutman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth = FirebaseAuth.getInstance();
                auth.signOut();
                startActivity(new Intent(ManagerActivity.this,MainActivity.class));
            }
        });

    }

    private void openDialog() {
        AddFoodDialog addFoodDialog = new AddFoodDialog();
        addFoodDialog.show(getSupportFragmentManager(),"Add Food Item");
//        addFoodDialog.show(getFragmentManager(),"Add Food Item");
    }
}