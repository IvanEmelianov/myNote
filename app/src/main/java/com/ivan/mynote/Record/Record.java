package com.ivan.mynote.Record;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "record")
public class Record extends Throwable {
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "recordId")
    private int recordId;

    @ColumnInfo (name = "title")
    private String title;

    @ColumnInfo (name = "text")
    private String text;

    public Record(String title, String text){
       this.title = title;
       this.text = text;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public int getRecordId() {
        return recordId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }
}
