package ie.pars.opennlp.persian.postag;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import opennlp.tools.cmdline.postag.POSEvaluationErrorListener;
import opennlp.tools.cmdline.postag.POSTaggerFineGrainedReportListener;
import opennlp.tools.postag.POSEvaluator;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.WordTagSampleStream;

/**
 *
 * @author Behrang QasemiZadeh <me at atmykitchen.info>
 */
public class EvaluatePosTagger {

    public static void main(String[] ss) throws IOException {
        POSModel model = new POSModel(new File("lang/fa/fa-pos.bin"));
        //  POSTaggerME pos = new POSTaggerME(model);
        OutputStream printWriter = new FileOutputStream(new File("temp-del"));
        POSTaggerFineGrainedReportListener poer = new POSTaggerFineGrainedReportListener(printWriter);
        WordTagSampleStream wstream;
        Reader s = new //StringReader("وینستون_N اسمیت_N ,_T درحالی_K که_C");
                FileReader(new File(
                                "lang/fa/pos/oana-fa-sent-tagged-per-line.split"
                        //  "lang/fa/pos/trainset-red-mlt"
                        // "lang/fa/pos/oana-fa-spl-red-tag.std"
                        ));
        //  FileReader(new File("corpus/upc-sentence-tag"));
        wstream = new WordTagSampleStream(s);

        POSEvaluator evaluator = new POSEvaluator(
                new opennlp.tools.postag.POSTaggerME(model), poer
        //  null
        );

        System.out.print("Evaluating ... ");

        
        evaluator.evaluate(wstream);
        poer.writeReport();
        System.out.println(evaluator.getWordAccuracy());
        System.out.println(evaluator.getWordCount());
        System.out.println(poer.getTagFMeasure("ADJ"));
        printWriter.close();
        for(String t: poer.getConfusionMatrixTagset()){
            System.out.println(t+" "+poer.getTagFMeasure(t) +" " + poer.getTagPrecision(t)+" " + poer.getTagRecall(t)+" " + poer.getTagFrequency(t));
        
        }
        

    }
}
