package pl.sydygaliev.java_journey.model.AnnotationAndInterface;



/**
 * This interface is used for experimental purposes
 *
 * @author Ulan Sydygaliev
 * @version f2
 */
@FunctionalInterface
@UsefulAnnotation(usefulness = 1)
public interface ExperimentalInterface {

    /**
     * Created to combine characters
     *
     * @param message message to build
     * @param character character to be combined
     * @return
     */
    String combine(StringBuilder message, char character);
}
