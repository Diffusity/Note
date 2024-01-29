package com.example.note.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.note.R;
import com.example.note.entities.note;
import com.example.note.listeners.noteListener;
import com.google.android.material.color.utilities.DynamicColor;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class notesadapter extends RecyclerView.Adapter<notesadapter.noteHolder>{

    private List<note> list,notesource;
    noteListener noteListener;
    Timer timer;

    public notesadapter(List<note> list,noteListener noteListener) {
        this.list = list;
        this.noteListener=noteListener;
        notesource=list;
    }

    @NonNull
    @Override
    public noteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new noteHolder(
                LayoutInflater.from(parent.getContext()).inflate(
                        R.layout.note_container,parent,false
                    )
        );
    }

    @Override
    public void onBindViewHolder(@NonNull noteHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.setNote(list.get(position));
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noteListener.onNoteClicked(list.get(position),position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    static class noteHolder extends RecyclerView.ViewHolder{
        TextView noteTitle,notesubtitle,dateandtime;
        LinearLayout layout;
        Context context;
        public noteHolder(@NonNull View itemView) {
            super(itemView);
            noteTitle=itemView.findViewById(R.id.notetitle);
            notesubtitle=itemView.findViewById(R.id.notesubtitle);
            dateandtime=itemView.findViewById(R.id.dateandtime);
            layout=itemView.findViewById(R.id.notelayout);
        }

        void setNote(note note){
            noteTitle.setText(note.getTitle());
            String date= note.getDateandtime();
            if(note.getSubtitle().trim().isEmpty()){
                notesubtitle.setVisibility(View.GONE);
            }
            else{
                notesubtitle.setText(note.getSubtitle());
            }
            dateandtime.setText(date);
            GradientDrawable gradientDrawable=(GradientDrawable) layout.getBackground();
            if(note.getColor() != null){
                gradientDrawable.setColor(Color.parseColor(note.getColor()));
            }
            else{
                gradientDrawable.setColor(Color.parseColor("#d1e5f4"));
            }
        }
    }
    public void searchNote(final String searchKeyword){
        timer=new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if(searchKeyword.trim().isEmpty()){
                    list = notesource;
                }
                else{
                    ArrayList<note> temp=new ArrayList<>();
                    for (note note:notesource){
                        if(note.getTitle().toLowerCase().contains(searchKeyword.toLowerCase())|| note.getSubtitle().toLowerCase().contains(searchKeyword.toLowerCase())){
                            temp.add(note);
                        }
                    }
                    list=temp;
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
            }
        },200);
    }
    public void cancelTimer(){
        if(timer!=null){
            timer.cancel();
        }
    }
}
