package com.omerfarukisik.erdemlibelediyesiisprogramlama.pages;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.omerfarukisik.erdemlibelediyesiisprogramlama.R;
import com.omerfarukisik.erdemlibelediyesiisprogramlama.adapter.IslerAdapter;
import com.omerfarukisik.erdemlibelediyesiisprogramlama.databinding.ActivityIsEklemeEkraniBinding;
import com.omerfarukisik.erdemlibelediyesiisprogramlama.databinding.ActivityListeEkraniBinding;
import com.omerfarukisik.erdemlibelediyesiisprogramlama.databinding.IsRecycleViewBinding;
import com.omerfarukisik.erdemlibelediyesiisprogramlama.model.Isler;
import com.omerfarukisik.erdemlibelediyesiisprogramlama.model.Personeller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ListeEkrani extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private ActivityListeEkraniBinding binding;


    //Islerin Saklanacağı arraylist
    ArrayList<Isler> islerArrayList;

    IslerAdapter islerAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        //binding liste
        binding = ActivityListeEkraniBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        //firebase authentication
        mAuth=FirebaseAuth.getInstance();
        // Access a Cloud Firestore instance from your Activity
        firebaseFirestore = FirebaseFirestore.getInstance();
        //
        islerArrayList=new ArrayList<>();

        getData();


        //Veri alındıktan sonra

        binding.isListesi.setLayoutManager(new LinearLayoutManager(this));
        islerAdapter=new IslerAdapter(islerArrayList);
        binding.isListesi.setAdapter(islerAdapter);



    }


    private void getData(){


        firebaseFirestore.collection("SikayetKayit").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Toast.makeText(ListeEkrani.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                if (value != null){


                    for (DocumentSnapshot snapshot:value.getDocuments()){


                        Map<String,Object> data= snapshot.getData();

                        //Casting

                        String sorumluIsim=(String) data.get("SorumluPersonel");
                        String mahalle=(String)  data.get("Mahallesi");
                        String sikayetIrtibat=(String) data.get("SikayetciTel");
                        String bitisTarihi=(String) data.get("bitisTarihi");
                        String sikayetTC=(String) data.get("SikayetciTel");



                        Isler isler=new Isler(sorumluIsim,mahalle,sikayetIrtibat,bitisTarihi,sikayetTC);

                        islerArrayList.add(isler);

                    }

                    islerAdapter.notifyDataSetChanged();

                }

            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.liste_ekrani_yonlendirme,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId()==R.id.addJob){
            Intent goisekle=new Intent(ListeEkrani.this,IsEklemeEkrani.class);
            startActivity(goisekle);


        }else if (item.getItemId()==R.id.exit){

            mAuth.signOut();
            Intent intent=new Intent(ListeEkrani.this,GirisEkrani.class);
            startActivity(intent);
            finish();

        }else if (item.getItemId()==R.id.addPersonel){
            Intent addpersonelintent=new Intent(ListeEkrani.this,PersonelEklemeEkrani.class);
            startActivity(addpersonelintent);


        }


        return super.onOptionsItemSelected(item);
    }




}