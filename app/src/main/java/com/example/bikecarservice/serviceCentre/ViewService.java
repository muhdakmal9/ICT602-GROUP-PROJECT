// ViewService.java

package com.example.bikecarservice.serviceCentre;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bikecarservice.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

// Import statements

public class ViewService extends AppCompatActivity {

    private List<ServiceModel> mList = new ArrayList<>();
    private RecyclerView recyclerView;
    private ViewServiceAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_service);

        recyclerView = findViewById(R.id.rv_showAllService);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewService.this));

        // Initialize and set up the adapter
        mAdapter = new ViewServiceAdapter(ViewService.this, mList);
        recyclerView.setAdapter(mAdapter);

        getAllServices();
    }

    private void getAllServices() {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference servicesRef = db.collection("services");

            servicesRef.whereEqualTo("id", firebaseUser.getUid())
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                mList.clear();
                                for (DocumentSnapshot document : task.getResult()) {
                                    ServiceModel model = document.toObject(ServiceModel.class);
                                    mList.add(model);
                                }
                                mAdapter.notifyDataSetChanged(); // Notify adapter about data change
                            } else {
                                // Handle case where there are no documents
                                Toast.makeText(ViewService.this, "No services found", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Handle errors
                            Exception e = task.getException();
                            if (e != null) {
                                e.printStackTrace();
                                Toast.makeText(ViewService.this, "Error reading data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ViewService.this, "Error reading data", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            // Handle case where the user is not authenticated
            Toast.makeText(ViewService.this, "User not authenticated", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onBackPressed() {

        // Create an Intent to navigate back to ServiceDashboard
        Intent intent = new Intent(this, ServiceDashboard.class);

        // Add any additional flags if needed
        // For example, to clear the back stack
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Start ServiceDashboard
        startActivity(intent);

        // Finish the current activity to remove it from the back stack
        finish();
    }
}
