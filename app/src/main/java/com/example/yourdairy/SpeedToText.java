package com.example.yourdairy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class SpeedToText extends AppCompatActivity {

    EditText editText;
    Button btn_insert,btn_save;
    DBHelper db;
    ListView listview;
    TextView textviewData;

    ArrayList arrayList;
    ArrayAdapter arrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_to_text);

        //btn_insert = findViewById(R.id.btn_insert);
        btn_save = findViewById(R.id.save_data);
        //editText = findViewById(R.id.et_view);
        textviewData = findViewById(R.id.textview_data);
        db = new DBHelper(this);

        /*arrayList = db.getdata();
        arrayAdapter = new ArrayAdapter(SpeedToText.this, android.R.layout.simple_list_item_1,arrayList);
        listview.setAdapter(arrayAdapter);
*/      textviewData.setText(db.getdata());

        /*btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dataTxt = editText.getText().toString();
                Boolean checkInsertData = db.insertData(dataTxt);
                if(checkInsertData == true){
                    Toast.makeText(SpeedToText.this, "Inserted seccessfully.",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SpeedToText.this, "Inserted seccessfully.",Toast.LENGTH_SHORT).show();
                }
                editText.setText("");
                textviewData.setText(db.getdata());
                *//*arrayList.clear();
                arrayList.addAll(db.getdata());
                arrayAdapter.notifyDataSetChanged();
                listview.invalidateViews();
                listview.refreshDrawableState();*//*
            }
        });

        */btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataTxt = textviewData.getText().toString();
                //Toast.makeText(SpeedToText.this, dataTxt,Toast.LENGTH_SHORT).show();
                Boolean checkInsertFile = db.saveFile(dataTxt);
                if(checkInsertFile == true){
                    Toast.makeText(SpeedToText.this, "File Saved.",Toast.LENGTH_SHORT).show();
                    db.resetData();
                }
                else{
                    Toast.makeText(SpeedToText.this, "Error While saving file.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getSpeechInput(View view) {

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 10);
        } else {
            Toast.makeText(this, "Your Device Don't Support Speech Input", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 10:
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    Boolean checkInsertData = db.insertData(result.get(0));
                    if(checkInsertData == false){
                        Toast.makeText(this, "Retry please !!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        textviewData.setText(db.getdata());
                    }
                }
                break;
        }
    }
}