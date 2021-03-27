package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

public class Add_stu_B_details extends AppCompatActivity {


    TextView course,branch,admission_year,tv_wife_or_hus_name;

   EditText edit_stu_name,edit_stu_clg_add_no,edit_stu_uni_roll_no,edit_section,edit_stu_dob,edit_stu_gender,edit_stu_phone,edit_stu_email,
           edit_stu_blood_group,edit_stu_mother_name,edit_stu_father_name,edit_stu_parent_phone,edit_current_sem,
           edit_stu_permanent_address,edit_stu_temp_address,edit_teacher_id_no,edit_teacher_department,edit_stu_cast,edit_wife_or_hus_name,edit_teacher_joining_date;

   String edit_stu_name_str,edit_stu_clg_add_no_str,edit_stu_uni_roll_no_str,edit_section_str,edit_stu_dob_str,edit_stu_gender_str,
           edit_stu_phone_str,edit_stu_email_str,edit_stu_blood_group_str,edit_stu_mother_name_str, edit_teacher_id_no_str,edit_teacher_department_str,edit_current_sem_str,
           edit_stu_father_name_str,edit_stu_parent_phone_str,edit_stu_permanent_address_str,edit_stu_temp_address_str,edit_stu_cast_str,edit_wife_or_hus_name_str,edit_teacher_joining_date_str;

    CheckBox same_address_checkbox,married_checkbox;
    Button save_next,save_close;
    ImageView stu_image;
    String downloadUri = "nothing";

    LinearLayout stu_special_layout,teacher_layout;


    String[] storagePermission;
    public static final int STORAGE_REQUEST_CODE = 200;
    public static final int IMAGE_PICK_GALLERY_REQUEST_CODE = 300;
    Uri upload_Stu_ImageURI = null;

   StorageReference stu_img_storageReference, teacher_img_storageReference,upload_img_sel_path;


   String course_name,branch_name,admissionY,Person_str;

   String married_check_str = "No";

     DatabaseReference  student_db;
     DatabaseReference verified_email_storageReference;
     DatabaseReference teacher_db;
     DatabaseReference student_class;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_stu__b_details);

        course_name = Objects.requireNonNull(getIntent().getExtras()).getString("course")+"";
        branch_name = getIntent().getExtras().getString("branch")+"";
        admissionY = getIntent().getExtras().getString("admission_year")+"";
        Person_str = getIntent().getExtras().getString("Person")+"";


        student_db = FirebaseDatabase.getInstance().getReference("Student Details")
                .child(course_name).child(branch_name).child(admissionY);

        teacher_db = FirebaseDatabase.getInstance().getReference("Teachers Details");

        student_class = FirebaseDatabase.getInstance().getReference("Student Classes")
                .child(course_name).child(branch_name).child(admissionY).child("Section");




        stu_img_storageReference  = FirebaseStorage.getInstance().getReference("Student Images")
                .child(course_name).child(branch_name).child(admissionY);



     verified_email_storageReference = FirebaseDatabase.getInstance().getReference("Verified Emails");


        course = findViewById(R.id.course);
        branch = findViewById(R.id.branch);
        admission_year = findViewById(R.id.admission_year);

        course.setText(course_name);
        branch.setText(branch_name);
        admission_year.setText(admissionY);


        edit_stu_name = findViewById(R.id.edit_stu_name);
        edit_stu_clg_add_no = findViewById(R.id.edit_stu_clg_add_no);
        edit_stu_uni_roll_no = findViewById(R.id.edit_stu_uni_roll_no);
        edit_section = findViewById(R.id.edit_section);
        edit_current_sem = findViewById(R.id.edit_current_sem);
        edit_stu_dob = findViewById(R.id.edit_stu_dob);
        edit_stu_gender = findViewById(R.id.edit_stu_gender);
        edit_stu_phone = findViewById(R.id.edit_stu_phone);
        edit_stu_email = findViewById(R.id.edit_stu_email);
        edit_stu_blood_group = findViewById(R.id.edit_stu_blood_group);
        edit_stu_mother_name = findViewById(R.id.edit_stu_mother_name);
        edit_stu_father_name = findViewById(R.id.edit_stu_father_name);
        edit_stu_parent_phone = findViewById(R.id.edit_stu_parent_phone);
        edit_stu_permanent_address = findViewById(R.id.edit_stu_permanent_address);
        edit_stu_temp_address = findViewById(R.id.edit_stu_temp_address);
        edit_teacher_id_no = findViewById(R.id.edit_teacher_id_no);
        edit_teacher_department = findViewById(R.id.edit_teacher_department);
        edit_teacher_joining_date = findViewById(R.id.edit_teacher_joining_date);
        edit_stu_cast = findViewById(R.id.edit_stu_cast);
        edit_wife_or_hus_name = findViewById(R.id.edit_wife_or_hus_name);
        tv_wife_or_hus_name = findViewById(R.id.tv_wife_or_hus_name);
        edit_teacher_joining_date = findViewById(R.id.edit_teacher_joining_date);

        stu_image = findViewById(R.id.stu_image);

        same_address_checkbox  = findViewById(R.id.same_address_checkbox);
        married_checkbox  = findViewById(R.id.married_checkbox);

        save_close = findViewById(R.id.save_close);
        save_next = findViewById(R.id.save_next);

        stu_special_layout = findViewById(R.id.stu_special_layout);
        teacher_layout = findViewById(R.id.teacher_layout);



        tv_wife_or_hus_name.setVisibility(View.GONE);
        edit_wife_or_hus_name.setVisibility(View.GONE);




        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        edit_stu_permanent_address_str = edit_stu_permanent_address.getText().toString()+" ";

        if (Person_str.equals("teacher")){

            stu_special_layout.setVisibility(View.GONE);
            teacher_layout.setVisibility(View.VISIBLE);
            save_close.setVisibility(View.GONE);
            edit_current_sem.setVisibility(View.GONE);


        }

        if (Person_str.equals("student")){

            stu_special_layout.setVisibility(View.VISIBLE);
            teacher_layout.setVisibility(View.GONE);
            married_checkbox.setVisibility(View.GONE);

        }




        stu_image.isClickable();

        stu_image.setOnClickListener(new View.OnClickListener() {


            public void onClick(View v) {

                if (!checkStoragePermission()) {
                    requsetStoragePermission();
                } else {
                    pickFromGalley();
                }


            }
        });

        if (married_checkbox.isChecked()){

            tv_wife_or_hus_name.setVisibility(View.VISIBLE);
            edit_wife_or_hus_name.setVisibility(View.VISIBLE);
            married_check_str = "Yes";

        }





        save_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                get_strings();

                if (downloadUri.equals("nothing")){


                }
                else {

                    if (Person_str.equals("teacher")) {

                        store_teacher_details();

                        startActivity(new Intent(Add_stu_B_details.this, Home_Activity.class));
                        finish();
                    } else if (Person_str.equals("student")) {

                        stu_sorted_data_db();

                        startActivity(getIntent());

                    }
                }

            }


        });

        save_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (downloadUri.equals("nothing")){


                }
                else {

                    stu_sorted_data_db();

                    startActivity(new Intent(Add_stu_B_details.this, Home_Activity.class));
                    finish();
                }

            }
        });


    }

    private void get_strings() {

        // get data in string form to edit text


        edit_stu_name_str = edit_stu_name.getText().toString()+"";
        edit_stu_clg_add_no_str = edit_stu_clg_add_no.getText().toString()+"";
        edit_stu_email_str = edit_stu_email.getText().toString()+"";
        edit_section_str = edit_section.getText().toString()+"";
        edit_stu_dob_str = edit_stu_dob.getText().toString()+"";
        edit_stu_gender_str = edit_stu_gender.getText().toString()+"";
        edit_stu_phone_str = edit_stu_phone.getText().toString()+"";
        edit_stu_uni_roll_no_str = edit_stu_uni_roll_no.getText().toString()+"";
        edit_stu_blood_group_str = edit_stu_blood_group.getText().toString()+"";
        edit_stu_mother_name_str = edit_stu_mother_name.getText().toString()+"";
        edit_stu_father_name_str = edit_stu_father_name.getText().toString()+"";
        edit_stu_parent_phone_str = edit_stu_parent_phone.getText().toString()+"";
        edit_stu_permanent_address_str = edit_stu_permanent_address.getText().toString()+"";
        edit_stu_temp_address_str = edit_stu_temp_address.getText().toString()+"";
        edit_teacher_id_no_str = edit_teacher_id_no.getText().toString()+"";
        edit_stu_cast_str = edit_stu_cast.getText().toString()+"";
        edit_teacher_department_str = edit_teacher_department.getText().toString()+"";
        edit_wife_or_hus_name_str = edit_wife_or_hus_name.getText().toString()+"";
        edit_teacher_joining_date_str = edit_teacher_joining_date.getText().toString()+"";

        if (same_address_checkbox.isChecked()){

            edit_stu_temp_address_str = edit_stu_permanent_address_str;

        }





    }


    private void stu_sorted_data_db() {

        student_db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                // Hash Map for Student Basic Details

                HashMap<Object,String> stu_details_map = new HashMap<>();

                if (downloadUri.equals("nothing")){

                    Toast.makeText(Add_stu_B_details.this, "Update Image", Toast.LENGTH_SHORT).show();

                }
                else {

                    stu_details_map.put("Name", edit_stu_name_str);
                    stu_details_map.put("Image", downloadUri);
                    stu_details_map.put("DOB",edit_stu_dob_str);
                    stu_details_map.put("Gender",edit_stu_gender_str);
                    stu_details_map.put("Course", course_name);
                    stu_details_map.put("Branch", branch_name);
                    stu_details_map.put("Email_Id", edit_stu_email_str);
                    stu_details_map.put("Section", edit_section_str);
                    stu_details_map.put("Admission_Year", admissionY);
                    stu_details_map.put("College_RollNo", edit_stu_clg_add_no_str);
                    stu_details_map.put("University_RollNo",edit_stu_uni_roll_no_str);
                    stu_details_map.put("Phone_No",edit_stu_phone_str);
                    stu_details_map.put("Blood_Group",edit_stu_blood_group_str);
                    stu_details_map.put("Mother_Name",edit_stu_mother_name_str);
                    stu_details_map.put("Father_Name",edit_stu_father_name_str);
                    stu_details_map.put("Parent_Phone_No",edit_stu_parent_phone_str);
                    stu_details_map.put("Cast",edit_stu_cast_str);
                    stu_details_map.put("Address",edit_stu_permanent_address_str);
                    stu_details_map.put("Local_Address",edit_stu_temp_address_str);

                    student_db.child("Students").child(edit_stu_clg_add_no_str).setValue(stu_details_map);

                    edit_current_sem_str = edit_current_sem.getText().toString();
                    student_db.child("Current_Semester").setValue(edit_current_sem_str);



                    verified_email_storageReference.child("Students").child(edit_stu_clg_add_no_str).child("V_Email").setValue(edit_stu_email_str);
                    verified_email_storageReference.child("Students").child(edit_stu_clg_add_no_str).child("Course").setValue(course_name);
                    verified_email_storageReference.child("Students").child(edit_stu_clg_add_no_str).child("Branch").setValue(branch_name);
                    verified_email_storageReference.child("Students").child(edit_stu_clg_add_no_str).child("Admission_Year").setValue(admissionY);
                    verified_email_storageReference.child("Students").child(edit_stu_clg_add_no_str).child("College_RollNo").setValue(edit_stu_clg_add_no_str);


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    private void store_teacher_details() {

        teacher_db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                HashMap<Object,String> teacher_details_map = new HashMap<>();

                if (downloadUri.equals("nothing")){

                    Toast.makeText(Add_stu_B_details.this, "Update Image", Toast.LENGTH_SHORT).show();

                }

                else {

                    teacher_details_map.put("Name", edit_stu_name_str);
                    teacher_details_map.put("Image", downloadUri);
                    teacher_details_map.put("DOB", edit_stu_dob_str);
                    teacher_details_map.put("Gender", edit_stu_gender_str);
                    teacher_details_map.put("Phone_No", edit_stu_phone_str);
                    teacher_details_map.put("Email_Id", edit_stu_email_str);
                    teacher_details_map.put("Blood_Group", edit_stu_blood_group_str);
                    teacher_details_map.put("Mother_Name", edit_stu_mother_name_str);
                    teacher_details_map.put("Father_Name", edit_stu_father_name_str);
                    teacher_details_map.put("Parent_Phone_No", edit_stu_parent_phone_str);
                    teacher_details_map.put("Cast", edit_stu_cast_str);
                    teacher_details_map.put("Address", edit_stu_permanent_address_str);
                    teacher_details_map.put("Local_Address", edit_stu_temp_address_str);
                    teacher_details_map.put("Teacher_Id_No", edit_teacher_id_no_str);
                    teacher_details_map.put("Department", edit_teacher_department_str);
                    teacher_details_map.put("Married", married_check_str);
                    teacher_details_map.put("Wife_or_Husband_Name", edit_wife_or_hus_name_str);
                    teacher_details_map.put("Joining_Date", edit_teacher_joining_date_str);

                    teacher_db.child(edit_teacher_id_no_str).setValue(teacher_details_map);


                    verified_email_storageReference.child("Teachers").child(edit_teacher_id_no_str).child("V_Email").setValue(edit_stu_email_str);
                    verified_email_storageReference.child("Teachers").child(edit_teacher_id_no_str).child("Teacher Id No").setValue(edit_teacher_id_no_str);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private boolean checkStoragePermission() {

        boolean result_SP;
        result_SP = ContextCompat.checkSelfPermission(Add_stu_B_details.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);

        return result_SP;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requsetStoragePermission() {

        ActivityCompat.requestPermissions(Add_stu_B_details.this,storagePermission, STORAGE_REQUEST_CODE);

    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if (grantResults.length> 0){
            boolean writeStorageAccepted = grantResults[0] ==  PackageManager.PERMISSION_GRANTED;
            if(writeStorageAccepted){
                pickFromGalley(); //permission graunted
            }
            else {

                Toast.makeText(Add_stu_B_details.this, "Please Enable Storage Permission",Toast.LENGTH_SHORT).show();


            }
        }

    }

    private void pickFromGalley() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent,IMAGE_PICK_GALLERY_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {


        if (requestCode == IMAGE_PICK_GALLERY_REQUEST_CODE && resultCode == RESULT_OK) {

            upload_Stu_ImageURI = data.getData();

//            stu_image.setPadding(10,3,10,3);


            try {
                Glide.with(getApplicationContext()).load(upload_Stu_ImageURI).circleCrop().into(stu_image);
            }
            catch (Exception e){
                Glide.with(getApplicationContext()).load(R.drawable.ic_person).circleCrop().into(stu_image);
            }



            get_strings();
            if (Person_str.equals("student")) {

                if (edit_stu_clg_add_no_str.equals(null) && edit_stu_name_str.equals(null)) {

                    Toast.makeText(this, "Enter Student Name & Admission Number", Toast.LENGTH_SHORT).show();

                }
                else{

                    try {
                        upload_image(upload_Stu_ImageURI);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            else if (Person_str.equals("teacher")) {

                if (edit_teacher_department_str.equals(null) && edit_teacher_id_no_str.equals(null)) {

                    Toast.makeText(this, "Enter Teacher's Name & Department ", Toast.LENGTH_SHORT).show();

                }


                else{

                    try {
                        upload_image(upload_Stu_ImageURI);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }



        }

        super.onActivityResult(requestCode, resultCode, data);
    }



    private void upload_image(Uri upload_stu_imageURI_p) throws IOException {

                Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),upload_stu_imageURI_p);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                image_bitmap.compress(Bitmap.CompressFormat.PNG,30,baos);
                byte[] bytes_data = baos.toByteArray();

        String file_name = "unNamedPath";



        if (Person_str.equals("student")) {

            upload_img_sel_path = stu_img_storageReference;
            file_name = edit_stu_clg_add_no_str + "_" + edit_stu_name_str;

        }
        else if (Person_str.equals("teacher")){

            teacher_img_storageReference = FirebaseStorage.getInstance().getReference("Teacher Images")
                    .child(edit_teacher_department_str).child(edit_teacher_id_no_str);

            upload_img_sel_path = teacher_img_storageReference;

            file_name = edit_teacher_department_str + "_" + edit_teacher_id_no_str;


        }


        else {
            upload_img_sel_path = stu_img_storageReference;
            file_name = edit_stu_clg_add_no_str + "_" + edit_stu_name_str;


        }

        upload_img_sel_path.child(file_name).putBytes(bytes_data)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                            while (!uriTask.isSuccessful()) ;


                            if (uriTask.isSuccessful()) {
                                downloadUri = uriTask.getResult().toString();

                                Toast.makeText(Add_stu_B_details.this, "Image Upload Successfully", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Add_stu_B_details.this, "Image Upload Unsuccessful " + e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });



    }


}