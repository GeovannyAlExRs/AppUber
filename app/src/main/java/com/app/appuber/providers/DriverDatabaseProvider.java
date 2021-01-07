package com.app.appuber.providers;

import com.app.appuber.model.Clients;
import com.app.appuber.model.Drivers;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverDatabaseProvider {

    DatabaseReference databaseReference;

    public DriverDatabaseProvider() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers");
    }

    public Task<Void> create(Drivers drivers) {
        return databaseReference.child(drivers.getId_driver()).setValue(drivers);
    }
}
