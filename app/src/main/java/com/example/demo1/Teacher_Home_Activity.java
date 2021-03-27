package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.ValueAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Teacher_Home_Activity extends AppCompatActivity {

    ImageView teacher_image;
    TextView tv_teacher_name,tv_teacher_department,tv_teacher_id,sel_class,tv_current_period,tv_current_period_h;
    TextView class_1,class_2,class_3,class_4,class_5;
    LinearLayout sel_class_layout;
    Button all_info_btn;

    String teacher_id_str = "";
    String class_1_str,class_2_str,class_3_str,class_4_str,class_5_str;

    FirebaseUser currentUser;
    String currentUser_email;
    DatabaseReference teacher_details_db,teacher_db;

    String teacher_name_str,teacher_department_str,teacher_email_str;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher__home_);

        teacher_image = findViewById(R.id.teacher_image_id);
        tv_teacher_name = findViewById(R.id.tv_teacher_name);
        tv_teacher_department = findViewById(R.id.tv_teacher_department);
        tv_teacher_id = findViewById(R.id.tv_teacher_id);
         sel_class = findViewById(R.id.sel_class);
         tv_current_period = findViewById(R.id.tv_current_period);
         tv_current_period_h = findViewById(R.id.tv_current_period_h);
         class_1 = findViewById(R.id.class_1);
         class_2 = findViewById(R.id.class_2);
         class_3 = findViewById(R.id.class_3);
         class_4 = findViewById(R.id.class_4);
         class_5 = findViewById(R.id.class_5);
         sel_class_layout = findViewById(R.id.class_sel_layout);

         all_info_btn = findViewById(R.id.btn_all_info);

         // get data from incoming intent

        teacher_id_str = getIntent().getExtras().getString("Teacher_Id")+"";
        teacher_email_str = getIntent().getExtras().getString("Email_Id")+"";

        teacher_details_db = FirebaseDatabase.getInstance().getReference("Teachers Details");
        teacher_db =  FirebaseDatabase.getInstance().getReference("Teacher Classes");



        sel_class_layout.setVisibility(View.GONE);

        tv_teacher_id.setText(teacher_id_str);


        currentUser = FirebaseAuth.getInstance().getCurrentUser();
        currentUser_email = currentUser.getEmail();


        all_info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent all_info_intent = new Intent(Teacher_Home_Activity.this,About_Activity.class);

                all_info_intent.putExtra("Person","Teacher");
                all_info_intent.putExtra("Id_No",teacher_id_str);
                all_info_intent.putExtra("Email",teacher_email_str);
                startActivity(all_info_intent);

            }
        });



         sel_class.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 sel_class_layout.setVisibility(View.VISIBLE);

             }
         });


        Query query = teacher_details_db.orderByChild("Teacher_Id_No").equalTo(teacher_id_str);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){

                    for (DataSnapshot teacher_db_snapshot : snapshot.getChildren()){


                        String photo_str = ""+teacher_db_snapshot.child("Image").getValue();
                        Uri photo_uri = Uri.parse(photo_str);


                        try {
                            Glide.with(getApplicationContext()).load(photo_uri).circleCrop().into(teacher_image);
                        }
                        catch (Exception e){
                            Glide.with(getApplicationContext()).load(R.drawable.ic_person).circleCrop().into(teacher_image);
                        }

                        teacher_name_str = ""+teacher_db_snapshot.child("Name").getValue();
                        tv_teacher_name.setText(teacher_name_str);

                        teacher_id_str = ""+teacher_db_snapshot.child("Teacher_Id_No").getValue();
                        tv_teacher_id.setText(teacher_id_str);

                        teacher_department_str = ""+teacher_db_snapshot.child("Department").getValue();
                        tv_teacher_department.setText(teacher_department_str);


                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        teacher_db.child(teacher_id_str).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){

                    int i = 1;

                    for (DataSnapshot xyz : snapshot.getChildren()){

                        String a = xyz.getKey();


                        if (i == 1 ){
                        class_1.setText(a);
                        class_1_str = a;
                        class_1.isClickable();

                        }
                        if (i == 2 ){
                            class_2.setText(a);
                            class_2_str = a;
                            class_2.isClickable();

                        }
                        if (i == 3 ){
                            class_3.setText(a);
                            class_3_str = a;
                            class_3.isClickable();

                        }
                        if (i == 4 ){
                            class_4.setText(a);
                            class_4_str = a;
                            class_4.isClickable();

                        }
                        if (i == 5 ){
                            class_5.setText(a);
                            class_5_str = a;
                            class_5.isClickable();

                        }

                        i++;

                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        class_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goto_class(class_1_str);
            }
        });

        class_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goto_class(class_2_str);
            }
        });

        class_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goto_class(class_3_str);
            }
        });

        class_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goto_class(class_4_str);
            }
        });
        class_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                goto_class(class_5_str);
            }
        });



    }

    private void goto_class(String class__str) {

        final Intent dailog_sel_intet = new Intent(Teacher_Home_Activity.this, List_all_stu_in_Class_Activity.class);

        dailog_sel_intet.putExtra("Teacher_id_str",teacher_id_str);
        dailog_sel_intet.putExtra("Class_str",class__str);

        final Intent dailog_attendence_intet = new Intent(Teacher_Home_Activity.this, Take_Attendance_Activity.class);

            dailog_attendence_intet.putExtra("Teacher_id_str",teacher_id_str);
            dailog_attendence_intet.putExtra("Teacher_email_id_str",teacher_email_str);
            dailog_attendence_intet.putExtra("Class_str",class__str);


        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                Teacher_Home_Activity.this);

        alertDialog.setTitle("What do you want");

        alertDialog.setMessage("What do you want of "+class__str+" Students");

        alertDialog.setIcon(R.drawable.ic_person);


        alertDialog.setPositiveButton("Attendance",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        startActivity(dailog_attendence_intet);

                    }
                });


        alertDialog.setNegativeButton("List",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {


                        startActivity(dailog_sel_intet);
                        dialog.cancel();
                    }
                });

        alertDialog.show();





    }
}