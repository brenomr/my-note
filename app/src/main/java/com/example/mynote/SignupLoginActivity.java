package com.example.mynote;

import androidx.annotation.NonNull;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class SignupLoginActivity extends AppCompatActivity {

    DatabaseReference userDatabase;
    private ProgressBar loadingProgressBar;
    String idioma = "pt";

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        mudarIdioma();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login);
        getSupportActionBar().hide();
        userDatabase = FirebaseDatabase.getInstance().getReference("users");

        final EditText usernameEditText = findViewById(R.id.edtUsername);
        final EditText passwordEditText = findViewById(R.id.edtPassword);
        final Button loginButton = findViewById(R.id.btnAcessar);
        loadingProgressBar = findViewById(R.id.loading);

        checkAlreadyLoggedIn();

        loginButton.setOnClickListener((v) -> {
            if(usernameEditText.getText().toString().isEmpty()){
                usernameEditText.setError("Informe seu e-mail.");
                usernameEditText.requestFocus();
                return;
            }
            if(passwordEditText.getText().toString().isEmpty()){
                passwordEditText.setError("Informe sua senha.");
                passwordEditText.requestFocus();
                return;
            }
                loadingProgressBar.setVisibility(View.VISIBLE);
                ProcessLogin(usernameEditText.getText().toString(), passwordEditText.getText().toString());
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void mudarIdioma() {

        SharedPreferences dados = getSharedPreferences("MyPref", MODE_PRIVATE);
        idioma = dados.getString("idioma", "pt");

        Locale lang = new Locale(idioma);
        Locale.setDefault(lang);

        Context context = this;
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());

        config.setLocale(lang);
        res.updateConfiguration(config, res.getDisplayMetrics());
    }

    private void checkAlreadyLoggedIn() {
        if(StaticUtils.getUserEmail(SignupLoginActivity.this) != null) {
            startActivity(new Intent(SignupLoginActivity.this, MainActivity.class));
            finish();
        }
    }

    private void ProcessLogin(final String email, final String password) {
        userDatabase.child(email).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String username1 = dataSnapshot.child("email").getValue(String.class);
                    String password1 = dataSnapshot.child("password").getValue(String.class);
                    String created = dataSnapshot.child("created_at").getValue(String.class);
                    if(password1.equalsIgnoreCase(password)) {
                        loadingProgressBar.setVisibility(View.GONE);
                        StaticUtils.StoreLoggedEmail(SignupLoginActivity.this, email);
                        startActivity(new Intent(SignupLoginActivity.this, MainActivity.class));
                        finish();
                    }
                    else {
                        loadingProgressBar.setVisibility(View.GONE);
                        Toast.makeText(SignupLoginActivity.this,"Email ou senha inválidos.", Toast.LENGTH_SHORT).show();
                    }
                }

                else {
                    loadingProgressBar.setVisibility(View.GONE);
                    Toast.makeText(SignupLoginActivity.this,"Usuário não existe, crie uma conta.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void CreateNewUser(View v) {
        startActivity(new Intent(SignupLoginActivity.this, SignInActivity.class));
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void idiomaPT(View v) {
        idioma = "pt";

        Locale lang = new Locale(idioma);
        Locale.setDefault(lang);

        Context context = SignupLoginActivity.this;
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());

        config.setLocale(lang);

        res.updateConfiguration(config, res.getDisplayMetrics());

        SharedPreferences.Editor dados = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
        dados.putString("idioma", idioma);
        dados.apply();
        recreate();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void idiomaEN(View v) {
        idioma = "en";

        Locale lang = new Locale(idioma);
        Locale.setDefault(lang);

        Context context = SignupLoginActivity.this;
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());

        config.setLocale(lang);
        res.updateConfiguration(config, res.getDisplayMetrics());

        SharedPreferences.Editor dados = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
        dados.putString("idioma", idioma);
        dados.apply();
        recreate();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void idiomaES(View v) {
        idioma = "es";

        Locale lang = new Locale(idioma);
        Locale.setDefault(lang);

        Context context = SignupLoginActivity.this;
        Resources res = context.getResources();
        Configuration config = new Configuration(res.getConfiguration());

        config.setLocale(lang);
        res.updateConfiguration(config, res.getDisplayMetrics());

        SharedPreferences.Editor dados = getSharedPreferences("MyPref", MODE_PRIVATE).edit();
        dados.putString("idioma", idioma);
        dados.commit();
        recreate();
    }
}