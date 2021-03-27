package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Objects;

public class Allot_Teacher_Activity extends AppCompatActivity {


    String course_name,branch_name,admissionY,Person_str;

    EditText sel_section,sub_1_teacher_id,sub_2_teacher_id,sub_3_teacher_id,sub_4_teacher_id,sub_5_teacher_id;
    String str_sel_section,str_sub_1_teacher_id,str_sub_2_teacher_id,str_sub_3_teacher_id,str_sub_4_teacher_id,str_sub_5_teacher_id;

    EditText sub_1_name,sub_2_name,sub_3_name,sub_4_name,sub_5_name;
    String str_sub_1_name,str_sub_2_name,str_sub_3_name,str_sub_4_name,str_sub_5_name;
    CheckBox checkBox_1,checkBox_2,checkBox_3,checkBox_4,checkBox_5;


//    String clg_add_no_str;
    Button apply_btn;
    String current_semester,sem;

    DatabaseReference student_class,student_db,teacher_db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allot__teacher_);


        course_name = Objects.requireNonNull(getIntent().getExtras()).getString("course")+"";
        branch_name = getIntent().getExtras().getString("branch")+"";
        admissionY = getIntent().getExtras().getString("admission_year")+"";


        sel_section = findViewById(R.id.sel_section);
        apply_btn = findViewById(R.id.apply_btn);
        sub_1_teacher_id  = findViewById(R.id.sub_1_teacher_id);
        sub_2_teacher_id  = findViewById(R.id.sub_2_teacher_id);
        sub_3_teacher_id  = findViewById(R.id.sub_3_teacher_id);
        sub_4_teacher_id  = findViewById(R.id.sub_4_teacher_id);
        sub_5_teacher_id  = findViewById(R.id.sub_5_teacher_id);

        sub_1_name = findViewById(R.id.sub_1_name);
        sub_2_name = findViewById(R.id.sub_2_name);
        sub_3_name = findViewById(R.id.sub_3_name);
        sub_4_name = findViewById(R.id.sub_4_name);
        sub_5_name = findViewById(R.id.sub_5_name);

        checkBox_1 = findViewById(R.id.t_s_checkbox_1);
        checkBox_2 = findViewById(R.id.t_s_checkbox_2);
        checkBox_3 = findViewById(R.id.t_s_checkbox_3);
        checkBox_4 = findViewById(R.id.t_s_checkbox_4);
        checkBox_5 = findViewById(R.id.t_s_checkbox_5);







        student_class = FirebaseDatabase.getInstance().getReference("Student Classes")
                .child(course_name).child(branch_name).child(admissionY).child("Section");

        student_db = FirebaseDatabase.getInstance().getReference("Student Details")
                .child(course_name).child(branch_name).child(admissionY);

        teacher_db =  FirebaseDatabase.getInstance().getReference("Teacher Classes");

        student_db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                current_semester = snapshot.child("Current_Semester").getValue().toString();
                 sem = current_semester+"_Sem";

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        apply_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                getstrings();


                student_db.child("Students").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){


                            for (DataSnapshot abc : snapshot.getChildren()) {

                                String clg_add_no_str = abc.child("College_RollNo").getValue().toString();

                                if(clg_add_no_str != null) {

                                    if (checkBox_1.isChecked()) {

                                        set_student_class_details(str_sel_section, clg_add_no_str, str_sub_1_teacher_id, str_sub_1_name);
                                        set_teacher_class_details(str_sub_1_teacher_id, str_sub_1_name);


                                    }

                                    if (checkBox_2.isChecked()) {


                                        set_student_class_details(str_sel_section, clg_add_no_str, str_sub_2_teacher_id, str_sub_2_name);
                                        set_teacher_class_details(str_sub_2_teacher_id, str_sub_2_name);
                                    }


                                    if (checkBox_3.isChecked()) {


                                        set_student_class_details(str_sel_section, clg_add_no_str, str_sub_3_teacher_id, str_sub_3_name);
                                        set_teacher_class_details(str_sub_3_teacher_id, str_sub_3_name);

                                    }

                                    if (checkBox_4.isChecked()) {


                                        set_student_class_details(str_sel_section, clg_add_no_str, str_sub_4_teacher_id, str_sub_4_name);
                                        set_teacher_class_details(str_sub_4_teacher_id, str_sub_4_name);

                                    }

                                    if (checkBox_5.isChecked()) {

                                        set_student_class_details(str_sel_section, clg_add_no_str, str_sub_5_teacher_id, str_sub_5_name);
                                        set_teacher_class_details(str_sub_5_teacher_id, str_sub_5_name);
                                    }

                                }

                               
                            }



                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




                Toast.makeText(Allot_Teacher_Activity.this, "Done", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Allot_Teacher_Activity.this, Home_Activity.class));


       }

            private void set_student_class_details(String str_sel_section, String clg_add_no_str, String str_sub__teacher_id, String str_sub__name) {

                if(clg_add_no_str != null) {

                    student_class.child(str_sel_section).child(clg_add_no_str).child("Attendance").child(str_sub__teacher_id).child("Subject_Attendance").setValue("0");
                    student_class.child(str_sel_section).child(clg_add_no_str).child("Attendance").child(str_sub__teacher_id).child("Total_Subject_Lecture").setValue("0");
                    student_class.child(str_sel_section).child(clg_add_no_str).child("Attendance").child(str_sub__teacher_id).child("Subject_Name").setValue(str_sub__name);

                    student_class.child(str_sel_section).child(clg_add_no_str).child("Total_Attendance").setValue("0");
                    student_db.child("Students").child(clg_add_no_str).child("Attendance").child(sem).setValue("0");

                }

            }

            private void set_teacher_class_details(String str_sub_teacher_id,String str_sub_name ) {

                    HashMap<Object, String> class_details_map = new HashMap<>();

                    String abcd = branch_name + "_" + sem + "_" + str_sel_section;

                    class_details_map.put("Branch", branch_name);
                    class_details_map.put("Course", course_name);
                    class_details_map.put("Admission_Year", admissionY);
                    class_details_map.put("Section", str_sel_section);
                    class_details_map.put("Number_of_lectures", "0");
                    class_details_map.put("Subject_Name", str_sub_name);
                    class_details_map.put("Class_Name", abcd);

                    teacher_db.child(str_sub_teacher_id).child(abcd).setValue(class_details_map);



            }

        });


    }

         private void getstrings() {


        // get strings of edit texts

          str_sel_section = sel_section.getText().toString();

          str_sub_1_teacher_id = sub_1_teacher_id.getText().toString();
          str_sub_2_teacher_id = sub_2_teacher_id.getText().toString();
          str_sub_3_teacher_id = sub_3_teacher_id.getText().toString();
          str_sub_4_teacher_id = sub_4_teacher_id.getText().toString();
          str_sub_5_teacher_id = sub_5_teacher_id.getText().toString();


          str_sub_1_name = sub_1_name.getText().toString();
          str_sub_2_name = sub_2_name.getText().toString();
          str_sub_3_name = sub_3_name.getText().toString();
          str_sub_4_name = sub_4_name.getText().toString();
          str_sub_5_name = sub_5_name.getText().toString();



        }
}