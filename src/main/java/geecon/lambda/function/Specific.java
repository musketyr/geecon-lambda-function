package geecon.lambda.function;

import javax.validation.Constraint;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Disallows vague words such as someone or something.
 */
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = { })
public @interface Specific {

    String message() default "please do not use vague words like 'someone' ({validatedValue})";

}
