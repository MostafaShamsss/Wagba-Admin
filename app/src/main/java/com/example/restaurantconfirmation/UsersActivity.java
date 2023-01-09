package com.example.restaurantconfirmation;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.restaurantconfirmation.databinding.ActivityUsersBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersActivity extends AppCompatActivity implements UsersInterface
{

    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    UsersAdapter usersAdapter;
    ActivityUsersBinding binding;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    ArrayList<OrdersModel> ordersModels;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityUsersBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        ordersModels = new ArrayList<>();

        getOrderInfo();
    }



    private void getOrderInfo()
    {
        databaseReference.child("users").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                //to prevent adding the item again to the recycler view if its status changed
                ordersModels.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    final String userID = postSnapshot.getKey();

                    for(DataSnapshot childSnapshot : postSnapshot.child("orders").getChildren())
                    {
                        final String orderID = childSnapshot.getKey();

                        ordersModels.add(new OrdersModel(childSnapshot.child("orderGate").getValue().toString(),
                                childSnapshot.child("orderDate").getValue().toString(),
                                childSnapshot.child("totalPrice").getValue().toString(),
                                childSnapshot.child("status").getValue().toString(), orderID, userID));
                    }
                }
                initRecyclerView();
            }

            @Override
            public void onCancelled(DatabaseError error) { Log.w("RT DB", "Failed to read value.", error.toException());}
        });
    }


    private void initRecyclerView()
    {
        recyclerView = findViewById(R.id.recyclerViewUsers);
        linearLayoutManager = new LinearLayoutManager(UsersActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        usersAdapter = new UsersAdapter(ordersModels, UsersActivity.this);
        recyclerView.setAdapter(usersAdapter);
    }


    @Override
    public void onItemClick(int position)
    {
        Intent intent = new Intent(UsersActivity.this, OrderStatusActivity.class);

        intent.putExtra("userID", ordersModels.get(position).getUserID());
        intent.putExtra("orderID", ordersModels.get(position).getOrderID());
        intent.putExtra("orderGate", ordersModels.get(position).getOrderGate());
        intent.putExtra("orderDate", ordersModels.get(position).getOrderDate());
        intent.putExtra("totalPrice", ordersModels.get(position).getTotalPrice());
        intent.putExtra("status", ordersModels.get(position).getStatus());

        startActivity(intent);
    }
}