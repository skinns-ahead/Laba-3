package com.example.labaratornaya3java;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "students.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "classmates";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "full_name";
    private static final String COLUMN_TIME_ADDED = "time_added";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Создаем таблицу при первом запуске приложения
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_NAME + " TEXT, " +
                COLUMN_TIME_ADDED + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP)";
        db.execSQL(createTable);
        // Удаляем и добавляем начальные записи
        resetData(db);
    }

    public void resetData(SQLiteDatabase db) {
        // Очищаем все записи из таблицы и добавляем начальные записи
        db.execSQL("DELETE FROM " + TABLE_NAME);
        addInitialRecords(db);
    }

    public void addInitialRecords(SQLiteDatabase db) {
        // Добавляем 5 начальных записей
        for (int i = 1; i <= 5; i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, "Студент " + i);
            db.insert(TABLE_NAME, null, values);
        }
    }

    public void addRecord(String name) {
        // Метод для добавления новой записи в таблицу
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        db.insert(TABLE_NAME, null, values);
    }

    public void updateLastRecord(String name) {
        // Метод для обновления ФИО последней записи
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(" + COLUMN_ID + ") FROM " + TABLE_NAME, null);
        if (cursor.moveToFirst()) {
            int lastId = cursor.getInt(0);
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, name);
            db.update(TABLE_NAME, values, COLUMN_ID + "=?", new String[]{String.valueOf(lastId)});
        }
        cursor.close();
    }

    public Cursor getAllRecords() {
        // Получаем все записи из таблицы
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Обновляем базу данных при изменении версии
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
}
