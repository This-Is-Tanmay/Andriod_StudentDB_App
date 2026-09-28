package com.example.studentdatabase;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DeleteStudentActivity extends AppCompatActivity {

    private EditText etDeleteId;
    private Button btnDeleteStudent, btnBack;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_student);

        // Initialize DatabaseHelper instance
        dbHelper = new DatabaseHelper(this);

        // Link views from XML
        btnBack = findViewById(R.id.btnBack);
        etDeleteId = findViewById(R.id.etDeleteId);
        btnDeleteStudent = findViewById(R.id.btnDeleteStudent);

        // Top Back Button Listener
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Return to MainActivity
            }
        });

        // Delete Student Button Listener
        btnDeleteStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etDeleteId.getText().toString().trim();

                if (id.isEmpty()) {
                    Toast.makeText(DeleteStudentActivity.this, "Please enter Student ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                // ALERT DIALOG: Show confirmation dialog before deleting
                showDeleteConfirmationDialog(id);
            }
        });
    }

    // Method to show AlertDialog confirmation for deleting student record
    private void showDeleteConfirmationDialog(final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete this student?");

        // Positive Button (Yes)
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Delete record from SQLite database
                boolean isDeleted = dbHelper.deleteStudent(id);

                if (isDeleted) {
                    Toast.makeText(DeleteStudentActivity.this, "Student Deleted Successfully", Toast.LENGTH_SHORT).show();
                    etDeleteId.setText("");
                } else {
                    Toast.makeText(DeleteStudentActivity.this, "Student Not Found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Negative Button (No)
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Show the dialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
