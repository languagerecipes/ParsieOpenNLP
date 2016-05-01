/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.pars.opennlp.persian.pos.convert;

import ie.pars.multext.PoSUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Behrang QasemiZadeh <me at atmykitchen.info>
 */
public class BREAK_ZWNJ_THEFILEFORMAJIDMULTEX1 {

    public static void main(String[] s) throws FileNotFoundException, IOException {
        // String file2 = "C:\\prj\\Parsie\\corpus\\UPC-2016.txt";
        String file
                = "C:\\prj\\Parsie\\corpus\\MLTCorpus.txt";
        PrintWriter fw = new PrintWriter(new FileWriter(new File("universal-sent-bq.txt.split")));
        try {
            TreeMap<String, Integer> countMap = new TreeMap();
            //  BufferedReader br2 = new BufferedReader(new InputStreamReader(new FileInputStream(file2), "utf-8"));
            BufferedReader br = new BufferedReader(new FileReader(new File(file)));
            // BufferedReader br2 = new BufferedReader(new FileReader(new File(file2)));
            int count = 0;
         //   Pattern r = Pattern.compile("\\w+\\t\\w+");

            // Now create matcher object.
            String sent = "";
            while ((file = br.readLine()) != null) {
                if (file.split("\t").length == 2 &&!file.startsWith("#")) {
                    //  String l =br2.readLine();
                    if (file.equals(".\t.") || file.equals("؟\t,") || file.equals("!\t,")) {
                        //System.out.println("kk");
                        fw.println(file.split("\t")[0] + "\tPUNCT");
                        fw.println();
                    } else {
                        if (file.split("\t")[1].equals(",")) {
                            file = file.split("\t")[0] + "\tPUNCT";
                        } else if (file.split("\t")[1].startsWith("!") || file.split("\t")[1].startsWith("@")) {
                            file = file.split("\t")[0] + "\t" + file.split("\t")[1].substring(1);

                        }
                        //Matcher m = r.matcher(file);
                        String word = file.split("\t")[0];
                        String tag = PoSUtil.MultextTagtoUniversalTag(file.split("\t")[1])
                                + "\n";
                        for (String prt : word.split("\u200c")) {
                            fw.append(prt + "\t" + tag
                            );
                        }
                        //    fw.append(file+"\n");

                        if (countMap.containsKey(tag)) {
                            Integer get = countMap.get(tag) + 1;
                            countMap.put(tag, get);
                        } else {
                            countMap.put(tag, 1);
                        }
                        //if("CLITIC".equals(file.split("\t")[1])&& !file.split("\t")[0].equals("را")){
                        // System.out.println(file.split("\t")[0]);
                        //}
                    }
                } else {
                    fw.append("\n");
                    //System.out.println("");
                }

            }
            fw.close();
            // br2.close();
            br.close();

            System.out.println("count " + countMap.size());
            for (String k : countMap.keySet()) {
                System.out.println(k + " " + countMap.get(k));
            }
        } catch (Exception ex) {
            System.out.println(file);
            Logger.getLogger(BREAK_ZWNJ_THEFILEFORMAJIDMULTEX1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
