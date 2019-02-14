package db.migration;

import org.flywaydb.core.api.migration.BaseJavaMigration;
import org.flywaydb.core.api.migration.Context;

import java.sql.Statement;

public class V1__create_users_table extends BaseJavaMigration {
    @Override
    public void migrate(Context context) throws Exception {
        final Statement statement = context.getConnection().createStatement();

        try (statement) {
            statement.execute("create table users" +
                    "( id             bigserial not null constraint users_pkey primary key," +
                    "  login          varchar     not null constraint uk_users_login unique," +
                    "  password      varchar     not null)");
        }
    }
}
