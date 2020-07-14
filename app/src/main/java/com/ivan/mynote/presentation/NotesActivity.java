package com.ivan.mynote.presentation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.ivan.mynote.data.RecordAddDataBase;
import com.ivan.mynote.R;
import com.ivan.mynote.entity.Record;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesActivity extends AppCompatActivity {

    private RecordAddDataBase recordAddDataBase;
    private ImageButton delete;
    private ImageButton marker;
    private ImageButton update;
    private EditText edTitle;
    private EditText edText;
    private TextView tvDate;
    private boolean isUpdate = false;
    private int id = -1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        edTitle = findViewById(R.id.edTitle);
        edText = findViewById(R.id.edText);
        tvDate = findViewById(R.id.edDate);
        /*delete = findViewById(R.id.btnDelete);*/

        recordAddDataBase = Room.databaseBuilder(getApplicationContext(), RecordAddDataBase.class, "RecordDB").allowMainThreadQueries().build();

        getData();

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = simpleDateFormat.format(date);
        tvDate.setText(formattedDate);

        /*delete.setOnClickListener(v -> deleteMember());*/
    }


    public static void open(MainActivity activity, String title, String text, String date, int id){
        Intent intent = new Intent(activity, NotesActivity.class);
        intent.putExtra("title", title);
        intent.putExtra("text", text);
        intent.putExtra("date", date);
        intent.putExtra("id", id);
        activity.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.save_member:
                if (isUpdate){
                    updateMember();
                }else {
                    saveMember();
                }
                break;

        }
        return super.onOptionsItemSelected(item);
    }



    private void updateMember() {
        Record record = recordAddDataBase.RecordDAO().getRecord(id);
        record.setTitle(edTitle.getText().toString());
        record.setText(edText.getText().toString());
        record.setDate(tvDate.getText().toString());
        recordAddDataBase.RecordDAO().updateRecord(record);
        this.finish();
    }

    private void deleteMember(){
        Record record = recordAddDataBase.RecordDAO().getRecord(id);
        record.setTitle(edTitle.getText().toString());
        record.setText(edText.getText().toString());
        record.setDate(tvDate.getText().toString());
        recordAddDataBase.RecordDAO().deleteRecord(record);
        this.finish();
    }

    private void saveMember() {
        Record record = new Record(edTitle.getText().toString(), edText.getText().toString(), tvDate.getText().toString());
        recordAddDataBase.RecordDAO().insertRecord(record);
        Log.d("tag", String.valueOf(recordAddDataBase));
        this.finish();

    }
    public void getData(){
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("title")){
            String title = intent.getStringExtra("title");
            String text = intent.getStringExtra("text");
            String date = intent.getStringExtra("date");
            id = intent.getIntExtra("id",-1);
            Log.d("tag", "Record = " + title);
            edTitle.setText(title);
            edText.setText(text);
            tvDate.setText(date);
            isUpdate = true;
        }
    }
}
