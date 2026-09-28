package com.example.studentdatabase;

import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewStudentActivity extends AppCompatActivity {

    private LinearLayout layoutStudentRecords;
    private Button btnBack;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_student);

        btnBack = findViewById(R.id.btnBack);
        layoutStudentRecords = findViewById(R.id.layoutStudentRecords);
        dbHelper = new DatabaseHelper(this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Return to MainActivity
            }
        });

        // Load and display all student records from SQLite
        displayStudentRecords();
    }

    private void displayStudentRecords() {
        // Query database to get all records
        Cursor cursor = dbHelper.getAllStudents();

        // Check if database is empty
        if (cursor == null || cursor.getCount() == 0) {
            TextView tvEmpty = new TextView(this);
            tvEmpty.setText("No student records found in database.");
            tvEmpty.setTextSize(16);
            tvEmpty.setPadding(0, 20, 0, 0);
            layoutStudentRecords.addView(tvEmpty);
            return;
        }

        // Loop through cursor rows and display each student's details
        while (cursor.moveToNext()) {
            String id = cursor.getString(0);    // Column 0: id
            String name = cursor.getString(1);  // Column 1: name
            String course = cursor.getString(2);// Column 2: course
            String marks = cursor.getString(3); // Column 3: marks
            byte[] photoBytes = cursor.getBlob(4); // Column 4: photo BLOB

            // Format record details text
            String recordText = "ID: " + id + "\n" +
                    "Name: " + name + "\n" +
                    "Course: " + course + "\n" +
                    "Marks: " + marks;

            // Create TextView for student text details
            TextView tvRecord = new TextView(this);
            tvRecord.setText(recordText);
            tvRecord.setTextSize(16);
            tvRecord.setPadding(0, 10, 0, 5);
            layoutStudentRecords.addView(tvRecord);

            // Display student photo if available
            if (photoBytes != null && photoBytes.length > 0) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(photoBytes, 0, photoBytes.length);
                ImageView imgPhoto = new ImageView(this);
                imgPhoto.setImageBitmap(bitmap);
                imgPhoto.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 400));
                imgPhoto.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                imgPhoto.setPadding(0, 5, 0, 10);
                layoutStudentRecords.addView(imgPhoto);
            } else {
                TextView tvNoPhoto = new TextView(this);
                tvNoPhoto.setText("Photo: No photo available");
                tvNoPhoto.setTextSize(14);
                tvNoPhoto.setPadding(0, 5, 0, 10);
                layoutStudentRecords.addView(tvNoPhoto);
            }

            // Separator line
            TextView tvSeparator = new TextView(this);
            tvSeparator.setText("----------------------------------------");
            tvSeparator.setPadding(0, 5, 0, 20);
            layoutStudentRecords.addView(tvSeparator);
        }

        // Always close the cursor after use
        cursor.close();
    }
}
