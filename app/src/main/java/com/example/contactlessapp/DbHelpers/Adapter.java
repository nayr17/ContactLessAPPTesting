package com.example.contactlessapp.DbHelpers;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.contactlessapp.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter extends FirebaseRecyclerAdapter<Model, Adapter.show_data>
{


    public Adapter(@NonNull FirebaseRecyclerOptions<Model> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull show_data holder, int position, @NonNull Model model)
    {
        holder.name.setText(model.getName());
        holder.address.setText(model.getAddress());
        holder.phoneNumber.setText(model.getPhoneNumber());
        holder.date.setText(model.getDate());
        holder.time.setText(model.getTime());
        Glide.with(holder.imageView.getContext()).load(model.getUrl()).into(holder.imageView);

    }

    @NonNull
    @Override
    public show_data onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout, parent, false);
        return new show_data(view);
    }

    class show_data extends RecyclerView.ViewHolder
    {

        CircleImageView imageView;
        TextView name, address, phoneNumber, date, time;

        public show_data(@NonNull View itemView)
        {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageViewCustomerPic);
            name = itemView.findViewById(R.id.textViewGetCustomerName);
            address = itemView.findViewById(R.id.textViewGetCustomerAddress);
            phoneNumber = itemView.findViewById(R.id.textViewGetCustomerPhoneNumber);
            date = itemView.findViewById(R.id.textViewDate);
            time = itemView.findViewById(R.id.textViewTime);
        }
    }
}
