package geecon.lambda.function;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;

import javax.annotation.Nullable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Optional;

@Controller("/api/tasks")
public class TaskController {

    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @Get("/")
    public Iterable<Task> list() {
        return this.repository.findAll();
    }

    @Get("/{id}")
    public Optional<Task> show(@NotNull Long id) {
        return repository.findById(id);
    }

    @Post("/")
    @Status(HttpStatus.CREATED)
    public Task create(@NotEmpty String summary, @Nullable String description) {
        Task task = new Task();
        task.setSummary(summary);
        task.setDescription(description);
        return repository.save(task);
    }

    @Status(HttpStatus.ACCEPTED)
    @Put("/{id}")
    public Optional<Task> update(@NotNull Long id, @NotEmpty String summary, @Nullable String description) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        repository.update(id, summary, description);
        return repository.findById(id);
    }

    @Status(HttpStatus.NO_CONTENT)
    @Delete("/{id}")
    public Optional<Task> delete(@NotNull Long id) {
        Optional<Task> task = repository.findById(id);
        task.ifPresent(repository::delete);
        return task;
    }
}
