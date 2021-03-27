package com.example.demo1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter_tt_timing extends RecyclerView.Adapter<Adapter_tt_timing.MyHolder> {

    Context context;
    List<Model_tt_timing> tt_timingList;

    String start,end;

    public Adapter_tt_timing(Context context, List<Model_tt_timing> tt_timingList){
        this.context =context;
        this.tt_timingList = tt_timingList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_tt_timing, parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        start = tt_timingList.get(position).getStart();
        end = tt_timingList.get(position).getEnd();

        holder.start_tv.setText(start);
        holder.end_tv.setText(end);

    }


    @Override
    public int getItemCount() {
        return tt_timingList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

        TextView start_tv,end_tv;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
             start_tv = itemView.findViewById(R.id.start_time);
            end_tv = itemView.findViewById(R.id.end_time);


        }
    }

}
