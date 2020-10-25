package com.example.foodorderingsystem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class CartFragment extends Fragment {
    private String rupeesymbol = "\u20B9";
    private ListView cartlist;
    private TextView totamount;
    private TextView totalnoOfitem;
    private String fooditemname;
    private String fooditemprice= rupeesymbol+" 0";
    private String foodquantity;
    public static MyAdapterCart adapterCart;
    public  static ArrayList<String> fname = new ArrayList<>();
    public  static ArrayList<String> fprice = new ArrayList<>();
    public  static ArrayList<String> fquantity = new ArrayList<>();
    public  static ArrayList<String> frestaurant = new ArrayList<>();
    public static ArrayList<String> forPayment= new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_cart,container,false);

        cartlist = v.findViewById(R.id.cartlist);
        totamount = v.findViewById(R.id.totamount);
        totalnoOfitem = v.findViewById(R.id.noOfItems);
//        if(fname.isEmpty())
//        {
//
//            return v;
//        }
        int sum=0;
        for (int i = 0; i < fprice.size(); i++) {
            sum += Integer.parseInt(fprice.get(i));
            forPayment.add(fname.get(i)+" - "+frestaurant.get(i)+" - "+fquantity.get(i)+" - "+fprice.get(i));
        }
        PaymentActivity.totalquantity = fname.size();
        totalnoOfitem.setText(Integer.toString(fname.size()));
        String s =rupeesymbol+Integer.toString(sum);
        totamount.setText(s);
        adapterCart = new MyAdapterCart(getActivity(),fname,fprice,fquantity,frestaurant);
        cartlist.setAdapter(adapterCart);
        adapterCart.notifyDataSetChanged();
//        PaymentActivity.adapterPay.notifyDataSetChanged();
        Button toPay = v.findViewById(R.id.toPay);
        toPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fname.isEmpty())
                {
                    Toast.makeText(getActivity(), "Add food to cart", Toast.LENGTH_SHORT).show();
                    return;
                }
                PaymentActivity.finalorderlist= forPayment;
                PaymentActivity.total_amt=Integer.parseInt(totamount.getText().toString().substring(1));

                startActivity(new Intent(getActivity(),PaymentActivity.class));

            }
        });
        return v;
    }


    public  class MyAdapterCart extends ArrayAdapter<String>
    {
        Context context;
        ArrayList<String> rfood_item;
        ArrayList<String> rprice;
        ArrayList<String> rquantity;
        ArrayList<String> rrestaurant;
       MyAdapterCart(Context c, ArrayList<String> fooditemname, ArrayList<String> foodprice, ArrayList<String> quantity,ArrayList<String> restaurant)
        {
            super(c,R.layout.cart_list_item,R.id.fooditemname,fooditemname);
            this.context = c;
            this.rfood_item = fooditemname;
            this.rprice = foodprice;
            this.rquantity = quantity;
            this.rrestaurant = restaurant;
        }

        @NonNull
        @Override
        public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final View r = layoutInflater.inflate(R.layout.cart_list_item,parent,false);

           Button decreaseQuantity = r.findViewById(R.id.decreaseInCart);
           Button increaseQuantity = r.findViewById(R.id.increaseInCart);
           TextView fooditemname = r.findViewById(R.id.fooditemname);
           TextView restaurantname = r.findViewById(R.id.restaurantname);
           final TextView amount = r.findViewById(R.id.amount);
           final TextView quantity = r.findViewById(R.id.quantity);

            fooditemname.setText(rfood_item.get(position));
            amount.setText(rprice.get(position));
            quantity.setText(rquantity.get(position));
            restaurantname.setText(rrestaurant.get(position));

            increaseQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rquantity.set(position,Integer.toString(Integer.parseInt(rquantity.get(position))+1));
                    fquantity.set(position,rquantity.get(position));
                    totalnoOfitem.setText(Integer.toString(Integer.parseInt(totalnoOfitem.getText().toString())+1));
                    quantity.setText(rquantity.get(position));
                    totamount.setText(rupeesymbol +Integer.toString(Integer.parseInt(totamount.getText().toString().substring(1)) + Integer.parseInt(rprice.get(position))));
                    forPayment.set(position,fname.get(position)+" - "+frestaurant.get(position)+" - "+fquantity.get(position)+" - "+fprice.get(position));

                }
            });

            decreaseQuantity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    rquantity.set(position,Integer.toString(Integer.parseInt(rquantity.get(position))-1));
                    fquantity.set(position,rquantity.get(position));
                    totalnoOfitem.setText(Integer.toString(Integer.parseInt(totalnoOfitem.getText().toString())-1));


                    quantity.setText(rquantity.get(position));
                    totamount.setText(rupeesymbol +Integer.toString(Integer.parseInt(totamount.getText().toString().substring(1)) - Integer.parseInt(rprice.get(position))));
                    if(Integer.parseInt(rquantity.get(position) ) == 0 )
                    {
                        rfood_item.remove(position);
                        rprice.remove(position);
                        rquantity.remove(position);
                        rrestaurant.remove(position);
                        forPayment.remove(position);
                        adapterCart.notifyDataSetChanged();
                        if(rfood_item.isEmpty()) startActivity(new Intent(getActivity(),HomeActivity.class));
                    }

                    forPayment.set(position,fname.get(position)+" - "+frestaurant.get(position)+" - "+fquantity.get(position)+" - "+fprice.get(position));


                }
            });



            return r;
        }
    }
}
