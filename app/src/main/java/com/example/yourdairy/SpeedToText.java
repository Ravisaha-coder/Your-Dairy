package com.example.yourdairy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class SpeedToText extends AppCompatActivity {

    Button btn_reset,btn_save;
    DBHelper db;
    EditText editTextData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_to_text);

        btn_reset = findViewById(R.id.reset_data);
        btn_save = findViewById(R.id.save_data);
        editTextData = findViewById(R.id.edittext_data);
        db = new DBHelper(this);

        editTextData.setText(db.getdata());

        btn_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Boolean checkResettData = db.resetData();;
                if(checkResettData == true){
                    Toast.makeText(SpeedToText.this, "Reset Successfullly",Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(SpeedToText.this, "Error in reseting data",Toast.LENGTH_SHORT).show();
                }
                editTextData.setText("");
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dataTxt = editTextData.getText().toString();
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
                        editTextData.setText(db.getdata());
                    }
                }
                break;
        }
    }
}