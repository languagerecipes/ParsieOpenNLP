/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.pars.opennlp.persian.pos.convert;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import opennlp.tools.postag.POSDictionary;
import opennlp.tools.postag.POSDictionaryWriter;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Behrang QasemiZadeh <me at atmykitchen.info>
 */
public class MakeDictionaryPosTagger {

    public static void main(String[] s) throws ParserConfigurationException, SAXException, IOException {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document parse = builder.parse("data/Lexicon.xml2.txt");
        NodeList elementsByTagName = parse.getElementsByTagName("lexeme");
        System.out.println(elementsByTagName.getLength());
        PrintWriter pw = new PrintWriter(new FileWriter("lexicon-large-2"));

        Set<String> set = new TreeSet();
        Set<String> tagset = new TreeSet<>();
        //POSDictionaryWriter posw = new POSDictionaryWriter("pos-dictionary", "UTF-8");

        POSDictionary posd = new POSDictionary();

        TreeMap<String, Set<String>> dicLex = new TreeMap();
        for (int i = 0; i < elementsByTagName.getLength(); i++) {
            // System.out.println(elementsByTagName.item(i).getTextContent());
            String pronounciation = elementsByTagName.item(i).getAttributes().getNamedItem("pronunciation").getTextContent();
            String ana = elementsByTagName.item(i).getAttributes().getNamedItem("ana").getTextContent();
            String tag = ana.substring(0, Math.min( 2, ana.length()));
            tagset.add(tag);
            String lemma = elementsByTagName.item(i).getAttributes().getNamedItem("lemma").getTextContent();
            set.add(lemma);
            String word = elementsByTagName.item(i).getTextContent();
            if (dicLex.containsKey(word)) {
                dicLex.get(word).add(tag);
            } else {
                Set<String> tagS = new TreeSet<>();
                tagS.add(tag );
                dicLex.put(word, tagS);
            }
          //  posw.addEntry(elementsByTagName.item(i).getTextContent(), ana.charAt(0)+"");

            pw.println(elementsByTagName.item(i).getTextContent() + "\t" + lemma + "\t" + ana + "\t" + pronounciation);
        }
        posd.put("صادق", "Np");
        for (String word : dicLex.keySet()) {
            String[] taggg = new String[dicLex.get(word).size()];
            dicLex.get(word).toArray(taggg);
            String[] put = posd.put(word, taggg);
            if (put != null) {
                System.out.println(word + dicLex.get(word));
            }
        }

        pw.close();
        System.out.println("lemma size " + set.size());
        // posw.write(1);
        posd.serialize(new FileOutputStream(new File("newdic")));

    }
}
