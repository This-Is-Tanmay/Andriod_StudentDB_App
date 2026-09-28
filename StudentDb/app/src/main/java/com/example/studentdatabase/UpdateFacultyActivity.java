package com.example.studentdatabase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class UpdateFacultyActivity extends AppCompatActivity {

    private EditText etUpdateFacultyId, etUpdateFacultyName, etUpdateFacultyDept, etUpdateFacultyEmail, etUpdateFacultyPhone;
    private Button btnUpdateFacultySubmit, btnBack;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);

        dbHelper = new DatabaseHelper(this);

        btnBack = findViewById(R.id.btnBack);
        etUpdateFacultyId = findViewById(R.id.etUpdateFacultyId);
        etUpdateFacultyName = findViewById(R.id.etUpdateFacultyName);
        etUpdateFacultyDept = findViewById(R.id.etUpdateFacultyDept);
        etUpdateFacultyEmail = findViewById(R.id.etUpdateFacultyEmail);
        etUpdateFacultyPhone = findViewById(R.id.etUpdateFacultyPhone);
        btnUpdateFacultySubmit = findViewById(R.id.btnUpdateFacultySubmit);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnUpdateFacultySubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etUpdateFacultyId.getText().toString().trim();
                String name = etUpdateFacultyName.getText().toString().trim();
                String dept = etUpdateFacultyDept.getText().toString().trim();
                String email = etUpdateFacultyEmail.getText().toString().trim();
                String phone = etUpdateFacultyPhone.getText().toString().trim();

                if (id.isEmpty() || name.isEmpty() || dept.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(UpdateFacultyActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                boolean isUpdated = dbHelper.updateFaculty(id, name, dept, email, phone);

                if (isUpdated) {
                    Toast.makeText(UpdateFacultyActivity.this, "Faculty Updated Successfully", Toast.LENGTH_SHORT).show();
                    clearFields();
                } else {
                    Toast.makeText(UpdateFacultyActivity.this, "Faculty Not Found", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void clearFields() {
        etUpdateFacultyId.setText("");
        etUpdateFacultyName.setText("");
        etUpdateFacultyDept.setText("");
        etUpdateFacultyEmail.setText("");
        etUpdateFacultyPhone.setText("");
    }
}
