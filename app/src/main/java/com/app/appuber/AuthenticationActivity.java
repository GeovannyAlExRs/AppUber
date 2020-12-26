package com.app.appuber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class AuthenticationActivity extends AppCompatActivity implements View.OnClickListener{

    private AppCompatButton ac_btn_login, ac_btn_register, ac_btn_main;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        getViewId();
    }

    private void getViewId() {
        ac_btn_login = findViewById(R.id.id_btn_login);
        ac_btn_register = findViewById(R.id.id_btn_register);
        ac_btn_main = findViewById(R.id.id_btn_main);

        ac_btn_login.setOnClickListener(this);
        ac_btn_register.setOnClickListener(this);
        ac_btn_main.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_btn_login:
                goToView(LoginActivity.class);
                break;
            case R.id.id_btn_register:
                goToView(RegisterActivity.class);
                break;
            case R.id.id_btn_main:
                //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                Toast.makeText(this, "Proximamente...", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void goToView(Class activiyClass) {
        Intent intent = new Intent(AuthenticationActivity.this, activiyClass);
        startActivity(intent);
    }
}