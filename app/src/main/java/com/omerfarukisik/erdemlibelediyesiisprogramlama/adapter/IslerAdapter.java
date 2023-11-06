package com.omerfarukisik.erdemlibelediyesiisprogramlama.adapter;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.transition.Hold;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.omerfarukisik.erdemlibelediyesiisprogramlama.databinding.ActivityListeEkraniBinding;
import com.omerfarukisik.erdemlibelediyesiisprogramlama.databinding.IsRecycleViewBinding;
import com.omerfarukisik.erdemlibelediyesiisprogramlama.model.Isler;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;



public class IslerAdapter extends RecyclerView.Adapter<IslerAdapter.IslerHolder> {




    private ArrayList<Isler> islerArrayList;

    public IslerAdapter(ArrayList<Isler> islerArrayList) {
        this.islerArrayList = islerArrayList;
    }

    @NonNull
    @Override
    public IslerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        IsRecycleViewBinding isRecycleViewBinding=IsRecycleViewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new IslerHolder(isRecycleViewBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull IslerHolder holder, int position) {


        holder.isRecycleViewBinding.sorumluPersonel.setText(islerArrayList.get(position).sorumluPersonel);
        holder.isRecycleViewBinding.bitisTarihi.setText(islerArrayList.get(position).bitisTarihi);
        holder.isRecycleViewBinding.mahalle.setText(islerArrayList.get(position).mahalle);
        holder.isRecycleViewBinding.sikayetTel.setText(islerArrayList.get(position).sikayetTel);
        holder.isRecycleViewBinding.sikayetTC.setText(islerArrayList.get(position).sikayetTC);






    }

    @Override
    public int getItemCount() {
        return islerArrayList.size();
    }

    class IslerHolder extends RecyclerView.ViewHolder{

        IsRecycleViewBinding isRecycleViewBinding;

        public IslerHolder(IsRecycleViewBinding isRecycleViewBinding) {
            super(isRecycleViewBinding.getRoot());
            this.isRecycleViewBinding=isRecycleViewBinding;
        }
    }
}
