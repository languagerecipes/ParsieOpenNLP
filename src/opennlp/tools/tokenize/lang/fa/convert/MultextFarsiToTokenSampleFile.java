/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opennlp.tools.tokenize.lang.fa.convert;

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

/**
 *
 * @author Behrang QasemiZadeh
 */
public class MultextFarsiToTokenSampleFile {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {

        String filepathFarsi = "final-data/oana-fa.xml";
        String output = "final-data/token/token-sample-oana-fa.txt";
        List<Node> nListOfChapters = loadSentences(filepathFarsi);
        //let's go forward chapter by chapter
        String spaceCharDel =" ";
        PrintWriter pw = new PrintWriter(new FileWriter(new File(output)));
        for (int chapter = 0; chapter < nListOfChapters.size(); chapter++) {
            cleanNode(nListOfChapters.get(chapter));
            for (int i = 0; i < nListOfChapters.get(chapter).getChildNodes().getLength(); i++) {
                Node faPara = nListOfChapters.get(chapter).getChildNodes().item(i);
                cleanNode(faPara);
                int faNumSent = faPara.getChildNodes().getLength();

                for (int j = 0; j < faNumSent; j++) {

                    if (faPara.getChildNodes().item(j).getNodeName().equals("s")) {

                        NodeList wordList = faPara.getChildNodes().item(j).getChildNodes();
                        String sentence = "";
                        String tokens = "";
                        int offset = 0;
                        for (int k = 0; k < wordList.getLength(); k++) {
                            if (wordList.item(k).getNodeName().equals("w")) { // here add the rules for clitics
                                String ana = wordList.item(k).getAttributes().getNamedItem("ana").getNodeValue().replace("#", "");
                                String word = wordList.item(k).getTextContent().replaceAll(" ", "‌");
                                if (!isClitic(ana)) {
                                    offset = sentence.length();
                                    sentence
                                            += word + spaceCharDel;
                                    int end = (offset + word.length()); //-1);
                                    tokens += offset + "<t>" + end + "<t>" + word + "\t";
                                } else {
                                    sentence =sentence.trim();
                                    offset = sentence.length();
                                    sentence += wordList.item(k).getTextContent() + spaceCharDel;
                                    int end = (offset + word.length()); //-1);
                                    tokens += offset + "<t>" + end + "<t>" + word + "\t";

                                }
                            } else if (wordList.item(k).getNodeName().equals("c")) {
                                if ("«".equals(wordList.item(k).getTextContent())) {
                                    /// no trim and no space after
                                    offset = sentence.length();
                                    sentence += wordList.item(k).getTextContent();
                                    int end = (offset + wordList.item(k).getTextContent().length()); //-1);
                                    tokens += offset + "<t>" + end + "<t>" + wordList.item(k).getTextContent() + "\t";

                                } else
                                if ("»".equals(wordList.item(k).getTextContent())) {
                                    sentence = sentence.trim();
                                    offset = sentence.length();
                                    sentence += wordList.item(k).getTextContent()+spaceCharDel;
                                    int end = (offset + wordList.item(k).getTextContent().length()); //-1);
                                    tokens += offset + "<t>" + end + "<t>" + wordList.item(k).getTextContent() + "\t";

                                } else {
                                    sentence = sentence.trim();
                                    offset = sentence.length();
                                    sentence += wordList.item(k).getTextContent() + spaceCharDel;
                                    int end = (offset + wordList.item(k).getTextContent().length()); //-1);
                                    tokens += offset + "<t>" + end + "<t>" + wordList.item(k).getTextContent() + "\t";
                                }
                            }
                        }

                        pw.println(sentence.trim() + "<sample>" + tokens);
                    }

                }

            }
        }

        pw.close();

    }

    /**
     * Properly address clitics in the tokenisation task!
     *
     * @param ana
     * @return
     */
    private static boolean isClitic(String ana) {
        if (ana.startsWith("V") && ana.length() >= 11) {
            if (ana.substring(10, 11).equals("y")) {
                //System.out.println("YEST " + ana);
                return true;

            } else {
                return false;
            }
        } else if (ana.startsWith("P") && ana.length() >= 9) {
            if (ana.substring(8, 9).equals("y")) {
                return true;

            } else {
                return false;
            }
        } else {
            return false;
        }
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

    /**
     * Get the nodelist of certain type
     *
     * @param node
     */
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
