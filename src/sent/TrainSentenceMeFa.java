package sent;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import opennlp.tools.cmdline.SystemInputStreamFactory;
import opennlp.tools.ml.EventTrainer;
import opennlp.tools.sentdetect.SentenceDetectorFactory;
import opennlp.tools.sentdetect.SentenceModel;
import opennlp.tools.sentdetect.SentenceSample;
import opennlp.tools.sentdetect.SentenceSampleStream;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

/**
 *
 * @author Behrang QasemiZadeh <me at atmykitchen.info>
 */
public class TrainSentenceMeFa {
    
    public static void main(String[] ss) throws UnsupportedEncodingException, FileNotFoundException, IOException {
       // InputStreamFactory ios = new MarkableFileInputStreamFactory(new File("../Parsie/final-data/sent/sent-all.txt"));
        
        ObjectStream<String> lineStream = new PlainTextByLineStream(new FileInputStream("../Parsie/final-data/sent/sent-all.txt"), "UTF-8");
        ObjectStream<SentenceSample> sampleStream = new SentenceSampleStream(lineStream);
       
        char[] eosChars = {'!', '?', '؟', '.','\n','\r'};
        TrainingParameters trainingParameters =  parameters();
       
        opennlp.tools.sentdetect.SentenceDetectorFactory sfdsfd = new SentenceDetectorFactory("fa", false, null, eosChars);
        SentenceModel model = opennlp.tools.sentdetect.SentenceDetectorME.train("fa", sampleStream, sfdsfd,  trainingParameters);

        OutputStream modelOut = null;
        try {
            modelOut = new BufferedOutputStream(new FileOutputStream(new File("../parsie/final-data/model/sent-farsi-model2.bin")));
            model.serialize(modelOut);
        } finally {
            if (modelOut != null) {
                modelOut.close();
            }
        }
    }
    
    public static final TrainingParameters parameters() {
        TrainingParameters mlParams = new TrainingParameters();
        mlParams.put(TrainingParameters.ALGORITHM_PARAM, "MAXENT");
        mlParams.put(TrainingParameters.TRAINER_TYPE_PARAM, EventTrainer.EVENT_VALUE);
        mlParams.put(TrainingParameters.ITERATIONS_PARAM, Integer.toString(178));
        mlParams.put(TrainingParameters.CUTOFF_PARAM, Integer.toString(1));

        return mlParams;
    }
}
