package geecon.lambda.function;

import io.micronaut.core.beans.BeanIntrospection;
import io.micronaut.core.beans.BeanIntrospector;
import io.micronaut.core.beans.BeanProperty;
import io.micronaut.data.annotation.Id;
import org.junit.jupiter.api.Test;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class IntrospectionTest {

    private final BeanIntrospector introspector = BeanIntrospector.SHARED;

    @Test
    public void testTaskIntrospection() throws Throwable {
        BeanIntrospection<Task> introspection = introspector.findIntrospection(Task.class).orElseThrow(() -> {
            throw new IllegalStateException("Task introspection is missing");
        });

        Collection<BeanProperty<Task, Object>> ids = introspection.getIndexedProperties(Id.class);

        assertEquals(1, ids.size());

        BeanProperty<Task, Object> id = ids.iterator().next();

        Task task = introspection.instantiate();

        assertNull(task.getId());

        id.set(task, 12345L);

        assertEquals(12345L, task.getId());
    }
}
