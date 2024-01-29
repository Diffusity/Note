package com.example.note.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.PictureDrawable;
import android.graphics.drawable.VectorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.note.R;
import com.example.note.databse.noteDatabase;
import com.example.note.entities.note;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity2 extends AppCompatActivity {

    ImageView backbtn,savebtn;
    EditText title,subtitle,notetext;
    TextView dateandtime;
    String date;
    String selectedcolor;
    View view;
    private note alreadyavailablenotes;
    AlertDialog delete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        backbtn=findViewById(R.id.backBtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        savebtn=findViewById(R.id.save);
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
        if(isDarkTheme()){
            Drawable drawable=savebtn.getDrawable();
            if(drawable instanceof VectorDrawable){
                VectorDrawable vectorDrawable=(VectorDrawable) drawable;
                vectorDrawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN));
                savebtn.setImageDrawable(vectorDrawable);
            }
            Drawable drawable1=backbtn.getDrawable();
            if(drawable1 instanceof VectorDrawable){
                VectorDrawable vectorDrawable=(VectorDrawable) drawable1;
                vectorDrawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_IN));
                backbtn.setImageDrawable(vectorDrawable);
            }
        }
        else {
            Drawable drawable=savebtn.getDrawable();
            Drawable drawable1=backbtn.getDrawable();
            if(drawable instanceof VectorDrawable){
                VectorDrawable vectorDrawable=(VectorDrawable) drawable;
                vectorDrawable.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN));
                savebtn.setImageDrawable(vectorDrawable);
            }
            if(drawable1 instanceof VectorDrawable){
                VectorDrawable vectorDrawable1=(VectorDrawable) drawable1;
                vectorDrawable1.setColorFilter(new PorterDuffColorFilter(getResources().getColor(R.color.black), PorterDuff.Mode.SRC_IN));
                backbtn.setImageDrawable(vectorDrawable1);
            }
        }

        title=findViewById(R.id.title);
        subtitle=findViewById(R.id.subTitle);
        notetext=findViewById(R.id.description);
        dateandtime=findViewById(R.id.date);

        dateandtime.setText(
                 new SimpleDateFormat("EEEE , DD MMMM YYYY HH:mm a", Locale.getDefault())
                        .format(new Date())
        );
        view=findViewById(R.id.view);
        selectedcolor="#d1e5f4";
        if(getIntent().getBooleanExtra("isVieworUpdate",false)){
            alreadyavailablenotes = (note) getIntent().getSerializableExtra("note");
            setVieworUpdate();
        }
        initmis();
        subtitleindicatorcolor();

    }

    private void setVieworUpdate(){
        title.setText(alreadyavailablenotes.getTitle());
        subtitle.setText(alreadyavailablenotes.getSubtitle());
        notetext.setText(alreadyavailablenotes.getNotetext());
        dateandtime.setText(alreadyavailablenotes.getDateandtime());
    }
    private void saveNote(){
        if(title.getText().toString().trim().isEmpty()){
            Toast.makeText(MainActivity2.this,"Title can't be empty...",Toast.LENGTH_SHORT).show();
            return;
        }
        else if(notetext.getText().toString().trim().isEmpty()){
            Toast.makeText(this,"Note can't be empty...",Toast.LENGTH_SHORT).show();
            return;
        }
        final note note=new note();
        note.setTitle(title.getText().toString());
        note.setSubtitle(subtitle.getText().toString());
        note.setNotetext(notetext.getText().toString());
        note.setDateandtime(dateandtime.getText().toString());
        note.setColor(selectedcolor);

        if(alreadyavailablenotes!= null){
            note.setId(alreadyavailablenotes.getId());
        }
        @SuppressLint("StaticFieldLeak")
        class SaveNoteTask extends AsyncTask<Void,Void,Void>{
            @Override
            protected Void doInBackground(Void... voids) {
                noteDatabase.getDatabase(getApplicationContext()).noteDAO().insertNote(note);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Intent intent=new Intent();
                setResult(RESULT_OK,intent);
                finish();
            }
        }

        new SaveNoteTask().execute();
    }

    private void initmis(){
        final LinearLayout layoutmis=findViewById(R.id.miscelleneous);
        final BottomSheetBehavior bottomSheetBehavior=BottomSheetBehavior.from(layoutmis);
        layoutmis.findViewById(R.id.txtmiscellaneous).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bottomSheetBehavior.getState()!=BottomSheetBehavior.STATE_EXPANDED){
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
                }
                else{
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
        });

        final ImageView imgcolor1=layoutmis.findViewById(R.id.imgcolor1);
        final ImageView imgcolor2=layoutmis.findViewById(R.id.imgcolor2);
        final ImageView imgcolor3=layoutmis.findViewById(R.id.imgcolor3);
        final ImageView imgcolor4=layoutmis.findViewById(R.id.imgcolor4);

        layoutmis.findViewById(R.id.notecolor1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedcolor="#f4c06c";
                imgcolor1.setImageResource(R.drawable.baseline_brush_24);
                imgcolor2.setImageResource(0);
                imgcolor3.setImageResource(0);
                imgcolor4.setImageResource(0);
                subtitleindicatorcolor();
            }
        });

        layoutmis.findViewById(R.id.notecolor2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedcolor="#d9e587";
                imgcolor2.setImageResource(R.drawable.baseline_brush_24);
                imgcolor1.setImageResource(0);
                imgcolor3.setImageResource(0);
                imgcolor4.setImageResource(0);
                subtitleindicatorcolor();
            }
        });

        layoutmis.findViewById(R.id.notecolor3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedcolor="#b08bee";
                imgcolor3.setImageResource(R.drawable.baseline_brush_24);
                imgcolor2.setImageResource(0);
                imgcolor1.setImageResource(0);
                imgcolor4.setImageResource(0);
                subtitleindicatorcolor();
            }
        });

        layoutmis.findViewById(R.id.notecolor4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedcolor="#d1e5f4";
                imgcolor4.setImageResource(R.drawable.baseline_brush_24);
                imgcolor2.setImageResource(0);
                imgcolor3.setImageResource(0);
                imgcolor1.setImageResource(0);
                subtitleindicatorcolor();
            }
        });
        if(alreadyavailablenotes!=null && alreadyavailablenotes.getColor()!=null && !alreadyavailablenotes.getColor().trim().isEmpty()){
            switch (alreadyavailablenotes.getColor()){
                case "#d9e587":
                    layoutmis.findViewById(R.id.notecolor2).performClick();
                    break;
                case "#b08bee":
                    layoutmis.findViewById(R.id.notecolor3).performClick();
                    break;
                case "#f4c06c":
                    layoutmis.findViewById(R.id.notecolor1).performClick();
                    break;
            }
        }

        if(alreadyavailablenotes!=null){
            layoutmis.findViewById(R.id.deletenote).setVisibility(View.VISIBLE);
            layoutmis.findViewById(R.id.deletenote).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                    showNoteDelete();
                }
            });
        }
    }

    private void showNoteDelete(){
        if(delete==null){
            AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity2.this);
            View view1= LayoutInflater.from(this).inflate(R.layout.deletenotedialog,(ViewGroup) findViewById(R.id.deletenotedialog));
            builder.setView(view1);
            delete=builder.create();
            if(delete.getWindow()!=null){
                delete.getWindow().setBackgroundDrawable(new ColorDrawable(0));
            }
            view1.findViewById(R.id.deletenote).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    class DeleteNoteTask extends AsyncTask<Void,Void,Void>{

                        @Override
                        protected Void doInBackground(Void... voids) {
                            noteDatabase.getDatabase(getApplicationContext()).noteDAO().deleteNote(alreadyavailablenotes);
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void unused) {
                            super.onPostExecute(unused);
                            Intent intent=new Intent();
                            intent.putExtra("isdeleted",true);
                            setResult(RESULT_OK,intent);
                            finish();
                        }
                    }
                    new DeleteNoteTask().execute();
                }
            });
            view1.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delete.dismiss();
                }
            });
        }
        delete.show();
    }

    private void subtitleindicatorcolor(){
        GradientDrawable gradientDrawable=(GradientDrawable) view.getBackground();
        gradientDrawable.setColor(Color.parseColor(selectedcolor));
    }

    private boolean isDarkTheme() {
        int currentNightMode = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;

        switch (currentNightMode) {
            case Configuration.UI_MODE_NIGHT_NO:
                // Night mode is not active, we are in day mode
                return false;
            case Configuration.UI_MODE_NIGHT_YES:
                // Night mode is active, we are in dark mode
                return true;
            default:
                return false;
        }
    }

}