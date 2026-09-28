package com.example.studentdatabase;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private Button btnGoToAdd, btnGoToView, btnGoToUpdate, btnGoToDelete;
    private Button btnOpenWebsite, btnLogout, btnSwitchToFaculty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Link views from XML
        btnGoToAdd = findViewById(R.id.btnGoToAdd);
        btnGoToView = findViewById(R.id.btnGoToView);
        btnGoToUpdate = findViewById(R.id.btnGoToUpdate);
        btnGoToDelete = findViewById(R.id.btnGoToDelete);
        btnOpenWebsite = findViewById(R.id.btnOpenWebsite);
        btnLogout = findViewById(R.id.btnLogout);
        btnSwitchToFaculty = findViewById(R.id.btnSwitchToFaculty);

        // 1. EXPLICIT INTENT: Opens AddStudentActivity
        btnGoToAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddStudentActivity.class);
                startActivity(intent);
            }
        });

        // 1. EXPLICIT INTENT: Opens ViewStudentActivity
        btnGoToView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ViewStudentActivity.class);
                startActivity(intent);
            }
        });

        // 1. EXPLICIT INTENT: Opens UpdateStudentActivity
        btnGoToUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, UpdateStudentActivity.class);
                startActivity(intent);
            }
        });

        // 1. EXPLICIT INTENT: Opens DeleteStudentActivity
        btnGoToDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeleteStudentActivity.class);
                startActivity(intent);
            }
        });

        // 2. IMPLICIT INTENT: Opens a web browser with URL
        btnOpenWebsite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent webIntent = new Intent(Intent.ACTION_VIEW);
                webIntent.setData(Uri.parse("https://www.google.com"));
                startActivity(webIntent);
            }
        });

        // 3. LOGOUT: Asks confirmation and returns to LoginActivity
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLogoutConfirmationDialog();
            }
        });

        // 4. SWITCH TO FACULTY: Opens Faculty Database Main Activity
        btnSwitchToFaculty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FacultyMainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showLogoutConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Logout Confirmation");
        builder.setMessage("Are you sure you want to logout?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Return to LoginActivity and clear activity stack
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
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
