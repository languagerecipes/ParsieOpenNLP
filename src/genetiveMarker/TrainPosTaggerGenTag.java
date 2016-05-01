/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genetiveMarker;

import ie.pars.opennlp.persian.postag.*;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Reader;
import java.util.TreeMap;
import java.util.TreeSet;
import opennlp.tools.ml.EventTrainer;
import opennlp.tools.postag.POSDictionary;
import opennlp.tools.postag.POSModel;
import opennlp.tools.postag.POSTaggerFactory;
import opennlp.tools.postag.POSTaggerME;
import opennlp.tools.postag.WordTagSampleStream;
import opennlp.tools.util.TrainingParameters;

/**
 *
 * @author Behrang QasemiZadeh <me at atmykitchen.info>
 */
public class TrainPosTaggerGenTag {

    public static void main(String[] ss) throws IOException {

        WordTagSampleStream wstream;
        Reader s = new FileReader(new File(
                "lang/fa/gen/oana-fa-spl-gen.std"
              //  "lang/fa/gen/trainset-gen-marker"
             
        )
        );
        wstream = new WordTagSampleStream(s);

        POSTaggerFactory pot = new POSTaggerFactory();
       // pot.setTagDictionary(loadPosDictionary());

        POSModel model = POSTaggerME.train("fa", wstream, parameters(), pot);

        OutputStream modelOut = null;

        modelOut = new BufferedOutputStream(new FileOutputStream("lang/fa/fa-gen.bin"));
        model.serialize(modelOut);

        modelOut.close();

        System.out.println("Lets eva");
        EvaluatePosTaggerGen.main(ss);
    }

    public static final TrainingParameters parameters() {
        TrainingParameters mlParams = new TrainingParameters();
        mlParams.put(TrainingParameters.ALGORITHM_PARAM, "MAXENT");
        mlParams.put(TrainingParameters.TRAINER_TYPE_PARAM, EventTrainer.EVENT_VALUE);
        mlParams.put(TrainingParameters.ITERATIONS_PARAM, Integer.toString(80));
        mlParams.put(TrainingParameters.CUTOFF_PARAM, Integer.toString(1));

        return mlParams;
    }

    public static final POSDictionary loadPosDictionary() throws FileNotFoundException, IOException {

        POSDictionary posd
                = new POSDictionary();
        String fileInput = "lang/fa/pos/dictionary";
        String line;
        BufferedReader br = new BufferedReader(new FileReader(fileInput));
        TreeMap<String, TreeSet<String>> wordPosMap = new TreeMap();
        while ((line = br.readLine()) != null) {
            String[] wordTag = line.split("\t");
            if (wordPosMap.containsKey(wordTag[0])) {
                wordPosMap.get(wordTag[0]).add(wordTag[1]);
            } else {
                TreeSet<String> s = new TreeSet<>();
                s.add(wordTag[1]);
                wordPosMap.put(wordTag[0], s);
            }
        }
        br.close();
        for (String word : wordPosMap.keySet()) {
            TreeSet<String> get = wordPosMap.get(word);

            String[] t = get.toArray(new String[get.size()]);
            posd.put(word, t);
        }

        return posd;
    }
}
