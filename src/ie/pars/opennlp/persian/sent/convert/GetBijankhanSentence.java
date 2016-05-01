/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.pars.opennlp.persian.sent.convert;

import ie.pars.opennlp.persian.pos.convert.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import opennlp.tools.tokenize.lang.fa.CharMapper;

/**
 *
 * @author Behrang QasemiZadeh <me at atmykitchen.info>
 */
public class GetBijankhanSentence {

    public static void main(String[] s) throws FileNotFoundException {
        String inputFile = "lang/fa/corpus/unitagset-bijankhan";
        try {

            PrintWriter pw = new PrintWriter(new FileWriter(new File("lang/fa/sent/trainset-bj")));
            BufferedReader br = new BufferedReader(new FileReader(new File(inputFile)));
            int count = 0;
            CharMapper cm = new CharMapper();
            
            String sent = "";
            while ((inputFile = br.readLine()) != null) {
                if (inputFile.trim().length() == 0) {
//                    if(sent.trim().endsWith(".")){
//                        sent=sent.trim().substring(0,sent.trim().length()-1);
//                    }
                    pw.println(sent.trim()); //+" ");
                    sent = "";
                } else {
                    //System.out.println(file);
                    String wordToken = cm.mapStrChars(inputFile.split("\t", 2)[0].trim());
                    String posToken = inputFile.split("\t", 2)[1].trim();
                    if (!"PUNCT".equals(posToken)) {
                        sent += " ";
                    }
                    sent += wordToken;
                }
            }
            br.close();
            pw.close();
            System.out.println("count " + count);
        } catch (Exception ex) {
            System.out.println(inputFile);
            Logger.getLogger(GetBijankhanSentence.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
