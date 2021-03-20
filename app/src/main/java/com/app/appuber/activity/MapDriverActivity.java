package com.app.appuber.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.app.appuber.R;
import com.app.appuber.include.MyNewToolbar;
import com.app.appuber.providers.AuthFirebaseProvider;
import com.app.appuber.providers.GeoFirebaseProvider;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapDriverActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    private AppCompatButton btn_connect;

    private AuthFirebaseProvider firebaseAuth;
    private GeoFirebaseProvider geoFirebaseProvider;

    private GoogleMap googleMap;
    private SupportMapFragment supportMapFragment;

    private LocationRequest locationRequest;
    private FusedLocationProviderClient fusedLocationProviderClient;

    private Marker marker;
    private boolean isConnect = false;
    private LatLng currentUbication;

    private final static int LOCATION_REQUEST_CODE = 1;
    private final static int SETTINGS_REQUEST_CODE = 2;

    LocationCallback locationCallback = new LocationCallback() {
        @Override
        public void onLocationResult(LocationResult locationResult) {
            for (Location location: locationResult.getLocations()) {

                if (getApplicationContext() != null) {

                    currentUbication = new LatLng(location.getLatitude(), location.getLongitude());

                    if (marker != null) {
                        marker.remove();
                    }
                    marker = googleMap.addMarker(new MarkerOptions().position(currentUbication)
                            .title("Posicion: " + currentUbication)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.ico_marker1)));

                    // Get Location User real time
                    googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(
                            new CameraPosition.Builder()
                            .target(currentUbication)
                            .zoom(15f)
                            .build()
                    ));

                    updateLocation();
                }
            }
        }
    };

    private void updateLocation() {
        if (firebaseAuth.usersExitSession() && currentUbication != null) {
            geoFirebaseProvider.createLocation(firebaseAuth.getIdUsers(), currentUbication);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_driver);

        MyNewToolbar.showView(MapDriverActivity.this, "Conductor", false);

        getViewId();

        firebaseAuth = new AuthFirebaseProvider();
        geoFirebaseProvider = new GeoFirebaseProvider();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.id_frag_map);
        supportMapFragment.getMapAsync(this);
    }

    private void getViewId() {
        btn_connect = findViewById(R.id.id_btn_connect);
        btn_connect.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_btn_connect:
                    if (isConnect) {
                        disconnect();
                    } else {
                        startLocation();
                    }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_driver, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.id_item_logout); {
            disconnect(); // Video 8 - Min 2:09
            firebaseAuth.logout();
            goToView(MainActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }

    public void goToView(Class activiyClass) {
        Intent intent = new Intent(this, activiyClass);
        startActivity(intent);
        finish();
    }

    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setSmallestDisplacement(5);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_REQUEST_CODE) {

            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    if (gpsActived()) {
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                        googleMap.setMyLocationEnabled(true);
                    } else {
                        showAlertDialogGPS();
                    }
                } else {
                    checkLocationPermission();
                }

            } else {
                checkLocationPermission();
            }
        }
    }

    private void disconnect() {
        if (fusedLocationProviderClient != null) {
            btn_connect.setText("CONECTARSE");
            isConnect = false;
            fusedLocationProviderClient.removeLocationUpdates(locationCallback);
            if (firebaseAuth.usersExitSession()) {
                geoFirebaseProvider.deleteLocation(firebaseAuth.getIdUsers());
            }
        } else {
            Toast.makeText(this, "No se puede desconectar", Toast.LENGTH_SHORT).show();
        }
    }

    public void startLocation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

                if (gpsActived()) {
                    btn_connect.setText("DESCONECTARSE");
                    isConnect = true;

                    fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                    googleMap.setMyLocationEnabled(true);
                } else {
                    showAlertDialogGPS();
                }

            } else {
                checkLocationPermission();
            }

        } else {

            if (gpsActived()) {
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                googleMap.setMyLocationEnabled(true);
            } else {
                showAlertDialogGPS();
            }

        }
    }

    public void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setTitle("Proporciona los servicios para continuar")
                        .setMessage("Esta aplicacion necesita acceder a los permisos del GPS")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                ActivityCompat.requestPermissions(MapDriverActivity.this,
                                        new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                                        LOCATION_REQUEST_CODE);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(MapDriverActivity.this,
                        new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST_CODE);
            }
        }
    }

    // check GPS Active
    private boolean gpsActived() {
        boolean isActive = false;
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            isActive = true;
        }
        return isActive;
    }

    private void showAlertDialogGPS() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Activa tu ubicacion GPS para continuar")
                .setPositiveButton("Configuración", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), SETTINGS_REQUEST_CODE);
                    }
                }).create().show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SETTINGS_REQUEST_CODE && gpsActived()) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
                googleMap.setMyLocationEnabled(true);
            }
            //fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.myLooper());
        } else {
            showAlertDialogGPS();
        }
    }
}