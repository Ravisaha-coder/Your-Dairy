package com.example.yourdairy;

import androidx.appcompat.app.AppCompatActivity;

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

    EditText editText;
    Button btn_newFile;
    DBHelper db;
    ListView listview;
    Object obj;
    ArrayList arrayList,fileNames;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initGui();
    }
    public void initGui(){

        btn_newFile = findViewById(R.id.btn_newFile);
        listview = findViewById(R.id.listview_files);
        db = new DBHelper(this);
        arrayList = db.getFileNames();
        arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1,arrayList);
        listview.setAdapter(arrayAdapter);

        btn_newFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SpeedToText.class);
                startActivity(intent);
            }
        });

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this,FileActivity.class);
                String fileCreatets = (String) arrayList.get(position);
                FileObj fileObj = db.getFileByCreatets(fileCreatets);
                Bundle bundle = new Bundle();
                bundle.putSerializable("fileObj", fileObj);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        initGui();
        arrayList = db.getFileNames();
        arrayAdapter.notifyDataSetChanged();
        listview.invalidateViews();
        listview.refreshDrawableState();

    }
}