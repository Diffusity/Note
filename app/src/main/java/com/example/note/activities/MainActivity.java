package com.example.note.activities;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.example.note.R;
import com.example.note.adapters.notesadapter;
import com.example.note.databse.noteDatabase;
import com.example.note.entities.note;
import com.example.note.listeners.noteListener;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements noteListener {

    public static final int REQUEST_CODE_ADD_NOTE=1;
    public static final int REQUEST_CODE_UPDATE_NOTE=2;
    public static final int REQUEST_CODE_SHOW_NOTE=3;
    ExtendedFloatingActionButton btn;
    RecyclerView recyclerView;
    List<note> note;
    notesadapter adapter;
    int noteClickedposition=-1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        setContentView(R.layout.activity_main);
        btn=findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(

                        new Intent(getApplicationContext(),MainActivity2.class),
                        REQUEST_CODE_ADD_NOTE
                );
            }
        });
        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL)
        );
        note=new ArrayList<>();
        adapter=new notesadapter(note,this);
        recyclerView.setAdapter(adapter);
        getNotes(REQUEST_CODE_SHOW_NOTE,false);
        EditText search=findViewById(R.id.searchText);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.cancelTimer();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(note.size()!=0){
                    adapter.searchNote(s.toString());
                }
            }
        });
    }

    private void getNotes(final int requestCode,final Boolean isDeleted) {

        @SuppressLint("StaticFieldLeak")
        class GetNoteTask extends AsyncTask<Void,Void, List<note>>{

            @Override
            protected List<note> doInBackground(Void... voids) {
                return noteDatabase.getDatabase(getApplicationContext()).noteDAO().getAllNotes();
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void onPostExecute(List<note> notes) {
                super.onPostExecute(notes);
                if(requestCode==REQUEST_CODE_SHOW_NOTE){
                    note.addAll(notes);
                    adapter.notifyDataSetChanged();
                } else if (requestCode==REQUEST_CODE_ADD_NOTE) {
                    note.add(0,notes.get(0));
                    adapter.notifyItemInserted(0);
                    recyclerView.smoothScrollToPosition(0);
                    
                } else if (requestCode == REQUEST_CODE_UPDATE_NOTE) {
                    note.remove(noteClickedposition);
                    if(isDeleted){
                        adapter.notifyItemRemoved(noteClickedposition);
                    }else {
                        note.add(noteClickedposition,notes.get(noteClickedposition));
                        adapter.notifyItemChanged(noteClickedposition);
                    }
                }
            }
        }

        new GetNoteTask().execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CODE_ADD_NOTE&&resultCode==RESULT_OK){
            getNotes(REQUEST_CODE_ADD_NOTE,false);
        } else if (requestCode==REQUEST_CODE_UPDATE_NOTE&&resultCode==RESULT_OK) {
            if(data!=null){
                getNotes(REQUEST_CODE_UPDATE_NOTE,data.getBooleanExtra("isdeleted",false));
            }
        }
    }

    @Override
    public void onNoteClicked(com.example.note.entities.note note, int position) {
        noteClickedposition=position;
        Intent intent=new Intent(MainActivity.this, MainActivity2.class);
        intent.putExtra("isVieworUpdate",true);
        intent.putExtra("note",note);
        startActivityForResult(intent,REQUEST_CODE_UPDATE_NOTE);
    }
}