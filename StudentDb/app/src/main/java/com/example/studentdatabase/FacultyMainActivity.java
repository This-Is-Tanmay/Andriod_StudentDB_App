package com.example.studentdatabase;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class FacultyMainActivity extends AppCompatActivity {

    private Button btnGoToAddFaculty, btnGoToViewFaculty, btnGoToUpdateFaculty, btnGoToDeleteFaculty;
    private Button btnLogout, btnSwitchToStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_main);

        btnGoToAddFaculty = findViewById(R.id.btnGoToAddFaculty);
        btnGoToViewFaculty = findViewById(R.id.btnGoToViewFaculty);
        btnGoToUpdateFaculty = findViewById(R.id.btnGoToUpdateFaculty);
        btnGoToDeleteFaculty = findViewById(R.id.btnGoToDeleteFaculty);
        btnLogout = findViewById(R.id.btnLogout);
        btnSwitchToStudent = findViewById(R.id.btnSwitchToStudent);

        // Add Faculty Info
        btnGoToAddFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FacultyMainActivity.this, AddFacultyActivity.class);
                startActivity(intent);
            }
        });

        // View Faculty Info
        btnGoToViewFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FacultyMainActivity.this, ViewFacultyActivity.class);
                startActivity(intent);
            }
        });

        // Update Faculty Info
        btnGoToUpdateFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FacultyMainActivity.this, UpdateFacultyActivity.class);
                startActivity(intent);
            }
        });

        // Delete Faculty Info
        btnGoToDeleteFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(FacultyMainActivity.this, DeleteFacultyActivity.class);
                startActivity(intent);
            }
        });

        // Logout Button
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog();
            }
        });

        // Switch to Student Button
        btnSwitchToStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Return to MainActivity (Student Database)
            }
        });
    }

    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(FacultyMainActivity.this);
        builder.setTitle("Logout Confirmation");
        builder.setMessage("Are you sure you want to logout?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(FacultyMainActivity.this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
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
}
