package com.example.demo1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class Home_Activity extends AppCompatActivity {

    Button add_stu_button;
    Button add_teacher_button;
    Button change_sem_button;
    Button modify_stu_details_button;
    Button process_button;
    Button ct_tt;
    Button allot_teacher_button;
    Button tt_modification_button;
    Button partially_tt_modification_button;
    Button permanent_tt_modification_button;
    Button add_new_tt_modification_button;
    Button other_btn_show;
    RelativeLayout other_btn_rl,modify_stu_sec_rl;

    TextView tv1;
    EditText m_edit_stu_clg_add_no;

    Animation anim_D_1,anim_D_2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_);



        add_stu_button = findViewById(R.id.add_stu_button);
        add_teacher_button = findViewById(R.id.add_teacher_button);
        change_sem_button = findViewById(R.id.change_sem_button);
        modify_stu_details_button = findViewById(R.id.modify_stu_details_button);
        allot_teacher_button = findViewById(R.id.allot_teacher_button);

        ct_tt = findViewById(R.id.ct_tt_button);
        tt_modification_button = findViewById(R.id.tt_modification_button);
        add_new_tt_modification_button = findViewById(R.id.add_new_tt_modification_button);
        partially_tt_modification_button= findViewById(R.id.partially_tt_modification_button);
        permanent_tt_modification_button = findViewById(R.id.permanent_tt_modification_button);

        partially_tt_modification_button.setVisibility(View.GONE);
        permanent_tt_modification_button.setVisibility(View.GONE);
        add_new_tt_modification_button.setVisibility(View.GONE);

        process_button = findViewById(R.id.process_button);

        other_btn_rl = findViewById(R.id.other_btn);
        modify_stu_sec_rl = findViewById(R.id.modify_stu_sec);
        modify_stu_sec_rl.setVisibility(View.GONE);

        other_btn_show = findViewById(R.id.other_btn_show);

        anim_D_1 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_comes_down);

        anim_D_2 = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_2_comes_down);

        anim_D_1.setAnimationListener(new Animation.AnimationListener() {
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



        anim_D_2.setAnimationListener(new Animation.AnimationListener() {
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



        m_edit_stu_clg_add_no = findViewById(R.id.m_edit_stu_clg_add_no);
        tv1 = findViewById(R.id.tv1);

        process_button.setVisibility(View.GONE);
        m_edit_stu_clg_add_no.setVisibility(View.GONE);
        tv1.setVisibility(View.GONE);


        add_stu_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent add_student_intent = new Intent(Home_Activity.this,Add_Stream_branch.class);
                add_student_intent.putExtra("type","add_student");

                startActivity(add_student_intent);



            }
        });

        add_teacher_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent add_teacher_intent = new Intent(Home_Activity.this,Add_stu_B_details.class);

                add_teacher_intent.putExtra("course", " ");
                add_teacher_intent.putExtra("branch", " ");
                add_teacher_intent.putExtra("admission_year"," ");
                add_teacher_intent.putExtra("Person","teacher");

                startActivity(add_teacher_intent);

            }
        });

        allot_teacher_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent allort_teacher_intent  = new Intent(Home_Activity.this,Add_Stream_branch.class);
                allort_teacher_intent.putExtra("type","allot_teacher");
                startActivity(allort_teacher_intent);


            }
        });

        change_sem_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               Intent change_sem_intent = new Intent(Home_Activity.this,Add_Stream_branch.class);
               change_sem_intent.putExtra("type","change_sem");
                startActivity(change_sem_intent);

            }
        });


        modify_stu_details_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                other_btn_rl.setVisibility(View.GONE);
                modify_stu_sec_rl.setVisibility(View.VISIBLE);
                process_button.setVisibility(View.VISIBLE);
                m_edit_stu_clg_add_no.setVisibility(View.VISIBLE);
                tv1.setVisibility(View.VISIBLE);

            }
        });


        other_btn_show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                other_btn_rl.setVisibility(View.VISIBLE);
                modify_stu_sec_rl.setVisibility(View.GONE);

            }
        });

        ct_tt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ct_tt_intent = new Intent(Home_Activity.this,Add_Stream_branch.class);
                ct_tt_intent.putExtra("type","ct_tt");
                startActivity(ct_tt_intent);
            }
        });

        process_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String m_stu_add_no_str = m_edit_stu_clg_add_no.getText().toString();


//                startActivity(new Intent(Home_Activity.this,Add_Stream_branch.class));

                Toast.makeText(Home_Activity.this, "Will be Available Soon", Toast.LENGTH_SHORT).show();

            }
        });




        tt_modification_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    add_new_tt_modification_button.setVisibility(View.VISIBLE);
                    partially_tt_modification_button.setVisibility(View.VISIBLE);
                    permanent_tt_modification_button.setVisibility(View.VISIBLE);

                    add_new_tt_modification_button.startAnimation(anim_D_1);
                    permanent_tt_modification_button.startAnimation(anim_D_2);
                    partially_tt_modification_button.startAnimation(anim_D_2);



            }
        });



        add_new_tt_modification_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent add_new_tt_intent = new Intent(Home_Activity.this,Add_Stream_branch.class);

//                add_new_tt_intent.putExtra("type","new_tt");
//                add_new_tt_intent.putExtra("modification_type","new");
//                startActivity(add_new_tt_intent);



                AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home_Activity.this);

                alertDialog.setTitle("Alert");

                alertDialog.setMessage("This Service will Update Soon");

                alertDialog.show();

            }
        });

        partially_tt_modification_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alertDialog_aa = new AlertDialog.Builder(
                        Home_Activity.this);

                alertDialog_aa.setTitle("Alert !");

                alertDialog_aa.setMessage("This Service will Update Soon");

                alertDialog_aa.show();

            }
        });


        permanent_tt_modification_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Home_Activity.this);

                alertDialog.setTitle("Alert !");

                alertDialog.setMessage("Do You want to Change Time Table Permanently ?");

                alertDialog.setIcon(R.drawable.ic_person);


                alertDialog.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                alertDialog.setMessage("This Service will Update Soon");

                            }
                        });


                alertDialog.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {


                                dialog.cancel();
                            }
                        });

                alertDialog.show();


            }
        });

    }
}