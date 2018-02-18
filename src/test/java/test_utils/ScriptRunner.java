package test_utils;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class ScriptRunner {
    private static final String DEFAULT_DELIMITER = ";";
    private static final String PL_SQL_BLOCK_SPLIT_DELIMITER = "+";
    private static final String PL_SQL_BLOCK_END_DELIMITER = "#";

    private final boolean autoCommit;
    private final boolean stopOnError;
    private final Connection connection;
    private final PrintWriter out;
    private final PrintWriter err;

    public ScriptRunner(final Connection connection, final boolean autoCommit, final boolean stopOnError) {
        if (connection == null) {
            throw new RuntimeException("ScriptRunner requires an SQL Connection");
        }

        this.connection = connection;
        this.autoCommit = autoCommit;
        this.stopOnError = stopOnError;
        this.out = new PrintWriter(System.out);
        this.err = new PrintWriter(System.err);
    }

    public void runScript(final Reader reader) throws SQLException, IOException {
        final boolean originalAutoCommit = this.connection.getAutoCommit();
        try {
            if (originalAutoCommit != this.autoCommit) {
                this.connection.setAutoCommit(this.autoCommit);
            }
            this.runScript(this.connection, reader);
        } finally {
            this.connection.setAutoCommit(originalAutoCommit);
        }
    }

    private void runScript(Connection conn, final Reader reader) throws SQLException, IOException {
        StringBuilder command = null;

        try {
            final LineNumberReader lineReader = new LineNumberReader(reader);
            String line = null;
            while ((line = lineReader.readLine()) != null) {
                if (command == null) {
                    command = new StringBuilder();
                }

                String trimmedLine = line.trim();

                // Interpret SQL Comment & Some statement that are not executable
                if (trimmedLine.startsWith("--")
                        || trimmedLine.startsWith("//")
                        || trimmedLine.startsWith("#")
                        || trimmedLine.toLowerCase().startsWith("rem inserting into")
                        || trimmedLine.toLowerCase().startsWith("set define off")) {

                    // do nothing...
                } else if (trimmedLine.endsWith(DEFAULT_DELIMITER) ||
                        trimmedLine.endsWith(PL_SQL_BLOCK_END_DELIMITER)) { // Line is end of statement

                    // Append
                    if (trimmedLine.endsWith(DEFAULT_DELIMITER)) {
                        command.append(line.substring(0, line.lastIndexOf(DEFAULT_DELIMITER)));
                        command.append(" ");
                    } else if (trimmedLine.endsWith(PL_SQL_BLOCK_END_DELIMITER)) {
                        command.append(line.substring
                                (0, line.lastIndexOf(PL_SQL_BLOCK_END_DELIMITER)));
                        command.append(" ");
//                    } else if (!trimmedLine.trim().isEmpty()){
//
                    }

                    Statement stmt = conn.createStatement();
                    if (this.stopOnError) {
                        stmt.executeUpdate(command.toString());
                    } else {
                        try {
                            stmt.executeUpdate(command.toString());
                        } catch (final SQLException e) {
                            e.fillInStackTrace();
                            err.println("Error executing SQL Command: \"" +
                                    command + "\"");
                            err.println(e);
                            err.flush();
                            throw e;
                        }
                    }
                    command = null;
                } else if (trimmedLine.endsWith(PL_SQL_BLOCK_SPLIT_DELIMITER)) {
                    command.append(line.substring
                            (0, line.lastIndexOf(PL_SQL_BLOCK_SPLIT_DELIMITER)));
                    command.append(" ");
                } else { // Line is middle of a statement

                    // Append
                    command.append(line);
                    command.append(" ");
                }
            }
            if (!this.autoCommit) {
                conn.commit();
            }
        } catch (final SQLException e) {
            conn.rollback();
            e.fillInStackTrace();
            err.println("Error executing SQL Command: \"" + command + "\"");
            err.println(e);
            err.flush();
            throw e;
        } catch (final IOException e) {
            e.fillInStackTrace();
            err.println("Error reading SQL Script.");
            err.println(e);
            err.flush();
            throw e;
        }
    }
}
