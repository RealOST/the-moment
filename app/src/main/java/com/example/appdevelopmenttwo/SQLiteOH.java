package com.example.appdevelopmenttwo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appdevelopmenttwo.bean.Student;

import java.util.ArrayList;
import java.util.List;

public class SQLiteOH extends SQLiteOpenHelper {

    private static final String DB_NAME = "name";
    private static final String TABLE_NAME_STUDENT = "student";

    private static final String CREATE_TABLE_SQL = "create table "+ TABLE_NAME_STUDENT +" (id integer primary key autoincrement,name text,password text,uuid text);\n";

    public SQLiteOH(Context context) {
        super(context,DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public long insertData(Student student) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("name",student.getName());
        values.put("password",student.getPassword());
        values.put("uuid",student.getUuid());

        return db.insert(TABLE_NAME_STUDENT,null,values);
    }

    public List<Student> queryFromDbByName(String name) {
        SQLiteDatabase db = getWritableDatabase();
        List<Student> studentList= new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME_STUDENT, null, "name like ?", new String[]{name}, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name1 = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                String uuid = cursor.getString(cursor.getColumnIndexOrThrow("uuid"));

                Student student = new Student();
                student.setName(name1);
                student.setPassword(password);
                student.setUuid(uuid);

                studentList.add(student);
            }
            cursor.close();
        }
        return studentList;
    }

    public List<Student> queryAllFromDb() {
        SQLiteDatabase db = getWritableDatabase();
        List<Student> studentList= new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME_STUDENT, null, null,null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name1 = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                String uuid = cursor.getString(cursor.getColumnIndexOrThrow("uuid"));

                Student student = new Student();
                student.setName(name1);
                student.setPassword(password);
                student.setUuid(uuid);

                studentList.add(student);
            }
            cursor.close();
        }
        return studentList;
    }
}