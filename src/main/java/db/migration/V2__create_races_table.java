package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.Statement;

public class V2__create_races_table extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        final Statement statement = context.getConnection().createStatement();

        try (statement) {
            statement.execute("create table races" +
                    "(id        bigserial not null constraint races_pkey primary key," +
                    " distance integer not null default 0, " +
                    " race_time integer not null default 0, " +
                    " race_date date not null, " +
                    " user_id   bigint, " +
                    "FOREIGN KEY (user_id) REFERENCES users (id))");
        }
    }
}
