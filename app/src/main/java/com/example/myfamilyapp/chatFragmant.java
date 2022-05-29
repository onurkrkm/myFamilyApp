package com.example.myfamilyapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myfamilyapp.databinding.FragmentChatFragmantBinding;
import com.example.myfamilyapp.databinding.FragmentLoginFramentBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.protobuf.Any;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class chatFragmant extends Fragment {

    private FragmentChatFragmantBinding binding;
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private ChatReAdapter chatAdapter;
    private ArrayList<Chat> chatts;
    myGrubs myGrubs;
    private RecyclerView.LayoutManager LinearLayoutManager;
    String userName;
    private ArrayList<String> member;

    public chatFragmant() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firestore = FirebaseFirestore.getInstance();
        mAuth= FirebaseAuth.getInstance();





    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentChatFragmantBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager = new LinearLayoutManager(getContext());

        chatAdapter= new ChatReAdapter();
        binding.chatView2.setAdapter(chatAdapter);
        binding.chatView2.setLayoutManager(LinearLayoutManager);
        chatts=new ArrayList<Chat>();
        myGrubs=new myGrubs();
        member=new ArrayList<String>();



        firestore.collection("users").addSnapshotListener((value, error) -> {


            if (error != null){
                Toast.makeText(requireContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }else{
                if(value !=null){
                    if(value.isEmpty()){
                        Toast.makeText(requireContext(), "Kullanıcı Yok", Toast.LENGTH_SHORT).show();
                    }else{
                        List<DocumentSnapshot> documents = value.getDocuments();

                        for(DocumentSnapshot document : documents){
                            if(mAuth.getCurrentUser().getEmail().toString().equals(document.get("userEmail").toString())) {
                                userName = document.get("userName").toString() + " " + document.get("userLastName").toString();

                            }
                        }

                    }

                }
            }


        });






        binding.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String chatText = binding.message.getText().toString();
                String user = mAuth.getCurrentUser().getEmail().toString();
                String userReal=userName;
                System.out.println(userReal);
                FieldValue date = FieldValue.serverTimestamp();

                Map<String,Object> dataMap = new HashMap<>();
                dataMap.put("user",user);
                dataMap.put("userName",userReal);
                dataMap.put("text",chatText);
                dataMap.put("date",date);

                firestore.collection(myGrubs.chats.toString()).add(dataMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        binding.message.setText("");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        binding.message.setText("");
                    }
                });



            }
        });


        firestore.collection(myGrubs.chats.toString()).orderBy("date", Query.Direction.ASCENDING).addSnapshotListener((value, error) -> {
            if (error != null){
                Toast.makeText(requireContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }else{
                if(value !=null){
                    if(value.isEmpty()){
                        Toast.makeText(requireContext(), "Mesaj Yok", Toast.LENGTH_SHORT).show();
                    }else{
                        List<DocumentSnapshot> documents = value.getDocuments();
                        chatts.clear();
                        String user;

                        for(DocumentSnapshot document : documents){
                            String text=document.get("text").toString();
                            String email=document.get("user").toString();
                            if(document.get("userName")!=null){
                                user=document.get("userName").toString();
                            }else{
                                user="";
                            }

                            Chat chat= new Chat(user,text,email);

                            chatts.add(chat);

                            chatAdapter.dataSource =chatts;
                            /*if(document.get("user").toString().equals(mAuth.getCurrentUser().getEmail().toString())){
                                user= userName;
                                Chat chat= new Chat(user,text);

                                chatts.add(chat);
                                chatAdapter.dataSource =chatts;
                            }else{
                                user=document.get("user").toString();

                            }*/


                        }

                    }

                    chatAdapter.notifyDataSetChanged();
                    binding.chatView2.smoothScrollToPosition(chatts.size()+1);
                }
            }
        });





    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}