package com.app.appuber.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.app.appuber.R;
import com.app.appuber.providers.AuthFirebaseProvider;

public class MapDriverActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton ac_btn_logout;

    AuthFirebaseProvider firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_driver);

        getViewId();
        //Aparentemente no subiste el activity del MAPA CONDUCTOR...
        firebaseAuth = new AuthFirebaseProvider();
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
}