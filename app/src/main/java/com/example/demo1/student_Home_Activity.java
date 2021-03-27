package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Date;

public class student_Home_Activity extends AppCompatActivity {

    ImageView stu_image_id;
    TextView tv_name, tv_course, tv_branch, tv_add_no, tv_sem_no, tv_section, tv_university_rollno, tv_attendance, tv_email, tv_date_time;
    Button btn_timetable, btn_ct_details, btn_all_info,btn_signout;
    RelativeLayout attendance_layout;

    FirebaseUser currentUser;

    public String currentUser_email;
    private GoogleSignInClient googleSignInClient;


    public String email_by_intent = "";
    public  String course_by_intent = "";
    public String branch_by_intent = "";
    public String ad_year_by_intent = "";
    public  String clg_rollno_by_intent = "";

    DatabaseReference student_details_db,student_class_db;

    public String name, branch, email, college_rollno, section, university_rollno,semester;

    public  String sub_attend_lec_string,subject_total_lec;
    public int total_attendance = 0,total_lecture = 0 ,sub_attend_lec_int = 0,sub_total_lec_int = 0;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__home_);

        stu_image_id = findViewById(R.id.stu_image_id);
        tv_name = findViewById(R.id.tv_name);
        tv_name.setSelected(true);
        tv_course = findViewById(R.id.tv_course);
        tv_branch = findViewById(R.id.tv_branch);
        tv_branch.setSelected(true);
        tv_email = findViewById(R.id.tv_email);
        tv_add_no = findViewById(R.id.tv_add_no);
        tv_sem_no = findViewById(R.id.tv_sem_no);
        tv_section = findViewById(R.id.tv_section);
        tv_university_rollno = findViewById(R.id.tv_university_rollno);
        tv_attendance = findViewById(R.id.tv_attendance);
        tv_date_time = findViewById(R.id.tv_date_time);

        btn_timetable = findViewById(R.id.btn_timetable);
        btn_ct_details = findViewById(R.id.btn_ct_details);
        btn_all_info = findViewById(R.id.btn_all_info);
        btn_signout = findViewById(R.id.btn_signout);

        attendance_layout = findViewById(R.id.attendance_layout);



        email_by_intent = getIntent().getExtras().getString("Email");
        branch_by_intent = getIntent().getExtras().getString("Branch");
        ad_year_by_intent = getIntent().getExtras().getString("Admission_Year");
        clg_rollno_by_intent = getIntent().getExtras().getString("College_RollNo");
        course_by_intent = getIntent().getExtras().getString("Course");


        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUser_email = currentUser.getEmail();

        GoogleSignInOptions googleSignInOptions  = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = (GoogleSignInClient) GoogleSignIn.getClient(this,googleSignInOptions);


        student_class_db = FirebaseDatabase.getInstance().getReference("Student Classes");
        student_details_db = FirebaseDatabase.getInstance().getReference("Student Details");

        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        tv_date_time.setText(currentDateTimeString);


        if (currentUser_email.equals(email_by_intent)) {

            student_details_db.child(course_by_intent).child(branch_by_intent).child(ad_year_by_intent).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()){

                        semester = snapshot.child("Current_Semester").getValue().toString();
                        tv_sem_no.setText(semester);

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


            student_details_db.child(course_by_intent).child(branch_by_intent).child(ad_year_by_intent).child("Students").child(clg_rollno_by_intent).
                    addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    if (snapshot.exists()) {


                        final String photo_str = snapshot.child("Image").getValue().toString();
                        Uri photo_uri = Uri.parse(photo_str);


                        try {
                            Glide.with(getApplicationContext()).load(photo_uri).circleCrop().into(stu_image_id);
                        }
                        catch (Exception e){
                            Glide.with(getApplicationContext()).load(R.drawable.ic_person).circleCrop().into(stu_image_id);
                        }

                            name = snapshot.child("Name").getValue().toString();
                            branch = snapshot.child("Branch").getValue().toString();
                            college_rollno = snapshot.child("College_RollNo").getValue().toString();
                            email = "" + snapshot.child("Email_Id").getValue();
                            section = snapshot.child("Section").getValue().toString();
                            university_rollno = "" + snapshot.child("University_RollNo").getValue();

                            tv_name.setText(name);
                            tv_course.setText(course_by_intent);
                            tv_branch.setText(branch);
                            tv_email.setText(email);
                            tv_add_no.setText(college_rollno);
                            tv_university_rollno.setText(university_rollno);
                            tv_section.setText(section);


                        student_class_db.child(course_by_intent).child(branch_by_intent).child(ad_year_by_intent).child("Section").child(section).child(college_rollno).child("Attendance")
                                .addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                        for (DataSnapshot attendancedb : snapshot.getChildren()) {

                                            sub_attend_lec_string = "1";
//
                                            sub_attend_lec_string = ""+attendancedb.child("Subject_Attendance").getValue().toString();

                                             sub_attend_lec_int = Integer.parseInt(sub_attend_lec_string);

                                            total_attendance = total_attendance + sub_attend_lec_int;                // add in loop

                                            subject_total_lec =  ""+attendancedb.child("Total_Subject_Lecture").getValue().toString();

                                            sub_total_lec_int  = Integer.parseInt(subject_total_lec);

                                            total_lecture = total_lecture + sub_total_lec_int;                    // add in loop


                                        }

                                        int total_attendance_set = 0;

                                        if (total_lecture != 0) {

                                             total_attendance_set = total_attendance * 100 / total_lecture;
                                        }

                                        if (total_attendance_set <70 && total_attendance_set > 50){

                                            tv_attendance.setBackgroundColor(getColor(android.R.color.holo_orange_light));
                                        }

                                        else if (total_attendance_set < 50 && total_attendance_set > 0){

                                            tv_attendance.setBackgroundColor(getColor(android.R.color.holo_orange_light));
                                        }

                                        tv_attendance.setText(String.valueOf(total_attendance_set));


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });

                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });










            btn_all_info.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent all_info_intent = new Intent(student_Home_Activity.this, About_Activity.class);

                    all_info_intent.putExtra("Person", "Student");
                    all_info_intent.putExtra("Id_No", clg_rollno_by_intent);
                    all_info_intent.putExtra("Email", email_by_intent);
                    all_info_intent.putExtra("course", course_by_intent);
                    all_info_intent.putExtra("branch", branch_by_intent);
                    all_info_intent.putExtra("admission_Year", ad_year_by_intent);


                    startActivity(all_info_intent);

                }
            });

            btn_timetable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    Intent show_tt_intent = new Intent(student_Home_Activity.this, show_tt_new_Activity.class);
//
//
//                    show_tt_intent.putExtra("course", course_by_intent);
//                    show_tt_intent.putExtra("branch", branch_by_intent);
//                    show_tt_intent.putExtra("admission_Year", ad_year_by_intent);
//                    show_tt_intent.putExtra("section", section);
//                    show_tt_intent.putExtra("semester", semester);

//                    startActivity(show_tt_intent);


                    AlertDialog.Builder alertDialog = new AlertDialog.Builder(student_Home_Activity.this);
                    alertDialog.setTitle("Alert");
                    alertDialog.setMessage("This Service will Update Soon");
                    alertDialog.show();


                }
            });



            btn_ct_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    show_option();

                }

                private void show_option() {


                    final AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                            student_Home_Activity.this);

                    alertDialog.setTitle("Hello "+name);

                    alertDialog.setMessage("What do you want");

                    alertDialog.setIcon(R.drawable.ic_person);


                    alertDialog.setPositiveButton("CT Marks",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {


                                    AlertDialog.Builder alertDialog_1 = new AlertDialog.Builder(
                                            student_Home_Activity.this);

                                    alertDialog_1.setTitle("Sorry");

                                    alertDialog_1.setMessage("This Service will update soon");
                                    alertDialog_1.show();
                                    dialog.dismiss();

                                }
                            });


                    alertDialog.setNegativeButton("Time Table",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent tt_show_intent = new Intent(student_Home_Activity.this, ct_tt_show_Activity.class);

                                    tt_show_intent.putExtra("course", course_by_intent);
                                    tt_show_intent.putExtra("branch", branch_by_intent);


                                    startActivity(tt_show_intent);

                                    dialog.cancel();
                                }
                            });

                    alertDialog.show();

                }
            });

            btn_signout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    googleSignInClient.signOut();
                    startActivity(new Intent(student_Home_Activity.this, Login_Activity.class));
                    finish();
                }
            });


        }
    }




}