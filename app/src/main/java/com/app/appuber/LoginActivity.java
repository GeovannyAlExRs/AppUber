package com.app.appuber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText in_txt_email, in_txt_password;
    private AppCompatButton ac_btn_enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getViewId();
    }

    private void getViewId() {
        in_txt_email = findViewById(R.id.id_btn_login);
        in_txt_password = findViewById(R.id.id_btn_register);
        ac_btn_enter = findViewById(R.id.id_btn_enter);

        ac_btn_enter.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_btn_enter:
                goLogin();
                Toast.makeText(this, "Proximamente...", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void goLogin() {
        String email = in_txt_email.getText().toString();
        String password = in_txt_password.getText().toString();

        if(email.isEmpty() && password.isEmpty()) {
            if (password.length() > 8){

            }
        }
    }

    public void goToView(Class activiyClass) {
        Intent intent = new Intent(this, activiyClass);
        startActivity(intent);
    }
}