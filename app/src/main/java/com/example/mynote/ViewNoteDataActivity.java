package com.example.mynote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ViewNoteDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_note_data);

        String id = getIntent().getStringExtra(Constants.id);
        String notetext = getIntent().getStringExtra(Constants.note_text);
        String create_time = getIntent().getStringExtra(Constants.create_time);

        TextView note_data = findViewById(R.id.note_data);
        TextView note_time = findViewById(R.id.create_time);

        note_data.setText(notetext);
        note_time.setText(create_time);
    }
}