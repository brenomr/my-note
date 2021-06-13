package com.example.mynote;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class CreateNoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        FloatingActionButton floatingActionButton = findViewById(R.id.save);
        final EditText editTextArea = findViewById(R.id.editTextArea);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNotes(editTextArea.getText().toString());
            }
        });
    }

    private void saveNotes(String note) {
        /* Sou um horário formatado! */
        Date agora = new Date();
        SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        fmt.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        String data = fmt.format(agora);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users").child(StaticUtils.getUsername(CreateNoteActivity.this)).child("noteModelList");
        String id = databaseReference.push().getKey();
        NoteModel noteModel = new NoteModel(id, note, data);
        databaseReference.child(id).setValue(noteModel);
        startActivity(new Intent(CreateNoteActivity.this, MainActivity.class));
    }
}