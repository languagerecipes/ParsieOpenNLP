/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genetiveMarker;

import ie.pars.opennlp.persian.pos.convert.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import util.CharMapper;

/**
 *
 * @author Behrang QasemiZadeh
 */
public class MULTEXTtoGenset {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, Exception {
        boolean ff = true;
        String filepathFarsi = "lang/fa/corpus/oana-fa.xml";
        List<Node> nListOfChapters = loadSentences(filepathFarsi);
        PrintWriter pw = new PrintWriter(
                new FileWriter(new File("lang/fa/pos"
                                + "/oana-fa-spl-gen.std")));
        CharMapper cm = new CharMapper();
        //       PrintWriter pwTest = new PrintWriter(new FileWriter(new File("final-data/pos/oana-fa-sent-tagged-per-line.testset.split")));
        int sentCount = 0;
        for (int chapter = 0; chapter < nListOfChapters.size(); chapter++) {

            cleanNode(nListOfChapters.get(chapter));
            for (int i = 0; i < nListOfChapters.get(chapter).getChildNodes().getLength(); i++) {
                // add the if here that if it is text and empty then reove?

                Node faPara = nListOfChapters.get(chapter).getChildNodes().item(i);
                cleanNode(faPara);

                int faNumSent = faPara.getChildNodes().getLength();

                for (int j = 0; j < faNumSent; j++) {

                    if (faPara.getChildNodes().item(j).getNodeName().equals("s")) {

                        NodeList wordList = faPara.getChildNodes().item(j).getChildNodes();
                        String sentenceStr = "";
                        for (int k = 0; k < wordList.getLength(); k++) {
                            if (wordList.item(k).getNodeName().equals("w")) { // here add the rules for clitics
                                String ana = wordList.item(k).getAttributes().getNamedItem("ana").getNodeValue().replace("#", "");
                                String word =cm.mapStrChars(wordList.item(k).getTextContent());

                                /// make 2 version of the corpus
                                //  String[] split = word.replaceAll("‌", " ").split(" ");
                                String[] split = word.replaceAll(" ", "\u200c").split(" ");
                                String tag = PoSUtil.getGenTag(ana);
                                sentenceStr += (" " + split[0] + "_" + tag);
                                for (int l = 1; l < split.length; l++) {
                                    sentenceStr += (" " + split[l] + "_" + tag); //I had an extra i marker

                                }

                            } else if (wordList.item(k).getNodeName().equals("c")) {
                                sentenceStr += (" " + wordList.item(k).getTextContent() + "_PUNCT");
//                                pw.println(wordList.item(k).getTextContent() + "\tc\t"
//                                        + wordList.item(k).getTextContent()+"\tc");

                            }
                        }

                        if (ff) {
                            System.out.println(sentenceStr);
                            ff = false;
                        }
                        //if (sentCount++ < 6600) {
                        pw.print(sentenceStr.trim() + "\r\n");
//                        } else {
//                            pwTest.print(sentenceStr.trim() + "\r\n");
//                        }

                    }
                }

            }
        }

        pw.close();
//        pwTest.close();

    }

    private static List<Node> loadSentences(String path) throws ParserConfigurationException, SAXException, IOException {

        File oana = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(oana);
        doc.getDocumentElement().normalize();

        NodeList nList = doc.getElementsByTagName("div");

        List<Node> ret = new ArrayList<>();
        for (int i = 0; i < nList.getLength(); i++) {

            if (nList.item(i).getAttributes().getNamedItem("type").getTextContent().equals("chapter")
                    && nList.item(i).getChildNodes().getLength() != 0) {
                ret.add(nList.item(i));
            }
        }

        return ret;

    }

    public static void cleanNode(Node node) {
        NodeList list = node.getChildNodes();

        for (int i = 0; i < list.getLength(); i++) {
            boolean emptyText = (list.item(i).getNodeType() == 3 && list.item(i).getTextContent().trim().isEmpty());

            if (emptyText) {
                list.item(i).getParentNode().removeChild(list.item(i));
            }

        }

    }

}
