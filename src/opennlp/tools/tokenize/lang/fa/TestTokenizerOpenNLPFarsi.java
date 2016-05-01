/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opennlp.tools.tokenize.lang.fa;

import java.io.File;
import java.io.IOException;
import opennlp.tools.tokenize.Tokenizer;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import org.xml.sax.SAXException;

/**
 *
 * @author Behrang QasemiZadeh <me at atmykitchen.info>
 */
public class TestTokenizerOpenNLPFarsi {

    public static void main(String[] ss) throws IOException, SAXException, Exception {


        String ssss = "من به کتابهایم ریسدم و کفتم دوستانش  را بدهید.";
        CharMapper cm = new CharMapper();
        
        TokenizerModel model = new TokenizerModel(new File("lang/fa/fa-token.bin"));
        Tokenizer tokenizer = new TokenizerME(model);
        String[] tokenize = tokenizer.tokenize(cm.mapStrChars(ssss));
        for (String s : tokenize) {
            System.out.println(s);
        }
       // }
//        MapChars mc = new MapChars("final-data/char-map-data.xml");
//        String readFile = IOUtils.readFile("corpus\\6-83\\shargh 1-6-83 .txt.txt");
//        String topass= "";
//        for (int i = 0; i < readFile.length(); i++) {
//           topass += mc.getCharMap(readFile.charAt(i)).getCharStandard();
//            
//        }
//        String[] tokenize1 = tokenizer.tokenize(topass);
//        PrintWriter pw = new PrintWriter(new OutputStreamWriter(new FileOutputStream("shargh-token-opennlp.txt"), "UTF-8"));
//        for (String wr : tokenize1) {
//          pw.println( wr);
//         //System.out.println(   wr.getStandardText()  );
//        }
//        pw.close();

    }
}
