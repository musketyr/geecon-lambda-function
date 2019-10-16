package geecon.lambda.function;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.micronaut.http.annotation.Error;
import io.micronaut.http.hateoas.JsonError;

import javax.annotation.Nullable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.NoSuchElementException;

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
    public Task show(@NotNull Long id) {
        return repository.findById(id).orElse(null); // orElseThrow(() -> new NoSuchElementException("No such task for ID " + id));
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
    public void update(@NotNull Long id, @NotEmpty String summary, @Nullable String description) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("No such task for ID " + id);
        }
        repository.update(id, summary, description);
    }

    @Status(HttpStatus.NO_CONTENT)
    @Delete("/{id}")
    public void delete(@NotNull Long id) {
        if (!repository.existsById(id)) {
            throw new NoSuchElementException("No such task for ID " + id);
        }
        repository.findById(id).ifPresent(repository::delete);
    }

    @Error(exception = NoSuchElementException.class)
    @Status(HttpStatus.NOT_FOUND)
    public JsonError noSuchElement(NoSuchElementException e){
        return new JsonError(e.getMessage());
    }

}
