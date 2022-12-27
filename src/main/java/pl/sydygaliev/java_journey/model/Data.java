package pl.sydygaliev.java_journey.model;

/**
 * Generic type for processing input information
 * @author Ulan Sydygaliev
 * @param <T> generic type 
 * @version f2
 */
public class Data<T> {
    /**
     * information to be operated on
     */
    private T information;
    
    /**
     * basic set-up constructor
     * @param information assigns given information
     */
    public Data(T information){
        this.information = information;
    }

    /**
     * basic accessor for information
     * @return stored information 
     */
    public T getInformation() {
        return information;
    }

    /**
     * basic mutator for information
     * @param information information to be stored
     */
    public void setInformation(T information) {
        this.information = information;
    }
    
    /**
     * Method to get String from the type
     * @return Stringified type 
     */
    @Override
    public String toString(){
        return information.toString();
    }
}
