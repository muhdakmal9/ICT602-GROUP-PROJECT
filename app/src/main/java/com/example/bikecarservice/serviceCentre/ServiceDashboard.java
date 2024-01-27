package com.example.bikecarservice.serviceCentre;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bikecarservice.R;
import com.example.bikecarservice.vehicleOwner.ShowServiceCentreLocation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ServiceDashboard extends AppCompatActivity {

    Button btn_viewService, btn_addService, btn_addServiceType;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_dashboard);

        btn_viewService = findViewById(R.id.btn_viewServices);
        btn_addService = findViewById(R.id.btn_addService);
        btn_addServiceType = findViewById(R.id.btn_addServiceType);

        btn_viewService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServiceDashboard.this, ViewService.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });
        String restName = "";
        btn_addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createDialog(restName);
            }
        });
        btn_addServiceType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ServiceDashboard.this, ShowServiceCentreLocation.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

        progressDialog = new ProgressDialog(ServiceDashboard.this);

    }

    private void addServiceTypeToFirestore(String vehicleType, String costForCar, String costForBike, String costForActiva, String restName) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();
            CollectionReference serviceTypesRef = db.collection("service_types");

            // Create a Map to store the data
            Map<String, Object> serviceTypeData = new HashMap<>();
            serviceTypeData.put("vehicleType", vehicleType);
            serviceTypeData.put("costForCar", costForCar);
            serviceTypeData.put("costForBike", costForBike);
            serviceTypeData.put("costForActiva", costForActiva);
            serviceTypeData.put("restName", restName);

            // Add the data to Firestore
            serviceTypesRef.add(serviceTypeData)
                    .addOnSuccessListener(documentReference -> {
                        // Successfully added data to Firestore
                        progressDialog.dismiss();
                        Toast.makeText(ServiceDashboard.this, "Service Type added successfully", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        // Handle errors
                        progressDialog.dismiss();
                        Toast.makeText(ServiceDashboard.this, "Error adding Service Type: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }


    private void createDialog(String restName) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ServiceDashboard.this);
        View view = LayoutInflater.from(ServiceDashboard.this).inflate(R.layout.add_service, null, false);
        builder.setView(view);
        EditText et_username = view.findViewById(R.id.et_username);
        EditText et_vehicleType = view.findViewById(R.id.et_vehicleType);
        EditText et_vehicleNumber = view.findViewById(R.id.et_vehicleNumber);
        EditText et_serviceCost = view.findViewById(R.id.et_serviceCost);
        Button btn_addService = view.findViewById(R.id.btn_addService);
        AlertDialog alertDialog = builder.create();

        alertDialog.show();
        btn_addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString();
                String vehicleType = et_vehicleType.getText().toString();
                String vehicleNumber = et_vehicleNumber.getText().toString();
                String serviceCost = et_serviceCost.getText().toString();

                if (username.isEmpty() || vehicleType.isEmpty() || vehicleNumber.isEmpty() || serviceCost.isEmpty()) {
                    // Handle the case where any of the fields is empty
                    Toast.makeText(ServiceDashboard.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                } else {
                    progressDialog.setMessage("Adding Service");
                    progressDialog.setTitle("Adding...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    // Call the method to add service details to Firestore
                    addServiceToFirestore(username, vehicleType, vehicleNumber, serviceCost, restName);

                    alertDialog.dismiss();
                }
            }
        });
    }

    private void addServiceToFirestore(String username, String vehicleType, String vehicleNumber, String serviceCost, String restName) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = firebaseUser.getUid();

        // Assuming 'services' is your Firestore collection name
        CollectionReference servicesCollection = db.collection("services");

        // Create a new document with a generated ID
        DocumentReference newServiceRef = servicesCollection.document();

        Map<String, Object> data = new HashMap<>();
        data.put("id", userId);
        data.put("username", username);
        data.put("vehicleType", vehicleType);
        data.put("vehicleNumber", vehicleNumber);
        data.put("serviceCost", serviceCost);
        data.put("restName", restName);

        // Set the data for the new document
        newServiceRef.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(ServiceDashboard.this, "Service added successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ServiceDashboard.this, "Failed to add service", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private void createFood(String foodName, String foodDesc, String foodPrice, String restName, String foodImage) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String userId = firebaseUser.getUid();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child(ServiceRegister.SERVICE_CENTRE).child(restName);
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("foodName", foodName);
        hashMap.put("foodDesc", foodDesc);
        hashMap.put("foodPrice", foodPrice);
        hashMap.put("foodImage", foodImage);
        hashMap.put("id", userId);
        reference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
//                    getAllFood(restName);
                    Toast.makeText(ServiceDashboard.this, "Added Successfully", Toast.LENGTH_SHORT).show();
//                    imageString = "";
                } else {
                    Toast.makeText(ServiceDashboard.this, "Created Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {

        // Create an Intent to navigate back to ServiceDashboard
        Intent intent = new Intent(this, ServiceLogin.class);

        // Add any additional flags if needed
        // For example, to clear the back stack
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Start ServiceDashboard
        startActivity(intent);

        // Finish the current activity to remove it from the back stack
        finish();
    }
}