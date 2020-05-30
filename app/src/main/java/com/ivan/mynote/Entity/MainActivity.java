package com.ivan.mynote.Entity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ivan.mynote.Adapter.NoteAdapter;
import com.ivan.mynote.Data.RecordAddDataBase;
import com.ivan.mynote.R;
import com.ivan.mynote.Record.Record;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageButton btnNote;
    private RecyclerView recyclerView;
    private NoteAdapter rvAdapter;
    List<Record> records;
    private RecordAddDataBase recordAddDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNote = findViewById(R.id.btnNote);
        btnNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNotes();
            }
        });
        dataBase();

        returnActivity();

        initRecyclerView();
    }

    private void initRecyclerView (){
        recyclerView = findViewById(R.id.recyclerView);
        rvAdapter = new NoteAdapter(records);
        recyclerView.setAdapter(rvAdapter);

    }
    private void dataBase(){
        recordAddDataBase = Room.databaseBuilder(getApplicationContext(), RecordAddDataBase.class, "RecordDB").allowMainThreadQueries().build();
        records = recordAddDataBase.RecordDAO().getAllRecord();
    }

    public void openNotes(){
        Intent transitionNotes = new Intent(MainActivity.this, Notes.class);
        startActivity(transitionNotes);
    }

    private void returnActivity(){
        ActionBar returnActivity = this.getSupportActionBar();
        if (returnActivity != null){
            returnActivity.setDisplayHomeAsUpEnabled(true);
        }
    }


}
