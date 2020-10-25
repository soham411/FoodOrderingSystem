package com.example.foodorderingsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class OrderDetails extends AppCompatActivity {
    public static String uid;
    public static String restantname;
    public static String delivname;
    ListView listView;
    TextView name;
    TextView address;
    TextView phoneno;
    TextView delvboy;
    Button selectDelv;
    Button dispatch;

    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        auth = FirebaseAuth.getInstance();
        listView = findViewById(R.id.itemlist);
        delvboy = findViewById(R.id.delivname);
        name = findViewById(R.id.namedelv);
        phoneno = findViewById(R.id.phonedelv);
        address = findViewById(R.id.addressdelv);
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
        DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference().child("Users").child("Customer").child(uid);
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    switch (dataSnapshot.getKey())
                    {
                        case "username" :
                            name.setText(dataSnapshot.getValue().toString());
                            break;
                        case "address" :
                            address.setText(dataSnapshot.getValue().toString());
                            break;
                        case "phoneno":
                            phoneno.setText(dataSnapshot.getValue().toString());
                            break;
                        default:break;

                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        selectDelv = findViewById(R.id.selectdelv);
        dispatch = findViewById(R.id.dispatch);
        DatabaseReference ref3 = FirebaseDatabase.getInstance().getReference().child("DeliveryPersonnel");
        ref3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    delvboy.setText(dataSnapshot.getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        dispatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference ref4 = FirebaseDatabase.getInstance().getReference().child("Users").child("Manager").child(auth.getCurrentUser().getUid());
                ref4.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for(DataSnapshot dataSnapshot : snapshot.getChildren())
                        {
                            if(Objects.equals(dataSnapshot.getKey(), "Restaurant"))
                            {
                                String restantname = dataSnapshot.getValue().toString();
                                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Orders").child(restantname).child(uid);
                                HashMap<String,Object> reg_user = new HashMap<String,Object>();
//                                reg_user.put(newfoodname.getText().toString(),newfoodprice.getText().toString());
                                ref1.updateChildren(reg_user);


                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });
//        selectDelv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//               delvboy.setText(delivname);
//            }
//        });

    }

    public void setname(String s)
    {
        delvboy.setText(s);

    }


    private String openDelvDialog() {
        DeliveryDialog deliveryDialog = new DeliveryDialog();
        deliveryDialog.show(getSupportFragmentManager(),"Add Food Item");
        return deliveryDialog.getDelivname();
    }
}