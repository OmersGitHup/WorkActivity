package com.omerfarukisik.erdemlibelediyesiisprogramlama;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.omerfarukisik.erdemlibelediyesiisprogramlama.databinding.ActivityMapsBinding;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
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
            public void onStatusChanged(String provider, int status, Bundle extras) {}
            public void onProviderEnabled(String provider) {}
            public void onProviderDisabled(String provider) {}
        };

        // Konum bilgisi isteği yapma
        locationManager.requestSingleUpdate(provider, locationListener, null);


    }




}