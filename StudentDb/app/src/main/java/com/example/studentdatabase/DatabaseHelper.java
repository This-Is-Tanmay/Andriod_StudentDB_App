package com.example.studentdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// SQLiteOpenHelper class manages database creation and version management
public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Name and Version constants (Upgraded to version 3 to support Faculty table)
    private static final String DATABASE_NAME = "StudentDB.db";
    private static final int DATABASE_VERSION = 3;

    // Students Table and Columns
    public static final String TABLE_NAME = "students";
    public static final String COL_ID = "id";
    public static final String COL_NAME = "name";
    public static final String COL_COURSE = "course";
    public static final String COL_MARKS = "marks";
    public static final String COL_PHOTO = "photo";

    // Users Table and Columns
    public static final String TABLE_USERS = "users";
    public static final String USER_COL_ID = "id";
    public static final String USER_COL_NAME = "name";
    public static final String USER_COL_USERNAME = "username";
    public static final String USER_COL_PASSWORD = "password";

    // Faculty Table and Columns
    public static final String TABLE_FACULTY = "faculty";
    public static final String FACULTY_COL_ID = "faculty_id";
    public static final String FACULTY_COL_NAME = "faculty_name";
    public static final String FACULTY_COL_DEPARTMENT = "department";
    public static final String FACULTY_COL_EMAIL = "email";
    public static final String FACULTY_COL_PHONE = "phone";

    // Constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // onCreate is called when the database is created for the first time
    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create students table with photo BLOB column
        String createStudentsTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COL_ID + " TEXT PRIMARY KEY, " +
                COL_NAME + " TEXT, " +
                COL_COURSE + " TEXT, " +
                COL_MARKS + " TEXT, " +
                COL_PHOTO + " BLOB)";
        db.execSQL(createStudentsTable);

        // Create users table for multiple account registration
        String createUsersTable = "CREATE TABLE " + TABLE_USERS + " (" +
                USER_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                USER_COL_NAME + " TEXT, " +
                USER_COL_USERNAME + " TEXT, " +
                USER_COL_PASSWORD + " TEXT)";
        db.execSQL(createUsersTable);

        // Create faculty table
        String createFacultyTable = "CREATE TABLE " + TABLE_FACULTY + " (" +
                FACULTY_COL_ID + " TEXT PRIMARY KEY, " +
                FACULTY_COL_NAME + " TEXT, " +
                FACULTY_COL_DEPARTMENT + " TEXT, " +
                FACULTY_COL_EMAIL + " TEXT, " +
                FACULTY_COL_PHONE + " TEXT)";
        db.execSQL(createFacultyTable);
    }

    // onUpgrade is called when database version changes (safely preserves existing data)
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            try {
                db.execSQL("ALTER TABLE " + TABLE_NAME + " ADD COLUMN " + COL_PHOTO + " BLOB");
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_USERS + " (" +
                        USER_COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        USER_COL_NAME + " TEXT, " +
                        USER_COL_USERNAME + " TEXT, " +
                        USER_COL_PASSWORD + " TEXT)");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (oldVersion < 3) {
            try {
                db.execSQL("CREATE TABLE IF NOT EXISTS " + TABLE_FACULTY + " (" +
                        FACULTY_COL_ID + " TEXT PRIMARY KEY, " +
                        FACULTY_COL_NAME + " TEXT, " +
                        FACULTY_COL_DEPARTMENT + " TEXT, " +
                        FACULTY_COL_EMAIL + " TEXT, " +
                        FACULTY_COL_PHONE + " TEXT)");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // --- USER AUTHENTICATION METHODS ---

    public boolean insertUser(String name, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USER_COL_NAME, name);
        values.put(USER_COL_USERNAME, username);
        values.put(USER_COL_PASSWORD, password);

        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE " +
                USER_COL_USERNAME + " = ? AND " + USER_COL_PASSWORD + " = ?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // --- STUDENT CRUD METHODS ---

    public boolean insertStudent(String id, String name, String course, String marks, byte[] photo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_ID, id);
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_COURSE, course);
        contentValues.put(COL_MARKS, marks);
        contentValues.put(COL_PHOTO, photo);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    public Cursor getAllStudents() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public boolean updateStudent(String id, String name, String course, String marks) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NAME, name);
        contentValues.put(COL_COURSE, course);
        contentValues.put(COL_MARKS, marks);

        int rowsAffected = db.update(TABLE_NAME, contentValues, COL_ID + " = ?", new String[]{id});
        return rowsAffected > 0;
    }

    public boolean deleteStudent(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_NAME, COL_ID + " = ?", new String[]{id});
        return rowsAffected > 0;
    }

    // --- FACULTY CRUD METHODS ---

    public boolean insertFaculty(String id, String name, String department, String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FACULTY_COL_ID, id);
        contentValues.put(FACULTY_COL_NAME, name);
        contentValues.put(FACULTY_COL_DEPARTMENT, department);
        contentValues.put(FACULTY_COL_EMAIL, email);
        contentValues.put(FACULTY_COL_PHONE, phone);

        long result = db.insert(TABLE_FACULTY, null, contentValues);
        return result != -1;
    }

    public Cursor getAllFaculty() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_FACULTY, null);
    }

    public boolean updateFaculty(String id, String name, String department, String email, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FACULTY_COL_NAME, name);
        contentValues.put(FACULTY_COL_DEPARTMENT, department);
        contentValues.put(FACULTY_COL_EMAIL, email);
        contentValues.put(FACULTY_COL_PHONE, phone);

        int rowsAffected = db.update(TABLE_FACULTY, contentValues, FACULTY_COL_ID + " = ?", new String[]{id});
        return rowsAffected > 0;
    }

    public boolean deleteFaculty(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsAffected = db.delete(TABLE_FACULTY, FACULTY_COL_ID + " = ?", new String[]{id});
        return rowsAffected > 0;
    }
}
