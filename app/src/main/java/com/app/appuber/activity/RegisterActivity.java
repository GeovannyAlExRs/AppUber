package com.app.appuber.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.app.appuber.R;
import com.app.appuber.model.Clients;
import com.app.appuber.providers.AuthFirebaseProvider;
import com.app.appuber.providers.ClientDatabaseProvider;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputEditText in_txt_namefirts, in_txt_namelast, in_txt_email, in_txt_password;
    private AppCompatButton ac_btn_enter;
    private ProgressBar progressBar;

    AuthFirebaseProvider authFirebaseProvider;
    ClientDatabaseProvider clientDatabaseProvider;

    //SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /*sharedPref = getSharedPreferences("typeUser", MODE_PRIVATE);
        String typeUser = sharedPref.getString("user", "");
        Toast.makeText(this, "Selecciono: " + typeUser, Toast.LENGTH_SHORT).show();*/

        getViewId();

        authFirebaseProvider = new AuthFirebaseProvider();
        clientDatabaseProvider = new ClientDatabaseProvider();
    }

    private void getViewId() {
        in_txt_namefirts = findViewById(R.id.id_txt_namefirts);
        in_txt_namelast = findViewById(R.id.id_txt_namelast);
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
                goRegister();
                break;
        }
    }

    private void goRegister() {

        final String namefirts = in_txt_namefirts.getText().toString();
        final String namelast = in_txt_namelast.getText().toString();
        final String email = in_txt_email.getText().toString();
        final String password = in_txt_password.getText().toString();

        if(!namefirts.isEmpty() && !namelast.isEmpty() && !password.isEmpty() && !email.isEmpty()) {
            if (password.length() >= 8){
                progressBar.setVisibility(View.VISIBLE);

                saveUser(namefirts, namelast, email, password);

            } else {
                Toast.makeText(RegisterActivity.this, "Clave debe tener mas de 8 caracteres", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(RegisterActivity.this, "Ingrese todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveUser(final String namefirts, final String namelast, final String email, final String password) {
        authFirebaseProvider.register(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(RegisterActivity.this, "El usuario se registro correctamente", Toast.LENGTH_SHORT).show();
                    String id = FirebaseAuth.getInstance().getCurrentUser().getUid();

                    Clients clients = new Clients(id, namefirts, namelast, email, password);
                    createClient(clients);
                }else {
                    Toast.makeText(RegisterActivity.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void createClient(Clients clients) {
        clientDatabaseProvider.create(clients).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Conductor registrado correctamente", Toast.LENGTH_SHORT).show();
                    goToView(MapClientActivity.class);
                }else {
                    Toast.makeText(RegisterActivity.this, "Error al registrar el Conductor", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void goToView(Class activiyClass) {
        Intent intent = new Intent(RegisterActivity.this, activiyClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}