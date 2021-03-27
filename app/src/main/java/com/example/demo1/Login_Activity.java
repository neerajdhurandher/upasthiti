package com.example.demo1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Login_Activity extends AppCompatActivity {


    Button googleSigninbtn;
    private int RC_SIGN_IN = 1;
    private CheckBox checkBox;
    FirebaseAuth mFirebaseAuth;
    FirebaseAuth.AuthStateListener mAuthStateListener;
    private GoogleSignInClient getSignInIntent;
    private GoogleSignInClient googleSignInClient;

    DatabaseReference verified_emails_db;
    DatabaseReference DR_student, DR_teacher,DR_management;
    DatabaseReference abc_pass;

    String  get_email_s,get_uid_s;
    String email_by_db = "";
    String course_by_db = "";
    String branch_by_db = "";
    String ad_year_by_db = "";
    String  clg_rollno_by_db = "";
    String  teacher_id_by_db = "";

    Button student_btn,teacher_btn;
    String stu_or_teacher_str = "Empty";
    String abc_str,password_str;
    EditText password;
    TextView login_problem;

    Runnable myTask;

    public  int show_not_allow_int = 1;

   ImageSwitcher imageSwitcher;

    int imageSwitcherImages[] = {R.drawable.illust_1, R.drawable.illust_2, R.drawable.illust_3};

    int switcherImageLength = imageSwitcherImages.length;
    int counter = -1;

    private Handler mHandler = new Handler();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);


        imageSwitcher = findViewById(R.id.image_switcher);

        imageSwitcher.setFactory(new ViewSwitcher.ViewFactory() {
            @Override
            public View makeView() {
                ImageView switcherImageView = new ImageView(getApplicationContext());
                switcherImageView.setLayoutParams(new ImageSwitcher.LayoutParams(
                        ActionBar.LayoutParams.FILL_PARENT, ActionBar.LayoutParams.FILL_PARENT
                ));
                switcherImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                //switcherImageView.setMaxHeight(100);
                return switcherImageView;
            }
        });

        final Animation aniOut = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        final Animation aniIn = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);



        myTask = new Runnable() {
            public void run() {
                counter++;
                if (counter == switcherImageLength){
                    counter = 0;
                }
                imageSwitcher.setImageResource(imageSwitcherImages[counter]);
                imageSwitcher.setOutAnimation(aniOut);
                imageSwitcher.setInAnimation(aniIn);
                mHandler.postDelayed(this, 3000); // Running this thread again after 5000 milliseconds
            }
        };

         myTask.run();

        mFirebaseAuth = FirebaseAuth.getInstance();


        googleSigninbtn = findViewById(R.id.google_button);
        checkBox = findViewById(R.id.checkBox);

        login_problem = findViewById(R.id.login_problem);

        student_btn = findViewById(R.id.btn_student);
        teacher_btn = findViewById(R.id.btn_teacher);

        password = findViewById(R.id.edit_password);
        password.setVisibility(View.INVISIBLE);

        verified_emails_db = FirebaseDatabase.getInstance().getReference("Verified Emails");

         abc_pass = FirebaseDatabase.getInstance().getReference();







        student_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                student_btn.setBackground(getDrawable(R.drawable.btn_cor_green));
                teacher_btn.setBackground(getDrawable(R.drawable.btn_cor_grey));
                stu_or_teacher_str = "Student";

            }
        });

        teacher_btn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ResourceType")
            @Override
            public void onClick(View v) {

                teacher_btn.setBackground(getDrawable(R.drawable.btn_cor_green));
                student_btn.setBackground(getDrawable(R.drawable.btn_cor_grey));
                stu_or_teacher_str = "Teacher";

            }
        });

        teacher_btn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {


                teacher_btn.setBackground(getDrawable(R.drawable.btn_cor_red));

                password.setVisibility(View.VISIBLE);


                stu_or_teacher_str = "M";

                return true;
            }
        });


        login_problem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login_Activity.this,MainActivity.class));
            }
        });



        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();

                if( mFirebaseUser != null ) {

                    // this help to sigin again if user already exits.....

                    FirebaseUser already_user = mFirebaseAuth.getCurrentUser();
                    get_email_s = already_user.getEmail();

                    DR_student = verified_emails_db.child("Students");
                    DR_teacher = verified_emails_db.child("Teachers");
                    DR_management = verified_emails_db.child("Management");


                    if (stu_or_teacher_str.equals("Student")) {

                        stu_login();

                    }

                    if (stu_or_teacher_str.equals("Teacher")) {

                        teacher_login();


                    }
                    if (stu_or_teacher_str.equals("M")){


                        password_str = password.getText().toString();

                        abc_pass.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if (snapshot.exists()){

                                    abc_str = snapshot.child("M_Keys").getValue().toString();

                                    if (password_str.equals(abc_str)) {

                                        management_login();

                                    } else {
                                        Toast.makeText(Login_Activity.this, "Wrong Password", Toast.LENGTH_SHORT).show();

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
        };




        GoogleSignInOptions googleSignInOptions  = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = (GoogleSignInClient) GoogleSignIn.getClient(this,googleSignInOptions);

        googleSigninbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (checkBox.isChecked()){

                    if (stu_or_teacher_str.equals("Empty")){

                        Toast.makeText(Login_Activity.this, "Are You a Student or Teacher", Toast.LENGTH_SHORT).show();
                    }

                    if (!stu_or_teacher_str.equals("Empty")) {
                        password.setVisibility(View.INVISIBLE);
                        SigIn();
                        mHandler.removeCallbacks(myTask);
                    }

                }
                else {
                    Toast.makeText(Login_Activity.this,"Accept Terms & conditions",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }




    private void management_login() {

        show_not_allow_int = 1;

        DR_management.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()){

                    for (DataSnapshot ds: snapshot.getChildren()) {

                        email_by_db = ds.getValue()+"";

                        if (email_by_db.equals(get_email_s)){

                            show_not_allow_int = 0;

                            String person = "Management";
                            sp_user_type(person);


                            Intent management_home = new Intent(Login_Activity.this, Home_Activity.class);
//                            Toast.makeText(Login_Activity.this, "Welcome  "+email_by_db, Toast.LENGTH_SHORT).show();
                            startActivity(management_home);
                            finish();

                        }
                    }

                    if (show_not_allow_int == 1) {

                        Toast.makeText(Login_Activity.this, "You Are Not Allow to Access", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });

    }


    private void teacher_login() {

        show_not_allow_int = 1;


        Query teacher_query = DR_teacher.orderByChild("V_Email").equalTo(get_email_s);

        teacher_query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    for (DataSnapshot ds1 : snapshot.getChildren()) {

                        email_by_db = ds1.child("V_Email").getValue()+"";

                        if (email_by_db.equals(get_email_s)) {


                            teacher_id_by_db = ds1.child("Teacher Id No").getValue()+"";

                            show_not_allow_int = 0;
                            String person = "Teacher";
                            sp_user_type(person);

                            save_sp_teacher(teacher_id_by_db,get_email_s);


                            Intent teacher_home = new Intent(Login_Activity.this, Teacher_Home_Activity.class);

                            teacher_home.putExtra("Teacher_Id",teacher_id_by_db);
                            teacher_home.putExtra("Email_Id",get_email_s);
                            startActivity(teacher_home);
                            finish();
                        }

                    }

                    if (show_not_allow_int == 1) {

                        Toast.makeText(Login_Activity.this, "Selected User is not a Teacher", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {



            }
        });

    }



    private void stu_login() {

        show_not_allow_int = 1;

        Query student_query = DR_student.orderByChild("V_Email").equalTo(get_email_s);


        student_query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {

                    for (DataSnapshot ds1 : snapshot.getChildren()) {

                        email_by_db = ds1.child("V_Email").getValue()+"";


                        if (email_by_db.equals(get_email_s)) {

                            branch_by_db = ds1.child("Branch").getValue()+"";
                            ad_year_by_db = ds1.child("Admission_Year").getValue()+"";
                            course_by_db = ds1.child("Course").getValue()+"";
                            clg_rollno_by_db = ds1.child("College_RollNo").getValue()+"";


                            show_not_allow_int = 0;

                            String person = "Student";

                            sp_user_type(person);

                            save_sp_student(email_by_db,branch_by_db,ad_year_by_db,clg_rollno_by_db,course_by_db);

                            Intent student_home = new Intent(Login_Activity.this, student_Home_Activity.class);

                            student_home.putExtra("Email",email_by_db);
                            student_home.putExtra("Branch",branch_by_db);
                            student_home.putExtra("Admission_Year",ad_year_by_db);
                            student_home.putExtra("College_RollNo",clg_rollno_by_db);
                            student_home.putExtra("Course",course_by_db);

                            startActivity(student_home);
                            finish();

                        }

                    }

                    if (show_not_allow_int == 1) {

                        Toast.makeText(Login_Activity.this, "Selected User is not a Student", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {



            }
        });

    }




    private void sp_user_type(String person) {

        SharedPreferences sharedPref_user = getSharedPreferences("sp_user_type",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_user = sharedPref_user.edit();

        editor_user.putString("User_Type",person);
        editor_user.apply();
    }

    private void save_sp_teacher(String teacher_id_by_db, String get_email_s) {

        SharedPreferences sharedPref_teacher = getSharedPreferences("save_sp_teacher",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_teacher = sharedPref_teacher.edit();

        editor_teacher.putString("Teacher_Id",teacher_id_by_db);
        editor_teacher.putString("Email_Id",get_email_s);

        editor_teacher.apply();
    }

    private void save_sp_student(String email_by_db, String branch_by_db, String ad_year_by_db, String clg_rollno_by_db, String course_by_db) {

        SharedPreferences sharedPref_student = getSharedPreferences("save_sp_student",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor_student = sharedPref_student.edit();

        editor_student.putString("Email",email_by_db);
        editor_student.putString("Branch",branch_by_db);
        editor_student.putString("Admission_Year",ad_year_by_db);
        editor_student.putString("College_RollNo",clg_rollno_by_db);
        editor_student.putString("Course",course_by_db);

        editor_student.apply();

    }




    private void SigIn(){
        Intent GsignIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(GsignIntent, RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                handleSignInResult(task);
            }
            catch (Exception e){
            }
        }

//

    }

    private void handleSignInResult(Task <GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
//            Toast.makeText( Login_Activity.this,"Signed In Successfully", Toast.LENGTH_SHORT).show();
            FirebaseGoogleAuth(acc);


        }
        catch (ApiException e){
            Toast.makeText( Login_Activity.this,"Signed In Failed", Toast.LENGTH_SHORT).show();
            myTask.run();
//            Toast.makeText( Login_Activity.this," " + e, Toast.LENGTH_LONG).show();


        }
    }

    private void FirebaseGoogleAuth (GoogleSignInAccount acct){
        AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getIdToken(),null);

        mFirebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {


            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    FirebaseUser user = mFirebaseAuth.getCurrentUser();
                    get_email_s = user.getEmail();
                    get_uid_s = user.getUid();



//                    finish();

                } else {

                    Toast.makeText(Login_Activity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                    finish();

                }
            }
        });

    }


    @Override
    protected void onStart() {
        super.onStart();

        check_sp();

        mFirebaseAuth.addAuthStateListener(mAuthStateListener);

    }

    private void check_sp() {

        SharedPreferences get_sharedPref_user = getSharedPreferences("sp_user_type",Context.MODE_PRIVATE);

        SharedPreferences get_sharedPref_teacher = getSharedPreferences("save_sp_teacher",Context.MODE_PRIVATE);
        SharedPreferences get_sharedPref_student = getSharedPreferences("save_sp_student",Context.MODE_PRIVATE);

        String User_Type = get_sharedPref_user.getString("User_Type", "nothing");


        String student_email  = get_sharedPref_student.getString("Email","a");
        String student_branch  = get_sharedPref_student.getString("Branch","a");
        String student_add_y  = get_sharedPref_student.getString("Admission_Year","a");
        String student_clg_rollno  = get_sharedPref_student.getString("College_RollNo","a");
        String student_course = get_sharedPref_student.getString("Course","a");

        String teacher_id_sp = get_sharedPref_teacher.getString("Teacher_Id","b");
        String teacher_email_id_sp = get_sharedPref_teacher.getString("Email_Id","b");



        if (User_Type.equals("Management")){

            Intent management_home = new Intent(Login_Activity.this, Home_Activity.class);
            startActivity(management_home);
            finish();

        }

        if (User_Type.equals("Student")){

            Intent student_home = new Intent(Login_Activity.this, student_Home_Activity.class);

            student_home.putExtra("Email",student_email);
            student_home.putExtra("Branch",student_branch);
            student_home.putExtra("Admission_Year",student_add_y);
            student_home.putExtra("College_RollNo",student_clg_rollno);
            student_home.putExtra("Course",student_course);

            startActivity(student_home);
            finish();


        }

        if (User_Type.equals("Teacher")){

            Intent teacher_home = new Intent(Login_Activity.this, Teacher_Home_Activity.class);

            teacher_home.putExtra("Teacher_Id",teacher_id_sp);
            teacher_home.putExtra("Email_Id",teacher_email_id_sp);
            startActivity(teacher_home);
            finish();


        }




    }


}