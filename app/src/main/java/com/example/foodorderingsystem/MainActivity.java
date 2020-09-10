package com.example.foodorderingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private Button register;
    private Button login;
    private EditText email;
    private EditText password;

    private FirebaseAuth auth;

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
                String txt_email_login = login.getText().toString();
                String txt_password_login = password.getText().toString();
                loginUser(txt_email_login,txt_password_login);
                  }
        });
    }

    public void loginUser(String txt_email_login, String txt_password_login) {
//    private FirebaseAuth auth = FirebaseAuth.getInstance();
//        auth.signInWithEmailAndPassword(txt_email_login,txt_password_login).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
//            @Override
//            public void onSuccess(AuthResult authResult) {
//                Log.d("LOGGEDIN","log in successfull");
//                Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(MainActivity.this,HomeActivity.class));
//                finish();
//            }
//        });
        auth.signInWithEmailAndPassword(txt_email_login,txt_password_login).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                    Toast.makeText(MainActivity.this, "Logged in", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Failed log in", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
