package com.example.statusupload.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.statusupload.MyDatabase;
import com.example.statusupload.R;
import com.example.statusupload.model.StatusModel;
import com.example.statusupload.ui.EditActivity;

import java.util.List;

public class statusAdapter extends RecyclerView.Adapter<statusAdapter.statusViewHolder>{

        private Context context;
        private MyDatabase myDatabase;
        private List<StatusModel> statusModelList;

    public statusAdapter(Context context, MyDatabase myDatabase, List<StatusModel> statusModelList) {
        this.context = context;
        this.myDatabase = myDatabase;
        this.statusModelList = statusModelList;
    }


    @NonNull
    @Override
    public statusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_status,parent,false);

        return new statusViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull statusViewHolder holder, int position) {
        holder.tvUsername.setText(statusModelList.get(position).getUserName());
        holder.tvStatus.setText(statusModelList.get(position).getStatus());

        holder.ivDelete.setOnClickListener(v -> {
            myDatabase.deleteStatus(statusModelList.get(position).getStatusID());
            statusModelList.remove(position);
            notifyDataSetChanged();

        });
        holder.ivEdit.setOnClickListener(v -> {
            Intent i = new Intent(context, EditActivity.class);
            i.putExtra("statusId",statusModelList.get(position).getStatusID());
            i.putExtra("status",statusModelList.get(position).getStatus());
            context.startActivity(i);
        });


    }

    @Override
    public int getItemCount() {
        return statusModelList.size();
    }

    public class statusViewHolder extends RecyclerView.ViewHolder{
        TextView   tvUsername,tvStatus;

        ImageView ivEdit, ivDelete;
        public statusViewHolder(@NonNull View itemView) {
            super(itemView);
            tvUsername = itemView.findViewById(R.id.tvUsername);
            tvStatus = itemView.findViewById(R.id.tvStatus);

            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDelete = itemView.findViewById(R.id.ivDelete);

        }
    }
}
