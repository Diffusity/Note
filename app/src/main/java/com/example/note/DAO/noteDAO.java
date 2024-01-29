package com.example.note.DAO;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Ignore;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.note.entities.note;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface noteDAO {

    @Query(value = "SELECT * FROM Notes ORDER BY id DESC")
    List<note> getAllNotes();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNote(note note);

    @Delete
    void deleteNote(note note);
}
