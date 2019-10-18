package geecon.lambda.function;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Delete;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.annotation.Put;
import io.micronaut.http.annotation.Status;

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
        return this.repository.findAllByDone(false);
    }

    @Get("/done")
    public Iterable<Task> listDone() {
        return this.repository.findAllByDone(true);
    }

    @Get("/search")
    public Iterable<Task> search(String q) {
        return this.repository.searchTasks(q);
    }


    @Get("/{id}")
    public Optional<Task> show(@NotNull Long id) {
        return repository.findById(id);
    }

    @Post("/")
    @Status(HttpStatus.CREATED)
    public Task create(@NotEmpty @Specific String summary, String description) {
        Task task = new Task();
        task.setSummary(summary);
        task.setDescription(description);
        return repository.save(task);
    }

    @Status(HttpStatus.ACCEPTED)
    @Put("/{id}/done")
    public Optional<Task> finish(@NotNull Long id) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        repository.updateDone(id, true);
        return repository.findById(id);
    }

    @Status(HttpStatus.ACCEPTED)
    @Put("/{id}")
    public Optional<Task> update(@NotNull Long id, @NotEmpty @Specific String summary, String description, Boolean done) {
        if (!repository.existsById(id)) {
            return Optional.empty();
        }
        repository.update(id, summary, description, done == null ? false : done);
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
