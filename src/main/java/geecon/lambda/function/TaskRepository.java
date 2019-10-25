package geecon.lambda.function;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;

@JdbcRepository(dialect = Dialect.H2)
public interface TaskRepository extends CrudRepository<Task, Long> {

    void update(@Id Long id, String summary, String description, boolean done);

    List<Task> findAllByDone(boolean done);

    void updateDone(@Id Long id, boolean done);

    @Query("select * from task where summary @@ plainto_tsquery(:query) OR description @@ plainto_tsquery(:query)")
    List<Task> searchTasks(String query);

}
