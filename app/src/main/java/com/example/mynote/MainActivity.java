package com.example.mynote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NoteClickListener {

    List<NoteModel> noteModels = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView noteList = findViewById(R.id.noteList);
        noteModels.add(new NoteModel("1", "Informação de teste 1", "06 Junho 14:00"));
        noteModels.add(new NoteModel("2", "Informação de teste 2", "06 Junho 14:05"));
        noteModels.add(new NoteModel("3", "Informação de teste 3", "06 Junho 14:10"));

        NoteItemsRecyclerViews noteItemsRecyclerViews = new NoteItemsRecyclerViews(noteModels,MainActivity.this);
        noteList.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        noteList.setAdapter(noteItemsRecyclerViews);
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