/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ie.pars.opennlp.persian.pos.convert;

/**
 *
 * @author Behrang QasemiZadeh <me at atmykitchen.info>
 */
public class PoSUtil {

    public static String MultextTagtoUniversalTag(String tagMultext) throws Exception {
        if (tagMultext.startsWith("A")) {
            return "ADJ";
        }
        // remember order of this rules is important!!!
        if (tagMultext.startsWith("St")) {
            // return "PART";
            return "ADP";
        }
        if (tagMultext.startsWith("S")) {
            return "ADP";
        }
        if (tagMultext.startsWith("R")) {
            return "ADV";
        }
        if (tagMultext.startsWith("Va") || tagMultext.startsWith("Vo") || tagMultext.startsWith("Vl")) {
            //return "AUX";
            return "VERB";
        }
        if (tagMultext.startsWith("Cc")) {
            return "CONJ";
        }
        if (tagMultext.startsWith("D")) {
            return "DET";
        }
        if (tagMultext.startsWith("I")) {
            return "INTJ";
        }
        if (tagMultext.startsWith("Np") || tagMultext.startsWith("Y")) {
            // later to add proper names
            return "PROPN";
        }
        if (tagMultext.startsWith("Nc") || tagMultext.startsWith("Y")) {
            // later to add proper names
            return "NOUN";
        }
        if (tagMultext.startsWith("M")) {
            return "NUM";
        }

        if (tagMultext.startsWith("P")) {
            return "PRON";
        }
        if (tagMultext.startsWith("c")) {
            return "PUNCT";
        }
        if (tagMultext.startsWith("Cs")) {
            return "SCONJ";
        }
        if (tagMultext.startsWith("Vm") || tagMultext.startsWith("Vc")) {
            return "VERB";
        }
        if (tagMultext.startsWith("V*")) {
            return "VERB";
        }
        if (tagMultext.startsWith("X")) {
            return "X";
        }

        if (tagMultext.startsWith("!")) {
            return MultextTagtoUniversalTag(tagMultext.substring(1));
        }
        // System.err.println("The input " + tagMultext+ " not defined");
        return null;

    }
  
    public static String getGenTag(String tagMultext) {
        if(tagMultext.contains("PUNCT")){
            return tagMultext;
        }
        if (tagMultext.contains("g")) {
            return "g";
        } else {
            return "na";
        }
    }

    public static String reduceMULTEXTTag(String tagMultext) {
        if (tagMultext.startsWith("V")) {
            return tagMultext.substring(0, 1);
        } else if (tagMultext.startsWith("P") && !tagMultext.startsWith("PUNCT")) {
            return tagMultext.substring(0, 1);
        }
//        else if (tagMultext.startsWith("M")) {
//            return tagMultext.substring(0, 2);
//        } 
        else if (tagMultext.startsWith("!")) {
            return reduceMULTEXTTag(tagMultext.substring(1));
        } else if (tagMultext.startsWith("Np")) {
            if (tagMultext.endsWith("g")) {
                return "Npg";
            } else {
                return "Np";
            }
        }else if (tagMultext.startsWith("Nc")) {
            if (tagMultext.endsWith("g")) {
                return "Ncg";
            } else {
                return "Nc";
            }
        }
        else if (tagMultext.startsWith("A")) {
            if (tagMultext.endsWith("g")) {
                return "Ag";
            } else {
                return "A";
            }
        } else if (tagMultext.startsWith("PUNCT")) {
            return "PUNCT";
        }else if (tagMultext.startsWith("S")) {
            return tagMultext;
        }
        
        else {
            return tagMultext.substring(0, 1);
        }
    }

}
