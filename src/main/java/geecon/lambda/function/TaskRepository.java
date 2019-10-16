package geecon.lambda.function;

import io.micronaut.data.annotation.Id;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

@JdbcRepository(dialect = Dialect.POSTGRES)
public interface TaskRepository extends CrudRepository<Task, Long> {

    void update(@Id Long id, String summary, String description);

}
