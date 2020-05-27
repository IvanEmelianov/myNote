package com.ivan.mynote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ivan.mynote.Adapter.Note;
import com.ivan.mynote.Adapter.NoteAdapter;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    TextView tvNote;
    ImageButton btnNote;

    RecyclerView recyclerView;
    NoteAdapter rvAdapter;
    LinearLayoutManager rvlManager;

    ArrayList<Note> mNote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvNote = findViewById(R.id.tvNote);
        btnNote = findViewById(R.id.btnNote);

        noteList();

        initRecyclerView();

        btnNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNotes();
            }
        });

        recyclerView.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Номер ", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void initRecyclerView (){
        recyclerView = findViewById(R.id.recyclerView);
        rvAdapter = new NoteAdapter(mNote);
        rvlManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(rvAdapter);
        recyclerView.setLayoutManager(rvlManager);

        recyclerView.setOnClickListener(new AdapterView.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Номер ", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void openNotes(){
        Intent transitionNotes = new Intent(MainActivity.this, Notes.class);
        startActivity(transitionNotes);
    }

    public void noteList(){
        mNote = new ArrayList<>();
        mNote.add(new Note("Заголовок", "Пока пусто..."));

    }
}
