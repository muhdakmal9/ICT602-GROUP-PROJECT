package com.example.bikecarservice.vehicleOwner;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bikecarservice.R;

public class ServiceCompleted extends AppCompatActivity {

    TextView tv_serviceCompleted;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_completed);
        Boolean isCompleted = true;
        tv_serviceCompleted = findViewById(R.id.tv_completed);

        if (isCompleted) {
            tv_serviceCompleted.setText("Your Vehicle servicing is done. Please collect your vehicle");
        } else {
            tv_serviceCompleted.setText("Your Vehicle servicing is pending.");
        }
    }
}