package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Instrumentation;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Add_Stream_branch extends AppCompatActivity {

    TextView sel_year,sel_branch;
    TextView sel_ed_course,sel_ed_branch;
    TextView course_be,course_poly,course_pharma,course_bsc;
    TextView branch_cse,branch_civil,branch_mech,branch_electrical,branch_it;
    EditText sel_ed_add_year;
    LinearLayout branch_sel_layout,course_sel_layout;
    CheckBox checkBox;
    RelativeLayout addno_branch_rl;

    Button process,btn_sel_pdf;

    DatabaseReference student_db;
    StorageReference pdf_storage_ref;

    String get_type = "add_student";
    String sel_course_y_n = "no";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__stream_branch);

        sel_branch = findViewById(R.id.sel_branch);
        sel_year = findViewById(R.id.sel_year);

        sel_ed_course = findViewById(R.id.sel_ed_course);
        sel_ed_branch = findViewById(R.id.sel_ed_branch);

        course_be = findViewById(R.id.course_be);
        course_poly = findViewById(R.id.course_poly);
        course_pharma = findViewById(R.id.course_pharma);
        course_bsc = findViewById(R.id.course_bsc);

        branch_cse = findViewById(R.id.branch_cse);
        branch_civil = findViewById(R.id.branch_civil);
        branch_mech = findViewById(R.id.branch_mech);
        branch_electrical = findViewById(R.id.branch_electrical);
        branch_it = findViewById(R.id.branch_it);

        sel_ed_add_year = findViewById(R.id.sel_ed_add_year);
        branch_sel_layout = findViewById(R.id.branch_sel_layout);
        course_sel_layout = findViewById(R.id.course_sel_layout);

        process = findViewById(R.id.process);
        checkBox = findViewById(R.id.checkBox);

        addno_branch_rl = findViewById(R.id.addno_branch_rl);
        btn_sel_pdf = findViewById(R.id.sel_pdf);

        process.setVisibility(View.GONE);

        get_type = getIntent().getExtras().getString("type");


        pdf_storage_ref = FirebaseStorage.getInstance().getReference();

        if (get_type.equals("ct_tt")){

            ct_tt(sel_ed_course.getText().toString());

        }

        String admission_year = sel_ed_add_year.getText().toString();
        sel_ed_add_year.setText(admission_year);


        sel_ed_course.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sel_year.setVisibility(View.GONE);
                sel_ed_add_year.setVisibility(View.GONE);
                sel_branch.setVisibility(View.GONE);
                sel_ed_branch.setVisibility(View.GONE);
                branch_sel_layout.setVisibility(View.GONE);
                checkBox.setVisibility(View.GONE);
                process.setVisibility(View.GONE);

                course_sel_layout.setVisibility(View.VISIBLE);

                course_be.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        sel_ed_course.setText("BE");

                        after_sel_course();
                    }
                });

                course_poly.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        sel_ed_course.setText("Polytechnic");

                        after_sel_course();
                    }
                });

                course_pharma.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        sel_ed_course.setText("Pharmacy");

                        after_sel_course();
                    }
                });

                course_bsc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        sel_ed_course.setText("BSC");

                        after_sel_course();
                    }
                });



            }

            private void after_sel_course() {

                sel_year.setVisibility(View.VISIBLE);
                sel_ed_add_year.setVisibility(View.VISIBLE);
                sel_branch.setVisibility(View.VISIBLE);
                sel_ed_branch.setVisibility(View.VISIBLE);
                checkBox.setVisibility(View.VISIBLE);

                course_sel_layout.setVisibility(View.GONE);
                
                sel_course_y_n = "yes";


            }
        });




        sel_ed_branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                branch_sel_layout.setVisibility(View.VISIBLE);


                branch_civil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sel_ed_branch.setText("Civil Engineering");
                        branch_sel_layout.setVisibility(View.GONE);
                    }
                });

                branch_cse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sel_ed_branch.setText("Computer Science Engineering");
                        branch_sel_layout.setVisibility(View.GONE);
                    }
                });

                branch_electrical.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sel_ed_branch.setText("Electrical Engineering");
                        branch_sel_layout.setVisibility(View.GONE);
                    }
                });

                branch_it.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sel_ed_branch.setText("Information Technology Engineering");
                        branch_sel_layout.setVisibility(View.GONE);
                    }
                });

                branch_mech.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sel_ed_branch.setText("Mechanical Engineering");
                        branch_sel_layout.setVisibility(View.GONE);
                    }
                });

            }



        });








        final Intent goto_add_stu_b_details = new Intent(this,Add_stu_B_details.class);
        final Intent goto_allot_teacher = new Intent(this,Allot_Teacher_Activity.class);
        final Intent add_new_tt_intent = new Intent(Add_Stream_branch.this,Add_new_tt_Activity.class);


        student_db = FirebaseDatabase.getInstance().getReference("Student Details")
                .child(sel_ed_course.getText().toString()).child(sel_ed_branch.getText().toString()).child(sel_ed_add_year.getText().toString());



        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
           @Override
           public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked ){
                   process.setVisibility(View.VISIBLE);

               }

          process.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                       if (get_type.equals("allot_teacher")){

                           student_db.addValueEventListener(new ValueEventListener() {
                               @Override
                               public void onDataChange(@NonNull DataSnapshot snapshot) {

                                   if (snapshot.exists()){

                                       goto_allot_teacher.putExtra("course", sel_ed_course.getText().toString());
                                       goto_allot_teacher.putExtra("branch", sel_ed_branch.getText().toString());
                                       goto_allot_teacher.putExtra("admission_year", sel_ed_add_year.getText().toString());

                                       Add_Stream_branch.this.startActivity(goto_allot_teacher);
                                   }

                                   else {
                                       Toast.makeText(Add_Stream_branch.this, "Class does not exists", Toast.LENGTH_SHORT).show();
                                   }

                               }

                               @Override
                               public void onCancelled(@NonNull DatabaseError error) {

                               }
                           }) ;




                       }

                       else if (get_type.equals("add_student")) {

                           goto_add_stu_b_details.putExtra("course", sel_ed_course.getText().toString());
                           goto_add_stu_b_details.putExtra("branch", sel_ed_branch.getText().toString());
                           goto_add_stu_b_details.putExtra("admission_year", sel_ed_add_year.getText().toString());
                           goto_add_stu_b_details.putExtra("Person", "student");

                           Add_Stream_branch.this.startActivity(goto_add_stu_b_details);
                       }


                       else if(get_type.equals("new_tt")){



                           add_new_tt_intent.putExtra("course", sel_ed_course.getText().toString());
                           add_new_tt_intent.putExtra("branch", sel_ed_branch.getText().toString());
                           add_new_tt_intent.putExtra("admission_year", sel_ed_add_year.getText().toString());
                           add_new_tt_intent.putExtra("tt_action",get_type);

                           Add_Stream_branch.this.startActivity(add_new_tt_intent);


                        }

                       else if (get_type.equals("change_sem")){

                         change_sem_fun();

                       }



                   }
               });


           }
       });


    }

    private void change_sem_fun() {


        student_db = student_db.child(sel_ed_course.getText().toString()).child(sel_ed_branch.getText().toString())
                .child(sel_ed_add_year.getText().toString());

        student_db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    String sem = snapshot.child("Current_Semester").getValue().toString();

                    int i = Integer.parseInt(sem) + 1;

                    if (i < 9){

                        sem = String.valueOf(i);

                        student_db.child("Current_Semester").setValue(sem).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                startActivity(new Intent(Add_Stream_branch.this, Home_Activity.class));
                                finish();

                            }
                        });
                    }
                    else {

                        Toast.makeText(Add_Stream_branch.this, "Students are in Final Semester", Toast.LENGTH_SHORT).show();
                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }



    private void ct_tt(String sel_course) {
        addno_branch_rl.setVisibility(View.GONE);
        btn_sel_pdf.setVisibility(View.VISIBLE);

        

            btn_sel_pdf.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (sel_course_y_n.equals("yes")) {
                        Intent sel_pdf_intent = new Intent();
                        sel_pdf_intent.setType("application/pdf");
                        sel_pdf_intent.setAction(sel_pdf_intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(sel_pdf_intent, "Select PDF File"), 12);

                    }
                    else if(sel_course_y_n.equals("no")) {
                        Toast.makeText(Add_Stream_branch.this, "Please Select Course", Toast.LENGTH_SHORT).show();
                    }

                    else {
                        Toast.makeText(Add_Stream_branch.this, "String is corrupted", Toast.LENGTH_SHORT).show();

                    }
                }
            });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if (requestCode==12 && resultCode==RESULT_OK && data != null && data.getData() != null){
            upload_pdf(data.getData());
        }

    }

    private void upload_pdf(Uri data) {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("File is Uploading");
        progressDialog.show();

        pdf_storage_ref = pdf_storage_ref.child(sel_ed_course.getText().toString()+" CT Time Table")
                .child(sel_ed_course.getText().toString()+"_CT_Time_Table"+".pdf");

        // save pdf in storage database


        pdf_storage_ref.putFile(data).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri uri = uriTask.getResult();

                // save pdf link in realtime database
                pdf_events putPdf = new pdf_events(sel_ed_course.getText().toString()+"_CT_Time_Table"+".pdf",uri.toString());
                DatabaseReference ct_tt_db_ref = FirebaseDatabase.getInstance().getReference("Ct_tt");
                ct_tt_db_ref.child(sel_ed_course.getText().toString()).setValue(putPdf);


                Toast.makeText(Add_Stream_branch.this, "File Uploaded", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
                finish();
                startActivity(new Intent(Add_Stream_branch.this,Home_Activity.class));
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progress = 100 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount();
                progressDialog.setMessage("File Uploaded.."+(int) progress + " %" );
            }
        }).addOnCanceledListener(new OnCanceledListener() {
            @Override
            public void onCanceled() {

            }
        });

    }

}

