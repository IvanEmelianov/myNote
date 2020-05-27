package com.ivan.mynote.Adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivan.mynote.MainActivity;
import com.ivan.mynote.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    ViewHolder viewHolder;
    private ArrayList<Note> mNote;


    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        public TextView data;
        public TextView description;



        public ViewHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.titleNote);
            description = (TextView) view.findViewById(R.id.description);

            Calendar calendar = Calendar.getInstance();
            String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
            data = (TextView) view.findViewById(R.id.tvData);
            data.setText(currentDate);

        }
    }

    public NoteAdapter(ArrayList<Note> mNote){
        this.mNote = mNote;
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View noteView = inflater.inflate(R.layout.recycler_view, parent, false);

        viewHolder = new ViewHolder(noteView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = mNote.get(position);

        viewHolder.title.setText(note.getTitle());
        //viewHolder.data.setText(note.getData());
        viewHolder.description.setText(note.getDescription());

    }

    @Override
    public int getItemCount() {
        return mNote.size();
    }
}


