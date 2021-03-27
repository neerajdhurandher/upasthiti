package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Add_new_tt_Activity extends AppCompatActivity {

    String modification_type_getIntent;

    String course_name,branch_name,admissionY;

    EditText sem_input,sec_input;

    RelativeLayout new_tt_rl;
    LinearLayout subject_ll,sel_day_ll;

    DatabaseReference student_db;

    EditText t1_s,t2_s,t3_s,t4_s,t5_s,t6_s,t7_s;
    EditText t1_e,t2_e,t3_e,t4_e,t5_e,t6_e,t7_e;
    TextView sel_monday,sel_tuesday,sel_wednesday,sel_thursday,sel_friday,sel_saturday;
    EditText p1,p2,p3,p4,p5,p6,p7;
    EditText sb_s,sb_e,b_s,b_e;

    Button btn_next,btn_done;

    String selected_day = "new";
    String sem,sec;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_tt_);

        modification_type_getIntent = getIntent().getExtras().getString("tt_action");

        course_name = Objects.requireNonNull(getIntent().getExtras()).getString("course")+"";
        branch_name = getIntent().getExtras().getString("branch")+"";
        admissionY = getIntent().getExtras().getString("admission_year")+"";


        if (modification_type_getIntent.equals("new")){

            new_tt_rl.setVisibility(View.VISIBLE);
        }


        btn_next = findViewById(R.id.btn_next);
        btn_done = findViewById(R.id.btn_done);


        sel_monday = findViewById(R.id.btn_monday);
        sel_tuesday = findViewById(R.id.btn_tuesday);
        sel_wednesday = findViewById(R.id.btn_wednesday);
        sel_thursday = findViewById(R.id.btn_thursday);
        sel_friday = findViewById(R.id.btn_friday);
        sel_saturday = findViewById(R.id.btn_saturday);

        t1_s = findViewById(R.id.p1_start_time);
        t2_s = findViewById(R.id.p2_start_time);
        t3_s = findViewById(R.id.p3_start_time);
        t4_s = findViewById(R.id.p4_start_time);
        t5_s = findViewById(R.id.p5_start_time);
        t6_s = findViewById(R.id.p6_start_time);
        t7_s = findViewById(R.id.p7_start_time);

        t1_e = findViewById(R.id.p1_end_time);
        t2_e = findViewById(R.id.p2_end_time);
        t3_e = findViewById(R.id.p3_end_time);
        t4_e = findViewById(R.id.p4_end_time);
        t5_e = findViewById(R.id.p5_end_time);
        t6_e = findViewById(R.id.p6_end_time);
        t7_e = findViewById(R.id.p7_end_time);

        p1 = findViewById(R.id.p1_sub);
        p2 = findViewById(R.id.p2_sub);
        p3 = findViewById(R.id.p3_sub);
        p4 = findViewById(R.id.p4_sub);
        p5 = findViewById(R.id.p5_sub);
        p6 = findViewById(R.id.p6_sub);
        p7 = findViewById(R.id.p7_sub);

        b_s = findViewById(R.id.break_start_ip);
        b_e = findViewById(R.id.break_end_ip);
        sb_s = findViewById(R.id.short_break_start_ip);
        sb_e = findViewById(R.id.short_break_end_ip);


        new_tt_rl = findViewById(R.id.new_tt_rl);
        sel_day_ll  = findViewById(R.id.sel_day_ll);
        subject_ll  = findViewById(R.id.subject_ll);
        subject_ll.setEnabled(false);

        sem_input = findViewById(R.id.sem_input);
        sec_input = findViewById(R.id.sec_input);

        sem = sem_input.getText().toString();
        sec = sec_input.getText().toString();


// .........................................................................................................................................................


        sel_day();

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sem = sem_input.getText().toString();
                sec = sec_input.getText().toString();
                student_db = FirebaseDatabase.getInstance().getReference("Classes_Subjects").child(course_name).child(admissionY).child("CSE")
                        .child(sem).child(sec);




                if (selected_day.equals("D6")){

                    save_details(selected_day,"1",t1_s.getText().toString(),t1_e.getText().toString(), p1.getText().toString());
                    save_details(selected_day,"2",t2_s.getText().toString(),t2_e.getText().toString(), p2.getText().toString());
                    save_details(selected_day,"3",t3_s.getText().toString(),t3_e.getText().toString(), p3.getText().toString());
                    save_details(selected_day,"4",t4_s.getText().toString(),t4_e.getText().toString(), p4.getText().toString());
                    save_details(selected_day,"5",null,null,null);
                    save_details(selected_day,"6",null,null,null);
                    save_details(selected_day,"7",null,null,null);


                }

                else {
                    save_details(selected_day, "1", t1_s.getText().toString(), t1_e.getText().toString(), p1.getText().toString());
                    save_details(selected_day, "2", t2_s.getText().toString(), t2_e.getText().toString(), p2.getText().toString());
                    save_details(selected_day, "3", t3_s.getText().toString(), t3_e.getText().toString(), p3.getText().toString());
                    save_details(selected_day, "4", t4_s.getText().toString(), t4_e.getText().toString(), p4.getText().toString());
                    save_details(selected_day, "5", t5_s.getText().toString(), t5_e.getText().toString(), p5.getText().toString());
                    save_details(selected_day, "6", t6_s.getText().toString(), t6_e.getText().toString(), p6.getText().toString());
                    save_details(selected_day, "7", t7_s.getText().toString(), t7_e.getText().toString(), p7.getText().toString());
                }

                clear_sub_name_field();
                reset_box_colour();


                student_db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        student_db.child("aaBreak_Start").setValue(b_s.getText().toString());
                        student_db.child("aaBreak_End").setValue(b_e.getText().toString());

                        student_db.child("aaShort_Break_Start").setValue(sb_s.getText().toString());
                        student_db.child("aaShort_Break_End").setValue(sb_e.getText().toString());

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }



        });

        btn_done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent home_intent = new Intent(Add_new_tt_Activity.this,Home_Activity.class);
                startActivity(home_intent);
                finish();

            }
        });



    }


    private void clear_sub_name_field() {

        p1.setText("");
        p2.setText("");
        p3.setText("");
        p4.setText("");
        p5.setText("");
        p6.setText("");
        p7.setText("");


    }

    private void sel_day() {

        sel_monday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_day = "D1";
                reset_box_colour();
                sel_monday.setBackgroundColor(getColor(android.R.color.holo_orange_dark));
                subject_ll.setEnabled(true);

            }
        });

        sel_tuesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_day = "D2";
                reset_box_colour();
                sel_tuesday.setBackgroundColor(getColor(android.R.color.holo_orange_dark));
            }
        });

        sel_wednesday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_day = "D3";
                reset_box_colour();
                sel_wednesday.setBackgroundColor(getColor(android.R.color.holo_orange_dark));
            }
        });
        sel_thursday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_day = "D4";
                reset_box_colour();
                sel_thursday.setBackgroundColor(getColor(android.R.color.holo_orange_dark));
            }
        });
        sel_friday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_day = "D5";
                reset_box_colour();
                sel_friday.setBackgroundColor(getColor(android.R.color.holo_orange_dark));
            }
        });
        sel_saturday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selected_day = "D6";
                reset_box_colour();
                sel_saturday.setBackgroundColor(getColor(android.R.color.holo_orange_dark));
            }
        });


    }

    private void reset_box_colour() {

        sel_monday.setBackgroundColor(getColor(android.R.color.white));
        sel_tuesday.setBackgroundColor(getColor(android.R.color.white));
        sel_wednesday.setBackgroundColor(getColor(android.R.color.white));
        sel_thursday.setBackgroundColor(getColor(android.R.color.white));
        sel_friday.setBackgroundColor(getColor(android.R.color.white));
        sel_saturday.setBackgroundColor(getColor(android.R.color.white));


    }

    private void save_details(final String day, final String p_no, final String start, final String end, final String subject) {



        student_db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                DatabaseReference db = student_db.child(day).child(p_no);
                db.child("Subject").setValue(subject);

                DatabaseReference db_timing = student_db.child("Timing").child(p_no);
                db_timing.child("Start").setValue(start);
                db_timing.child("End").setValue(end);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }



}