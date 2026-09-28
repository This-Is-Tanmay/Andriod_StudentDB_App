package com.example.studentdatabase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.io.ByteArrayOutputStream;

public class AddStudentActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 1;
    private static final String CHANNEL_ID = "student_notification_channel";

    private EditText etStudentId, etStudentName, etStudentCourse, etStudentMarks;
    private Button btnSaveStudent, btnClearFields, btnBack, btnTakePhoto;
    private ImageView imgStudentCamera;
    private DatabaseHelper dbHelper;
    private Bitmap capturedBitmap = null; // Variable to store captured student photo

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_student);

        // Initialize DatabaseHelper instance
        dbHelper = new DatabaseHelper(this);

        // Link views from XML
        btnBack = findViewById(R.id.btnBack);
        etStudentId = findViewById(R.id.etStudentId);
        etStudentName = findViewById(R.id.etStudentName);
        etStudentCourse = findViewById(R.id.etStudentCourse);
        etStudentMarks = findViewById(R.id.etStudentMarks);
        btnTakePhoto = findViewById(R.id.btnTakePhoto);
        imgStudentCamera = findViewById(R.id.imgStudentCamera);
        btnSaveStudent = findViewById(R.id.btnSaveStudent);
        btnClearFields = findViewById(R.id.btnClearFields);

        // Top Back Button: Returns to MainActivity
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Open camera using Camera Intent
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE);
            }
        });

        // Save Student Button Listener
        btnSaveStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etStudentId.getText().toString().trim();
                String name = etStudentName.getText().toString().trim();
                String course = etStudentCourse.getText().toString().trim();
                String marks = etStudentMarks.getText().toString().trim();

                // Validation: ensure fields are not empty
                if (id.isEmpty() || name.isEmpty() || course.isEmpty() || marks.isEmpty()) {
                    Toast.makeText(AddStudentActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // ALERT DIALOG: Show confirmation dialog before saving
                showSaveConfirmationDialog(id, name, course, marks);
            }
        });

        // Clear Fields Button Listener
        btnClearFields.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearFields();
            }
        });
    }

    // Callback method for Camera Intent result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getExtras() != null) {
            Bitmap rawBitmap = (Bitmap) data.getExtras().get("data");
            if (rawBitmap != null) {
                // Rotate captured image right (90 degrees clockwise) and store
                Matrix matrix = new Matrix();
                matrix.postRotate(90);
                capturedBitmap = Bitmap.createBitmap(rawBitmap, 0, 0, rawBitmap.getWidth(), rawBitmap.getHeight(), matrix, true);

                imgStudentCamera.setImageBitmap(capturedBitmap);
                Toast.makeText(this, "Photo captured successfully", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // Method to show AlertDialog confirmation for saving student data
    private void showSaveConfirmationDialog(final String id, final String name, final String course, final String marks) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Save Student");
        builder.setMessage("Do you want to save this student?");

        // Positive Button (YES)
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Convert captured Bitmap to byte[] (BLOB) for SQLite storage
                byte[] photoBytes = null;
                if (capturedBitmap != null) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    capturedBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    photoBytes = stream.toByteArray();
                }

                // Insert data into SQLite Database with photo BLOB
                boolean isInserted = dbHelper.insertStudent(id, name, course, marks, photoBytes);
                if (isInserted) {
                    Toast.makeText(AddStudentActivity.this, "Student Added Successfully", Toast.LENGTH_SHORT).show();
                    
                    // Send Notification
                    showStudentAddedNotification();

                    // Return to MainActivity
                    finish();
                } else {
                    Toast.makeText(AddStudentActivity.this, "Error: Could not save student (Duplicate ID?)", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Negative Button (NO)
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }

    // Method to show notification after successful student addition
    private void showStudentAddedNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Student Database Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle("Student Database")
                .setContentText("Student added successfully")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        if (notificationManager != null) {
            notificationManager.notify(1, builder.build());
        }
    }

    // Helper method to clear input fields and image view
    private void clearFields() {
        etStudentId.setText("");
        etStudentName.setText("");
        etStudentCourse.setText("");
        etStudentMarks.setText("");
        imgStudentCamera.setImageBitmap(null);
        imgStudentCamera.setBackgroundColor(0xFFCCCCCC);
        capturedBitmap = null;
    }
}
