package test_utils;


import com.liashenko.app.service.data_source.DbConnectionService;
import org.junit.runners.Parameterized;

import java.io.*;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;
import java.util.ResourceBundle;

public abstract class TestDbUtil {

    private static final String PATH_TO_CREATE_TEST_DB_SCENARIO = "sql_queries" + File.separator
            + "test" + File.separator
            + "my_sql" + File.separator
            + "create_db_with_tables.sql";

    private static final String PATH_TO_FILL_TEST_DB_WITH_DATA_SCENARIO = "sql_queries" + File.separator
            + "test" + File.separator
            + "my_sql" + File.separator
            + "fill_with_data.sql";

    private static final String PATH_TO_DROP_TABLES_SCENARIO = "sql_queries" + File.separator
            + "test" + File.separator
            + "my_sql" + File.separator
            + "drop_tables.sql";

    private static final String SQL_TEST_QUERIES_PATH = "sql_queries" + File.separator
            + "test" + File.separator
            + "my_sql" + File.separator
            + "test_my_sql_dml_queries";

    private static final Locale UA_LOCALE = new Locale("uk", "UA");
    private static final Locale EN_LOCALE = new Locale("en", "GB");

    private static final ResourceBundle UA_RES_BUNDLE = ResourceBundle.getBundle(SQL_TEST_QUERIES_PATH, UA_LOCALE);
    private static final ResourceBundle UK_RES_BUNDLE = ResourceBundle.getBundle(SQL_TEST_QUERIES_PATH, EN_LOCALE);


    private static final DbConnectionService TEST_DB_CONNECT_SERVICE = new TestDbConnectServiceImpl();

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {UA_RES_BUNDLE},
                {UK_RES_BUNDLE}
        });
    }

    protected static Connection getConnection() {
        return TEST_DB_CONNECT_SERVICE.getConnection();
    }

    protected static void close(Connection connection) {
        TEST_DB_CONNECT_SERVICE.close(connection);
    }

    private static void createTablesInTestDb(Connection connection) throws SQLException {
        ScriptRunner sr = new ScriptRunner(connection, true, true);
        try {
            sr.runScript(new InputStreamReader(new FileInputStream(decodeResourceFilePath(PATH_TO_CREATE_TEST_DB_SCENARIO)),
                    StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(connection);
        }
    }

    private static void fillTestDbWithData(Connection connection) throws SQLException {
        ScriptRunner sr = new ScriptRunner(connection, false, true);
        try {
            //Get file from resources folder
            sr.runScript(new InputStreamReader(new FileInputStream(decodeResourceFilePath(PATH_TO_FILL_TEST_DB_WITH_DATA_SCENARIO)),
                    StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            close(connection);
        }
    }

    protected static void prepareEmptyTablesInTestDb() {
        try {
            createTablesInTestDb(getConnection());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    protected static void fillTablesInTestDb() {
        try {
            fillTestDbWithData(getConnection());
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    protected static void dropTablesInTestDb() {
        Connection connection = getConnection();
        ScriptRunner sr = new ScriptRunner(connection, false, true);
        try {
            //Get file from resources folder
            sr.runScript(new InputStreamReader(new FileInputStream(decodeResourceFilePath(PATH_TO_DROP_TABLES_SCENARIO)),
                    StandardCharsets.UTF_8));
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            close(connection);
        }
    }

    private static String decodeResourceFilePath(String propertyFilePath) {
        URL url = TestDbUtil.class.getClassLoader().getResource(propertyFilePath);
        if (url != null) {
            try {
                return URLDecoder.decode(url.getPath(), StandardCharsets.UTF_8.name());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File " + propertyFilePath + " not found!");
        }
        return "";
    }
}
