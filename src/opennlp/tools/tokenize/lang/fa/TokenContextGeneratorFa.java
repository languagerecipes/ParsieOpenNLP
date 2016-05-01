/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package opennlp.tools.tokenize.lang.fa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import opennlp.tools.tokenize.TokenContextGenerator;
import opennlp.tools.util.StringUtil;

/**
 *
 * @author Behrang QasemiZadeh <me at atmykitchen.info>
 */
public class TokenContextGeneratorFa implements TokenContextGenerator {

//    basically the deafult features plus some language dependant
//            especially add the location of the token in the list and char before the first char etc
//                    
  
    protected final Set<String> inducedAbbreviations;

    public TokenContextGeneratorFa() {
        this(Collections.<String>emptySet());          
                
    }

  /**
   * Creates a default context generator for tokenizer.
  /**
   * Creates a default context generator for tokenizer.
   */
  

  /**
   * Creates a default context generator for tokenizer.
   *
   * @param inducedAbbreviations the induced abbreviations
   */
  public TokenContextGeneratorFa(Set<String> inducedAbbreviations) {
    this.inducedAbbreviations = inducedAbbreviations;
  }

  /* (non-Javadoc)
   * @see opennlp.tools.tokenize.TokenContextGenerator#getContext(java.lang.String, int)
   */
  public String[] getContext(String sentence, int index) {
    List<String> preds = createContext(sentence, index);
    String[] context = new String[preds.size()];
    preds.toArray(context);
    return context;
  }

  /**
   * Returns an {@link ArrayList} of features for the specified sentence string
   * at the specified index. Extensions of this class can override this method
   * to create a customized {@link TokenContextGenerator}
   *
   * @param sentence
   *          the token been analyzed
   * @param index
   *          the index of the character been analyzed
   * @return an {@link ArrayList} of features for the specified sentence string
   *         at the specified index.
   */
  protected List<String> createContext(String sentence, int index) {
    
    List<String> preds = new ArrayList<String>();
    String prefix = sentence.substring(0, index);
    String suffix = sentence.substring(index);
    addCharPreds("f1", sentence.charAt(index), preds);
         if(index==0){
             preds.add("p="+"bos");
         }
    preds.add("p=" + prefix);
      preds.add("s=" + suffix);
      if (index > 0) {
          addCharPreds("p1", sentence.charAt(index - 1), preds);
          preds.add("p1f1=" + sentence.charAt(index - 1) + sentence.charAt(index));
          if (index > 1) {
              addCharPreds("p2", sentence.charAt(index - 2), preds);
              preds.add("p21=" + sentence.charAt(index - 2) + sentence.charAt(index - 1));
          } else {
              preds.add("p2=bok");
          }
          if (index > 2) {
              addCharPreds("p3", sentence.charAt(index - 3), preds);
              preds.add("p321=" + sentence.charAt(index - 3) + sentence.charAt(index - 2) + sentence.charAt(index - 1));
          } else {
              preds.add("p3=bok");
          }
      } else {
          preds.add("p1=bok");
      }
   
    if (index+1 < sentence.length()) {
          addCharPreds("f2", sentence.charAt(index + 1), preds);
          preds.add("f12=" + sentence.charAt(index) + sentence.charAt(index + 1));
      } else {
          preds.add("f2=bok");
      }
//      if (sentence.charAt(0) == '&' && sentence.charAt(sentence.length() - 1) == ';') {
//          preds.add("cc");//character code
//      }
//      if (sentence.charAt(0) == '«' && sentence.charAt(sentence.length() - 1) == ';') {
//          preds.add("cc2");//character code
//      }
//      if (sentence.charAt(0) == '»' && sentence.charAt(sentence.length() - 1) == ';') {
//          preds.add("cc2");//character code
//      }
      if (index == sentence.length() - 1 && inducedAbbreviations.contains(sentence)) {
          preds.add("pabb");
      }

    return preds;
  }


  /**
   * Helper function for getContext.
     * @param key
     * @param preds
   */
  protected void addCharPreds(String key, char c, List<String> preds) {
     // System.out.println(c);
    preds.add(key + "=" + c);
    if (Character.isLetter(c)) {
      preds.add(key + "_alpha");
    }
    else if (Character.isDigit(c)) {
      preds.add(key + "_num");
    }
    else if (StringUtil.isWhitespace(c)) {
      preds.add(key + "_ws");
    }
    else {
        if (c == '.' || c == '?' || c == '!') {
            preds.add(key + "_eos");
        } else if (c == '`' || c == '"' || c == '\''|| c == '«'|| c == '»') {
            preds.add(key + "_quote");
        } else if (c == '[' || c == '{' || c == '(' || c == '«'|| c == '»') {
            preds.add(key + "_lp");
        } else if (c == ']' || c == '}' || c == ')' || c == '»') {
            preds.add(key + "_rp");
        }
    }
  }
}


