package com.example.myfamilyapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myfamilyapp.databinding.FragmentUserDataBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class userData extends Fragment {

    private FragmentUserDataBinding binding;
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private  NavController navController;


    public userData() {
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
        // Inflate the layout for this fragment
        binding = FragmentUserDataBinding.inflate(inflater,container,false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View v, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(v, savedInstanceState);






        binding.saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String user = mAuth.getCurrentUser().getEmail().toString();
                String userName=binding.firtsNameEdt.getText().toString();
                String userLastName=binding.lastNameEdt.getText().toString();
                String telNum=binding.telEdit.getText().toString();

                Map<String,Object> dataMap = new HashMap<>();
                dataMap.put("userEmail",user);
                dataMap.put("userName",userName);
                dataMap.put("userLastName",userLastName);
                dataMap.put("phoneNum",telNum);





                firestore.collection("users").document(mAuth.getCurrentUser().getEmail().toString()).set(dataMap, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(requireContext(), "Başarılı Kayıt", Toast.LENGTH_LONG).show();

                        NavDirections action=userDataDirections.actionUserDataToUserDashBoard();


                        navController = Navigation.findNavController(view);
                        navController.navigate(action);
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