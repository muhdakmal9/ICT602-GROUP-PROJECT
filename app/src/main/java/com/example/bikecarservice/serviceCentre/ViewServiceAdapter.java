package com.example.bikecarservice.serviceCentre;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bikecarservice.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewServiceAdapter extends RecyclerView.Adapter<ViewServiceAdapter.viewHolder> {
    private Context mContext;
    private List<ServiceModel> mList = new ArrayList<>();

    private ProgressDialog progressDialog;

    public ViewServiceAdapter(Context mContext, List<ServiceModel> mList) {
        this.mContext = mContext;
        this.mList = mList;
        progressDialog = new ProgressDialog(mContext);
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_view_service, parent, false);
        return new viewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        ServiceModel model = mList.get(position);
        holder.tv_username.setText(model.getUsername());
        holder.tv_vehicleType.setText(model.getVehicleType());
        holder.tv_vehicleNumber.setText(model.getVehicleNumber());
        holder.tv_costForVehicle.setText(model.getCost());

        holder.btn_complete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setMessage("Complete");
                progressDialog.setTitle("Complete....");
                progressDialog.show();
                markAsComplete(model);
            }
        });

    }

    public void markAsComplete(ServiceModel model) {
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        String userId = firebaseUser.getUid();

        // Assuming 'services' is your Firestore collection name
        DocumentReference documentReference = db.collection("services").document(model.getUsername());

        Map<String, Object> data = new HashMap<>();
        data.put("id", userId);
        data.put("Name", model.getUsername());
        data.put("cost", model.getCost());

        documentReference.set(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                progressDialog.dismiss();
                if (task.isSuccessful()) {
                    Toast.makeText(mContext, "Successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    class viewHolder extends RecyclerView.ViewHolder {

        private TextView tv_username;
        private TextView tv_costForVehicle;
        private TextView tv_vehicleType;
        private TextView tv_vehicleNumber;
        private Button btn_complete;

        public viewHolder(@NonNull View itemView) {
            super(itemView);

            tv_username = itemView.findViewById(R.id.tv_username);
            tv_costForVehicle = itemView.findViewById(R.id.tv_serviceCost);
            tv_vehicleType = itemView.findViewById(R.id.tv_vehicleType);
            tv_vehicleNumber = itemView.findViewById(R.id.tv_vehicleNumber);

            btn_complete = itemView.findViewById(R.id.btn_complete);


        }
    }
}
