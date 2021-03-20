package com.app.appuber.providers;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class GeoFirebaseProvider {

    private DatabaseReference databaseReference;
    private GeoFire geoFire;

    public GeoFirebaseProvider() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("active_drivers");
        geoFire = new GeoFire(databaseReference);
    }

    public void createLocation(String idUsers, LatLng latLng) {
        geoFire.setLocation(idUsers, new GeoLocation(latLng.latitude, latLng.longitude));
    }

    public void deleteLocation(String idUsers) {
        geoFire.removeLocation(idUsers);
    }

    public GeoQuery readActiveDrivers(LatLng latLng) {
        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(latLng.latitude, latLng.longitude), 6);
        geoQuery.removeAllListeners();

        return geoQuery;
    }
}
