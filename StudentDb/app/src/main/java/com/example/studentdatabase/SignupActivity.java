package com.example.studentdatabase;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private EditText etSignupFullName, etSignupUsername, etSignupPassword, etSignupConfirmPassword;
    private Button btnSignupSubmit, btnBackToLogin;
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Initialize DatabaseHelper
        dbHelper = new DatabaseHelper(this);

        btnBackToLogin = findViewById(R.id.btnBackToLogin);
        etSignupFullName = findViewById(R.id.etSignupFullName);
        etSignupUsername = findViewById(R.id.etSignupUsername);
        etSignupPassword = findViewById(R.id.etSignupPassword);
        etSignupConfirmPassword = findViewById(R.id.etSignupConfirmPassword);
        btnSignupSubmit = findViewById(R.id.btnSignupSubmit);

        // Back button listener
        btnBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Return to LoginActivity
            }
        });

        // SIGN UP Submit listener
        btnSignupSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fullName = etSignupFullName.getText().toString().trim();
                String username = etSignupUsername.getText().toString().trim();
                String password = etSignupPassword.getText().toString().trim();
                String confirmPassword = etSignupConfirmPassword.getText().toString().trim();

                // Input validation
                if (fullName.isEmpty() || username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(SignupActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Insert user into SQLite users table (supports multiple accounts)
                boolean isInserted = dbHelper.insertUser(fullName, username, password);

                if (isInserted) {
                    // ALERT DIALOG: Show registration success alert
                    new AlertDialog.Builder(SignupActivity.this)
                            .setTitle("Success")
                            .setMessage("Registration Successful! You can now log in.")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish(); // Close signup screen and return to LoginActivity
                                }
                            })
                            .show();
                } else {
                    Toast.makeText(SignupActivity.this, "Registration Failed. Username may already exist.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
