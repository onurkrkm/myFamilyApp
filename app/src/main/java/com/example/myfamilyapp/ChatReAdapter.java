package com.example.myfamilyapp;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class ChatReAdapter extends RecyclerView.Adapter<ChatReAdapter.ChatHolder> {

    List<Chat> dataSource = new ArrayList<Chat>();
    int VIEW_SENDİNG=1;
    int VIEW_RECEIVED=2;

    DiffUtil.ItemCallback<Chat> diffUtil= new DiffUtil.ItemCallback<Chat>() {
       @Override
       public boolean areItemsTheSame(@NonNull Chat oldItem, @NonNull Chat newItem) {
           return oldItem==newItem;
       }

       @Override
       public boolean areContentsTheSame(@NonNull Chat oldItem, @NonNull Chat newItem) {
           return oldItem.text.toString().equals(newItem.text.toString());
       }
    };

    AsyncListDiffer reDiffer =new AsyncListDiffer(this,diffUtil);

    public List<Chat> getDataSource() {
        return reDiffer.getCurrentList();

    }

    public void setDataSource(List<Chat> dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public int getItemViewType(int position) {
        Chat chat = dataSource.get(position);

        if (chat.email.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail().toString())){
            return VIEW_SENDİNG;
        }else{
            return VIEW_RECEIVED;
        }



    }

    @NonNull
    @Override
    public ChatHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==VIEW_RECEIVED){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_rows,parent,false);

            return new ChatHolder(view);
        }else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_rows2,parent,false);

            return new ChatHolder(view);
        }



    }

    @Override
    public void onBindViewHolder(@NonNull ChatHolder holder, int i) {

        TextView textView= holder.itemView.findViewById(R.id.chatTextView);

        if(dataSource.get(i).email.equals(FirebaseAuth.getInstance().getCurrentUser().getEmail())){
            textView.setText(dataSource.get(i).text.toString());
        }else{
            textView.setText(dataSource.get(i).user.toString()+"\n"+dataSource.get(i).text.toString());
        }


    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class ChatHolder extends RecyclerView.ViewHolder{

        public ChatHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
