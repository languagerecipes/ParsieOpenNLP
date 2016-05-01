package ie.pars.opennlp.server;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
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

/**
 * A Socket interface to the sentence split, tokeniser and pos tagger for Parsie
 * OpenNLP
 *
 * @author Behrang QasemiZadeh <me at atmykitchen.info>
 */
public class STPServer {

    private final ServerSocket socket;

    private final TokenizerME tokenizer;
    final private CharMapper cm;
    private final SentenceDetectorME sentence;
    private final POSTaggerME posme;

    public STPServer(int port, String model)
            throws IOException, FileNotFoundException, ParserConfigurationException, SAXException {
        
        tokenizer = new TokenizerME(new TokenizerModel(new File("lang/fa/fa-token.bin")));
        SentenceModel models = new SentenceModel(new File("lang/fa/fa-sent.bin"));
        sentence = new SentenceDetectorME(models);
        this.socket = new ServerSocket(port);
        cm = new CharMapper();
        posme = new POSTaggerME(new POSModel(new File("lang/fa/fa-pos.bin")));
    }

    /**
     * Simple old style looping stuff. Change to NOI at some stage
     *
     * @throws IOException
     * @throws InterruptedException
     */
    public void scanPort() throws Exception {
        while (true) {
            Socket sockInput = null;
            try {
                sockInput = socket.accept();
                processInput(sockInput);
            } catch (IOException e) {

                sockInput.close();
                System.err.println(e);
            }
        }

    }

    // TODO: handle multiple requests in one connection?  why not?
    /**
     * Main method to process
     * @param inputSock
     * @throws IOException
     * @throws InterruptedException 
     */
    public void processInput(Socket inputSock)
            throws IOException, InterruptedException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputSock.getInputStream(), "utf-8"));
        String textInput = reader.readLine();

         if (textInput == null) {
            return;
        }
               
        processText(inputSock.getOutputStream(), textInput);
        inputSock.close();
    }

    /**
     * Returns the result of applying the parser to arg as a string.
     */
    public void processText(OutputStream outStream, String text)
            throws IOException, InterruptedException {
        //normalise
        text = cm.mapStrChars(text.trim());
        String[] sentDetect = sentence.sentDetect(text);
        OutputStreamWriter os = new OutputStreamWriter(outStream, "utf-8");

        for (String sent : sentDetect) {
            String[] tok = tokenizer.tokenize(sent);
            String[] pos = posme.tag(tok);
            os.write(toHTML(tok, pos));
        }

        os.write("\n");
        os.flush();

    }

    

    public static void main(String[] args)
            throws IOException, InterruptedException, FileNotFoundException, Exception {
        System.setOut(new PrintStream(System.out, true, "utf-8"));
        System.setErr(new PrintStream(System.err, true, "utf-8"));

        int port = 6666;

        STPServer server = new STPServer(port, "");
        System.err.println("Server ready!");
        server.scanPort();
    }

    private String toHTML(String[] tok, String[] pos) {
        String out ="<p dir=\"rtl\">";
        for (int i = 0; i < pos.length; i++) {
          if(tok[i].equals("PUNCT")){
              out+=" "+tok[i]+"<span style=\"color:blue\"><sup dir=\"ltr\">"+pos[i]+"<sup></span>"+" ";
          }else{
           out+=" <span style=\"font:blue\" dir='rtl'><bold>"+tok[i]+"</bold></span>"
                   + "<span style=\"color:blue\"><sup dir=\"ltr\"><small>"+pos[i]+"</small><sup></span>"+" ";
          }
        }
        out.trim();
        return out+="</p>\n";
    }

}
