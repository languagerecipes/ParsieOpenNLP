/**
 * File: CharMap.java
 *
 * Description: Class CharMap
 *
 */
package util;

/**
 * To do: change the charSysType to enumeration 
 * Description: The basic class storing data.
 * 
 * @author Behrang 
 *
 */
public class CharMap {
    
    private String charStandard;
    private String charSysType;

    public CharMap(String wcStandardForm, String wcType) {
        this.charStandard = wcStandardForm;
        this.charSysType = wcType;
    }

    public String getCharStandard() {
        return charStandard;
    }

    public String getCharSysType() {
        return charSysType;
    }

    public void setStandardType(String wcStandardForm) {
        this.charStandard = wcStandardForm;
    }

    public void setType(String wcType) {
        this.charSysType = wcType;
    }

}
