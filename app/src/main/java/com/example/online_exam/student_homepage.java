package com.example.online_exam;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

//import java.util.ArrayList;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class student_homepage extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    public List<ModelCourseList>coursesList;
    StudentHompepageAdapter adapter;
    //Button logout ;

    //Intent userIntent = getIntent();
    //Intent intent;

    public FirebaseDatabase db = FirebaseDatabase.getInstance();
    public FirebaseDatabase new_db = FirebaseDatabase.getInstance();
    public DatabaseReference root = db.getReference("joined_courses");
    public DatabaseReference new_root = new_db.getReference("courses");

    String Course_name;
    String Course_code;
    String student_username;
    String current_userName;
    String joined_course_code;
    String[] courseCodeArray = new String[99999];


    int size = 0;

    //int course_count;

    //String[] courseNameArray, courseCodeArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_homepage);
        // this.setTitle("Homepage");
        //Objects.requireNonNull(getSupportActionBar()).setTitle("Teacher Homepage");
        //logout = (Button)findViewById(R.id.logout) ;
        //teacher_username = userIntent.getStringExtra("currentUsername");

        SharedPreferences sp = getApplicationContext().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);

        student_username = sp.getString("UserName", "");

        System.out.println(student_username);

        //initData();
        initRecyclerView();
        list_show();
        //adapter.notifyDataSetChanged();

    }

//    private void new_created_course() {

//        boolean isItHere = getIntent().getBooleanExtra("isThereCourse",false);

//        if(isItHere == true) {

//            Course_name = getIntent().getStringExtra("passed_course_name");
//            Course_code = getIntent().getStringExtra("passed_course_code");

    //           courseNameArray[course_count] = Course_name;
//            courseCodeArray[course_count] = Course_code;

//            coursesList.add(new ModelCourseList(Course_name,Course_code));
//        }

//    }

//    private void initData() {

//

    //       courseNameArray = new String[100];
    //       courseCodeArray = new String[100];

//        coursesList.add(new ModelCourseList("CSE","4321"));

//        int travers = 0;

//        course_count = 0;

    //       while(courseNameArray[travers] != null) {

    //           travers++;
    //           course_count++;

    //           coursesList.add(new ModelCourseList(courseNameArray[travers],courseCodeArray[travers]));
//        }

//        new_created_course();

    //   }

    private void initRecyclerView() {

        coursesList = new ArrayList<>();

        recyclerView=findViewById(R.id.coursesRecyclerView);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter= new StudentHompepageAdapter(coursesList, this);
        recyclerView.setAdapter(adapter);

// ---------------------------------------------------------------------------------------------------

        root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    current_userName = dataSnapshot.child("currentUser").getValue(String.class);

                    if (current_userName.equals(student_username)) {

                        joined_course_code = dataSnapshot.child("courseCode").getValue(String.class);

                        courseCodeArray[size] = joined_course_code;
                        size++;
                    }

                }
                //adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

// ---------------------------------------------------------------------------------------------------

        //coursesList.add(new ModelCourseList("CSE", "12346753"));
        //coursesList.add(new ModelCourseList("CSE", "12346753"));
        //adapter.notifyDataSetChanged();

    }

    private void list_show() {

        new_root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                int count = 0;

                while (count < size) {

                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                        //current_userName = dataSnapshot.child("currentUser").getValue(String.class);
                        Course_code = dataSnapshot.child("courseCode").getValue(String.class);

                        if(courseCodeArray[count].equals(Course_code)) {

                            Course_name = dataSnapshot.child("courseName").getValue(String.class);
                            coursesList.add(new ModelCourseList(Course_name, Course_code));

                            break;
                        }
                    }

                    count++;
                }

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menuitem, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.course_create:
                Toast.makeText(this, "Join new course", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),JoinCourseActivity.class));
                return true;

            case R.id.inst:
                Toast.makeText(this, "Instruction selected", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),InstructActivity.class));
                return true;

            case R.id.log:
                Toast.makeText(this, "Logging out", Toast.LENGTH_SHORT).show();
                student_username = "";
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

}

//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//public class teacher_homepage extends AppCompatActivity {
//
//    Button logout ;
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_teacher_homepage);
//        getSupportActionBar().setTitle("Teacher_Homepage");
//        logout = (Button)findViewById(R.id.logout) ;
//
//
//    }
//
//    public void btn_logout(View view) {
//        startActivity(new Intent(getApplicationContext(),Login_form.class));
//    }
//
//    public void btn_assignment(View view) {
//        startActivity(new Intent(getApplicationContext(),teacher_assignment_page.class));
//    }
//}