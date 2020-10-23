package com.example.foodorderingsystem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChangeAddressDialog extends AppCompatDialogFragment {
    private EditText newaddress;
    private Button apply;
   private FirebaseAuth auth;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v =inflater.inflate(R.layout.chg_addr_dialog,null);
        newaddress = v.findViewById(R.id.newaddress);
//        apply = v.findViewById(R.id.apply_newaddress);
        builder.setView(v).setTitle("Change Address").setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("APPLY", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                auth = FirebaseAuth.getInstance();
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Customer").child(auth.getCurrentUser().getUid());
                ref.child("address").setValue(newaddress.getText().toString());
            }
        });
        return  builder.create();
    }
}
