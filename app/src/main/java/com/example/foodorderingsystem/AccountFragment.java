package com.example.foodorderingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AccountFragment extends Fragment  {
    private FirebaseAuth auth;
    public static String newaddress ;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_account,container,false);
        final TextView curraddress = v.findViewById(R.id.curraddress);
        TextView change_address = v.findViewById(R.id.chgaddress);
        TextView signout = v.findViewById(R.id.signout);
        auth = FirebaseAuth.getInstance();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Customer").child(auth.getCurrentUser().getUid());

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    curraddress.setText(dataSnapshot.getValue().toString());
                    newaddress = curraddress.getText().toString();
                    break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        change_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            openDialog();
            }
        });

        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth = FirebaseAuth.getInstance();
                auth.signOut();
                startActivity(new Intent(getActivity(),MainActivity.class));
            }
        });

        return v;
    }
     public void openDialog(){
        ChangeAddressDialog changeAddressDialog = new ChangeAddressDialog();
        changeAddressDialog.show(getFragmentManager(),"Change Address");

     }
}
