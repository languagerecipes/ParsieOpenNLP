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
public class UniDepFaToTokenSampleFile {
    
    public static void main(String[] ss) throws FileNotFoundException, IOException {
        String udp = "final-data/fa-ud-train.conllu";
        BufferedReader br = new BufferedReader(new FileReader(udp));
        FileWriter pw = new FileWriter(new File("final-data/token/token-sample-ud-test-fa.txt"));
        String line;
        TreeSet<String> set = new TreeSet();
        String sentence = "";
        String tokens = "";
        int count=0;
        while ((line = br.readLine()) != null) {
        //   if(count++>10000){break;}
            if (line.length()!=0 ) {

                String[] split = line.split("\t");
                String id = split[0];

                String word = split[1];

                String pos = split[3];
                if (pos.equals("PUNCT")) {
                    //on purpose I put the speial characters »« attached to text to have a model more robust to 
                    if (word.equals("«")) {
                        sentence += " " + word;
                    } else {
                        sentence += word;
                    }

                } else {
                    if(sentence.endsWith("«")){
                       sentence +=  word;
                    }else{
                    sentence += " " + word;}
                }
                int start = sentence.trim().lastIndexOf(word);
                if(!id.contains("-")){
                tokens += start + "<t>" + (start + word.length()) + "<t>" + word+ "\t";
                }else{
                    for (int i = 0; i < id.split("-").length; i++) {
                        line = br.readLine();
                        split = line.split("\t");
                        word = split[1];
                        start = sentence.trim().lastIndexOf(word);
                        tokens += start + "<t>" + (start + word.length()) + "<t>" + word + "\t";
                        
                        pos = split[3];

                    }
                }

            } else {
                pw.append(sentence.trim() + "<sample>" + tokens +"\n");
                sentence = "";tokens="";
                System.out.print(".");
            }
            
        }
        br.close();
        pw.close();
        for(String s: set){
            System.out.println(s);
        }
    }
}
