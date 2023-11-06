package com.omerfarukisik.erdemlibelediyesiisprogramlama.pages;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.omerfarukisik.erdemlibelediyesiisprogramlama.R;
import com.omerfarukisik.erdemlibelediyesiisprogramlama.adapter.IslerAdapter;
import com.omerfarukisik.erdemlibelediyesiisprogramlama.adapter.PersonelAdapter;
import com.omerfarukisik.erdemlibelediyesiisprogramlama.databinding.ActivityPersonelEklemeEkraniBinding;
import com.omerfarukisik.erdemlibelediyesiisprogramlama.model.Personeller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PersonelEklemeEkrani extends AppCompatActivity {

    //firebase kullanici
    private FirebaseAuth mAuth;
    //firebase data yükleme
    private FirebaseFirestore firebaseFirestore;
    //ekran okuma binding
    private ActivityPersonelEklemeEkraniBinding binding;
    //Dataya Gönderme İzni
    ArrayList<Personeller> personellerArrayList;
    PersonelAdapter personelAdapter;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ekran okuma binding
        binding = ActivityPersonelEklemeEkraniBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mAuth = FirebaseAuth.getInstance();
        // Access a Cloud Firestore instance from your Activity
        firebaseFirestore = FirebaseFirestore.getInstance();

        personellerArrayList=new ArrayList<>();
        getData();


        binding.personelListesi.setLayoutManager(new LinearLayoutManager(this));
        personelAdapter=new PersonelAdapter(personellerArrayList);
        binding.personelListesi.setAdapter(personelAdapter);



    }

     private void getData(){

        firebaseFirestore.collection("Personeller").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Toast.makeText(PersonelEklemeEkrani.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                if (value != null){
                    for (DocumentSnapshot snapshot : value.getDocuments()){
                        Map<String , Object>data= snapshot.getData();

                        //Casting

                        String personelAd =(String) data.get("PersoneliIsmi");
                        String personelSoyad=(String) data.get("PersonelinSoyadi");
                        String personelinTC=(String) data.get("PersonelTC");
                        String personelTel=(String) data.get("PersonelinTelefonu");
                        String personelMail=(String) data.get("personelMail");
                        String personelSifre=(String) data.get("personelSifre");

                        Personeller personeller = new Personeller(personelAd,personelSoyad,personelTel,personelinTC,personelMail,personelSifre);

                        personellerArrayList.add(personeller);

                    }
                    personelAdapter.notifyDataSetChanged();

                }

            }

        });


    }

    public void personel_kaydet(View view){




        String personelTel=binding.personelTel.getText().toString();
        String personelAd=binding.personelAd.getText().toString();
        String personelSoy=binding.personelSoyad.getText().toString();
        String personelTC=binding.personelTC.getText().toString();
        String personelMail=binding.personelMail2.getText().toString();
        String personelSifre=binding.personelSifre.getText().toString();
        String personelRolu=binding.personelRolu.getText().toString();
        //Kullanıcı alma
        FirebaseUser user =mAuth.getCurrentUser();
        String email =user.getEmail();



        Map<String, Object> kayit = new HashMap<>();

        kayit.put("useremail",email);
        kayit.put("PersoneliIsmi",personelAd);
        kayit.put("PersonelinTelefonu",personelTel);
        kayit.put("PersonelinSoyadi",personelSoy);
        kayit.put("PersonelinTC",personelTC);
        kayit.put("saveDate", FieldValue.serverTimestamp());
        kayit.put("personelSifre",personelSifre);
        kayit.put("personelMail",personelMail);
        kayit.put("personelRolu",personelRolu);

    if (email.isEmpty()||personelAd.isEmpty()||personelSoy.isEmpty()||personelTC.isEmpty()||personelMail.isEmpty()||personelSifre.isEmpty()){
        Toast.makeText(this, "Personel Eklerken Boş Alan Bırakmayın!", Toast.LENGTH_SHORT).show();
    }else {
        // Add a new document with a generated ID
        firebaseFirestore.collection("Personeller")
                .add(kayit).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Toast.makeText(PersonelEklemeEkrani.this, "Personel Kaydedildi !", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PersonelEklemeEkrani.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });


        mAuth.createUserWithEmailAndPassword(personelMail,personelSifre).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(PersonelEklemeEkrani.this, "Kullanıcı Giriş Bilgileri Oluşturuldu", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PersonelEklemeEkrani.this, ListeEkrani.class);
                startActivity(intent);
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PersonelEklemeEkrani.this, "Personel Giriş Bilgileri Oluşturulamadı", Toast.LENGTH_SHORT).show();
            }
        });

    }



    }




}

