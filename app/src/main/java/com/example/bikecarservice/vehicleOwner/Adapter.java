package com.example.bikecarservice.vehicleOwner;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bikecarservice.R;
import com.example.bikecarservice.serviceCentre.ViewServiceAdapter;

import java.util.ArrayList;
import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.viewHolder> {
    private Context mContext;
    private List<ServiceCentreModel> mList = new ArrayList<>();

    private ProgressDialog progressDialog;

    public Adapter(Context mContext, List<ServiceCentreModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
        progressDialog = new ProgressDialog(mContext);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_service_station, parent, false);
        return new viewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ServiceCentreModel model = mList.get(position);
        holder.tv_username.setText(model.getServiceCentreName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, ViewServiceStation.class);
                intent.putExtra("Bike", model.getCostForBike());
                intent.putExtra("Car", model.getCostForCar());
                intent.putExtra("Activa", model.getCostForActiva());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {

        private TextView tv_username;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            tv_username = itemView.findViewById(R.id.tv_username);


        }
    }
}
