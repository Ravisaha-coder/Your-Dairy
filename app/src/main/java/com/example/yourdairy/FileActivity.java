package com.example.yourdairy;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class FileActivity extends AppCompatActivity {
    TextView fileName;
    ListView fileData;
    FileObj fileObj;
    ArrayList arrayList = new ArrayList();
    ArrayAdapter dataAdapter;
    DBHelper db;
    Button dlt_btn;
    AlertDialog.Builder builder;
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

        dlt_btn=(Button) findViewById(R.id.delete_btn);
        builder = new AlertDialog.Builder(this);
        dlt_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder.setMessage("Do you want to delete this file?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                               deleteItem(view);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();

                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Delete File!");
                alert.show();
            }
        });
    }

    public void deleteItem(View view){
        db = new DBHelper(view.getContext());
        boolean result = db.deleteFileByCreatets(fileObj.getFileName());
        if(result){
            Toast.makeText(FileActivity.this, "File Deleted",Toast.LENGTH_LONG).show();
            finish();
        }
        else{
            Toast.makeText(FileActivity.this, "Failed to delete file.",Toast.LENGTH_SHORT).show();
        }

    }
}