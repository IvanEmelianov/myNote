package com.ivan.mynote.Data;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.ivan.mynote.Record.Record;

@Database(entities = {Record.class}, version = 1)
public abstract class RecordAddDataBase extends RoomDatabase {
    public abstract RecordDAO RecordDAO();
}
