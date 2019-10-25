package geecon.lambda.function;

import io.micronaut.core.annotation.TypeHint;
import io.micronaut.runtime.Micronaut;

@TypeHint(typeNames = {"org.h2.Driver", "org.h2.mvstore.db.MVTableEngine"})
public class Application {

    public static void main(String[] args) {
        Micronaut.run(Application.class);
    }

}