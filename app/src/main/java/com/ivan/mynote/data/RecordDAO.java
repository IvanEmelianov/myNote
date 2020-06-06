package com.ivan.mynote.data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.ivan.mynote.entity.Record;

import java.util.List;

@Dao
public interface RecordDAO {

    @Query("select * from Record")
    List<Record> getAllRecord();

    @Insert
    public long insertRecord (Record record);

    @Update
    public void updateRecord (Record record);

    @Delete
    public void deleteRecord (Record record);

    @Query("select * from Record where recordId ==:id")
    public Record getRecord (long id);
}
