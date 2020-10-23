package com.example.foodorderingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText address;
    private EditText phoneno;
    private EditText email;
    private EditText password;
    private EditText restaurantname;
    private Button register;
    private RadioGroup radioGroup;
    private RadioButton user_type_customer;
    private  RadioButton user_type_manager;
    private RadioButton user_type;

    private FirebaseAuth auth;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username);
        email = findViewById(R.id.email_reg);
        password = findViewById(R.id.password_reg);
        register = findViewById(R.id.register);
        radioGroup = findViewById(R.id.radioGroup);
        phoneno = findViewById(R.id.phoneno);
        address = findViewById(R.id.address_reg);
        restaurantname = findViewById(R.id.restaurantname);
        auth = FirebaseAuth.getInstance();
        user_type_customer = findViewById(R.id.radio_customer);
        user_type_manager = findViewById(R.id.radio_manager);


        user_type_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantname.setVisibility(View.INVISIBLE);
                restaurantname.setText("");
            }
        });

        user_type_manager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restaurantname.setVisibility(View.VISIBLE);
            }
        });



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_username = username.getText().toString();
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                String txt_phoneno = phoneno.getText().toString();
                String txt_address = address.getText().toString();
                String txt_restaurant = restaurantname.getText().toString();
                int usertype = radioGroup.getCheckedRadioButtonId();
                user_type = findViewById(usertype);
                String txt_user_type = user_type.getText().toString();
                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password) || TextUtils.isEmpty((txt_phoneno))|| TextUtils.isEmpty(txt_address))
                    Toast.makeText(RegisterActivity.this,"Empty Credentials",Toast.LENGTH_SHORT).show();
                else if(txt_password.length() < 6)
                    Toast.makeText(RegisterActivity.this,"Password too short",Toast.LENGTH_SHORT).show();
                else if(txt_phoneno.length() < 10)
                    Toast.makeText(RegisterActivity.this,"",Toast.LENGTH_SHORT).show();
                else
                {

                    registerUser(txt_email,txt_password,txt_phoneno,txt_username,txt_address,txt_user_type,txt_restaurant);
                }
            }
        });
    }

    private void registerUser(final String txt_email, final String txt_password, final String txt_phoneno,final String txt_username, final String txt_address, final String txt_user_type,final String txt_restaurant) {
        auth.createUserWithEmailAndPassword(txt_email,txt_password).addOnCompleteListener(RegisterActivity.this,new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
           if(task.isSuccessful())
           {
               HashMap<String,Object> reg_user = new HashMap<String,Object>();
               if(txt_user_type.equals("Manager")) reg_user.put("Restaurant",txt_restaurant);
               reg_user.put("username",txt_username);
               reg_user.put("email",txt_email);
               reg_user.put("password",txt_password);
               reg_user.put("phoneno",txt_phoneno);
               reg_user.put("address",txt_address);

               DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child(txt_user_type).child(auth.getCurrentUser().getUid());
               ref.updateChildren(reg_user);
               DatabaseReference ref1  = FirebaseDatabase.getInstance().getReference().child("Usertype").child(auth.getCurrentUser().getUid());
               ref1.setValue(txt_user_type);
               Toast.makeText(RegisterActivity.this,"Registered as "+txt_user_type,Toast.LENGTH_SHORT).show();

           }
            else Toast.makeText(RegisterActivity.this,"Registration failed :" + task.getException().getMessage() ,Toast.LENGTH_SHORT).show();

            }
        });
    }
}