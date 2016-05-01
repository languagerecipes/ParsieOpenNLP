/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opennlp.tools.tokenize.lang.fa.convert;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.TreeSet;

/**
 *
 * @author Behrang QasemiZadeh <me at atmykitchen.info>
 */
public class BJWithClitic {

    public static void main(String[] ss) throws FileNotFoundException, IOException {
        String udp = "lang/fa/corpus/e-fa-mlt.txt";
        BufferedReader br = new BufferedReader(new FileReader(udp));
        FileWriter pw = new FileWriter(new File("lang/fa/corpus/test-to-delete-token"));
        String line;
        TreeSet<String> set = new TreeSet();
        String sentence = "";
        String tokens = "";
        int count = 0;
        boolean hasClitic = false;
        while ((line = br.readLine()) != null) {
            //   if(count++>10000){break;}
            if (line.length() != 0) {

                String[] split = line.split("\t");
                String word = split[0];
                String pos = split[1];
                
                if (pos.equals("PUNCT")) {
                    //on purpose I put the speial characters »« attached to text to have a model more robust to 
                    if (word.equals("«")) {
                        sentence =sentence+" " + word;
                    } else {
                        sentence += word;
                    }

                }else if (pos.startsWith("!P")) {
                    //on purpose I put the speial characters »« attached to text to have a model more robust to 
                    sentence += word;
                    hasClitic = true;

                } else 
                    if (sentence.endsWith("«")) {
                        sentence +=  word ;
                    } else {
                        sentence += " "+ word;
                    }
                

                int start = sentence.trim().lastIndexOf(word);

                tokens += start + "<t>" + (start + word.length()) + "<t>" + word + "\t";

            } else {

                if (hasClitic) {
                    pw.append(sentence.trim()+ "<sample>" + tokens +"\n");
                }
                sentence = "";
                tokens = "";
                hasClitic = false;

            }

        }
        br.close();
        pw.close();
        for (String s : set) {
            System.out.println(s);
        }
    }
}
