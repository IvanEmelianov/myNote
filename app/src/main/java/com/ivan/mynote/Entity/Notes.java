package com.ivan.mynote.Entity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;

import com.ivan.mynote.Data.RecordAddDataBase;
import com.ivan.mynote.R;
import com.ivan.mynote.Record.Record;

public class Notes extends AppCompatActivity {
    EditText edTitle;
    EditText edText;
    ImageButton delete;
    ImageButton gallery;
    ImageButton camera;
    ImageButton marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        edTitle = findViewById(R.id.edTitle);
        edText = findViewById(R.id.edText);
        delete = findViewById(R.id.btnDelete);
        gallery = findViewById(R.id.btnGallery);
        camera = findViewById(R.id.btnCamera);
        marker = findViewById(R.id.btnMarker);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        saveMember();
        return super.onOptionsItemSelected(item);
    }

    private void saveMember() {
        final RecordAddDataBase recordAddDataBase = Room.databaseBuilder(getApplicationContext(), RecordAddDataBase.class, "RecordDB").allowMainThreadQueries().build();
        Record record = new Record(edTitle.getText().toString(), edText.getText().toString());
        Log.d("tagin", "eeee " + edText.getText().toString());
        recordAddDataBase.RecordDAO().insertRecord(record);
        startActivity(new Intent(Notes.this, MainActivity.class));
    }

}
