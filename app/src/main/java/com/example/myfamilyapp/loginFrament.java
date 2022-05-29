package com.example.myfamilyapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.myfamilyapp.databinding.FragmentLoginFramentBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

import static android.content.ContentValues.TAG;


public class loginFrament extends Fragment {

    private FragmentLoginFramentBinding binding;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    myGrubs myGrubs;
    View view;

    public loginFrament() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginFramentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        firestore.setFirestoreSettings(settings);






        if (mAuth.getCurrentUser() != null){
            NavDirections action = new ActionOnlyNavDirections(R.id.action_loginFrament_to_userDashBoard);
            Navigation.findNavController(view).navigate(action);
        }


        binding.singIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                mAuth.createUserWithEmailAndPassword(binding.emailText.getText().toString(),binding.password.getText().toString()).addOnSuccessListener(requireActivity(), new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        Toast.makeText(requireContext(), "Kullanıcı Oluşturuldu", Toast.LENGTH_SHORT).show();
                        NavDirections action = new ActionOnlyNavDirections(R.id.action_loginFrament_to_userData2);
                        Navigation.findNavController(view).navigate(action);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(), e+" Kullanıcı Oluşturulamadı", Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

        binding.login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signInWithEmailAndPassword(binding.emailText.getText().toString(),binding.password.getText().toString()).addOnSuccessListener(requireActivity(), new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        NavDirections action = new ActionOnlyNavDirections(R.id.action_loginFrament_to_userDashBoard);
                        Navigation.findNavController(view).navigate(action);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(),e+" ", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}