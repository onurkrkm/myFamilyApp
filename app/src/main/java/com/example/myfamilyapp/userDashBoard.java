package com.example.myfamilyapp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.ActionOnlyNavDirections;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myfamilyapp.databinding.FragmentUserDashBoardBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

public class userDashBoard extends Fragment {
    FragmentUserDashBoardBinding binding;
    private FirebaseFirestore firestoree;
    private FirebaseAuth mAuth;
    ActivityResultLauncher<String> izinAl;
    Double longi,latit;
    LocationManager locationManager;
    private GrubsAdapter grubsAdapter;
    private RecyclerView.LayoutManager LinearLayoutManager;
    ArrayList<Grubs> grubs;
    ArrayList<String> myGrubsss;
    ArrayList<DataUser> dataUsers;
    ArrayList<locationData> locationDatalist=new ArrayList<>();
    myGrubs myGrubs;
    myGrubs nameMember;
    String name;

    timer t;


    TextView textView;

    public userDashBoard() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firestoree = FirebaseFirestore.getInstance();
        mAuth= FirebaseAuth.getInstance();

        izinAlımı();


    }



    private void izinAlımı(){
        izinAl=registerForActivityResult(new ActivityResultContracts.RequestPermission(), new ActivityResultCallback<Boolean>() {
            @Override
            public void onActivityResult(Boolean result) {
                if(result){
                    if (ContextCompat.checkSelfPermission(requireContext(),Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED){
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,locationListener);
                    }
                }else{
                    Toast.makeText(requireContext(), "İzin Gerekli", Toast.LENGTH_LONG).show();
                }
            }
        });


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentUserDashBoardBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;









    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayoutManager = new LinearLayoutManager(getContext());
        grubsAdapter=new GrubsAdapter();
        binding.grubsView.setAdapter(grubsAdapter);
        binding.grubsView.setLayoutManager(LinearLayoutManager);
        grubs=new ArrayList<Grubs>();
        myGrubs=new myGrubs();
        myGrubsss = myGrubs.grbName;
        dataUsers=new ArrayList<>();


        locationManager =(LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
        t=new timer(20000,1000);


        textView=view.findViewById(R.id.grubsName);

        if(ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            //izin istenmesi gerek
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),Manifest.permission.ACCESS_FINE_LOCATION)){
                Snackbar.make(getView(),"Konum izni gereklidir",Snackbar.LENGTH_INDEFINITE).setAction("İzin Ver", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //izin butonu
                        izinAl.launch(Manifest.permission.ACCESS_FINE_LOCATION);
                    }
                }).show();
            }else{
                izinAl.launch(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,0,locationListener);
        }






        t.start();

        binding.button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavDirections action = new ActionOnlyNavDirections(R.id.action_userDashBoard_to_grubsCreat);
                Navigation.findNavController(view).navigate(action);
                Toast.makeText(requireContext(), "Grup Ekleme Butonuna Tıklandı", Toast.LENGTH_LONG).show();
            }
        });

        nameMember=new myGrubs();


        firestoree.collection("grubs").addSnapshotListener((value, error) -> {
            if (error != null){
                Toast.makeText(requireContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }else{
                if(value !=null){
                    if(value.isEmpty()){
                        Toast.makeText(requireContext(), "Kullanıcı Yok", Toast.LENGTH_SHORT).show();
                    }else{
                        List<DocumentSnapshot> documents = value.getDocuments();
                        grubs.clear();
                        for(DocumentSnapshot document : documents){
                            String member = document.get("member").toString();
                            String grubsnName = document.get("grubsnName").toString();
                            DataUser duser=new DataUser(member,grubsnName);
                            nameMember.dataUsers.add(duser);

                        }
                    }
                }
            }
        });




        for(int i = 1; i<=nameMember.member.size(); i++){



        }


        firestoree.collection("grubs").addSnapshotListener((value, error) -> {
            if (error != null){
                Toast.makeText(requireContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }else{
                if(value !=null){
                    if(value.isEmpty()){
                        Toast.makeText(requireContext(), "Kullanıcı Yok", Toast.LENGTH_SHORT).show();
                    }else{
                        List<DocumentSnapshot> documents = value.getDocuments();
                        grubs.clear();
                        for(DocumentSnapshot document : documents){
                            String member = document.get("member").toString();
                            String grubsnName = document.get("grubsnName").toString();
                            if(member.equals(mAuth.getCurrentUser().getEmail().toString())){
                                Grubs grub=new Grubs(grubsnName,"1");

                                grubs.add(grub);
                            }
                            grubsAdapter.dataSource=grubs;
                        }
                    }
                    grubsAdapter.notifyDataSetChanged();
                }
            }
        });

    }

    LocationListener locationListener=new LocationListener() {
        @Override
        public void onLocationChanged(@NonNull Location location) {

            latit=location.getLatitude();
            longi=location.getLongitude();







        }
    };



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding=null;
    }


    class timer extends CountDownTimer{

        public timer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {

            System.out.println(l);

        }

        @Override
        public void onFinish() {

            Map<String,Object> kordinat = new HashMap<>();
            kordinat.put("latit",latit);
            kordinat.put("longi",longi);

            firestoree.collection("users").document(mAuth.getCurrentUser().getEmail().toString()).set(kordinat, SetOptions.merge()).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    System.out.println("Konum gönderildi");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "Konum Gönderilemedi", Toast.LENGTH_SHORT).show();
                }
            });
            t.start();



        }
    }
}