package com.omerfarukisik.erdemlibelediyesiisprogramlama.pages;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.omerfarukisik.erdemlibelediyesiisprogramlama.MapsActivity;
import com.omerfarukisik.erdemlibelediyesiisprogramlama.databinding.ActivityIsEklemeEkraniBinding;

import java.util.HashMap;
import java.util.Map;

public class IsEklemeEkrani extends AppCompatActivity {

    private static final double MAPS_ACTIVITY_REQUEST_CODE = 123;
    //firebase kullanici
    private FirebaseAuth mAuth;
    //firebase data yükleme
    private FirebaseFirestore firebaseFirestore;
    //ekran okuma binding
    private ActivityIsEklemeEkraniBinding binding;


    //Dropdown menü


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //ekran okuma binding
        binding = ActivityIsEklemeEkraniBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        mAuth = FirebaseAuth.getInstance();
        // Access a Cloud Firestore instance from your Activity
        firebaseFirestore = FirebaseFirestore.getInstance();



        //DropdownMenü
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == MAPS_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
                // MapsActivity'den geri dönen koordinatları almak için
                double latitude = data.getDoubleExtra("latitude", 0.0);
                double longitude = data.getDoubleExtra("longitude", 0.0);

                // Konum verilerini Firebase veritabanına kaydedebilirsiniz
                // Bu örnekte, "konumlar" adlı bir "jobs" düğümü altına kaydediliyor
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("jobs").child("konumlar");
                Map<String, Object> location = new HashMap<>();
                location.put("latitude", latitude);
                location.put("longitude", longitude);
                databaseReference.push().setValue(location);
                // Konum seçildikten sonra geri dönmek için setResult kullanılır
                // İkinci parametrede konumun koordinatları bulunur.
                setResult(MapsActivity.RESULT_OK, new Intent().putExtra("latitude", latitude).putExtra("longitude", longitude));
                finish();

            }

        }


    }

    public void konumlink(View view) {

        Intent intent = new Intent(IsEklemeEkrani.this, MapsActivity.class);
        startActivity(intent);
        // Konum servisi için LocationManager oluşturma
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        // Konum bilgisi için kriterler oluşturma
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_LOW);

        // En iyi konum sağlayıcısını bulma
        String provider = locationManager.getBestProvider(criteria, true);

        // Konum bilgisi almak için LocationListener oluşturma
        LocationListener locationListener = new LocationListener() {
            // Konum değiştiğinde çağrılacak metot
            public void onLocationChanged(Location location) {
                // Konum bilgisi alındığında yapılacak işlemler
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                // Konum verilerini Firebase Realtime Database'e kaydetme
                DatabaseReference database = FirebaseDatabase.getInstance().getReference();
                String key = database.child("konumlar").push().getKey();
                Map<String, Object> konum = new HashMap<>();
                konum.put("latitude", latitude);
                konum.put("longitude", longitude);
                database.child("konumlar").child(key).setValue(konum);

                // Konum servisini durdurma
                locationManager.removeUpdates(this);
            }

            // Diğer metodlar boş bırakılabilir
            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        // Konum bilgisi isteği yapma
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;

        }
        locationManager.requestSingleUpdate(provider, locationListener, null);

    }




    public void kaydet(View view){



        //Şikayet Çekme -- string tanımlanmıştır.
        String personelIsim =binding.kayitSorumlu.getText().toString();
        String grubu=binding.kayitGrubu.getText().toString();
        String sikayetTC=binding.kayitTC.getText().toString();
        String kayitTarihi=binding.kayitTarihi.getText().toString();
        String sikayetIsim=binding.sikayetIsim.getText().toString();
        String sikayetTel=binding.sikayetciTelefon.getText().toString();
        String anaKonu=binding.konuVeDetay.getText().toString();
        String bitisTarihi=binding.bitisTarihi.getText().toString();
        String olayinDurumu=binding.olayinDurumu.getText().toString();
        //String konumLink=binding.konumLink.getText().toString();
        //String fotoLink=binding.konumLink.getText().toString();
        String mahalle=binding.mahalleAdres.getText().toString();
        String cimerTalepNo=binding.TalepNoCimer.getText().toString();
        String notes=binding.nOT.getText().toString();

        //Kullanıcı Çekme
        FirebaseUser user =mAuth.getCurrentUser();
        String email = user.getEmail();

        Map<String,Object>sikayetKayit=new HashMap<>();

        sikayetKayit.put("SorumluPersonel",personelIsim);
        sikayetKayit.put("KonuGrubu",grubu);
        sikayetKayit.put("SikayetciTC",sikayetTC);
        sikayetKayit.put("KayitTarihi",kayitTarihi);
        sikayetKayit.put("Sikatciİsim",sikayetIsim);
        sikayetKayit.put("SikayetciTel",sikayetTel);
        sikayetKayit.put("KonuTexti",anaKonu);
        sikayetKayit.put("Not",notes);
        sikayetKayit.put("bitisTarihi",bitisTarihi);
        sikayetKayit.put("OlayınAnlıkDurumu",olayinDurumu);
        //sikayetKayit.put("konumLinki",konumLink);
        //sikayetKayit.put("FotografLink",fotoLink);
        sikayetKayit.put("Mahallesi",mahalle);
        sikayetKayit.put("CimerTalepNo",cimerTalepNo);
        sikayetKayit.put("KaydedenKullanici",email);
        sikayetKayit.put("UygulamayaKaydi", FieldValue.serverTimestamp());

        if (personelIsim.isEmpty()||grubu.isEmpty()||sikayetTC.isEmpty()||kayitTarihi.isEmpty()||sikayetIsim.isEmpty()||sikayetTel.isEmpty()||
                anaKonu.isEmpty()||bitisTarihi.isEmpty()||olayinDurumu.isEmpty()/*||konumLink.isEmpty()||fotoLink.isEmpty()*/||mahalle.isEmpty()||cimerTalepNo.isEmpty()){
            Toast.makeText(this, "Lütfen Boş Alan Bırakmayın", Toast.LENGTH_SHORT).show();
            return;
        }firebaseFirestore.collection("SikayetKayit").add(sikayetKayit)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Intent intent=new Intent(IsEklemeEkrani.this,ListeEkrani.class);
                        startActivity(intent);
                        finish();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(IsEklemeEkrani.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });




    }

    public void geri(View view){
        Intent intent = new Intent(IsEklemeEkrani.this,ListeEkrani.class);
        startActivity(intent);
        finish();
    }
}