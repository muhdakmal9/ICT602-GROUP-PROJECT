package com.example.bikecarservice;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bikecarservice.serviceCentre.ServiceLogin;

public class MainActivity extends AppCompatActivity {

    Button btn_vehicleOwner, btn_serviceCentre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_serviceCentre = findViewById(R.id.btn_serviceCentre);

        btn_serviceCentre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ServiceLogin.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
            }
        });

    }
}