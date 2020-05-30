package com.ivan.mynote.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivan.mynote.Data.RecordAddDataBase;
import com.ivan.mynote.R;
import com.ivan.mynote.Record.Record;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    ViewHolder viewHolder;
    private List<Record> records;
    Context context;

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView data;
        public TextView title;
        ImageButton delete;

        public ViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.description);
            delete = view.findViewById(R.id.btnDelete);
            Calendar calendar = Calendar.getInstance();
            String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
            data = (TextView) view.findViewById(R.id.tvData);
            data.setText(currentDate);
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
        holder.title.setText(records.get(position).getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Номер " + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return records.size();
    }
}


