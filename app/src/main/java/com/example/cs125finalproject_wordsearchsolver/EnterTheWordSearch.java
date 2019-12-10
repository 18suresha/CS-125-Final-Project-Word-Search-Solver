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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EnterTheWordSearch extends AppCompatActivity {
    Button wordSearchButton;
    TextView wordSearchText;
    EditText wordSearchEditText;
    String wordSearchLetters;
    public static final int GALLERY_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_the_word_search);

        wordSearchButton = findViewById(R.id.wordSearchButton);
        wordSearchText = findViewById(R.id.wordSearchText);
        wordSearchEditText = findViewById(R.id.wordSearchEditText);
        wordSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = wordSearchButton.getText().toString().toLowerCase();
                if (text.equals("select")) {
                    pickFromGallery();
                    //do the capture stuff
                    wordSearchButton.setText("Continue");
                    wordSearchEditText.setVisibility(View.VISIBLE);

                } else {
                    Intent capWords = new Intent(EnterTheWordSearch.this, EnterTheWords.class);
                    wordSearchLetters = wordSearchLetters.replaceAll(" ", "");
                    wordSearchLetters = wordSearchLetters.replaceAll("\n", "");
                    wordSearchLetters = wordSearchLetters.toUpperCase();
                    capWords.putExtra("wordSearchLetters", wordSearchLetters);
                    startActivity(capWords);
                    finish();
                }
            }
        });
        wordSearchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                wordSearchText.setText(s);
                wordSearchLetters = s.toString();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
                    mTessOCR = new MyTessOCR(this);

                    wordSearchLetters = mTessOCR.getOCRResult(bmp);
                    wordSearchText.setText(wordSearchLetters);
                    wordSearchEditText.setText(wordSearchLetters);
                    Toast.makeText(this, "Make sure to double check the input!", Toast.LENGTH_SHORT).show();
                    break;

            }
    }
}
