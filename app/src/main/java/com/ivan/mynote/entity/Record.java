package com.ivan.mynote.entity;

import android.media.Image;
import android.provider.MediaStore;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "record")
public class Record implements Serializable {
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "recordId")
    private int recordId;

    @ColumnInfo (name = "title")
    private String title;

    @ColumnInfo (name = "text")
    private String text;

    @ColumnInfo (name = "date")
    private String date;

    /*@ColumnInfo (name = "photo")
    private Image photo;*/

    public Record(String title, String text, String date){
       this.title = title;
       this.text = text;
       this.date = date;
       /*this.photo = photo;*/
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }


    public String getDate() {
        return date;
    }

  /*  public Image getPhoto() {
        return photo;
    }*/

    public int getRecordId() {
        return recordId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setDate(String date) {
        this.date = date;
    }

    /*public void setPhoto(Image photo) {
        this.photo = photo;
    }*/

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }
}
