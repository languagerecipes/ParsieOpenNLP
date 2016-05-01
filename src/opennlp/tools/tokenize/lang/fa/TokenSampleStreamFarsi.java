/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opennlp.tools.tokenize.lang.fa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import opennlp.tools.tokenize.TokenSample;
import opennlp.tools.util.FilterObjectStream;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.Span;

/**
 *
 * @author Behrang QasemiZadeh <me at atmykitchen.info>
 */
public class TokenSampleStreamFarsi implements ObjectStream<TokenSample> {

    private BufferedReader in;
    private String line;
   

    public TokenSampleStreamFarsi(File sampleFile) {
        
        try {
            try {
                in = new BufferedReader(new InputStreamReader(new FileInputStream(sampleFile), "UTF-8"));
            } catch (FileNotFoundException ex) {
                Logger.getLogger(TokenSampleStreamFarsi.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(TokenSampleStreamFarsi.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            line = in.readLine();
        } catch (IOException ex) {
            Logger.getLogger(TokenSampleStreamFarsi.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean hasNext() {
        return line != null;
    }

    /**
     * Get the next instance
     * @return 
     */
    public TokenSample next() {
        String[] split = line.split("<sample>");
        int off = 0;
        Span[] spans = new Span[split[1].trim().split("\t").length];
        int count = 0;

        for (String w : split[1].trim().split("\t")) {

            String[] split1 = w.split("<t>");
            spans[count++] = new Span(Integer.parseInt(split1[0]), Integer.parseInt(split1[1]));

        }
        try {
            line = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            line = null;
        }

        return new TokenSample(split[0], spans);
    }

    @Override
    public TokenSample read() throws IOException {
        if (hasNext()) {
            return next();
        }
        return null;
    }

    @Override
    public void reset() throws IOException, UnsupportedOperationException {
        this.in.reset();
    }

    @Override
    public void close() throws IOException {
        this.in.close(); 
    }

}
