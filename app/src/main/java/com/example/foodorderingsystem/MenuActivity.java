package com.example.foodorderingsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelStore;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity  {

    private static String restaurant_name ;
    public String rupeesymbol = "\u20B9";
    private ListView listView;
    public ListView cartlist;
    private TextView text;
    ArrayList<String> fooditem = new ArrayList<>();
    ArrayList<String> foodprice = new ArrayList<>();
    ArrayList<String> foodquantity = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);


        listView = findViewById(R.id.menu);
        final ArrayList<String> fooditem = new ArrayList<>();
        final ArrayList<String> price = new ArrayList<>();
//        final ArrayList<String> quantity = new ArrayList<>();
//        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.menu_list_item,R.id.foodinfo,fooditem);

        final MyAdapter adapter = new MyAdapter(this,fooditem,price);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String selectedRestaurant = fooditem.get(position);
//              startActivity(new Intent(MenuActivity.this,MenuActivity.class));
            }
        });
        text = findViewById(R.id.food_item);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("Menu").child(restaurant_name);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fooditem.clear();
                price.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    fooditem.add(dataSnapshot.getKey().toString());
                    price.add("Rs."+dataSnapshot.getValue().toString());
//                    quantity.add("1");
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    @Override
//    public void getItemDetails(String foodname, String foodprice, String quantity) {
//        CartFragment cartFragment = new CartFragment();
//        cartFragment.getList(foodname,foodprice,quantity);
//    }


    class MyAdapter extends ArrayAdapter<String>
    {
        Context context;
        ArrayList<String> rtitle;
        ArrayList<String> rsubtitle;
        ArrayList<String> itemname= new ArrayList<>();;
        ArrayList<String> price = new ArrayList<>();;
        ArrayList<String> quantity = new ArrayList<>();
        MyAdapter(Context c,ArrayList<String> title,ArrayList<String> subtitle)
        {
            super(c,R.layout.menu_list_item,R.id.food_item,title);
            this.context = c;
            this.rtitle = title;
            this.rsubtitle = subtitle;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.menu_list_item,parent,false);
            final TextView mytitle = row.findViewById(R.id.food_item);
            final TextView mysubtitle = row.findViewById(R.id.item_price);
            final Button addtocart = row.findViewById(R.id.addToCart);
            
            addtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CartFragment.fname.add(rtitle.get(position) );
                    CartFragment.fprice.add(rsubtitle.get(position).substring(3));
                    CartFragment.frestaurant.add(restaurant_name);
                    CartFragment.fquantity.add("1");
                }
            });
            mytitle.setText(rtitle.get(position));
            mysubtitle.setText(rsubtitle.get(position));
            return row;
        }
    }

    public static void getRestaurant(String restaurant)
    {
        restaurant_name = restaurant;
    }
}