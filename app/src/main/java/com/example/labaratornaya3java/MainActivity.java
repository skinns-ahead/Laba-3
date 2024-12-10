package com.example.labaratornaya3java;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);

        Button btnViewRecords = findViewById(R.id.btnViewRecords);
        Button btnAddRecord = findViewById(R.id.btnAddRecord);
        Button btnUpdateRecord = findViewById(R.id.btnUpdateRecord);

        btnViewRecords.setOnClickListener(v -> startActivity(new Intent(this, ViewRecordsActivity.class)));

        btnAddRecord.setOnClickListener(v -> {
            dbHelper.addRecord("Новый студент");
            Toast.makeText(this, "Запись добавлена", Toast.LENGTH_SHORT).show();
        });

        btnUpdateRecord.setOnClickListener(v -> {
            dbHelper.updateLastRecord("Иванов Иван Иванович");
            Toast.makeText(this, "Последняя запись обновлена", Toast.LENGTH_SHORT).show();
        });
    }
}
