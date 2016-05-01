package ie.pars.opennlp.server;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.net.Socket;

/**
 * The sister class to LexicalizedParserServer. This class connects to the given
 * host and port. It can then either return a Tree or a string with the output
 * of the Tree, depending on the method called. getParse gets the string output,
 * getTree returns a Tree.
 */
public class STPClient {

    final String host;
    final int port;

    public STPClient(String host, int port)
            throws IOException {
        this.host = host;
        this.port = port;
    }

    /**
     * Returns the String output of the parse of the given query.
     * <br>
     * The "parse" method in the server is mostly useful for clients using a
     * language other than Java who don't want to import or wrap Tree in any
     * way. However, it is useful to provide getParse to test that functionality
     * in the server.
     */
    public String getParse(String query)
            throws IOException {
        Socket socket = new Socket(host, port);

        Writer out = new OutputStreamWriter(socket.getOutputStream(), "utf-8");
        out.write("parse " + query + "\n");
        out.flush();

        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line);
        }

        socket.close();
        return result.toString();
    }

    public static void main(String[] args)
            throws IOException {
        System.setOut(new PrintStream(System.out, true, "utf-8"));
        System.setErr(new PrintStream(System.err, true, "utf-8"));

        STPClient client
                = new STPClient("localhost",
                        6666);

        String query = "من بهرنگ هستم. من خوب هستم.";
        System.out.println(query);
        String parse = client.getParse(query);
        System.err.println(parse);


    }
}
