package pl.sydygaliev.java_journey.model.AnnotationAndInterface;



/**
 * This interface is used to create new
 * filtered string that will be valuable
 * for ciphering
 *
 * @author Ulan Sydygaliev
 * @version f2
 */
@FunctionalInterface
@UsefulAnnotation(usefulness = 1)
public interface MysteriousCombiner {

    /**
     * Created to combine characters
     *
     * @param message message to build
     * @param character character to be combined
     */
    void combine(StringBuilder message, Character character);
    
}
