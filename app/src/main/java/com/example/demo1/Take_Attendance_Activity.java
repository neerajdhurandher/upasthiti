package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Take_Attendance_Activity extends AppCompatActivity {

   public TextView tv_name_as,tv_branch_as,tv_clg_rollno_as,tv_subject_attendance_as,tv_num_attend_lec_as,tv_total_attendance_as,tv_subject_name_as;
   public ImageView tv_stu_image;

    public RelativeLayout layout;

   public DatabaseReference teacher_db,student_db,student_details_db;
   public  DatabaseReference current_student_details_db,current_student_class_db;
   public  DatabaseReference current_student_class_db_for_attendence;

   public String teacher_id_str,teacher_email_str,class_str;
   public String sub_attend_lec_string,subject_total_lec;
   public String stu_admission_no_str;

   public  String current_sub_total_lec_str,current_sub_attend_lec_str;
   public  int  current_sub_total_lec_int,current_sub_attend_lec_int;

   public  String sem,get_attendance_abc;
   public  ArrayList<Integer> stu_add_no_array = new ArrayList<Integer>(10);


    public int total_lecture = 1;
   public int total_attendance = 1;

    public  int sub_attend_lec_int = 50;
    public  int sub_total_lec_int = 40;

    public int i= -1;
    public Animation anim_L,anim_D,anim_C_UP;



   public Model_teacher_class mtc;

    List<Model_Student> model_students_list_as;



    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take__attendance_);

        tv_name_as = findViewById(R.id.tv_name_as);
        tv_branch_as  = findViewById(R.id.tv_branch_as);
        tv_clg_rollno_as  = findViewById(R.id.tv_clg_rollno_as);
        tv_subject_attendance_as  = findViewById(R.id.tv_subject_attendance_as);
        tv_num_attend_lec_as  = findViewById(R.id.tv_num_attend_lec_as);
        tv_total_attendance_as  = findViewById(R.id.tv_total_attendance_as);
        tv_subject_name_as  = findViewById(R.id.tv_subject_name_as);
        tv_stu_image  = findViewById(R.id.stu_image_id);


        layout = findViewById(R.id.relativeLayout);



        anim_L = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.attendance_left_swipe_animation);
        anim_D = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.attendance_down_swipe_animation);
        anim_C_UP = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.attendance_comes_up_animation);

        anim_L.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {


            }

            @Override
            public void onAnimationEnd(Animation animation) {

                layout.startAnimation(anim_C_UP);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        anim_D.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {


            }

            @Override
            public void onAnimationEnd(Animation animation) {

                layout.startAnimation(anim_C_UP);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        anim_C_UP.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {


            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });



        teacher_db =  FirebaseDatabase.getInstance().getReference("Teacher Classes");
        student_db =  FirebaseDatabase.getInstance().getReference("Student Classes");
        student_details_db = FirebaseDatabase.getInstance().getReference("Student Details");

        current_student_class_db =  FirebaseDatabase.getInstance().getReference("Student Classes");
        current_student_details_db = FirebaseDatabase.getInstance().getReference("Student Details");

        teacher_id_str = getIntent().getExtras().getString("Teacher_id_str");
        teacher_email_str = getIntent().getExtras().getString("Teacher_email_id_str");
        class_str = getIntent().getExtras().getString("Class_str");


        take_class_data();


    }

    private void take_class_data() {

        teacher_db = teacher_db.child(teacher_id_str);

        Query abcd = teacher_db.orderByChild("Class_Name").equalTo(class_str);

      abcd.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {


                for (DataSnapshot classdbss : snapshot.getChildren() ){

                    final String  get_course = classdbss.child("Course").getValue().toString();
                    tv_subject_attendance_as.setText(get_course);

                    final String get_branch  = classdbss.child("Branch").getValue().toString();
                    tv_branch_as.setText(get_branch);

                    final String get_admission_year  = classdbss.child("Admission_Year").getValue().toString();
                    tv_num_attend_lec_as.setText(get_admission_year);


                    final String get_section  = classdbss.child("Section").getValue().toString();
                    tv_name_as.setText(get_section);



                    final String get_subject_name  = classdbss.child("Subject_Name").getValue().toString();
                    tv_subject_name_as.setText(get_subject_name);

                    final String get_number_of_lectures = classdbss.child("Number_of_lectures").getValue().toString();

                    int number_of_lectures   =  Integer.parseInt(get_number_of_lectures) + 1;

                    FirebaseDatabase.getInstance().getReference("Teacher Classes").child(teacher_id_str).child(class_str)
                            .child("Number_of_lectures").setValue(String.valueOf(number_of_lectures));



                    get_stu_details(get_course,get_branch,get_admission_year,get_section);

                    break;

                }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });









    }

    private void get_stu_details(final String get_course_p, final String get_branch_p, final String get_admission_year_p, final String get_section_p) {


        // this database ref will work on loop for all student attendance


        student_db.child(get_course_p).child(get_branch_p).child(get_admission_year_p).child("Section").child(get_section_p).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    for (DataSnapshot stu_admission_no_ss : snapshot.getChildren()){

                        final String get_stu_admission_no_str_db =  stu_admission_no_ss.getKey();


                        int add_in_array_int  = Integer.parseInt(get_stu_admission_no_str_db);

                        // function for add item in array
                        if (stu_add_no_array.size() == 0){

                            stu_add_no_array.add(add_in_array_int);
                        }
                        else {
                            add_in_array_fun(add_in_array_int);
                        }

                    }


                    get_student_one_by_one(get_course_p, get_branch_p, get_admission_year_p, get_section_p);

                }
            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




    }


    private void add_in_array_fun(int add_in_array_int_p) {

        for (int element : stu_add_no_array) {

            if (element != add_in_array_int_p) {
                stu_add_no_array.add(add_in_array_int_p);
            }
            break;
        }


    }


    private void get_student_one_by_one(String get_course_p,String get_branch_p, String get_admission_year_p,String get_section_p) {


        final  int array_size_int  = stu_add_no_array.size()-1;
        i++;

        if (i <= (array_size_int)) {


            int element = stu_add_no_array.get(i);

            stu_admission_no_str = String.valueOf(element);


            // get student details from stu detail  node

            get_stu_basic_details(stu_admission_no_str,get_course_p,get_branch_p,get_admission_year_p);  // ok done working

            tv_clg_rollno_as.setText(stu_admission_no_str);



            // get total attendance from stu detail  node

            get_all_attendance(stu_admission_no_str,get_course_p,get_branch_p,get_admission_year_p,get_section_p);

            current_student_class_db_for_attendence = FirebaseDatabase.getInstance().getReference("Student Classes").child(get_course_p).child(get_branch_p).child(get_admission_year_p)
                    .child("Section").child(get_section_p).child(stu_admission_no_str).child("Attendance");


            swipe(get_course_p, get_branch_p, get_admission_year_p, get_section_p);

        }

        else {

            Intent teacher_home_2 = new Intent(Take_Attendance_Activity.this,Teacher_Home_Activity.class);
            teacher_home_2.putExtra("Teacher_Id",teacher_id_str);
            teacher_home_2.putExtra("Email_Id",teacher_email_str);
            startActivity(teacher_home_2);
            finish();

        }

    }



    private void get_all_attendance(String stu_admission_no_str, String get_course_p, String get_branch_p, String get_admission_year_p, String get_section_p) {


        // all over attendance  ( all subjects)

        // ok working

        student_db.child(get_course_p).child(get_branch_p).child(get_admission_year_p).child("Section").child(get_section_p).child(stu_admission_no_str).child("Attendance")
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot attendancedb : snapshot.getChildren()) {

                    sub_attend_lec_string = "123456789";

                    sub_attend_lec_string = ""+attendancedb.child("Subject_Attendance").getValue();
                    sub_attend_lec_int = Integer.parseInt(sub_attend_lec_string);



                    total_attendance = total_attendance + sub_attend_lec_int;                // add in loop

                   subject_total_lec =  ""+ attendancedb.child("Total_Subject_Lecture").getValue().toString();
                   sub_total_lec_int  = Integer.parseInt(subject_total_lec);

                   total_lecture = total_lecture + sub_total_lec_int;                    // add in loop


                }

                int total_attendance_set = 0;
                                        
                if (total_lecture != 0) {

                    total_attendance_set = total_attendance * 100 / total_lecture;
                }

                if (total_attendance_set <70 && total_attendance_set > 50){

                    tv_total_attendance_as.setBackgroundColor(getColor(android.R.color.holo_orange_dark));
                }

               else if (total_attendance_set < 50 && total_attendance_set > 0){

                    tv_total_attendance_as.setBackgroundColor(getColor(android.R.color.holo_red_dark));
                }

                tv_total_attendance_as.setText(String.valueOf(total_attendance_set));


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // get current subject attendance lecture

        // ok working


        student_db.child(get_course_p).child(get_branch_p).child(get_admission_year_p).child("Section").child(get_section_p).child(stu_admission_no_str).child("Attendance").child(teacher_id_str)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                    current_sub_total_lec_str = snapshot.child("Total_Subject_Lecture").getValue().toString();
                    current_sub_total_lec_int = Integer.parseInt(current_sub_total_lec_str);

                    current_sub_attend_lec_str = snapshot.child("Subject_Attendance").getValue().toString();
                    current_sub_attend_lec_int = Integer.parseInt(current_sub_attend_lec_str);
                    
                        int current_sub_attend_per = 0;
                    
                    if (current_sub_total_lec_int != 0) {

                         current_sub_total_lec_int = current_sub_attend_lec_int * 100 / current_sub_total_lec_int;

                        if (current_sub_total_lec_int < 70 && current_sub_total_lec_int > 50) {

                            tv_subject_attendance_as.setBackgroundColor(getColor(android.R.color.holo_orange_dark));
                        } else if (current_sub_total_lec_int < 50 && current_sub_total_lec_int > 0) {

                            tv_subject_attendance_as.setBackgroundColor(getColor(android.R.color.holo_red_dark));
                        }
                    }

                    tv_subject_attendance_as.setText(String.valueOf(current_sub_total_lec_int));
                    tv_num_attend_lec_as.setText(String.valueOf(current_sub_attend_lec_int));



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        // not need of showing current semester


//        FirebaseDatabase.getInstance().getReference("Student Details").child(get_course_p).child(get_branch_p).child(get_admission_year_p).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                sem = snapshot.child("Current_Semester").getValue().toString();
//                sem =  sem + "_Sem";
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });






    }




    private void get_stu_basic_details(String stu_admission_no_str_p, String get_course_p, String get_branch_p, String get_admission_year_p) {


        student_details_db.child(get_course_p).child(get_branch_p).child(get_admission_year_p).child("Students").child(stu_admission_no_str_p)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                            if (snapshot.exists()){


                                    final String name_abc = snapshot.child("Name").getValue().toString();
                                    tv_name_as.setText(name_abc);

                                    final  String branch_abc = snapshot.child("Branch").getValue().toString();
                                    tv_branch_as.setText(branch_abc);


                                    String photo_str = snapshot.child("Image").getValue().toString();

                                    Uri photo_uri = Uri.parse(photo_str);

                                    try {

                                        Glide.with(getApplicationContext()).load(photo_uri).circleCrop().into(tv_stu_image);
                                    }
                                    catch (Exception e){

                                        Glide.with(getApplicationContext()).load(R.drawable.ic_person).circleCrop().into(tv_stu_image);
                                    }


                            }


                        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }




    @SuppressLint("ClickableViewAccessibility")
    private void swipe(final String get_course_p, final String get_branch_p, final String get_admission_year_p, final String get_section_p) {

        layout.setOnTouchListener(new OnSwipeTouchListener(Take_Attendance_Activity.this) {
            @SuppressLint("ClickableViewAccessibility")
            @Override
            public void onSwipeLeft() {
                super.onSwipeLeft();

//                 present on left swipe
                 stu_present();

                layout.startAnimation(anim_L);

//                Toast.makeText(Take_Attendance_Activity.this, "Swipe Left gesture Present", Toast.LENGTH_SHORT).show();

            }


            @Override
            public void onSwipeRight() {
                super.onSwipeRight();
                // absence on right swipe
                // stu_absence();

//                Toast.makeText(Take_Attendance_Activity.this, "Swipe Right gesture detected", Toast.LENGTH_SHORT).show();
                Toast.makeText(Take_Attendance_Activity.this, "Not Any Action Assigned", Toast.LENGTH_SHORT).show();

            }


            @Override
            public void onSwipeUp() {
                super.onSwipeUp();
                // present on up swipe

                 stu_present();

//                Toast.makeText(Take_Attendance_Activity.this, "Swipe Up gesture detected", Toast.LENGTH_SHORT).show();

            }


            @Override
            public void onSwipeDown() {
                super.onSwipeDown();
                // absence on down swipe

                 stu_absence();

                layout.startAnimation(anim_D);

//                Toast.makeText(Take_Attendance_Activity.this, "Swipe Down gesture detected", Toast.LENGTH_SHORT).show();

            }


            private void stu_present() {


                current_student_class_db_for_attendence.child(teacher_id_str).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        String sub_att_add_str;

                        String sub_total_att_add_str;

                        sub_att_add_str = snapshot.child("Subject_Attendance").getValue().toString();

                        int sub_att_add_int = Integer.parseInt(sub_att_add_str) + 1;

                        sub_total_att_add_str =  snapshot.child("Total_Subject_Lecture").getValue().toString();

                        final int sub_total_att_add_int  = Integer.parseInt(sub_total_att_add_str) + 1 ;

                        // increase attendance by 1
                        current_student_class_db_for_attendence.child(teacher_id_str).child("Subject_Attendance").setValue(String.valueOf(sub_att_add_int));
                        // increase subject lecture count by 1
                        current_student_class_db_for_attendence.child(teacher_id_str).child("Total_Subject_Lecture").setValue(String.valueOf(sub_total_att_add_int)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                get_student_one_by_one(get_course_p, get_branch_p,  get_admission_year_p, get_section_p);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }

            private void stu_absence() {
                current_student_class_db_for_attendence.child(teacher_id_str).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {


                        String sub_att_add_str;

                        String sub_total_att_add_str;

                        sub_att_add_str = snapshot.child("Subject_Attendance").getValue().toString();

                        int sub_att_add_int = Integer.parseInt(sub_att_add_str);

                        sub_total_att_add_str =  snapshot.child("Total_Subject_Lecture").getValue().toString();

                        final int sub_total_att_add_int  = Integer.parseInt(sub_total_att_add_str) + 1 ;

                        // increase attendance by 1
                        current_student_class_db_for_attendence.child(teacher_id_str).child("Subject_Attendance").setValue(String.valueOf(sub_att_add_int));
                        // increase subject lecture count by 1
                        current_student_class_db_for_attendence.child(teacher_id_str).child("Total_Subject_Lecture").setValue(String.valueOf(sub_total_att_add_int)).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                get_student_one_by_one(get_course_p, get_branch_p,  get_admission_year_p, get_section_p);

                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                            }
                        });
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }

        });

    }
}