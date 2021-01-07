package com.app.appuber.providers;

import com.app.appuber.model.Clients;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ClientDatabaseProvider {

    DatabaseReference databaseReference;

    public ClientDatabaseProvider() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child("Clients");
    }

    public Task<Void> create(Clients clients) {
        /* // VIDEO 15:35
        Map<String, Object> map = new HashMap<>();

        map.put("", clients.getCli_nameFirts());
        map.put("", clients.getCli_nameLast());
        map.put("", clients.getCli_nameUser());
        map.put("", clients.getCli_nameFirts());
        map.put("", clients.getCli_nameFirts());
        return databaseReference.child(clients.getId_client()).setValue(map);
        */

        return databaseReference.child(clients.getId_client()).setValue(clients);
    }

}