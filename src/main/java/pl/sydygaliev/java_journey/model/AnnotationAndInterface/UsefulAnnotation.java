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
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface UsefulAnnotation {
    /**
     * Indicates usefullness of the interface
     * 0 if not useful at all, 1 if quite useful
     * @return mode, 0 if useless, 1 if useful
     */
    int usefulness() default 0;
}
