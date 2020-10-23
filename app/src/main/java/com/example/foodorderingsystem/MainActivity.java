package com.example.foodorderingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private Button register;
    private Button login;
    private EditText email;
    private EditText password;

    private FirebaseAuth auth;
    ProgressDialog pd ;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = findViewById(R.id.register);
        login = findViewById(R.id.login);
        password = findViewById(R.id.password);
        email = findViewById(R.id.email);

        auth = FirebaseAuth.getInstance();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,RegisterActivity.class));
//                finish();

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd = new ProgressDialog(MainActivity.this);
                pd.show();
                String txt_email_login = email.getText().toString();
                String txt_password_login = password.getText().toString();
                loginUser(txt_email_login,txt_password_login);
                  }
        });
    }

    @Override
    protected void onStart() {
            super.onStart();

            if(FirebaseAuth.getInstance().getCurrentUser() != null) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                final String[] usertype = new String[1];
                ref.child("Usertype").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren())
                        {
                            if(dataSnapshot.getKey().equals(auth.getCurrentUser().getUid()))
                            {

                                if (dataSnapshot.getValue().toString().equals("Customer"))
                                {
                                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                                }
                                else startActivity(new Intent(MainActivity.this,ManagerActivity.class));
                            }
                        }
//                        usertype[0] = String.valueOf(snapshot.child(auth.getCurrentUser().getUid()).getValue());
//                        if (usertype[0] == "Customer")
//                        {
//                            startActivity(new Intent(MainActivity.this,HomeActivity.class));
//                        }
//                        else startActivity(new Intent(MainActivity.this,ManagerActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
//                ref.child("Usertype").child(auth.getCurrentUser().getUid());
//                Toast.makeText(MainActivity.this, ref.getKey(), Toast.LENGTH_SHORT).show();

            }
    }

    public void loginUser(String txt_email_login, String txt_password_login) {
        auth.signInWithEmailAndPassword(txt_email_login,txt_password_login).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                pd.dismiss();
                Log.d("LOGGEDIN","log in successfull");
                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                final String[] usertype = new String[1];
                ref.child("Usertype").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren())
                        {
                            if(dataSnapshot.getKey().equals(auth.getCurrentUser().getUid()))
                            {
                                if (dataSnapshot.getValue().toString().equals("Customer"))
                                {
                                    startActivity(new Intent(MainActivity.this,HomeActivity.class));
                                }
                                else startActivity(new Intent(MainActivity.this,ManagerActivity.class));
                            }
                        }
//                       usertype[0] = String.valueOf(snapshot.child(auth.getCurrentUser().getUid()).getValue());
//                        if (usertype[0] == "Customer")
//                        {
//                            startActivity(new Intent(MainActivity.this,HomeActivity.class));
//                        }
//                        else startActivity(new Intent(MainActivity.this,ManagerActivity.class));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
//                ref.child("Usertype").child(auth.getCurrentUser().getUid());
//                Toast.makeText(MainActivity.this, ref.getKey(), Toast.LENGTH_SHORT).show();

//                finish();
            }
        });
        auth.signInWithEmailAndPassword(txt_email_login, txt_password_login).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, "Invalid emailid and password", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });

    }

}
