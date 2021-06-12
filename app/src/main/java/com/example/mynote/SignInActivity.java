package com.example.mynote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {

    DatabaseReference userDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        userDatabase = FirebaseDatabase.getInstance().getReference("users");

        final EditText usernameEditText = findViewById(R.id.edtCadUsuario);
        final EditText passwordEditText = findViewById(R.id.edtCadSenha);
        final Button registerButton = findViewById(R.id.btnCadastrar);

        registerButton.setOnClickListener((v) -> {
            if(usernameEditText.getText().toString().isEmpty()){
                usernameEditText.setError("Informe um nome de usuário.");
                usernameEditText.requestFocus();
                return;
            }
            if(passwordEditText.getText().toString().isEmpty()){
                passwordEditText.setError("Informe uma senha.");
                passwordEditText.requestFocus();
                return;
            }
            CreateNewUser(usernameEditText.getText().toString(), passwordEditText.getText().toString());
        });
    }

    private void CreateNewUser(String username, String password) {
        userDatabase.child(username).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(!dataSnapshot.exists()) {
                    String id = userDatabase.push().getKey();
                    UserModel userModel = new UserModel(id, username, password);
                    userDatabase.child(username).setValue(userModel);
                    finish();
                }
                else {
                    Toast.makeText(SignInActivity.this, "Nome de usuário já está em uso.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(SignInActivity.this,"Erro ao acessar o banco.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}