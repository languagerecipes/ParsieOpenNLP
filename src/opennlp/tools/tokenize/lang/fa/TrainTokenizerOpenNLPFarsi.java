/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opennlp.tools.tokenize.lang.fa;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import opennlp.tools.ml.EventTrainer;
import opennlp.tools.tokenize.TokenSample;
import opennlp.tools.tokenize.TokenizerFactory;
import opennlp.tools.tokenize.TokenizerME;
import opennlp.tools.tokenize.TokenizerModel;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.TrainingParameters;

/**
 *
 * @author Behrang QasemiZadeh <me at atmykitchen.info>
 */
public class TrainTokenizerOpenNLPFarsi {

    public static void main(String[] ss) throws IOException, Exception {
        ObjectStream<TokenSample> tokenSampleStreamFarsi = new TokenSampleStreamFarsi(new File("lang/fa/token/"
                + "train-set-tokenizer-fa.txt"));
        // + "token-sample-oana-fa.txt")); //to do later
//        Dictionary d = new Dictionary();
//        StringList sl = new StringList("ه.ش.", "ه.ق.");
//        d.put(sl);
        TokenizerFactory tf = new TokenizerFactory("fa", null, true, null);
   //     TokenizerModel model = TokenizerME.train(tokenSampleStreamFarsi, tf,
         //       parameters());

        OutputStream modelOut = null;

        modelOut = new BufferedOutputStream(new FileOutputStream("lang/fa/fa-token.bin"));
       // model.serialize(modelOut);
        modelOut.close();

    }

    public static final TrainingParameters parameters() {
        TrainingParameters mlParams = new TrainingParameters();
        mlParams.put(TrainingParameters.ALGORITHM_PARAM, "MAXENT");
        mlParams.put(TrainingParameters.TRAINER_TYPE_PARAM, EventTrainer.EVENT_VALUE);
        mlParams.put(TrainingParameters.ITERATIONS_PARAM, Integer.toString(100));
        mlParams.put(TrainingParameters.CUTOFF_PARAM, Integer.toString(3));

        return mlParams;
    }
    // }

}
