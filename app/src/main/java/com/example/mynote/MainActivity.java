package com.example.mynote;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteClickListener {

    List<NoteModel> noteModels = new ArrayList<>();

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem logout) {
        StaticUtils.logout(MainActivity.this);
        Intent tela = new Intent(this, SignupLoginActivity.class);
        startActivity(tela);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final RecyclerView noteList = findViewById(R.id.noteList);

        FloatingActionButton create = findViewById(R.id.create);
        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, CreateNoteActivity.class));
            }
        });

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users");

        databaseReference.child(StaticUtils.getUsername(MainActivity.this)).child("noteModelList").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1: dataSnapshot.getChildren()) {
                    String id = dataSnapshot1.child("id").getValue(String.class);
                    String note = dataSnapshot1.child("note_data").getValue(String.class);
                    String create = dataSnapshot1.child("created_at").getValue(String.class);
                    noteModels.add(new NoteModel(id, note, create));
                }
                    NoteItemsRecyclerViews noteItemsRecyclerViews = new NoteItemsRecyclerViews(noteModels, MainActivity.this);
                    noteList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    noteList.setAdapter(noteItemsRecyclerViews);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this,"Erro ao acessar o banco.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClickItem(NoteModel noteModel) {
        Intent intent = new Intent(MainActivity.this, ViewNoteDataActivity.class);
        intent.putExtra(Constants.id, noteModel.getId());
        intent.putExtra(Constants.note_text, noteModel.getNote_data());
        intent.putExtra(Constants.create_time, noteModel.getCreated_at());
        startActivity(intent);
    }
}