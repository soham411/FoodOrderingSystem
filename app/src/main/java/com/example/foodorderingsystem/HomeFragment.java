package com.example.foodorderingsystem;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment  {
    private TextView textView;
    private  FragmentHomeListener homeListener;

    public  interface FragmentHomeListener
    {
        void changeActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home,container,false);
        textView = v.findViewById(R.id.selectRestaurant);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               homeListener.changeActivity();

            }
        });

        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof  FragmentHomeListener){
            homeListener = (FragmentHomeListener)context;
        }
        else
        {
            throw new RuntimeException(context.toString()+" must implement FragmentHomeListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        homeListener =null;
    }
}
