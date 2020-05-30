package com.ivan.mynote.Data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ivan.mynote.Record.Record;

import java.util.List;

@Dao
public interface RecordDAO {
    @Query("select * from record")
    List<Record> getAllRecord();

    @Insert
    public long insertRecord (Record record);

    @Update
    public void updateRecord (Record record);

    @Delete
    public void deleteRecord (Record record);

    @Query("select * from record where recordId ==:id")
    public Record getRecord (long id);
}
