package com.example.myfamilyapp;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class MapsFragment extends Fragment {
    private FirebaseFirestore firestore;
    private FirebaseAuth mAuth;
    private FirebaseFirestoreSettings settings;
    myGrubs nameMember;
    userDashBoard userDashBoard=new userDashBoard();

    GoogleMap googleMap;

    ArrayList<locationData> locationdataList=new ArrayList<>();

    ArrayList<String> member;
    String name;


    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {

            firestore = FirebaseFirestore.getInstance();
            mAuth= FirebaseAuth.getInstance();
            nameMember=new myGrubs();



            firestore.collection("users").addSnapshotListener((value, error) -> {
                if (error != null){
                    Toast.makeText(requireContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }else{
                    if(value !=null){
                        List<DocumentSnapshot> documents = value.getDocuments();

                        for(DocumentSnapshot document : documents){

                            System.out.println(document);

                            for(int i=0;i<nameMember.dataUsers.size();i++){
                                if(nameMember.chats.equals(nameMember.dataUsers.get(i).GrubsName)){
                                    if(document.get("userEmail").equals(nameMember.dataUsers.get(i).UserName)){
                                        double longi=Double.parseDouble(document.get("longi").toString());
                                        double latit=Double.parseDouble(document.get("latit").toString());

                                        LatLng userLoc = new LatLng(latit, longi);



                                        googleMap.addMarker(new MarkerOptions().position(userLoc).title(document.get("userName").toString()));

                                        googleMap.moveCamera(CameraUpdateFactory.newLatLng(userLoc));
                                    }
                                }
                            }


                        }
                    }
                }
            });




        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }


        member=new ArrayList<>();





        /*firestore.collection("Users").document(member.get(0).toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                latit=Double.parseDouble(documentSnapshot.get("latit").toString());
                longi=Double.parseDouble(documentSnapshot.get("longi").toString());
                System.out.println(longi+"----"+latit);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });*/





    }



}