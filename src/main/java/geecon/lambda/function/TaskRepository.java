package geecon.lambda.function;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.reactive.RxJavaCrudRepository;
import io.reactivex.Flowable;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface TaskRepository extends RxJavaCrudRepository<Task, Long> {

    void update(@Id Long id, String summary, String description, boolean done);

    Flowable<Task> findAllByDone(boolean done);

    void updateDone(@Id Long id, boolean done);

    @Query("select * from task where summary @@ plainto_tsquery(:query) OR description @@ plainto_tsquery(:query)")
    Flowable<Task> searchTasks(String query);

}
