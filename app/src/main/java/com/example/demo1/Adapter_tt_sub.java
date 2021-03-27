package com.example.demo1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter_tt_sub extends RecyclerView.Adapter<Adapter_tt_sub.MyHolder> {

    Context context;
    List<Model_tt_subject> tt_subjectList;

    String sub_name;

public Adapter_tt_sub(Context context,List<Model_tt_subject> tt_subjectList){
    this.context =context;
    this.tt_subjectList = tt_subjectList;
   }

    @NonNull
    @Override
        public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.row_tt_sub, parent,false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

    sub_name = tt_subjectList.get(position).getSubject();
    holder.sub_tv.setText(sub_name);
    }

    @Override
    public int getItemCount() {

    return tt_subjectList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{

    TextView sub_tv;
       public MyHolder(@NonNull View itemView) {
           super(itemView);
           sub_tv =itemView.findViewById(R.id.sub_name_tv);


       }
   }




   }
