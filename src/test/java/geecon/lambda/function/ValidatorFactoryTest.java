package geecon.lambda.function;

import io.micronaut.test.annotation.MicronautTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest
public class ValidatorFactoryTest {

    @Inject
    Validator validator;

    @Test
    @DisplayName("Test @NotNull constraint")
    public void testNotNullConstraints() {
        Task tester = new Task();

        Set<ConstraintViolation<Task>> violations = validator.validate(tester);

        assertEquals(1, violations.size());
    }

    @Test
    @DisplayName("Test @Specific constraint")
    public void testSpecificConstraints() {
        Task tester = new Task();
        tester.setSummary("Someone needs to do this");

        Set<ConstraintViolation<Task>> violations = validator.validate(tester);

        assertEquals(1, violations.size());
    }
}