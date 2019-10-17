package geecon.lambda.function;

import io.micronaut.http.HttpStatus;
import io.micronaut.http.annotation.*;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Single;

import javax.annotation.Nullable;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Controller("/api/tasks")
public class TaskController {

    private final TaskRepository repository;

    public TaskController(TaskRepository repository) {
        this.repository = repository;
    }

    @Get("/")
    public Flowable<Task> list() {
        return this.repository.findAllByDone(false);
    }

    @Get("/done")
    public Flowable<Task> listDone() {
        return this.repository.findAllByDone(true);
    }

    @Get("/search")
    public Flowable<Task> search(String q) {
        return this.repository.searchTasks(q);
    }

    @Get("/{id}")
    public Maybe<Task> show(@NotNull Long id) {
        return repository.findById(id);
    }

    @Post("/")
    @Status(HttpStatus.CREATED)
    public Single<Task> create(@NotEmpty String summary, @Nullable String description) {
        Task task = new Task();
        task.setSummary(summary);
        task.setDescription(description);
        return repository.save(task);
    }

    @Status(HttpStatus.ACCEPTED)
    @Put("/{id}/done")
    public Maybe<Task> finish(@NotNull Long id) {
        return repository.findById(id).map(task -> {
            repository.updateDone(id, true);
            return task;
        });
    }

    @Status(HttpStatus.ACCEPTED)
    @Put("/{id}")
    public Maybe<Task> update(@NotNull Long id, @NotEmpty String summary, @Nullable String description, @Nullable Boolean done) {
        return repository.findById(id).map(task -> {
            repository.update(id, summary, description, done == null ? false : done);
            return task;
        });
    }

    @Status(HttpStatus.NO_CONTENT)
    @Delete("/{id}")
    public Maybe<Task> delete(@NotNull Long id) {
        return repository.findById(id).map(task -> {
            repository.delete(task);
            return task;
        });
    }
}
