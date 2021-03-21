package com.example.yourdairy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btn_newFile;
    DBHelper db;
    ArrayList fileNames;
    FilesAdapter filesAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGui();
    }
    public void initGui(){

        btn_newFile = findViewById(R.id.btn_newFile);
        recyclerView = (RecyclerView) findViewById(R.id.rv_fileNames);
        db = new DBHelper(this);
        fileNames = db.getFileNames();
        filesAdapter = new FilesAdapter(fileNames);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(filesAdapter);

        btn_newFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SpeedToText.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        initGui();
        fileNames = db.getFileNames();
        filesAdapter.notifyDataSetChanged();
    }
}