package com.example.cs125finalproject_wordsearchsolver;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class EnterTheWords extends AppCompatActivity {

    Button solve;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_the_words);

        solve = findViewById(R.id.solve);
        solve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnterTheWords.this, Results.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
