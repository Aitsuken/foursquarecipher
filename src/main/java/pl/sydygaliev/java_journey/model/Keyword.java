package pl.sydygaliev.java_journey.model;

/**
 * Type to store keyword
 * @author Ulan Sydygaliev
 * @version f2
 */
public class Keyword {
    /**
     * stores keyword
     */
    private String keyword;
    
    /**
     * Basic constructor to set-up keyword
     * @param keyword to be stored and operatred upon
     */
    public Keyword(String keyword){
        this.keyword = keyword;
    }

    /**
     * basic accessor for keyword
     * @return stored keyword
     */
    public String getKeyword() {
        return keyword;
    }

    /**
     * basic mutator for keyword
     * @param keyword to be stored
     */
    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
    /**
     * toString method for Keyword
     * @return String representation of the Keyword
     */
    @Override
    public String toString(){
        return this.keyword;
    }
    
}
