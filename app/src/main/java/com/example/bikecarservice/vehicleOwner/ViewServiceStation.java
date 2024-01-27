package com.example.bikecarservice.vehicleOwner;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bikecarservice.R;

public class ViewServiceStation extends AppCompatActivity {

    TextView tv_costForActiva, tv_costForBike, tv_costForCar;
    Intent intent;
    Button btn_viewLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_service_station);

        tv_costForActiva = findViewById(R.id.tv_costForActiva);
        tv_costForBike = findViewById(R.id.tv_costForBike);
        tv_costForCar = findViewById(R.id.tv_costForCar);

        intent = getIntent();

        ServiceCentreModel model = getServiceCentreModel();

        tv_costForBike.setText(model.getCostForBike());
        tv_costForActiva.setText(model.getCostForActiva());
        tv_costForCar.setText(model.getCostForCar());

        btn_viewLocation = findViewById(R.id.btn_viewLocation);
        btn_viewLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewServiceStation.this,ShowServiceCentreLocation.class));
            }
        });

    }

    private ServiceCentreModel getServiceCentreModel() {
        ServiceCentreModel model = new ServiceCentreModel();

        String costForBike = intent.getStringExtra("Bike");
        String costForActiva = intent.getStringExtra("Activa");
        String costForCar = intent.getStringExtra("Car");

        model.setCostForActiva(costForActiva);
        model.setCostForBike(costForBike);
        model.setCostForCar(costForCar);

        return model;
    }
}