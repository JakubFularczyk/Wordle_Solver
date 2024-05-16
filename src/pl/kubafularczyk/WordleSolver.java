package pl.kubafularczyk;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class WordleSolver
{
    private Set<Character> bonusLetter = new HashSet<>(Arrays.asList('r','l','s','t','n','e'));

    public static void main(String[] args) throws IOException {
        new WordleSolver().run(5);
    }

    private void run(int wordLenght) throws IOException {
        Set<String> dictionary = getWords("index.json",wordLenght);
        Set<String> words = new HashSet<>(dictionary);

        try(Scanner scanner = new Scanner(System.in)){
            for(int i=0; i<6; i++){
                String tryWord=getTryWord(words);
                String result;

                do {
                    System.out.println("#"+(i+1)+" Try this:  "+tryWord+" (out of "+words.size()+" words)");
                    System.out.println("(g=green, y=yellow, r=red)");
                    result=scanner.nextLine().trim();

                    if(result.startsWith("get ")){
                        printWordWith(dictionary,result.substring(4).toCharArray());
                    } else {
                        words=getNewWords(words,tryWord,result);
                    }
                }
                while(result.startsWith("get "));
            }
        }
    }

    private Set<String> getWords(String path, int wordLenght) throws IOException {
        Set<String> words = new HashSet<>();


        for(String w: Files.readAllLines(Paths.get(path)).get(0).split(","))
        {
            String word=w.replaceAll("[\\[\\],\\\"]", "");

            if(wordLenght==word.length()){
                words.add(word);
            }
        }
        return null;
    }

    private String getTryWord(Set<String> words) {
        String bestWord=null;
        int uniqueLetters=0;
        int maxScore=words.iterator().next().length()*2;
        Set<Character> letters = new HashSet<>();

        for(String word : words) {
            for(int i =0; i<word.length(); i++){
                letters.add(word.charAt(i));
            }
        }

        int score = letters.size();

        for(char c : letters) {
            if(bonusLetter.contains(c)){
                score++;
            }
        }

        if(score==maxScore){
            return word;
        }
        return null;
    }

}
