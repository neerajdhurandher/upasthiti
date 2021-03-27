package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class List_all_stu_in_Class_Activity extends AppCompatActivity {

    DatabaseReference teacher_db,student_db,student_details_db;
    String get_course,get_branch,get_admisson_year,get_section,get_Number_of_lectures,get_subject_name;

    String teacher_id_str,class_str;
    String  stu_admission_no_str;

    List<Model_Student> model_students_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_stu_in__class_);
//
//        teacher_db =  FirebaseDatabase.getInstance().getReference("Teacher Classes");
//        student_db =  FirebaseDatabase.getInstance().getReference("Student Classes");
//        student_details_db = FirebaseDatabase.getInstance().getReference("Student Details");
//
//        teacher_id_str = getIntent().getExtras().getString("Teacher_id_str")+"";
//        class_str = getIntent().getExtras().getString("Class_str")+"";
//
//        model_students_list = new ArrayList<>();
//
//
//
//
//        teacher_db.child(teacher_id_str).child(class_str).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                if (snapshot.exists()){
//
//                    for (DataSnapshot class_db_ss : snapshot.getChildren()){
//
//                        get_course = class_db_ss.child("Course").getValue().toString();
//                        get_branch = class_db_ss.child("Branch").getValue().toString();
//                        get_admisson_year = class_db_ss.child("Admission_Year").getValue().toString();
//                        get_section = class_db_ss.child("Section").getValue().toString();
//                        get_Number_of_lectures = class_db_ss.child("Number_of_lectures").getValue().toString();
//                        get_subject_name = class_db_ss.child("Subject_Name").getValue().toString();
//
//
//
//                    }
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//
//
//        student_db.child(get_course).child(get_branch).child(get_admisson_year).child("Section").child(get_section).addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()){
//
//                    model_students_list.clear();
//
//                    for (DataSnapshot stu_admission_no_ss : snapshot.getChildren()){
//
//                       stu_admission_no_str =  stu_admission_no_ss.getKey();
//
//
//                        student_details_db.child(get_course).child(get_branch).child(get_admisson_year).child("Students").addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                                if (snapshot.exists()){
//                                    model_students_list.clear();
//
//                                    for (DataSnapshot all_stu_of_class_ss : snapshot.getChildren()){
//
//                                        Model_Student model_student = all_stu_of_class_ss.child(stu_admission_no_str).getValue(Model_Student.class);
//                                        model_students_list.add(model_student);
//
//                                    }
//                                  }
//
//
//                            }
//
//                            @Override
//                            public void onCancelled(@NonNull DatabaseError error) {
//
//                            }
//                        });
//
//
//
//
//
//
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });


    }
}