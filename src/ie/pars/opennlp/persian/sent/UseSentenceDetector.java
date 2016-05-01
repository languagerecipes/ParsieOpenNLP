/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.pars.opennlp.persian.sent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.xml.parsers.ParserConfigurationException;

import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.lang.fa.CharMapper;
import org.xml.sax.SAXException;

/**
 *
 * @author Behrang QasemiZadeh <me at atmykitchen.info>
 */
public class UseSentenceDetector {

    public static void main(String[] sxx) throws FileNotFoundException, IOException, ParserConfigurationException, SAXException {
        File f = new File("corpus.txt");
        String line;
        String file = "";
        BufferedReader br = new BufferedReader(new FileReader(f));
         CharMapper cm = new CharMapper();
        while ((line = br.readLine()) != null) {
            file += cm.mapStrChars(line);
        }

        br.close();
       
        
        // for (File file : f.listFiles()) {
        PrintWriter pw =new PrintWriter(new FileWriter("test-sent"));
        SentenceModel model = new SentenceModel(new File("lang/fa/fa-sent.bin"));
        SentenceDetectorME sentence = new SentenceDetectorME(model);
        String[] sentences = sentence.sentDetect(file);
        for (String s : sentences) {
          //  System.out.println(s);
            pw.println(s);
        }
        pw.close();
    }
}
