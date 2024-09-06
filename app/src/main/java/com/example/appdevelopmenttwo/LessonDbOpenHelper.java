package com.example.appdevelopmenttwo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.appdevelopmenttwo.bean.Lesson;

import java.util.ArrayList;
import java.util.List;

public class LessonDbOpenHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "lessonSQLite.db";
    private static final String TABLE_NAME_LESSON = "lesson";

    private static final String CREATE_TABLE_SQL = "create table "+ TABLE_NAME_LESSON +" (id integer primary key autoincrement,uuid text,name text,day integer,timing integer,startTime text,endTime text,week text,location text)";
    private static final String DROP_TABLE_SQL = "drop table if exists lesson";
    private static final String DELETE_TABLE_SQL = "delete from lesson";

    public LessonDbOpenHelper(Context context) {
        super(context,DB_NAME,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL(DROP_TABLE_SQL);
//        db.execSQL(DELETE_TABLE_SQL);
        db.execSQL(CREATE_TABLE_SQL);

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    public long insertData(Lesson lesson){
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("uuid",lesson.getUuid());
        values.put("name",lesson.getName());
        values.put("day",lesson.getDay());
        values.put("timing",lesson.getTiming());
        values.put("startTime",lesson.getStartTime());
        values.put("endTime",lesson.getEndTime());
        values.put("week",lesson.getWeek());
        values.put("location",lesson.getLocation());

        return db.insert(TABLE_NAME_LESSON,null,values);
    }

    public int updateData(Lesson lesson) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("uuid",lesson.getUuid());
        values.put("name",lesson.getName());
        values.put("day",lesson.getDay());
        values.put("timing",lesson.getTiming());
        values.put("startTime",lesson.getStartTime());
        values.put("endTime",lesson.getEndTime());
        values.put("week",lesson.getWeek());
        values.put("location",lesson.getLocation());

        return db.update(TABLE_NAME_LESSON, values, "id like ?", new String[]{lesson.getId()});
    }

    public int deleteFromDbByUuid(String uuid) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME_LESSON, "uuid like ?", new String[]{uuid});
    }

    public int deleteFromDbById(String id) {
        SQLiteDatabase db = getWritableDatabase();
        return db.delete(TABLE_NAME_LESSON, "id like ?", new String[]{id});
    }

//    public int updateData(Lesson lesson) {
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues values = new ContentValues();
//        values.put("uuid",lesson.getName());
//        values.put("name",lesson.getName());
//        values.put("day",lesson.getDay());
//        values.put("timing",lesson.getTiming());
//        values.put("week",lesson.getWeek());
//        values.put("location",lesson.getLocation());
//        //会使得所有lesson都被覆盖，35个课程数据一样
//        return db.update(TABLE_NAME_LESSON, values, "uuid like ?", new String[]{lesson.getUuid()});
//    }

    public List<Lesson> queryAllFromDb() {
        SQLiteDatabase db = getWritableDatabase();
        List<Lesson> lessons = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME_LESSON, null, null, null, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                String uuid = cursor.getString(cursor.getColumnIndexOrThrow("uuid"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                int day = cursor.getInt(cursor.getColumnIndexOrThrow("day"));
                int timing = cursor.getInt(cursor.getColumnIndexOrThrow("timing"));
                String startTime = cursor.getString(cursor.getColumnIndexOrThrow("startTime"));
                String endTime = cursor.getString(cursor.getColumnIndexOrThrow("endTime"));
                String week = cursor.getString(cursor.getColumnIndexOrThrow("week"));
                String location = cursor.getString(cursor.getColumnIndexOrThrow("location"));

                Lesson lesson = new Lesson();
                lesson.setId(id);
                lesson.setUuid(uuid);
                lesson.setName(name);
                lesson.setDay(day);
                lesson.setTiming(timing);
                lesson.setStartTime(startTime);
                lesson.setEndTime(endTime);
                lesson.setWeek(week);
                lesson.setLocation(location);

                lessons.add(lesson);
            }
            cursor.close();
        }
        return lessons;
    }

    public List<Lesson> queryFromDbByUuid(String uuid1) {
        SQLiteDatabase db = getWritableDatabase();
        List<Lesson> lessons = new ArrayList<>();

        Cursor cursor = db.query(TABLE_NAME_LESSON,null, "uuid like ?", new String[]{uuid1}, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndexOrThrow("id"));
                String uuid = cursor.getString(cursor.getColumnIndexOrThrow("uuid"));
                String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
                int day = cursor.getInt(cursor.getColumnIndexOrThrow("day"));
                int timing = cursor.getInt(cursor.getColumnIndexOrThrow("timing"));
                String startTime = cursor.getString(cursor.getColumnIndexOrThrow("startTime"));
                String endTime = cursor.getString(cursor.getColumnIndexOrThrow("endTime"));
                String week = cursor.getString(cursor.getColumnIndexOrThrow("week"));
                String location = cursor.getString(cursor.getColumnIndexOrThrow("location"));

                Lesson lesson = new Lesson();
                lesson.setId(id);
                lesson.setUuid(uuid);
                lesson.setName(name);
                lesson.setDay(day);
                lesson.setTiming(timing);
                lesson.setStartTime(startTime);
                lesson.setEndTime(endTime);
                lesson.setWeek(week);
                lesson.setLocation(location);

                lessons.add(lesson);
            }
            cursor.close();
        }
        return lessons;
    }
}

