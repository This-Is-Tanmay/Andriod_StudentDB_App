package com.example.studentdatabase;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewFacultyActivity extends AppCompatActivity {

    private LinearLayout layoutFacultyRecords;
    private Button btnBack;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_faculty);

        btnBack = findViewById(R.id.btnBack);
        layoutFacultyRecords = findViewById(R.id.layoutFacultyRecords);
        dbHelper = new DatabaseHelper(this);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        displayFacultyRecords();
    }

    private void displayFacultyRecords() {
        Cursor cursor = dbHelper.getAllFaculty();

        if (cursor == null || cursor.getCount() == 0) {
            TextView tvEmpty = new TextView(this);
            tvEmpty.setText("No faculty records found in database.");
            tvEmpty.setTextSize(16);
            tvEmpty.setPadding(0, 20, 0, 0);
            layoutFacultyRecords.addView(tvEmpty);
            return;
        }

        while (cursor.moveToNext()) {
            String id = cursor.getString(0);
            String name = cursor.getString(1);
            String dept = cursor.getString(2);
            String email = cursor.getString(3);
            String phone = cursor.getString(4);

            String recordText = "Faculty ID: " + id + "\n" +
                    "Name: " + name + "\n" +
                    "Department: " + dept + "\n" +
                    "Email: " + email + "\n" +
                    "Phone: " + phone;

            TextView tvRecord = new TextView(this);
            tvRecord.setText(recordText);
            tvRecord.setTextSize(16);
            tvRecord.setPadding(0, 10, 0, 5);
            layoutFacultyRecords.addView(tvRecord);

            TextView tvSeparator = new TextView(this);
            tvSeparator.setText("----------------------------------------");
            tvSeparator.setPadding(0, 5, 0, 20);
            layoutFacultyRecords.addView(tvSeparator);
        }

        cursor.close();
    }
}
