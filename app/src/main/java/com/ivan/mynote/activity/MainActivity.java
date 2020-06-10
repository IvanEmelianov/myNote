package com.ivan.mynote.activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ivan.mynote.adapter.NoteAdapter;
import com.ivan.mynote.data.RecordAddDataBase;
import com.ivan.mynote.R;
import com.ivan.mynote.entity.Record;

import java.util.List;

import javax.security.auth.callback.Callback;

public class MainActivity extends AppCompatActivity{
    private ImageButton btnNote;
    private RecyclerView recyclerView;
    private NoteAdapter rvAdapter;
    private TextView tvNote;
    private List<Record> records;
    private RecordAddDataBase recordAddDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recordAddDataBase = Room.databaseBuilder(getApplicationContext(), RecordAddDataBase.class, "RecordDB").allowMainThreadQueries().build();

        tvNote = findViewById(R.id.tvNote);
        btnNote = findViewById(R.id.btnNote);
        recyclerView = findViewById(R.id.recyclerView);


        btnNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNotes();
            }
        });

        AllNotesAsyncTask allNotesAsyncTask = new AllNotesAsyncTask();
        allNotesAsyncTask.execute();


        returnActivity();
    }

    private void initRecyclerView (){
        rvAdapter = new NoteAdapter(records);
        recyclerView.setAdapter(rvAdapter);
        tvNote.setText("Номер " + String.valueOf(rvAdapter.getItemCount()));
    }
    //TODO - изменить название функции
    private void dataBase(){
        //TODO - экземпляр бд создаем в onCreate
        recordAddDataBase = Room.databaseBuilder(getApplicationContext(), RecordAddDataBase.class, "RecordDB").allowMainThreadQueries().build();
        records = recordAddDataBase.RecordDAO().getAllRecord();
    }

    public void openNotes(){
        Intent transitionNotes = new Intent(MainActivity.this, NotesActivity.class);
        startActivity(transitionNotes);
    }

    private void returnActivity(){
        ActionBar returnActivity = this.getSupportActionBar();
        if (returnActivity != null){
            returnActivity.setDisplayHomeAsUpEnabled(true);
        }
    }
    private class AllNotesAsyncTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            records = recordAddDataBase.RecordDAO().getAllRecord();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            initRecyclerView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataBase();
        initRecyclerView();
        //TODO - есть в ней смысл? Не имеет смысла, из-за onPostExecute (возможно из-за этого)
    }
}
