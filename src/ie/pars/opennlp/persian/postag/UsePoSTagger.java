/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.pars.opennlp.persian.postag;

import ie.pars.opennlp.persian.pos.convert.PoSUtil;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeSet;
import javax.xml.parsers.ParserConfigurationException;

import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.sentdetect.SentenceDetectorME;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.tokenize.lang.fa.CharMapper;
import org.xml.sax.SAXException;
import process.objects.Result;
import process.objects.WordResult;
import wrappers.LexiconServiceWrapper;

/**
 *
 * @author Behrang QasemiZadeh <me at atmykitchen.info>
 */
public class UsePoSTagger {

    public static void main(String[] ss) throws FileNotFoundException, IOException, ParserConfigurationException, SAXException, Exception {
        File f = new File("C:\\prj\\Parsie\\corpus\\corpus");
        LexiconServiceWrapper lexiconServiceWrapper
                = new LexiconServiceWrapper();

        FileWriter fw = new FileWriter("shargh-corpus");
        fw.append("<doc>\n");

        TokenizerModel modelt = new TokenizerModel(new File("lang/fa/fa-token.bin"));
        Tokenizer tokenizer = new TokenizerME(modelt);
//            String[] tokenize = tokenizer.tokenize(file);
        // for (File file : f.listFiles()) {
        SentenceModel models = new SentenceModel(new File("lang/fa/fa-sent.bin"));
        SentenceDetectorME sentence = new SentenceDetectorME(models);

        // for (File file : f.listFiles()) {
        CharMapper sm = new CharMapper();
        POSModel model = new POSModel(new File("lang/fa/fa-pos.bin"));
        POSTaggerME pos = new POSTaggerME(model);
        for (File inp : f.listFiles()) {
            String line;
            String file = "";

//        BufferedReader br = new BufferedReader(new FileReader(f));
//        while ((line = br.readLine()) != null) {
//            file += line;
//        }
//        br.close();
            String[] sentences = sentence.sentDetect(sm.mapStrChars(readFile(inp)));

            for (String s : sentences) {
                fw.append("<s>\n");
                String[] tokenizeRaw = tokenizer.tokenize(s);
                String[] posTags = pos.tag(tokenizeRaw);
                for (int i = 0; i < posTags.length; i++) {
                    String posTag = posTags[i];
                    WordResult word = new WordResult();
                    boolean partialWord = lexiconServiceWrapper.getPartialWord(tokenizeRaw[i], word);
                    String lemma = "_";
                    if (partialWord) {
                        TreeSet<Result> resultSet = word.getResultSet();
                        for (Result res : resultSet) {
                            if (res.annotationsArray.size() == 1) {
                                if (null!=PoSUtil.MultextTagtoUniversalTag(res.annotationsArray.get(0).strAna)&&
                                        PoSUtil.MultextTagtoUniversalTag(res.annotationsArray.get(0).strAna).equals(posTag)) {
                                    
                                    lemma = res.annotationsArray.get(0).strLemma;
                                }
                            }
                        }
                        //lemma = word.getLemma(0, 0);
                    }
                    fw.append(tokenizeRaw[i] + "\t" + posTag + "\t" + lemma + "\n");
                }
                fw.append("</s>\n");
            }
        }
        fw.append("</doc>\n");
//        for(String s: sentences){
//            System.out.println("s" + s);
//        }

    }

    public static String readFile(File f) throws FileNotFoundException, IOException {
        // File f = new File("C:\\prj\\Parsie\\corpus\\1-83\\shargh 18-1-83 .txt.txt");
        String line;
        String file = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f), "UNICODE"));
        while ((line = br.readLine()) != null) {
            file += line;
        }
        br.close();
        return file;
    }
}
