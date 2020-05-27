package com.ivan.mynote.Adapter;

import android.widget.TextView;

public class Note {
    private String title;

    private String description;

    public Note(String title, String description){

        this.description = description;
        this.title = title;
    }


    public String getTitle() {
        return title;
    }


    public String getDescription() {
        return description;
    }
}

