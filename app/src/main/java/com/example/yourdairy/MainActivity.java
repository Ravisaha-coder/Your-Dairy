package com.example.yourdairy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText editText;
    Button btn_insert;
    DBHelper db;
    ListView listview;

    ArrayList arrayList;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_insert = findViewById(R.id.btn_insert);
        editText = findViewById(R.id.et_view);
        listview = findViewById(R.id.listview);
        db = new DBHelper(this);

        arrayList = db.getdata();
        arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,arrayList);
        listview.setAdapter(arrayAdapter);

        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dataTxt = editText.getText().toString();
                Boolean checkInsertData = db.insertData(dataTxt);
                if(checkInsertData == true){
                    Toast.makeText(MainActivity.this, "Inserted seccessfully.",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Inserted seccessfully.",Toast.LENGTH_SHORT).show();
                }
                editText.setText("");
                arrayList.clear();
                arrayList.addAll(db.getdata());
                arrayAdapter.notifyDataSetChanged();
                listview.invalidateViews();
                listview.refreshDrawableState();
            }
        });
    }
}