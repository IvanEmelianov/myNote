package com.ivan.mynote.presentation.notes_activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.room.Room;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.ivan.mynote.data.RecordAddDataBase;
import com.ivan.mynote.R;
import com.ivan.mynote.entity.Record;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesActivity extends AppCompatActivity implements NotesView {
    final int CAMERA_PIC_REQUEST = 1;
    RecordAddDataBase recordAddDataBase;
    ImageButton delete;
    ImageButton gallery;
    ImageButton camera;
    ImageButton marker;
    ImageButton update;
    EditText edTitle;
    EditText edText;
    TextView tvDate;


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
        recordAddDataBase = Room.databaseBuilder(getApplicationContext(), RecordAddDataBase.class, "RecordDB").allowMainThreadQueries().build();

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = simpleDateFormat.format(date);
        tvDate.setText(formattedDate);

        gallery.setOnClickListener(v -> openGallery());

        camera.setOnClickListener(v -> verifyPermissions());
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

    private void openCamera() {
        Intent openCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(openCamera, CAMERA_PIC_REQUEST);
     }

    private void verifyPermissions(){
        Log.d("permission", "verifyPermissions");

        int permissionStatus = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);

        if (permissionStatus == PackageManager.PERMISSION_GRANTED){
            openCamera();
        }else{
            ActivityCompat.requestPermissions(NotesActivity.this,
                    new String[] {Manifest.permission.CAMERA},
                    CAMERA_PIC_REQUEST);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case CAMERA_PIC_REQUEST:
                if (/*grantResults.length > 0
                &&*/ grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    openCamera();
                } else {
                    Toast.makeText(this, "No permission open camera", Toast.LENGTH_SHORT).show();
                }
        return;
        }
    }
}
