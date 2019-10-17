package geecon.lambda.function;

import io.micronaut.context.annotation.Factory;
import io.micronaut.validation.validator.constraints.ConstraintValidator;

import javax.inject.Singleton;
import java.util.regex.Pattern;

@Factory
public class ValidatorFactory {

    private static final Pattern SOME_PATTERN = Pattern.compile("(^|\\S)some.*", Pattern.CASE_INSENSITIVE);

    @Singleton
    public ConstraintValidator<Specific, CharSequence> specificValidator() {
        return (value, annotationMetadata, context) -> {
            if (value == null) {
                return true;
            }
            return !SOME_PATTERN.matcher(value).matches();
        };
    }
}
