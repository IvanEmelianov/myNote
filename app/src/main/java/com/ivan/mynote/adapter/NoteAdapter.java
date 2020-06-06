package com.ivan.mynote.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivan.mynote.R;
import com.ivan.mynote.activity.MainActivity;
import com.ivan.mynote.activity.NotesActivity;
import com.ivan.mynote.data.RecordAddDataBase;
import com.ivan.mynote.entity.Record;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {


    private ViewHolder viewHolder;
    private List<Record> records;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView rvData;
        TextView rvText;
        TextView rvTitle;

        public ViewHolder(View view){
            super(view);
            rvTitle = view.findViewById(R.id.titleNote);
            rvText = view.findViewById(R.id.description);

            Calendar calendar = Calendar.getInstance();
            String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
            rvData = view.findViewById(R.id.tvData);
            rvData.setText(currentDate);
        }
    }

    public NoteAdapter(List<Record> mRecords){
        this.records = mRecords;
    }

    @NonNull
    @Override
    public NoteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View noteView = inflater.inflate(R.layout.recycler_view, parent, false);
        viewHolder = new ViewHolder(noteView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.rvTitle.setText(records.get(position).getTitle());
        holder.rvText.setText(records.get(position).getText());
        holder.rvData.setText(records.get(position).getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //Toast.makeText(context, "Номер " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return records.size();
    }
}


