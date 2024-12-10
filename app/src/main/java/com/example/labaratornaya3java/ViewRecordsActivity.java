package com.example.labaratornaya3java;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class ViewRecordsActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_records);

        dbHelper = new DatabaseHelper(this);
        TextView txtRecords = findViewById(R.id.txtRecords);

        StringBuilder records = new StringBuilder();
        Cursor cursor = dbHelper.getAllRecords();
        if (cursor.moveToFirst()) {
            do {
                records.append("ID: ").append(cursor.getInt(0)).append(", ")
                        .append("ФИО: ").append(cursor.getString(1)).append(", ")
                        .append("Время добавления: ").append(cursor.getString(2)).append("\n");
            } while (cursor.moveToNext());
        }
        cursor.close();
        txtRecords.setText(records.toString());
    }
}
