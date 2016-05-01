/**
 * File: CharMapper.java
 */
package opennlp.tools.tokenize.lang.fa;

import java.io.*;
import java.util.TreeMap;

import javax.xml.parsers.*;
import static javax.xml.parsers.DocumentBuilderFactory.newInstance;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Char Mapper
 *
 * @author Administrator
 *
 */
public class CharMapper extends TreeMap<String, CharMap> {

    private static final String UNKNOWN_TOKEN_TYPE ="unknown";
   // Document document;
    /**
     * Description: Default constructor.
     */
    public CharMapper() throws FileNotFoundException, ParserConfigurationException, SAXException, IOException {

       

        InputSource is = new InputSource(new FileInputStream("lang/fa/chars-map.xml"));
 
        DocumentBuilderFactory factory = newInstance();
        factory.setExpandEntityReferences(false);
        factory.setValidating(false);
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(is);
        Element rootNode = document.getDocumentElement();
        NodeList childNodes = rootNode.getElementsByTagName("c");
        Node childNode;
        Node node;
        NamedNodeMap childAttrs;
        String std;
        String src;
        String type;

        int lNodeCount = childNodes.getLength();
        for (int lNodeIndex = 0; lNodeIndex < lNodeCount; lNodeIndex++) {
            //Get child node lNodeIndex.
            childNode = childNodes.item(lNodeIndex);
            //Get this node's attributes.
            childAttrs = childNode.getAttributes();

            //Get this node's value and store in Src.
            src = childNode.getTextContent();

            //Get this node's attribute 0.
            node = childAttrs.item(0);
            //Get this attribute's value and store in Std.
            std = node.getNodeValue();

            //Get this node's attribute 1.
            node = childAttrs.item(1);
            //Get this attribute's value and store in Type.
            type = node.getNodeValue();
            //System.out.println(Src+ " " + Std);

//            assert (src.length() == 1);
//            assert (std.length() == 1);
//            assert (type.length() == 1);
            //Set CharMap.
            this.put(src, new CharMap(std, type.trim()));
        }

    }

    /**
     * Description: Return CharMap for getting Type and Standard char.
     *
     * @param wcChar
     * @return CharMap
     */
    public CharMap getCharMap(String wcChar) {
        if (this.containsKey(wcChar)) {
            return this.get(wcChar);
        } else {
            return new CharMap(wcChar, UNKNOWN_TOKEN_TYPE);
        }

    }

    
    public String mapStrChars(String line) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < line.length(); i++) {
            //   System.out.println(line);
            CharMap charMap = this.getCharMap(line.charAt(i) + "");
            if (charMap != null) {
                sb.append(charMap.getCharStandard());
            } else {
                sb.append(line.charAt(i));

            }

            //  pw.append(charMap.wcStandardForm);
        }
        return sb.toString();
    }
    /**
     * Description: Clean all array.
     */
//    public void terminate() {
//        charMapperData.cleanUp();
//    }
}
