package com.example.cs125finalproject_wordsearchsolver;

//Searching algorithm based off of a searching algorithm from https://www.geeksforgeeks.org/search-a-word-in-a-2d-grid-of-characters/

import android.util.Log;

public class WordSearchSolver {

    private String[][] wordSearch;
    private int[] xdir = { -1, -1, -1, 0, 0, 1, 1, 1 };
    private int[] ydir = { -1, 0, 1, -1, 1, -1, 0, 1 };

    WordSearchSolver(String[][] wordSearchEntry){
        wordSearch = wordSearchEntry;
    }

    public int[] search(int x, int y, String word) {
        if (!(wordSearch[x][y].equals(word.charAt(0) + ""))) {
            return new int[] {-1};
        }

        for (int dir = 0; dir < 8; dir++) {
            int foundWordLen;
            int xFound = x + xdir[dir];
            int yFound = y + ydir[dir];
            for (foundWordLen = 1; foundWordLen < word.length(); foundWordLen++) {
                if (xFound >= wordSearch[0].length || xFound < 0 || yFound >= wordSearch.length || yFound < 0) {
                    break;
                }
                if (!wordSearch[xFound][yFound].equals(word.charAt(foundWordLen) + "")) {
                    break;
                }
                xFound += xdir[dir];
                yFound += ydir[dir];
            }
            if (foundWordLen == word.length()) {
                Log.d("RESULTS", word);
                return new int[] {dir, x, y};
            }
        }
        return new int[] {-1};
    }
}
