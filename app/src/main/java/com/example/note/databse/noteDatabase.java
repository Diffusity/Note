package com.example.note.databse;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.note.DAO.noteDAO;
import com.example.note.entities.note;

@Database(entities = note.class,version = 1,exportSchema = false)
public abstract class noteDatabase extends RoomDatabase {

    private static noteDatabase noteDatabase;

    public static synchronized noteDatabase getDatabase(Context context){
        if(noteDatabase==null){
            noteDatabase= Room.databaseBuilder(
                    context,
                    noteDatabase.class,"notes_DB")
                    .build();
        }
        return noteDatabase;
    }

    public abstract noteDAO noteDAO();
}
