package com.example.bikecarservice.vehicleOwner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bikecarservice.R;
import com.example.bikecarservice.serviceCentre.ServiceRegister;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserDashboard extends AppCompatActivity {

    List<ServiceCentreModel> mList = new ArrayList<>();
    RecyclerView recyclerView;
    Adapter mAdapter;

    TextView tv_service1;
    ImageView img_viewnotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        tv_service1 = findViewById(R.id.tv_service1);
        tv_service1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboard.this, ViewServiceStation.class));
            }
        });
        recyclerView = findViewById(R.id.rv_showAllService);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserDashboard.this));

        img_viewnotification = findViewById(R.id.iv_viewNotification);
        img_viewnotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserDashboard.this, ServiceCompleted.class));
            }
        });

//        getAllServices();

    }

    private void getAllServices() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser.getUid() != null) {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(ServiceRegister.USERS).child(firebaseUser.getUid());
            reference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    mList.clear();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        ServiceCentreModel model = dataSnapshot.getValue(ServiceCentreModel.class);
                        mList.add(model);
                    }
                    mAdapter = new Adapter(UserDashboard.this, mList);
                    recyclerView.setAdapter(mAdapter);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}