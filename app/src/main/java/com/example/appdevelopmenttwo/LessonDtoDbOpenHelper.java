package com.example.appdevelopmenttwo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appdevelopmenttwo.bean.Student;

import java.util.ArrayList;
import java.util.List;

public class LessonDtoDbOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "lessonSQLite.db";
    private static final String TABLE_NAME_LESSON = "lesson";

    private static final String CREATE_TABLE_SQL = "create table "+ TABLE_NAME_LESSON +" (id integer primary key autoincrement,monday text,tuesday text,wednesday text,thursday text,friday text,saturday text,sunday text)";

    public LessonDtoDbOpenHelper(Context context) {
        super(context,DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SQL);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public long insertData(LessonDto lessonDto){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("monday",lessonDto.getMonday());
        values.put("tuesday",lessonDto.getTuesday());
        values.put("wednesday",lessonDto.getWednesday());
        values.put("thursday",lessonDto.getThursday());
        values.put("friday",lessonDto.getFriday());
        values.put("saturday",lessonDto.getSaturday());
        values.put("sunday",lessonDto.getSunday());

        return db.insert(TABLE_NAME_LESSON,null,values);
    }

    public int deleteFromDbById(String id) {
        SQLiteDatabase db = getWritableDatabase();
//        return db.delete(TABLE_NAME_NOTE, "id = ?", new String[]{id});
//        return db.delete(TABLE_NAME_NOTE, "id is ?", new String[]{id});
        return db.delete(TABLE_NAME_LESSON, "id like ?", new String[]{id});
    }
    public int updateData(LessonDto lessonDto) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("monday",lessonDto.getMonday());
        values.put("tuesday",lessonDto.getTuesday());
        values.put("wednesday",lessonDto.getWednesday());
        values.put("thursday",lessonDto.getThursday());
        values.put("friday",lessonDto.getFriday());
        values.put("saturday",lessonDto.getSaturday());
        values.put("sunday",lessonDto.getSunday());

        return db.update(TABLE_NAME_LESSON, values, "id like ?", new String[]{lessonDto.getId()});
    }

    public List<LessonDto> queryAllFromDb() {
        SQLiteDatabase db = getWritableDatabase();
        List<LessonDto> lessonDtos = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME_LESSON, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                String monday = cursor.getString(cursor.getColumnIndexOrThrow("monday"));
                String tuesday = cursor.getString(cursor.getColumnIndexOrThrow("tuesday"));
                String wednesday = cursor.getString(cursor.getColumnIndexOrThrow("wednesday"));
                String thursday = cursor.getString(cursor.getColumnIndexOrThrow("thursday"));
                String friday = cursor.getString(cursor.getColumnIndexOrThrow("friday"));
                String saturday = cursor.getString(cursor.getColumnIndexOrThrow("saturday"));
                String sunday = cursor.getString(cursor.getColumnIndexOrThrow("sunday"));

                LessonDto lessonDto = new LessonDto();
                lessonDto.setId(id);
                lessonDto.setMonday(monday);
                lessonDto.setMonday(tuesday);
                lessonDto.setMonday(wednesday);
                lessonDto.setMonday(thursday);
                lessonDto.setMonday(friday);
                lessonDto.setMonday(saturday);
                lessonDto.setMonday(sunday);

                lessonDtos.add(lessonDto);
            }
            cursor.close();
        }
        return lessonDtos;
    }

    public LessonDto queryFromDbByName(String timing) {
        SQLiteDatabase db = getWritableDatabase();
        LessonDto lessonDto= new LessonDto();

        Cursor cursor = db.query(TABLE_NAME_LESSON, null, "timing like ?", new String[]{timing}, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                String name1 = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                String gender = cursor.getString(cursor.getColumnIndexOrThrow("gender"));

                Student student = new Student();
                student.setName(name1);
                student.setPassword(password);
                student.setGender(gender);

            }
            cursor.close();
        }
        return lessonDto;
    }
}

