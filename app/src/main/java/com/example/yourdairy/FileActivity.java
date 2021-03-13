package com.example.yourdairy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class FileActivity extends AppCompatActivity {
    TextView fileName;
    ListView fileData;
    FileObj fileObj;
    ArrayList arrayList = new ArrayList();
    ArrayAdapter dataAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_file);

        Bundle bundle = getIntent().getExtras();
        fileObj = (FileObj) bundle.get("fileObj");
        arrayList.add(fileObj.getFileData());
        dataAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);

        fileName = findViewById(R.id.file_name);
        fileName.setText(fileObj.getFileName());

        fileData = findViewById(R.id.file_data);
        fileData.setAdapter(dataAdapter);
    }
}