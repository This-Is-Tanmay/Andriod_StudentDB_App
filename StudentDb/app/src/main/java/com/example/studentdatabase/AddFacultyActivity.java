package com.example.studentdatabase;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class AddFacultyActivity extends AppCompatActivity {

    private EditText etFacultyId, etFacultyName, etFacultyDepartment, etFacultyEmail, etFacultyPhone;
    private Button btnSaveFaculty, btnClearFields, btnBack;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_faculty);

        dbHelper = new DatabaseHelper(this);

        btnBack = findViewById(R.id.btnBack);
        etFacultyId = findViewById(R.id.etFacultyId);
        etFacultyName = findViewById(R.id.etFacultyName);
        etFacultyDepartment = findViewById(R.id.etFacultyDepartment);
        etFacultyEmail = findViewById(R.id.etFacultyEmail);
        etFacultyPhone = findViewById(R.id.etFacultyPhone);
        btnSaveFaculty = findViewById(R.id.btnSaveFaculty);
        btnClearFields = findViewById(R.id.btnClearFields);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnSaveFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etFacultyId.getText().toString().trim();
                String name = etFacultyName.getText().toString().trim();
                String department = etFacultyDepartment.getText().toString().trim();
                String email = etFacultyEmail.getText().toString().trim();
                String phone = etFacultyPhone.getText().toString().trim();

                if (id.isEmpty() || name.isEmpty() || department.isEmpty() || email.isEmpty() || phone.isEmpty()) {
                    Toast.makeText(AddFacultyActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                showSaveConfirmationDialog(id, name, department, email, phone);
            }
        });

        btnClearFields.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });
    }

    private void showSaveConfirmationDialog(final String id, final String name, final String department, final String email, final String phone) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save Faculty");
        builder.setMessage("Do you want to save this faculty record?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean isInserted = dbHelper.insertFaculty(id, name, department, email, phone);
                if (isInserted) {
                    Toast.makeText(AddFacultyActivity.this, "Faculty Info Saved Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(AddFacultyActivity.this, "Error: Could not save faculty info (Duplicate ID?)", Toast.LENGTH_SHORT).show();
                }
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    private void clearFields() {
        etFacultyId.setText("");
        etFacultyName.setText("");
        etFacultyDepartment.setText("");
        etFacultyEmail.setText("");
        etFacultyPhone.setText("");
    }
}
