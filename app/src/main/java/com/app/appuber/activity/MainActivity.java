package com.app.appuber.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.app.appuber.R;
import com.google.firebase.auth.FirebaseAuth;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton ac_btn_driver, ac_btn_client;

    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPref = getSharedPreferences("typeUser", MODE_PRIVATE);
        editor = sharedPref.edit();

        getViewId();
    }

    private void getViewId() {
        ac_btn_driver = findViewById(R.id.id_btn_driver);
        ac_btn_client = findViewById(R.id.id_btn_client);

        ac_btn_driver.setOnClickListener(this);
        ac_btn_client.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_btn_driver:
                editor.putString("user", "driver");
                editor.apply();
                goToView(AuthenticationActivity.class, false);
                break;
            case R.id.id_btn_client:
                editor.putString("user", "client");
                editor.apply();
                goToView(AuthenticationActivity.class, false);
                //Toast.makeText(this, "Proximamente...", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void goToView(Class activiyClass, boolean band) {
        Intent intent = new Intent(MainActivity.this, activiyClass);

        if (band == false) {
            startActivity(intent);
        } else {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            String typeUser = sharedPref.getString("user", "");
            if (typeUser.equals("client")) {
                goToView(MapClientActivity.class, true);
            } else if (typeUser.equals("driver")) {
                goToView(MapDriverActivity.class, true);
            }
        }
    }
}