package com.example.cs125finalproject_wordsearchsolver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.GridView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Results extends AppCompatActivity {
    String letters = "JCJTLZAJAAMNEXCMCFHSAUOBKASZFNRHELRAKENOTLSCHOOLPUMPKINMAPPLEYXX";
    ArrayList<String> wordSearch;
    String[][] wordSearchArr;
    ArrayList<String> wordsToFind;
    ArrayList<Integer> toHighlight;
    WordSearchSolver solver;
    Button home;
    GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        String temp = letters;
        wordSearch = new ArrayList<>();
        wordsToFind = new ArrayList<>();
        wordsToFind.add("ACORN");
        wordsToFind.add("APPLE");
        wordsToFind.add("FALL");
        wordsToFind.add("JACKET");
        wordsToFind.add("LEAF");
        wordsToFind.add("PUMPKIN");
        wordsToFind.add("RAKE");
        wordsToFind.add("SCHOOL");
        toHighlight = new ArrayList<>();
        int dim = (int) Math.sqrt(letters.length());
        wordSearchArr = new String[dim][dim];

        for (int i = 0; i < dim; i++) {
            for (int j = 0; j < dim; j++) {
                wordSearchArr[i][j] = temp.charAt(0) + "";
                if (temp.length() > 1) {
                    temp = temp.substring(1);
                }
            }
        }

        solver = new WordSearchSolver(wordSearchArr);

        for (int i = 0; i < letters.length(); i++) {
            wordSearch.add(letters.charAt(i) + "");
        }

        for (String s : wordsToFind) {
            for (int i = 0; i < wordSearchArr.length; i++) {
                for (int j = 0; j < wordSearchArr[i].length; j++) {
                    int[] results = solver.search(i, j, s);
                    Log.d("RESULTS", "It reached at least once " + s + " " + results[0]);
                    int dir = results[0];
                    if (dir != -1) {
                        Log.d("RESULTS", results[0] + " " + results[1] + " " + results[2] + s);
                        int x = results[1];
                        int y = results[2];
                        if (dir == 0) {
                            for (int k = 0; k < s.length(); k++) {
                                toHighlight.add(y + (dim * x));
                                x--;
                                y--;
                            }
                        } else if (dir == 1) {
                            for (int k = 0; k < s.length(); k++) {
                                toHighlight.add(y + (dim * x));
                                x--;
                            }
                        } else if (dir == 2) {
                            for (int k = 0; k < s.length(); k++) {
                                toHighlight.add(y + (dim * x));
                                x--;
                                y++;
                            }
                        } else if (dir == 3) {
                            for (int k = 0; k < s.length(); k++) {
                                toHighlight.add(y + (dim * x));
                                y--;
                            }
                        } else if (dir == 4) {
                            for (int k = 0; k < s.length(); k++) {
                                toHighlight.add(y + (dim * x));
                                y++;
                            }
                        } else if (dir == 5) {
                            for (int k = 0; k < s.length(); k++) {
                                toHighlight.add(y + (dim * x));
                                x++;
                                y--;
                            }
                        } else if (dir == 6) {
                            for (int k = 0; k < s.length(); k++) {
                                toHighlight.add(y + (dim * x));
                                x++;
                            }
                        } else {
                            for (int k = 0; k < s.length(); k++) {
                                toHighlight.add(y + (dim * x));
                                x++;
                                y++;
                            }
                        }
                    }

                }
            }
        }
        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Results.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        gridView = findViewById(R.id.gridView);
        gridView.setNumColumns(dim);
        MyAdapter adapter = new MyAdapter(this, R.id.letter, wordSearch);
        gridView.setAdapter(adapter);
    }
    private class MyAdapter extends ArrayAdapter {
        public MyAdapter(Context context, int textViewResourceId, List objects) {
            super(context, textViewResourceId, objects);
        }

        @Override
        public int getCount() {
            return super.getCount();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.griditem, null);
            TextView t = (TextView) v.findViewById(R.id.letter);
            if (toHighlight.contains((position))) {
                t.setBackgroundColor(Color.argb(150,142, 68, 173));
                t.setText(wordSearch.get(position));
                t.setTextColor(Color.WHITE);
            } else {
                t.setText(wordSearch.get(position));
            }
            return v;

        }

    }
}
