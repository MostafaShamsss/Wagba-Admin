package com.example.restaurantconfirmation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>
{
    private List<OrdersModel> usersList;
    private final UsersInterface usersInterface;

    public UsersAdapter(List<OrdersModel>usersList, UsersInterface usersInterface)
    {
        this.usersList = usersList;
        this.usersInterface = usersInterface;
    }

    @NonNull
    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_users, parent, false);
        return new UsersAdapter.ViewHolder(v);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView textView1, textView2, textView3, textView4;

        public ViewHolder(@NonNull View itemView)
        {
            super(itemView);
            textView1 = itemView.findViewById(R.id.textView2);
            textView2 = itemView.findViewById(R.id.textView4);
            textView3 = itemView.findViewById(R.id.textView6);
            textView4 = itemView.findViewById(R.id.textView8);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(usersInterface != null)
                    {
                        int pos = getAbsoluteAdapterPosition();

                        if(pos != RecyclerView.NO_POSITION)
                        {
                            usersInterface.onItemClick(pos);
                        }
                    }
                }
            });
        }

        public void setData(String text1, String text2, String text3, String text4)
        {
            textView1.setText(text1);
            textView2.setText(text2);
            textView3.setText(text3);
            textView4.setText(text4);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.ViewHolder holder, int position)
    {
        String orderDate = usersList.get(position).getOrderDate();
        String orderGate = usersList.get(position).getOrderGate();
        String totalPrice = usersList.get(position).getTotalPrice();
        String status = usersList.get(position).getStatus();

        holder.setData(orderGate, orderDate, totalPrice, status);
    }




    @Override
    public int getItemCount() {return usersList.size();}
}
