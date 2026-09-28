package com.example.studentdatabase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateStudentActivity extends AppCompatActivity {

    private EditText etUpdateId, etUpdateName, etUpdateCourse, etUpdateMarks;
    private Button btnUpdateStudent, btnBack;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_student);

        // Initialize DatabaseHelper instance
        dbHelper = new DatabaseHelper(this);

        // Link views from XML
        btnBack = findViewById(R.id.btnBack);
        etUpdateId = findViewById(R.id.etUpdateId);
        etUpdateName = findViewById(R.id.etUpdateName);
        etUpdateCourse = findViewById(R.id.etUpdateCourse);
        etUpdateMarks = findViewById(R.id.etUpdateMarks);
        btnUpdateStudent = findViewById(R.id.btnUpdateStudent);

        // Top Back Button Listener
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Return to MainActivity
            }
        });

        // Update Student Button Listener
        btnUpdateStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etUpdateId.getText().toString().trim();
                String name = etUpdateName.getText().toString().trim();
                String course = etUpdateCourse.getText().toString().trim();
                String marks = etUpdateMarks.getText().toString().trim();

                // Validation: ensure fields are not empty
                if (id.isEmpty() || name.isEmpty() || course.isEmpty() || marks.isEmpty()) {
                    Toast.makeText(UpdateStudentActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Update record in SQLite database
                boolean isUpdated = dbHelper.updateStudent(id, name, course, marks);

                if (isUpdated) {
                    Toast.makeText(UpdateStudentActivity.this, "Student Updated Successfully", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(UpdateStudentActivity.this, "Student Not Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void clearFields() {
        etUpdateId.setText("");
        etUpdateName.setText("");
        etUpdateCourse.setText("");
        etUpdateMarks.setText("");
    }
}
