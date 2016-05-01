/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.pars.opennlp.persian.pos.convert;


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
public class UniDepFaToSentenceTaggedStd {

    public static void main(String[] ss) throws FileNotFoundException, IOException {
        String udp = "final-data/fa-ud-train.conllu";
        BufferedReader br = new BufferedReader(new FileReader(udp));
        FileWriter pw = new FileWriter(new File("final-data/pos/sent-tagged-ud-train-set.std"));
        String line;
        TreeSet<String> set = new TreeSet();
        String sentence = "";

        int count = 0;
        String tokens="";
        while ((line = br.readLine()) != null) {
            //   if(count++>10000){break;}
            if (line.length() != 0) {

                String[] split = line.split("\t");
                String id = split[0];

                String word = split[1];

                String pos = split[3];

                if (!id.contains("-")) {
                    tokens += word + "_" + pos + " ";
                } else {
                    for (int i = 0; i < id.split("-").length; i++) {
                        line = br.readLine();
                        split = line.split("\t");
                        word = split[1];
                        pos = split[3];
                        tokens += word + "_" + pos + " ";
                    }
                }
                //sentence+=tokens;

            } else {
                pw.append(tokens.trim() + "\r\n");
                sentence = "";
                tokens ="";
                System.out.print(".");
            }

        }
        br.close();
        pw.close();
        for (String s : set) {
            System.out.println(s);
        }
    }
}
