package com.example.myfamilyapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myfamilyapp.databinding.FragmentGrubsCreatBinding;
import com.example.myfamilyapp.databinding.FragmentUserDataBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class grubsCreat extends Fragment {

    private FragmentGrubsCreatBinding binding;
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private ArrayList<String> myGrubsname;
    myGrubs myGrubs;




    public grubsCreat() {
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
        binding = FragmentGrubsCreatBinding.inflate(inflater,container,false);
        View view = binding.getRoot();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       binding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String grubsName =binding.grubsNamedt.getText().toString();
                String user=mAuth.getCurrentUser().getEmail().toString();

                Map<String,Object> dataMap = new HashMap<>();
                dataMap.put("user",user);


                firestore.collection(grubsName).document(mAuth.getCurrentUser().getEmail().toString()).set(dataMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {



                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(),"Grub Oluşturulamadı --->"+e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                Map<String,Object> grubMap = new HashMap<>();

                grubMap.put("member",mAuth.getCurrentUser().getEmail().toString());
                grubMap.put("grubsnName",grubsName);

                firestore.collection("grubs").add(grubMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(requireContext(), "Grup Oluşturuldu Lütfen Geriye Gidiniz", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });


            }
        });

        binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String grubsName =binding.grubsNamedt.getText().toString();
                String user=mAuth.getCurrentUser().getEmail().toString();

                Map<String,Object> dataMap = new HashMap<>();
                dataMap.put("user",user);


                firestore.collection(grubsName).document(mAuth.getCurrentUser().getEmail().toString()).set(dataMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {



                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(),"Grub Oluşturulamadı --->"+e.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                Map<String,Object> grubMap = new HashMap<>();

                grubMap.put("member",mAuth.getCurrentUser().getEmail().toString());
                grubMap.put("grubsnName",grubsName);

                firestore.collection("grubs").add(grubMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Toast.makeText(requireContext(), "Grup'a Katılım Sağlandı... Lütfen Geriye Gidiniz", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }
        });



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding =null;
    }
}