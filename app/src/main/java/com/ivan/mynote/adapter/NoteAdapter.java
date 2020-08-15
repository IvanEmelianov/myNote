package com.ivan.mynote.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ivan.mynote.CustomerClickListener;
import com.ivan.mynote.R;
import com.ivan.mynote.entity.Record;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;


public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>{
    CustomerClickListener listener;
    private ViewHolder viewHolder;
    private List<Record> records;
    private Context context;

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView rvDate;
        TextView rvText;
        TextView rvTitle;
        public ViewHolder(View view){
            super(view);
            rvTitle = view.findViewById(R.id.titleNote);
            rvText = view.findViewById(R.id.description);
            Calendar calendar = Calendar.getInstance();
            String currentDate = DateFormat.getDateInstance().format(calendar.getTime());
            rvDate = view.findViewById(R.id.tvData);
            rvDate.setText(currentDate);
        }
    }
    public NoteAdapter(List<Record> mRecords, CustomerClickListener listener){
        this.records = mRecords;
        this.listener = listener;
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
        holder.rvDate.setText(records.get(position).getDate());
        holder.itemView.setOnClickListener(v -> {
            if (viewHolder != null){
                listener.onCustomerClick(records.get(position).getTitle(),
                        records.get(position).getText(), records.get(position).getDate(),
                        records.get(position).getRecordId());
            }
        });
    }
    @Override
    public int getItemCount() {
        return records.size();
    }
}


