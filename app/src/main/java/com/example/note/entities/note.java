package com.example.note.entities;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "Notes")
public class note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "Title")
    private String title;

    @ColumnInfo(name = "DateandTime")
    private String dateandtime;

    @ColumnInfo(name = "subTitle")
    private String subtitle;

    @ColumnInfo(name = "note")
    private String notetext;

    @ColumnInfo(name = "imgPath")
    private String imgpath;

    @ColumnInfo(name = "color")
    private String color;

    @ColumnInfo(name = "webUrl")
    private String webURL;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateandtime() {
        return dateandtime;
    }

    public void setDateandtime(String dateandtime) {
        this.dateandtime = dateandtime;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getNotetext() {
        return notetext;
    }

    public void setNotetext(String notetext) {
        this.notetext = notetext;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getWebURL() {
        return webURL;
    }

    public void setWebURL(String webURL) {
        this.webURL = webURL;
    }

    @NonNull
    @Override
    public String toString() {
        return "note{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", dateandtime='" + dateandtime + '\'' +
                ", subtitle='" + subtitle + '\'' +
                ", notetext='" + notetext + '\'' +
                ", imgpath='" + imgpath + '\'' +
                ", color='" + color + '\'' +
                ", webURL='" + webURL + '\'' +
                '}';
    }
}
