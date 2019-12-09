package com.example.cs125finalproject_wordsearchsolver;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    Button openCamera;
    public static final int REQUEST_IMAGE_CAPTURE = 9;
    private static final int GALLERY_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pickFromGallery();
//        Bitmap bmp = BitmapFactory.decodeStream(getApplicationContext().getResources().openRawResource(R.raw.ocrtestwordsearch2));
//        MyTessOCR mTessOCR;
//        mTessOCR = new MyTessOCR(MainActivity.this);
//        String temp = mTessOCR.getOCRResult(bmp);
//        Log.d("text", temp);
//        openCamera = findViewById(R.id.openCamera);
//        openCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (openCamera.getText().toString().equals("Continue")) {
//                    Intent enterWords = new Intent(MainActivity.this, EnterTheWords.class);
//                    startActivity(enterWords);
//                    finish();
//                } else {
//                    Intent takeImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    if (takeImage.resolveActivity(getPackageManager()) != null){
//                        startActivityForResult(takeImage, REQUEST_IMAGE_CAPTURE);
//                        openCamera.setText("Continue");
//                    }
//                }
//            }
//        });
    }

    private void pickFromGallery() {
        //Create an Intent with action as ACTION_PICK
        Intent intent = new Intent(Intent.ACTION_PICK);
        // Sets the type as image/*. This ensures only components of type image are selected
        intent.setType("image/*");
        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
        String[] mimeTypes = {"image/jpeg", "image/png"};
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        // Launching the Intent
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    public void onActivityResult(int requestCode,int resultCode,Intent data){
        // Result code is RESULT_OK only if the user selects an Image
        if (resultCode == Activity.RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST_CODE:
                    //data.getData return the content URI for the selected Image
                    Log.d("message", "GALLERY_REQUEST_CODE");
                    Uri selectedImage = data.getData();
                    String[] filePathColumn = { MediaStore.Images.Media.DATA };
                    // Get the cursor
                    Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                    // Move to first row
                    cursor.moveToFirst();
                    //Get the column index of MediaStore.Images.Media.DATA
                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    //Gets the String value in the column
                    String imgDecodableString = cursor.getString(columnIndex);
                    cursor.close();
                    // Set the Image in ImageView after decoding the String
                    Bitmap bmp = BitmapFactory.decodeFile(imgDecodableString);
                    Log.d("abs path", imgDecodableString);
                    MyTessOCR mTessOCR;
                    mTessOCR = new MyTessOCR(MainActivity.this);

                    String temp = mTessOCR.getOCRResult(bmp);
                    Log.d("debug", "text produced");
//                    Log.d("text", temp);
//                    FOR word search
                    String[] arr = temp.split("\n");
                    String oneLine = "";
                    for (String s : arr) {
                        System.out.println(s);
                        oneLine += s;
                    }
                    System.out.println(oneLine);
//                    FOR words
//                    for (String s : temp.split("\n ")) {
//                        for (String s1 : s.split(" ")) {
//                            System.out.println(s1);
//                        }
//                    }
                    break;

            }
    }
}