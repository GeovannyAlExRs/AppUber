package com.app.appuber.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.appuber.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText in_txt_email, in_txt_password;
    private AppCompatButton ac_btn_enter;
    private ProgressBar progressBar;

    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    AlertDialog alertDialog;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sharedPref = getSharedPreferences("typeUser", MODE_PRIVATE);

        getViewId();

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void getViewId() {
        in_txt_email = findViewById(R.id.id_txt_email);
        in_txt_password = findViewById(R.id.id_txt_password);

        ac_btn_enter = findViewById(R.id.id_btn_enter);
        ac_btn_enter.setOnClickListener(this);

        progressBar = findViewById(R.id.id_spinkit_progress);
        Sprite doubleBounce = new DoubleBounce();

        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.id_btn_enter:
                goLogin();
                break;
        }
    }

    private void goLogin() {

        String email = in_txt_email.getText().toString();
        String password = in_txt_password.getText().toString();

        if(!email.isEmpty() && !password.isEmpty()) {
            if (password.length() >= 8){
                progressBar.setVisibility(View.VISIBLE);

                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            String typeUser = sharedPref.getString("user", "");
                            Toast.makeText(LoginActivity.this, "Selecciono: " + typeUser + ", INGRESO CORRECTO", Toast.LENGTH_SHORT).show();
                            if (typeUser.equals("client")) {
                                goToView(MapClientActivity.class);
                            } else if (typeUser.equals("driver")) {
                                goToView(MapDriverActivity.class);
                            }
                        }else {
                            Toast.makeText(LoginActivity.this, "Email o clave son incorrectos", Toast.LENGTH_SHORT).show();
                        }
                        progressBar.setVisibility(View.INVISIBLE);
                    }
                });
            } else {
                Toast.makeText(LoginActivity.this, "Clave debe tener mas de 8 caracteres", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(LoginActivity.this, "Email y clave son Obligatorios", Toast.LENGTH_SHORT).show();
        }
    }
    // Video 10 Cerrar Sesion 05:16
    public void goToView(Class activiyClass) {
        Intent intent = new Intent(this, activiyClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}