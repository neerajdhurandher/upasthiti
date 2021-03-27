package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
import java.util.Objects;

public class show_tt_new_Activity extends AppCompatActivity {

    String course_name,branch_name,admissionY,section,semester;
    TextView sem_show,sec_show,Break_Start_tv,Break_End_tv,Short_Break_Start_tv,Short_Break_End_tv;
    RecyclerView timing_recyclerView,d1_recyclerView,d2_recyclerView,d3_recyclerView,d4_recyclerView,d5_recyclerView,d6_recyclerView,d7_recyclerView;

    List<Model_tt_subject> tt_subjectList;
    List<Model_tt_timing> tt_timingList;
    Adapter_tt_sub adapter_tt_sub;
    Adapter_tt_timing adapter_tt_timing;

    DatabaseReference databaseReference =  FirebaseDatabase.getInstance().getReference().child("Classes_Subject");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_tt_new_);

        course_name = Objects.requireNonNull(getIntent().getExtras()).getString("course")+"";
        branch_name = getIntent().getExtras().getString("branch")+"";
        section = getIntent().getExtras().getString("section")+"";
        semester = getIntent().getExtras().getString("semester")+"";
        admissionY = getIntent().getExtras().getString("admission_Year")+"";


        Break_Start_tv = findViewById(R.id.break_start_show);
        Break_End_tv  = findViewById(R.id.break_end_show);
        Short_Break_Start_tv  = findViewById(R.id.short_break_start_show);
        Short_Break_End_tv  = findViewById(R.id.short_break_end_show);

        sec_show = findViewById(R.id.sec_show);
        sem_show = findViewById(R.id.sem_show);

        sem_show.setText(semester);
        sec_show.setText(section);




        d1_recyclerView = findViewById(R.id.subject_recyclerView_monday);
        d1_recyclerView.setHasFixedSize(true);
        d1_recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        timing_recyclerView = findViewById(R.id.timing_recyclerView);

        timing_recyclerView.setHasFixedSize(true);
        timing_recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        Toast.makeText(this, "Hello", Toast.LENGTH_SHORT).show();

        databaseReference = databaseReference.child(course_name).child(admissionY).child("CSE")
                .child(semester).child(section);



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String a = snapshot.child("D1").child("1").child("Subject").getValue()+"";

                Toast.makeText(getApplicationContext(),a,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // break timing data fetch


        databaseReference.child("Timing").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){

                    tt_timingList.clear();
                for (DataSnapshot ds_timing: snapshot.getChildren()){

                    Model_tt_timing model_tt_timing = ds_timing.getValue(Model_tt_timing.class);

                    tt_timingList.add(model_tt_timing);
                    adapter_tt_timing = new Adapter_tt_timing(getApplicationContext(),tt_timingList);
                    timing_recyclerView.setAdapter(adapter_tt_timing);
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });






        DatabaseReference d1_dr = databaseReference.child("D1");
        d1_dr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    tt_subjectList.clear();

                    for (DataSnapshot ds: snapshot.getChildren()){

                        Model_tt_subject model_tt_subject = ds.getValue(Model_tt_subject.class);

                        tt_subjectList.add(model_tt_subject);
                        adapter_tt_sub =  new Adapter_tt_sub(getApplicationContext(),tt_subjectList);
                        d1_recyclerView.setAdapter(adapter_tt_sub);

                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }
}