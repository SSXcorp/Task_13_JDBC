package Task13;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    private static final Logger logger = LogManager.getLogger(ConnectionFactory.class);
    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;

    static {
        config.setJdbcUrl( "jdbc:postgresql://localhost:5432/task10" );
        config.setUsername( "postgres" );
        config.setPassword( "123" );
        config.addDataSourceProperty( "cachePrepStmts" , "true" );
        config.addDataSourceProperty( "prepStmtCacheSize" , "250" );
        config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
        ds = new HikariDataSource( config );
        //connectionTimeout - how long client will wait for new Connection (min 250ms max 30sec)
        //maximumPoolSize - how many connection objects can ConnectionPool store
    }


    //dataSourceClassName - новая версия jdbcUrl. Есть пераметр DataSource для управления параметрами драйвера.
    //autoCommit - default = true. controls the default auto-commit behavior of connections returned from the pool
    //connectionTimeout - максимальное время ожидание Connection. Если оно превышает заданое значение будет SQL exception
    //idleTimeout - определяет сколько времени Connection может быть AFK в пуле (когда его не используют)
    //maxLifetime - максимальное время жизни Connection (from 30sec to 30min)
    //connectionTestQuery - for "legacy" drivers that do not support the JDBC4
    // и так далее...


    public ConnectionFactory() {
    }

    public static Connection getConnection() {
        try {
            logger.debug("Hikari pool" + config.getPoolName());
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("Exception when connecting to the database", e);
        }
    }
}



