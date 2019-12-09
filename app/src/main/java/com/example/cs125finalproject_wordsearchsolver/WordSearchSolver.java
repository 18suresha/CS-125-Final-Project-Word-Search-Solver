package com.example.cs125finalproject_wordsearchsolver;

//Searching algorithm based off of a searching algorithm from https://www.geeksforgeeks.org/search-a-word-in-a-2d-grid-of-characters/

import android.util.Log;

public class WordSearchSolver {

    private String[][] wordSearch;
    private int[] x = { -1, -1, -1, 0, 0, 1, 1, 1 };
    private int[] y = { -1, 0, 1, -1, 1, -1, 0, 1 };

    WordSearchSolver(String[][] wordSearchEntry){
        wordSearch = wordSearchEntry;
    }

    public int[] search(int row, int col, String word) {
        if (!(wordSearch[row][col].equals(word.charAt(0) + ""))) {
            return new int[] {-1};
        }

        for (int dir = 0; dir < 8; dir++) {
            int k;
            int rd = row + x[dir];
            int cd = col + y[dir];
            for (k = 1; k < word.length(); k++) {
                if (rd >= wordSearch[0].length || rd < 0 || cd >= wordSearch.length || cd < 0) {
                    break;
                }
                if (!wordSearch[rd][cd].equals(word.charAt(k) + "")) {
                    break;
                }
                rd += x[dir];
                cd += y[dir];
            }
            if (k == word.length()) {
                Log.d("RESULTS", word);
                return new int[] {dir, row, col};
            }
        }
        return new int[] {-1};
    }
}
