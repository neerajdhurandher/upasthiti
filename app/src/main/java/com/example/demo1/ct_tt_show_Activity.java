package com.example.demo1;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ct_tt_show_Activity extends AppCompatActivity {


    String course_by_intent,branch_by_intent;
    ListView listView;
    DatabaseReference db_ref;
    List<pdf_events> uploaded_PDF_list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ct_tt_show_);

         course_by_intent = getIntent().getExtras().getString("course");
         branch_by_intent = getIntent().getExtras().getString("branch")+"";

         db_ref =  FirebaseDatabase.getInstance().getReference("Ct_tt");


         listView = findViewById(R.id.listview);
        uploaded_PDF_list = new ArrayList<>();

         retrievePDFfiles();

          listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
              @Override
              public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                  pdf_events pdf = uploaded_PDF_list.get(position);

                  Intent intent = new Intent(Intent.ACTION_VIEW);
                  intent.setType("application/pdf");
                  intent.setData(Uri.parse(pdf.getUrl()));
                  startActivity(intent);
                  
              }
          });




    }

    private void retrievePDFfiles() {

       db_ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot ds : snapshot.getChildren()){

                    pdf_events putPDF = ds.getValue(pdf_events.class);
                    uploaded_PDF_list.add(putPDF);
                }
                String[] upload_name =  new String[uploaded_PDF_list.size()];

                for (int i = 0 ; i<upload_name.length;i++){

                    upload_name[i] = uploaded_PDF_list.get(i).getName();
                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,upload_name){

                    @NonNull
                    @Override
                    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

                     View view  = super.getView(position,convertView,parent);
                        TextView textView = (TextView)view.findViewById(R.id.textView);


//                        textView.setTextSize(20);
//                        textView.setTextColor(Color.BLACK);
                        return view;
                    }
                };
                listView.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}































