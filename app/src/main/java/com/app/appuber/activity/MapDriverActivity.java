package com.app.appuber.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.appuber.R;
import com.app.appuber.providers.AuthFirebaseProvider;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MapDriverActivity extends AppCompatActivity implements View.OnClickListener, OnMapReadyCallback {

    private AppCompatButton ac_btn_logout;

    private GoogleMap googleMap;
    private SupportMapFragment supportMapFragment;

    AuthFirebaseProvider firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_driver);

        getViewId();

        firebaseAuth = new AuthFirebaseProvider();

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.id_frag_map);
        supportMapFragment.getMapAsync(this);
    }

    private void getViewId() {
        ac_btn_logout = findViewById(R.id.id_btn_logout);
        ac_btn_logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_btn_logout:
                firebaseAuth.logout();
                goToView(MainActivity.class);
                break;
        }
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

        googleMap.moveCamera(CameraUpdateFactory
                            .newCameraPosition(new CameraPosition.Builder()
                                    .target(new LatLng(1.222122, -77.12222))
                                    .zoom(17f)
                                    .build()));
    }
}