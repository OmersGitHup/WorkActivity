package com.omerfarukisik.erdemlibelediyesiisprogramlama.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.omerfarukisik.erdemlibelediyesiisprogramlama.databinding.GirisEkraniBinding;


public class GirisEkrani extends AppCompatActivity {

    private GirisEkraniBinding binding;

    private FirebaseAuth mAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Binding
        binding = GirisEkraniBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user=mAuth.getCurrentUser();




        if (user != null){
            Intent intent =new Intent(GirisEkrani.this,ListeEkrani.class);
            startActivity(intent);

        }
    }

    public void giris(View view) {


        String email = binding.girisEmail.getText().toString();
        String password = binding.girisSifre.getText().toString();


        mAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                Intent intent = new Intent(GirisEkrani.this, ListeEkrani.class);
                startActivity(intent);
                finish();

                }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(GirisEkrani.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });











    }

    //Üye ol kapalı

    /*public void uyeol (View view) {

        String email = binding.girisEmail.getText().toString();
        String password = binding.girisSifre.getText().toString();


        mAuth.createUserWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                Toast.makeText(GirisEkrani.this,"User Created",Toast.LENGTH_LONG).show();

                Intent intent = new Intent(GirisEkrani.this,ListeEkrani.class);
                startActivity(intent);
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(GirisEkrani.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });
    }*/
}