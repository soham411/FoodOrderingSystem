package com.example.foodorderingsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class ManagerActivity extends AppCompatActivity {

    private Button addfooditem ;
    private Button dispatched;
    private Button neworders;
    private Button logoutman;

    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);

        addfooditem = findViewById(R.id.addfooditem);
        dispatched = findViewById(R.id.dispatchedorders);
        neworders = findViewById(R.id.neworders);
        logoutman = findViewById(R.id.logoutman);

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