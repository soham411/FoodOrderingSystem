package com.example.foodorderingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {

    private EditText email;
    private EditText password;
    private Button register;
    private RadioButton usertype;
    private FirebaseAuth auth;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        email = findViewById(R.id.email_reg);
        password = findViewById(R.id.password_reg);
        register = findViewById(R.id.register);

        auth = FirebaseAuth.getInstance();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password))
                    Toast.makeText(RegisterActivity.this,"Empty Credentials",Toast.LENGTH_SHORT).show();
                else if(txt_password.length() < 6)
                    Toast.makeText(RegisterActivity.this,"Password too short",Toast.LENGTH_SHORT).show();
                else
                {
                    registerUser(txt_email,txt_password);
                }
            }
        });
    }

    private void registerUser(String txt_email, String txt_password) {
        auth.createUserWithEmailAndPassword(txt_email,txt_password).addOnCompleteListener(RegisterActivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
           if(task.isSuccessful()) Toast.makeText(RegisterActivity.this,"Registered",Toast.LENGTH_SHORT).show();
            else Toast.makeText(RegisterActivity.this,"Registration failed",Toast.LENGTH_SHORT).show();
            }
        });
    }
}