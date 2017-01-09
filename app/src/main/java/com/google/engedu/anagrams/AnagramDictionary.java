package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    //Declare wordList
    private ArrayList<String> wordList = new ArrayList<String>();
    private HashSet<String> wordSet = new HashSet<>();
    private HashMap<String, ArrayList<String>> lettersToWord = new HashMap<>();
    private HashMap<Integer, ArrayList<String>> sizeToWords = new HashMap<>();
    private int wordLength=DEFAULT_WORD_LENGTH;


    public AnagramDictionary(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;

        while ((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            String sortedWord = sortLetters(word);
            if (lettersToWord.containsKey(sortedWord)) {
                lettersToWord.get(sortedWord).add(word);
            } else {
                ArrayList<String> newList = new ArrayList<>();
                newList.add(word);
                lettersToWord.put(sortedWord, newList);
            }
            if (sizeToWords.containsKey(word.length())) {
                sizeToWords.get(word.length()).add(word);
            } else {
                ArrayList<String> newList = new ArrayList<>();
                newList.add(word);
                sizeToWords.put(word.length(), newList);
            }
        }

    }

    public boolean isGoodWord(String word, String base) {

        if (wordSet.contains(word) && !(word.contains(base)))
            return true;
        else
            return false;
    }

    public ArrayList<String> getAnagrams(String targetWord) {
        ArrayList<String> result = new ArrayList<String>();
        String sortedWord = sortLetters(targetWord);
        if (lettersToWord.containsKey(sortedWord)) {
            result.addAll(lettersToWord.get(sortedWord));
        }

        return result;
    }

    public ArrayList<String> getAnagramsWithTwoMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 97; i < 123; i++) {

            for (int j = 97; j < 123; j++) {
                String newString = word.concat(String.valueOf((char) i) + String.valueOf((char) j));
                ArrayList<String> anagrams = getAnagrams(newString);
                for (String anagram : anagrams) {
                    if (isGoodWord(anagram, word))
                        result.add(anagram);
                }
            }
        }
        return result;
    }

    public ArrayList<String> getAnagramsWithOneMoreLetter(String word) {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 97; i < 123; i++) {
            String newString = word.concat(String.valueOf((char) i));
            ArrayList<String> anagrams = getAnagrams(newString);
            for (String anagram : anagrams) {
                if (isGoodWord(anagram, word))
                    result.add(anagram);
            }
        }

        return result;
    }


    public String pickGoodStarterWord() {

        ArrayList<String> words = sizeToWords.get(wordLength);
        if(wordLength< MAX_WORD_LENGTH)
            wordLength++;

        while (true) {
            String randomWord = words.get(random.nextInt(words.size() - 1));
            ArrayList<String> anagrams = getAnagramsWithOneMoreLetter(randomWord);
            if (anagrams.size() >= MIN_NUM_ANAGRAMS)
                return randomWord;        }

    }


 /*   public String pickGoodStarterWord() {
        String randomWord = wordList.get(new Random().nextInt(wordList.size() - 1));
        ArrayList<String> anagrams = getAnagrams(randomWord);
        while (true) {
            if (anagrams.size() >= MIN_NUM_ANAGRAMS)
                return randomWord;
        }
    }
    */

    public String sortLetters(String s){
        char[] letter=s.toCharArray();
        Arrays.sort(letter);
        return new String(letter);
    }

}
