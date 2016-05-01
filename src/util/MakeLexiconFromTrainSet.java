/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author Behrang QasemiZadeh <me at atmykitchen.info>
 */
public class MakeLexiconFromTrainSet {

    public static void main(String[] ss) throws FileNotFoundException, IOException {
        String file = "lang/fa/pos/train-set-all";
        String line;
        BufferedReader br = new BufferedReader(new FileReader(file));
        TreeMap<String, TreeSet<String>> wordPos = new TreeMap();
        while ((line = br.readLine()) != null) {
            String[] split = line.split(" ");
            for (int i = 0; i < split.length; i++) {
                String[] wordpos = split[i].split("_", 2);
                if (wordPos.containsKey(wordpos[0])) {
                    wordPos.get(wordpos[0]).add(wordpos[1]);
                } else {
                    TreeSet<String> s = new TreeSet<>();
                    s.add(wordpos[1]);
                    wordPos.put(wordpos[0], s);
                }
            }
        }
        System.out.println("There are " + wordPos.size());
        PrintWriter pw = new PrintWriter(new FileWriter("lang/fa/pos/dictionaries/dictionary-trainset"));
        for(String w: wordPos.keySet()){
            
            for(String pos: wordPos.get(w)){
                pw.println(w+"\t"+pos);
            }
            
        }
        pw.close();
    }
}
