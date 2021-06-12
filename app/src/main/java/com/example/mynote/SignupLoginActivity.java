package com.example.mynote;

import android.app.Activity;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mynote.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SignupLoginActivity extends AppCompatActivity {

    DatabaseReference userDatabase;
    private ProgressBar loadingProgressBar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_login);
        getSupportActionBar().hide();
        userDatabase = FirebaseDatabase.getInstance().getReference("users");

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
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
}