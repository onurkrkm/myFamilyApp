package com.example.myfamilyapp;



import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class GrubsAdapter extends RecyclerView.Adapter<GrubsAdapter.GrubsHolder> {

    List<Grubs> dataSource = new ArrayList<Grubs>();
    myGrubs chatName;


    @NonNull
    @Override
    public GrubsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grubs_maps,parent,false);

        return new GrubsHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull GrubsHolder holder, int position) {
        TextView textView = holder.itemView.findViewById(R.id.grubsName);
        ImageView imageView = holder.itemView.findViewById(R.id.imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatName = new myGrubs();
                chatName.chats=dataSource.get(position).grubsName.toString();
                NavDirections action = new ActionOnlyNavDirections(R.id.action_userDashBoard_to_mapsFragment);
                Navigation.findNavController(holder.itemView).navigate(action);
            }
        });

        textView.setText(dataSource.get(position).grubsName.toString());
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chatName = new myGrubs();
                chatName.chats=dataSource.get(position).grubsName.toString();
                NavDirections action = new ActionOnlyNavDirections(R.id.action_userDashBoard_to_chatFragmant);
                Navigation.findNavController(holder.itemView).navigate(action);
            }
        });
        /*holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = new ActionOnlyNavDirections(R.id.action_userDashBoard_to_chatFragmant);
                Navigation.findNavController(holder.itemView).navigate(action);
            }
        });*/
    }

    @Override
    public int getItemViewType(int position) {

        Grubs grubs = dataSource.get(position);
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class GrubsHolder extends RecyclerView.ViewHolder{
        public GrubsHolder(@NonNull View itemView) {
            super(itemView);


        }
    }





}

