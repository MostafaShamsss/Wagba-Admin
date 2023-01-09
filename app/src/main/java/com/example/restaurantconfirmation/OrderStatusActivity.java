package com.example.restaurantconfirmation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.restaurantconfirmation.databinding.ActivityOrderstatusBinding;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OrderStatusActivity extends AppCompatActivity
{
    ActivityOrderstatusBinding binding;
    int hour,min,myHour,myMin;
    String myTime;
    Date myDate;
    String DeliveryTime;
    boolean confirm = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityOrderstatusBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        String orderID = getIntent().getStringExtra("orderID");
        String userID = getIntent().getStringExtra("userID");
        String orderGate = getIntent().getStringExtra("orderGate");
        String orderDate = getIntent().getStringExtra("orderDate");
        String totalPrice = getIntent().getStringExtra("totalPrice");
        String status = getIntent().getStringExtra("status");

        binding.textViewIn2.setText(orderGate);
        binding.textViewIn4.setText(orderDate);
        binding.textViewIn6.setText(totalPrice);
        binding.textViewIn8.setText(status);

        hour = Integer.parseInt(orderDate.substring(0, 2));
        min = Integer.parseInt(orderDate.substring(orderDate.length() - 2));

        myDate = new Date();
        myTime = new SimpleDateFormat("HH:mm").format(myDate);
        myHour = Integer.parseInt(myTime.substring(0, 2));
        myMin = Integer.parseInt(myTime.substring(orderDate.length() - 2));

        compareTime();
        binding.DeliveryTime.setText(DeliveryTime);
        Log.d("cmp", "delivery time: "+hour);

        //to prevent the user from changing the delivered status by fault
        if(!status.matches("Delivered") && comparingMyTime())
        {
            binding.deliveredBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("orders").child(orderID).child("status").setValue("Delivered");
                    binding.textViewIn8.setText("Delivered");
                }
            });

            binding.deliveringBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("orders").child(orderID).child("status").setValue("Delivering");
                    binding.textViewIn8.setText("Delivering");
                }
            });

            binding.preparingBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("orders").child(orderID).child("status").setValue("Preparing");
                    binding.textViewIn8.setText("Preparing");
                }
            });
        }
    }

    //returns Delivery time
    private void compareTime()
    {
        if(hour >= 10  &&  hour < 13)
        {
            DeliveryTime = "3:00 pm";
        }
        else if(hour < 10  &&  hour>6)
        {
            DeliveryTime = "12:00 pm";
        }
    }

    private boolean comparingMyTime()
    {
        if(myHour >= 10 &&  myHour < 13)
        {
            if(myMin>=30)
            {
                confirm = true;
            }
        }
        else if(myHour >= 10 &&  myHour >= 13)
        {
            if(myMin<30)
            {
                confirm = true;
            }
        }
        else if(myHour < 10)
        {
            confirm = true;
        }
        return confirm;
    }
}