package com.example.cs125finalproject_wordsearchsolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button openCamera;
    public static final int REQUEST_IMAGE_CAPTURE = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openCamera = findViewById(R.id.openCamera);
        openCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (openCamera.getText().toString().equals("Continue")) {
                    Intent enterWords = new Intent(MainActivity.this, EnterTheWords.class);
                    startActivity(enterWords);
                    finish();
                } else {
                    Intent takeImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    if (takeImage.resolveActivity(getPackageManager()) != null){
                        startActivityForResult(takeImage, REQUEST_IMAGE_CAPTURE);
                        openCamera.setText("Continue");
                    }
                }
            }
        });
    }
}