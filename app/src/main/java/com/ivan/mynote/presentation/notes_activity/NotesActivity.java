package com.ivan.mynote.presentation.notes_activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.room.Room;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ivan.mynote.data.RecordAddDataBase;
import com.ivan.mynote.R;
import com.ivan.mynote.entity.Record;
import com.ivan.mynote.presentation.MainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesActivity extends AppCompatActivity implements NotesView {
    final int CAMERA_PIC_REQUEST = 1;
    final int ACTIVITY_SELECT_IMAGE = 123;
    final String AUTHORITY = "com.ivan.mynote.fileprovider";
    private Uri outputFileUri;
    private RecordAddDataBase recordAddDataBase;
    private ImageButton delete;
    private ImageButton gallery;
    private ImageButton camera;
    private ImageButton marker;
    private ImageButton update;
    private EditText edTitle;
    private EditText edText;
    private TextView tvDate;
    private ImageView ivPhoto;
    private boolean isUpdate = false;
    private int id = -1;



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
        ivPhoto = findViewById(R.id.ivPhoto);

        recordAddDataBase = Room.databaseBuilder(getApplicationContext(), RecordAddDataBase.class, "RecordDB").allowMainThreadQueries().build();

        getData();

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = simpleDateFormat.format(date);
        tvDate.setText(formattedDate);

        gallery.setOnClickListener(v -> openGallery());

        camera.setOnClickListener(v -> verifyPermissions());

        delete.setOnClickListener(v -> deleteMember());
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

    private void saveMember() {
        Record record = new Record(edTitle.getText().toString(), edText.getText().toString(), tvDate.getText().toString());
        recordAddDataBase.RecordDAO().insertRecord(record);
        this.finish();
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

    private void openGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        intent.setType("image/*");
        startActivityForResult(intent, ACTIVITY_SELECT_IMAGE);
    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, CAMERA_PIC_REQUEST);
        }else{
            Toast.makeText(this, "Error open camera", Toast.LENGTH_SHORT).show();
        }
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        Bitmap bitmap = null;

        switch (requestCode) {
            case ACTIVITY_SELECT_IMAGE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = intent.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        Toast.makeText(this, "Error bitmap", Toast.LENGTH_SHORT).show();
                    }
                    ivPhoto.setImageBitmap(bitmap);
                }
        }
        switch (requestCode){
            case CAMERA_PIC_REQUEST:
                //if (requestCode == Activity.RESULT_OK){
                    bitmap = (Bitmap) intent.getExtras().get("data");
                    if (bitmap != null){
                        ivPhoto.setImageBitmap(bitmap);
                    }
                }
    }
}
