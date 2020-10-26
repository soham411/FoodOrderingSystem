package com.example.foodorderingsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Objects;

public class Reviews extends AppCompatActivity {
    ArrayList<String> names = new ArrayList<>();
    ArrayList<String> ratings = new ArrayList<>();
    ArrayList<String> descs = new ArrayList<>();
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        auth = FirebaseAuth.getInstance();
        ListView revlist = findViewById(R.id.revratelist);
         final MyAdapterReview adapterR = new MyAdapterReview(this,names,ratings,descs);
         revlist.setAdapter(adapterR);
//        DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Reviews");
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Users").child("Manager").child(auth.getCurrentUser().getUid());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    if(Objects.equals(dataSnapshot.getKey(), "Restaurant"))
                    {
                        String restaurantname = dataSnapshot.getValue().toString();
                        final DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference().child("Reviews").child(restaurantname);
                        ref1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for(DataSnapshot dataSnapshot1 : snapshot.getChildren())
                                {
                                    edit_review(dataSnapshot1);
//                                    descs.add("Very Good");
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
    }

    private void edit_review(DataSnapshot dataSnapshot1) {
        names.add(dataSnapshot1.getKey());
        ratings.add(dataSnapshot1.child("rating").getValue().toString());
        descs.add(dataSnapshot1.child("review").getValue().toString());
    }

    public  class MyAdapterReview extends ArrayAdapter<String>
    {
        Context context;
        ArrayList<String> names;
        ArrayList<String> ratings;
        ArrayList<String> descs;
        MyAdapterReview(Context c, ArrayList<String> names, ArrayList<String> ratings, ArrayList<String> descs)
        {
            super(c,R.layout.rev_list_item,R.id.revname,names);
            this.context = c;
            this.names = names;
            this.ratings = ratings;
            this.descs = descs;

        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View r = layoutInflater.inflate(R.layout.rev_list_item,parent,false);

            TextView name = r.findViewById(R.id.revname);
            TextView rating = r.findViewById(R.id.revstar);
            TextView desc = r.findViewById(R.id.revdesc);
            name.setText(names.get(position));
            rating.setText(ratings.get(position));
            desc.setText(descs.get(position));
            return r;
        }
    }
}
