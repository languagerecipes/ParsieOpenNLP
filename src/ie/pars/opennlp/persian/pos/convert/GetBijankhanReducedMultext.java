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
import java.util.logging.Level;
import java.util.logging.Logger;
import opennlp.tools.tokenize.lang.fa.CharMapper;

/**
 *
 * @author Behrang QasemiZadeh <me at atmykitchen.info>
 */
public class GetBijankhanReducedMultext {

    public static void main(String[] s) throws FileNotFoundException {
        String file = "lang/fa/corpus/e-fa-mlt.txt";
        System.out.println("Read!");
        try {
            CharMapper cm = new CharMapper();
            PrintWriter pw = new PrintWriter(new FileWriter(new File("lang/fa/pos/trainset-red-mlt")));
            BufferedReader br = new BufferedReader(new FileReader(new File(file)));
            int count = 0;
            String sent = "";
            while ((file = br.readLine()) != null) {
                if (file.trim().length() == 0) {
                    pw.println(sent.trim());
                    sent = "";
                } else {
                    //System.out.println(file);
                    String wordToken = cm.mapStrChars(file.split("\t", 2)[0].trim());
                    String posToken = file.split("\t", 2)[1].trim();
//                if (!"DELM".equals(posToken)) {
//                    sent += " ";
//                }
                    sent += " " + wordToken + "_" + PoSUtil.reduceMULTEXTTag(posToken);
                }
            }
            br.close();
            pw.close();
            System.out.println("count " + count);
        } catch (Exception ex) {
            System.out.println(file);
            Logger.getLogger(GetBijankhanReducedMultext.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
