package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class About_Activity extends AppCompatActivity {



    TextView edit_stu_name,edit_stu_clg_add_no,edit_stu_uni_roll_no,edit_section,edit_stu_dob,edit_stu_gender,edit_stu_phone,edit_stu_email,
            edit_stu_blood_group,edit_stu_mother_name,edit_stu_father_name,edit_stu_parent_phone,
            edit_stu_permanent_address,edit_stu_temp_address,edit_teacher_id_no,edit_teacher_department,edit_stu_cast,
            edit_course,edit_branch,edit_semester,edit_add_year, edit_wife_hus,edit_joining_date,student_name_heading;


    String str_stu_name,str_stu_clg_add_no,str_stu_uni_roll_no,str_section,str_stu_dob,str_stu_gender,str_stu_phone,str_stu_email,
            str_stu_blood_group,str_stu_mother_name,str_stu_father_name,str_stu_parent_phone,
            str_stu_permanent_address,str_stu_temp_address,str_teacher_id_no,str_teacher_department,str_stu_cast,
            str_course,str_branch,str_semester,str_add_year, str_wife_hus,str_joining_date;


    LinearLayout ll_stu_clg_add_no,ll_stu_uni_roll_no,ll_section, ll_teacher_joining_date, ll_teacher_id_no,
            ll_teacher_department,ll_stu_cast,ll_course,ll_branch,ll_semester,ll_add_year, ll_wife_hus;



    String get_something, get_id_No,get_person,get_email;

    DatabaseReference teacher_details_db,student_details_db;
    FirebaseUser currentUser;
    String currentUser_email;

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_);



        student_name_heading = findViewById(R.id.student_name_heading);

        edit_stu_name = findViewById(R.id.name_Body);
        edit_stu_clg_add_no = findViewById(R.id.clg_add_no_Body);
        edit_stu_uni_roll_no = findViewById(R.id.uni_rollno_Body);
        edit_course = findViewById(R.id.course_Body);
        edit_section = findViewById(R.id.section_Body);
        edit_branch = findViewById(R.id.branch_Body);
        edit_semester = findViewById(R.id.semester_Body);
        edit_add_year = findViewById(R.id.add_year_Body);
        edit_stu_dob = findViewById(R.id.dob_Body);
        edit_stu_gender = findViewById(R.id.gender_Body);
        edit_stu_phone = findViewById(R.id.phone_no_Body);
        edit_stu_email = findViewById(R.id.email_Body);
        edit_stu_mother_name = findViewById(R.id.mother_name_Body);
        edit_stu_father_name = findViewById(R.id.father_name_Body);
        edit_stu_parent_phone = findViewById(R.id.parent_phone_no_Body);
        edit_stu_permanent_address = findViewById(R.id.p_address_Body);
        edit_stu_temp_address = findViewById(R.id.l_address_Body);
        edit_teacher_id_no = findViewById(R.id.teacher_id_Body);
        edit_teacher_department = findViewById(R.id.teacher_department_Body);
        edit_joining_date = findViewById(R.id.joining_date_Body);
        edit_stu_cast = findViewById(R.id.cast_Body);
        edit_wife_hus = findViewById(R.id.wife_hus_Body);
        edit_stu_blood_group = findViewById(R.id.blood_group_Body);

        // linear layout find view by Id


        ll_stu_clg_add_no = findViewById(R.id.clg_add_no_ll_body);
        ll_stu_uni_roll_no = findViewById(R.id.uni_rollno_ll_body);
        ll_course = findViewById(R.id.course_ll_body);
        ll_section = findViewById(R.id.section_ll_body);
        ll_branch = findViewById(R.id.branch_ll_body);
        ll_semester = findViewById(R.id.semester_ll_body);
        ll_add_year = findViewById(R.id.add_year_ll_body);
        ll_teacher_id_no = findViewById(R.id.teacher_id_no_ll_body);
        ll_teacher_department = findViewById(R.id.teacher_department_ll_body);
        ll_wife_hus = findViewById(R.id.wife_hus_ll_body);
        ll_teacher_joining_date = findViewById(R.id.teacher_joining_date_ll);


         // get data from incoming intent
        get_person = getIntent().getExtras().getString("Person")+"";
        get_id_No = getIntent().getExtras().getString("Id_No")+"";
        get_email = getIntent().getExtras().getString("Email")+"";

        teacher_details_db = FirebaseDatabase.getInstance().getReference("Teachers Details");
        student_details_db = FirebaseDatabase.getInstance().getReference("Student Details");

        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUser_email = currentUser.getEmail();


        if (currentUser_email.equals(get_email)) {


            if (get_person.equals("Student")) {

                String course_by_intent = getIntent().getExtras().getString("course")+"";
                String ad_year_by_intent = getIntent().getExtras().getString("admission_Year")+"";
                String branch_by_intent = getIntent().getExtras().getString("branch")+"";



                ll_teacher_id_no.setVisibility(View.GONE);
                ll_teacher_department.setVisibility(View.GONE);
                ll_wife_hus.setVisibility(View.GONE);
                ll_teacher_joining_date.setVisibility(View.GONE);



                student_details_db.child(course_by_intent).child(branch_by_intent).child(ad_year_by_intent).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){

                            str_semester = snapshot.child("Current_Semester").getValue().toString();
                            edit_semester.setText(str_semester);

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                student_details_db = student_details_db.child(course_by_intent).child(branch_by_intent).child(ad_year_by_intent).child("Students");

                Query query = student_details_db.orderByChild("College_RollNo").equalTo(get_id_No);  // set reference

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()) {

                            for (DataSnapshot student_db_snapshot : snapshot.getChildren()) {

                               str_stu_name =  student_db_snapshot.child("Name").getValue().toString();
                               edit_stu_name.setText(str_stu_name);
                               student_name_heading.setText(str_stu_name);

                               str_stu_gender = student_db_snapshot.child("Gender").getValue().toString();
                               edit_stu_gender.setText(str_stu_gender);

                               str_stu_dob = student_db_snapshot.child("DOB").getValue().toString();
                               edit_stu_dob.setText(str_stu_dob);

                               str_stu_email  = student_db_snapshot.child("Email_Id").getValue().toString();
                               edit_stu_email.setText(str_stu_email);

                               str_stu_cast  = student_db_snapshot.child("Cast").getValue().toString();
                               edit_stu_cast.setText(str_stu_cast);

                               str_stu_phone = student_db_snapshot.child("Phone_No").getValue().toString();
                               edit_stu_phone.setText(str_stu_phone);

                              str_stu_mother_name =  student_db_snapshot.child("Mother_Name").getValue().toString();
                              edit_stu_mother_name.setText(str_stu_mother_name);

                              str_stu_father_name  = student_db_snapshot.child("Father_Name").getValue().toString();
                              edit_stu_father_name.setText(str_stu_father_name);

                              str_stu_parent_phone  = student_db_snapshot.child("Parent_Phone_No").getValue().toString();
                              edit_stu_parent_phone.setText(str_stu_parent_phone);

                              str_stu_permanent_address  = student_db_snapshot.child("Address").getValue().toString();
                                edit_stu_permanent_address.setText(str_stu_permanent_address);

                              str_stu_temp_address  = student_db_snapshot.child("Local_Address").getValue().toString();
                              edit_stu_temp_address.setText(str_stu_temp_address);

                              str_stu_blood_group  = student_db_snapshot.child("Blood_Group").getValue().toString();
                              edit_stu_blood_group.setText(str_stu_blood_group);


                                str_course =  student_db_snapshot.child("Course").getValue().toString();
                                edit_course.setText(str_course);

                                str_branch =  student_db_snapshot.child("Branch").getValue().toString();
                                edit_branch.setText(str_branch);

                                str_add_year =  student_db_snapshot.child("Admission_Year").getValue().toString();
                                edit_add_year.setText(str_add_year);

                                str_stu_clg_add_no =  student_db_snapshot.child("College_RollNo").getValue().toString();
                                edit_stu_clg_add_no.setText(str_stu_clg_add_no);

                                str_stu_uni_roll_no =  student_db_snapshot.child("University_RollNo").getValue().toString();
                                edit_stu_uni_roll_no.setText(str_stu_uni_roll_no);

                                str_section  =  student_db_snapshot.child("Section").getValue().toString();
                                edit_section.setText(str_section);

//                                str_semester = "add here";  // add here later
//                                edit_semester.setText(str_semester);





                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });





            }


            else if (get_person.equals("Teacher")) {

                ll_stu_clg_add_no.setVisibility(View.GONE);
                ll_stu_uni_roll_no.setVisibility(View.GONE);
                ll_course.setVisibility(View.GONE);
                ll_section.setVisibility(View.GONE);
                ll_branch.setVisibility(View.GONE);
                ll_semester.setVisibility(View.GONE);
                ll_add_year.setVisibility(View.GONE);



                Query query = teacher_details_db.orderByChild("Teacher_Id_No").equalTo(get_id_No);  // set reference

                query.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.exists()) {

                            for (DataSnapshot teacher_db_snapshot : snapshot.getChildren()) {

                                str_stu_name =  teacher_db_snapshot.child("Name").getValue().toString();
                                edit_stu_name.setText(str_stu_name);
                                student_name_heading.setText(str_stu_name);


                                str_stu_gender = teacher_db_snapshot.child("Gender").getValue().toString();
                                edit_stu_gender.setText(str_stu_gender);

                                str_stu_dob = teacher_db_snapshot.child("DOB").getValue().toString();
                                edit_stu_dob.setText(str_stu_dob);

                                str_stu_email  = teacher_db_snapshot.child("Email_Id").getValue().toString();
                                edit_stu_email.setText(str_stu_email);

                                str_stu_cast  = teacher_db_snapshot.child("Cast").getValue().toString();
                                edit_stu_cast.setText(str_stu_cast);

                                str_stu_phone = teacher_db_snapshot.child("Phone_No").getValue().toString();
                                edit_stu_phone.setText(str_stu_phone);

                                str_stu_mother_name =  teacher_db_snapshot.child("Mother_Name").getValue().toString();
                                edit_stu_mother_name.setText(str_stu_mother_name);

                                str_stu_father_name  = teacher_db_snapshot.child("Father_Name").getValue().toString();
                                edit_stu_father_name.setText(str_stu_father_name);

                                str_stu_parent_phone  = teacher_db_snapshot.child("Parent_Phone_No").getValue().toString();
                                edit_stu_parent_phone.setText(str_stu_parent_phone);

                                str_stu_permanent_address  = teacher_db_snapshot.child("Address").getValue().toString();
                                edit_stu_permanent_address.setText(str_stu_permanent_address);

                                str_stu_temp_address  = teacher_db_snapshot.child("Local_Address").getValue().toString();
                                edit_stu_temp_address.setText(str_stu_temp_address);

                                str_stu_blood_group  = teacher_db_snapshot.child("Blood_Group").getValue().toString();
                                edit_stu_blood_group.setText(str_stu_blood_group);

                                str_teacher_id_no = teacher_db_snapshot.child("Teacher_Id_No").getValue().toString();
                                edit_teacher_id_no.setText(str_teacher_id_no);

                                str_teacher_department = teacher_db_snapshot.child("Department").getValue().toString();
                                edit_teacher_department.setText(str_teacher_department);

                                str_joining_date = teacher_db_snapshot.child("Joining_Date").getValue().toString();
                                edit_joining_date.setText(str_joining_date);






                            }

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }




        }



    }


}