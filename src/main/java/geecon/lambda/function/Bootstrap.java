package geecon.lambda.function;

import io.micronaut.context.event.ApplicationEventListener;
import io.micronaut.context.event.StartupEvent;

import javax.inject.Singleton;

@Singleton
public class Bootstrap implements ApplicationEventListener<StartupEvent> {

    private final TaskRepository repository;

    public Bootstrap(TaskRepository repository) {
        this.repository = repository;
    }

    @Override
    public void onApplicationEvent(StartupEvent event) {
        if (repository.count() == 0) {
            Task task = new Task();
            task.setSummary("Learn Micronaut with GraalVM");
            repository.save(task);
        }
    }
}
