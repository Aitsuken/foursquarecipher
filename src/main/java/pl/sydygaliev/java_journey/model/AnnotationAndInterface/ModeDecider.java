package pl.sydygaliev.java_journey.model.AnnotationAndInterface;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Interface that will be used for
 * annotational experimental purposes
 * @author Ulan Sydygaliev
 * @version f2
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModeDecider {
    /**
     * Useful to keep mode of the cipher
     * @return mode, 0 if manual, 1 if automatic
     */
    String mode() default "1";
}
