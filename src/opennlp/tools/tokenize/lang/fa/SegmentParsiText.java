/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opennlp.tools.tokenize.lang.fa;

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
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.xml.sax.SAXException;

/**
 *
 * @author Behrang QasemiZadeh <me at atmykitchen.info>
 */
public class SegmentParsiText {
     public static void main(String[] sxx) throws FileNotFoundException, IOException, ParserConfigurationException, SAXException {

        if (sxx.length != 2) {
            System.out.println("Please provide path to input and for output file");

        }
        CharMapper cm = new CharMapper();
        File f = new File(sxx[0]);
        String line;
        String file = "";
        BufferedReader br = new BufferedReader(new FileReader(f));
        while ((line = br.readLine()) != null) {
            file += line;
        }

        br.close();
       
        PrintWriter pw =new PrintWriter(new FileWriter("test-sent"));
        SentenceModel model = new SentenceModel(new File("lang/fa/fa-sent.bin"));
        SentenceDetectorME sentence = new SentenceDetectorME(model);
        String[] sentences = sentence.sentDetect(cm.mapStrChars(file));
        TokenizerModel modelt = new TokenizerModel(new File("lang/fa/fa-token.bin"));
        Tokenizer tokenizer = new TokenizerME(modelt);
        for (String s : sentences) {
            //  System.out.println(s);
            for (String t : tokenizer.tokenize(s)) {
                pw.println(t);
            }
            pw.println(s);
        }
        pw.close();
    }

}
