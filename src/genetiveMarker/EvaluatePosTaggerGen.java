package genetiveMarker;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import ie.pars.opennlp.persian.postag.*;
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
public class EvaluatePosTaggerGen {

    public static void main(String[] ss) throws IOException {
        POSModel model = new POSModel(new File("lang/fa/fa-gen.bin"));
        //  POSTaggerME pos = new POSTaggerME(model);
        OutputStream printWriter = new FileOutputStream(new File("temp-del"));
        POSTaggerFineGrainedReportListener poer = new POSTaggerFineGrainedReportListener(printWriter);
        WordTagSampleStream wstream;
        Reader s = new //StringReader("وینستون_N اسمیت_N ,_T درحالی_K که_C");
                FileReader(new File(
                          "lang/fa/gen/trainset-gen-marker"
                            //    "lang/fa/gen/oana-fa-spl-gen.std"
                        ));
        //  FileReader(new File("corpus/upc-sentence-tag"));
        wstream = new WordTagSampleStream(s);
        //OutputStream printWriter = new FileOutputStream(new File("temp-del"));
        POSEvaluator evaluator = new POSEvaluator(
                new opennlp.tools.postag.POSTaggerME(model), poer
        //  null
        //new POSTaggerFineGrainedReportListener(printWriter)
        //   null
        );

        System.out.print("Evaluating ... ");

        evaluator.evaluate(wstream);

        System.out.println(evaluator.getWordAccuracy());
        System.out.println(evaluator.getWordCount());
        evaluator.toString();
        poer.writeReport();
        printWriter.close();
        System.err.println("fsco" +  poer.getTagFMeasure("g"));

    }
}
