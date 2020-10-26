package com.example.foodorderingsystem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

public class AddFoodDialog extends AppCompatDialogFragment {

    private EditText newfoodname;
    private EditText newfoodprice;
    private static String restaurantname ="";
    private FirebaseAuth auth;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v =inflater.inflate(R.layout.add_food_dialog,null);
        newfoodname = v.findViewById(R.id.newfoodname);
        newfoodprice = v.findViewById(R.id.price);
        builder.setView(v).setTitle("Add Food Item").setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("ADD", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
                                Order.restaurantname = restaurantname;
                                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Menu").child(restaurantname);
                                HashMap<String,Object> reg_user = new HashMap<String,Object>();
                                reg_user.put(newfoodname.getText().toString(),newfoodprice.getText().toString());
                                ref1.updateChildren(reg_user);
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

        return builder.create();
    }


}
