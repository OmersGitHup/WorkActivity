package com.omerfarukisik.erdemlibelediyesiisprogramlama.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.omerfarukisik.erdemlibelediyesiisprogramlama.databinding.GirisEkraniBinding;
import com.omerfarukisik.erdemlibelediyesiisprogramlama.databinding.PersonelRecycleViewBinding;
import com.omerfarukisik.erdemlibelediyesiisprogramlama.model.Personeller;

import java.util.ArrayList;

public class PersonelAdapter extends RecyclerView.Adapter<PersonelAdapter.PersonelHolder>{





    private ArrayList<Personeller>personellerArrayList;

    public PersonelAdapter(ArrayList<Personeller> personellerArrayList) {
        this.personellerArrayList = personellerArrayList;

    }

    @NonNull
    @Override
    public PersonelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        PersonelRecycleViewBinding personelRecycleViewBinding =PersonelRecycleViewBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new PersonelHolder(personelRecycleViewBinding);

    }

    @Override
    public void onBindViewHolder(@NonNull PersonelHolder holder, int position) {
        holder.personelRecycleViewBinding.personelIsim.setText(personellerArrayList.get(position).personelAd);
        holder.personelRecycleViewBinding.personelSoyisim.setText(personellerArrayList.get(position).personelSoyad);
        holder.personelRecycleViewBinding.personelMail.setText(personellerArrayList.get(position).personelMail);

    }

    @Override
    public int getItemCount() {
        return personellerArrayList.size();
    }

    class PersonelHolder extends RecyclerView.ViewHolder{

        PersonelRecycleViewBinding personelRecycleViewBinding;

        public PersonelHolder(PersonelRecycleViewBinding personelRecycleViewBinding) {
            super(personelRecycleViewBinding.getRoot());
            this.personelRecycleViewBinding=personelRecycleViewBinding;
        }
    }


}


