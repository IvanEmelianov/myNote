package com.ivan.mynote.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ivan.mynote.data.RecordAddDataBase;
import com.ivan.mynote.R;
import com.ivan.mynote.entity.Record;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NotesActivity extends AppCompatActivity {

    private ImageButton delete;
    private ImageButton gallery;
    private ImageButton camera;
    private ImageButton marker;
    private ImageButton update;
    private EditText edTitle;
    private EditText edText;
    private TextView tvDate;
    private Callback callback;
    private RecordAddDataBase recordAddDataBase;

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
        update = findViewById(R.id.btnUpdateNote);
        tvDate = findViewById(R.id.edDate);

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = simpleDateFormat.format(date);
        tvDate.setText(String.valueOf(formattedDate));

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCamera();
            }
        });
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
                saveMember();
                break;

        }
        return super.onOptionsItemSelected(item);
    }

    private void saveMember() {
        Record record = new Record(edTitle.getText().toString(), edText.getText().toString(), tvDate.getText().toString());
        recordAddDataBase.RecordDAO().insertRecord(record);
        this.finish();
    }

    private void openGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        final int ACTIVITY_SELECT_IMAGE = 123;
        startActivityForResult(intent, ACTIVITY_SELECT_IMAGE);
    }

    private void openCamera(){
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final int CAMERA_PIC_REQUEST = 1234;
        startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
    }
}
