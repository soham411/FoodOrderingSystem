package com.example.foodorderingsystem;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DeliveryDialog extends AppCompatDialogFragment {
    ListView delvlist;
    int pos=0;
    public String getDelivname() {
        return delivname;
    }

    String delivname;
    private FirebaseAuth auth;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder =new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View v =inflater.inflate(R.layout.delv_item_list,null);
        delvlist = v.findViewById(R.id.delvlist);

        final ArrayList<String> dlist = new ArrayList<>();
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),R.layout.list_item,dlist);
        delvlist.setAdapter(adapter);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("DeliveryPersonnel");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    dlist.add(dataSnapshot.getValue().toString());
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        delvlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                delivname = dlist.get(position);
//                new OrderDetails().setname(dlist.get(position));
            }
        });
        builder.setView(v).setTitle("Select Delivery Personnel").setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setPositiveButton("CONFIRM", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                OrderDetails.delivname = delivname;
               if(delivname.equals("Vinod"))
                   Toast.makeText(getContext(), "working", Toast.LENGTH_SHORT).show();

            }
        });
        return builder.create();
    }
}
