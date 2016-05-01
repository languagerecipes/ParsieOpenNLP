package sent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


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
import util.CharMapper;

/**
 *
 * @author Behrang QasemiZadeh <me at atmykitchen.info>
 */
public class UseSentenceDetector {

    public static void main(String[] sxx) throws FileNotFoundException, IOException, ParserConfigurationException, SAXException {
        File f = new File("C:\\prj\\Parsie\\corpus\\1-83\\shargh 18-1-83 .txt.txt");
        String line;
        String file = "";
        BufferedReader br = new BufferedReader(new FileReader(f));
        while ((line = br.readLine()) != null) {
            file += line;
        }

        CharMapper cm= new CharMapper();
        br.close();
        // for (File file : f.listFiles()) {
        PrintWriter pw =new PrintWriter(new FileWriter("test-sent"));
        SentenceModel models = new SentenceModel(new File("../parsie/final-data/model/sent-farsi-model.bin"));
         SentenceModel models2 = new SentenceModel(new File("../parsie/final-data/model/sent-farsi-model2.bin"));
        SentenceDetectorME sentence = new SentenceDetectorME(models);
        SentenceDetectorME sentence2 = new SentenceDetectorME(models2);
        String[] sentences = sentence2.sentDetect(cm.mapStrChars(file));
        for (String s : sentences) {
            System.err.println(s);
            String[] sentDetect = sentence.sentDetect(s);
            if (sentDetect.length > 1) {
                System.err.println("**** " + s);
//                for (String sx : sentDetect) {
//                    System.err.println(sx);
//                }
            }
            //pw.println(s);
        }
        pw.close();
    }
}
