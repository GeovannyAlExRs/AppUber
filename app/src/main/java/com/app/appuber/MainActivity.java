package com.app.appuber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AppCompatButton ac_btn_driver, ac_btn_client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                goToView(AuthenticationActivity.class);
                break;
            case R.id.id_btn_client:
                goToView(AuthenticationActivity.class);
                //Toast.makeText(this, "Proximamente...", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    public void goToView(Class activiyClass) {
        Intent intent = new Intent(MainActivity.this, activiyClass);
        startActivity(intent);
    }
}