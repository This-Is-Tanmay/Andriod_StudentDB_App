package com.example.studentdatabase;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DeleteFacultyActivity extends AppCompatActivity {

    private EditText etDeleteFacultyId;
    private Button btnDeleteFacultySubmit, btnBack;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_faculty);

        dbHelper = new DatabaseHelper(this);

        btnBack = findViewById(R.id.btnBack);
        etDeleteFacultyId = findViewById(R.id.etDeleteFacultyId);
        btnDeleteFacultySubmit = findViewById(R.id.btnDeleteFacultySubmit);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnDeleteFacultySubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etDeleteFacultyId.getText().toString().trim();

                if (id.isEmpty()) {
                    Toast.makeText(DeleteFacultyActivity.this, "Please enter Faculty ID", Toast.LENGTH_SHORT).show();
                    return;
                }

                showDeleteConfirmationDialog(id);
            }
        });
    }

    private void showDeleteConfirmationDialog(final String id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to delete this faculty member?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                boolean isDeleted = dbHelper.deleteFaculty(id);

                if (isDeleted) {
                    Toast.makeText(DeleteFacultyActivity.this, "Faculty Deleted Successfully", Toast.LENGTH_SHORT).show();
                    etDeleteFacultyId.setText("");
                } else {
                    Toast.makeText(DeleteFacultyActivity.this, "Faculty Not Found", Toast.LENGTH_SHORT).show();
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
}
