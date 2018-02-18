package test_utils;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

public class DbInitFixtures extends TestDbUtil implements TestRule {

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                prepareEmptyTablesInTestDb();
                fillTablesInTestDb();
                base.evaluate();
                dropTablesInTestDb();
            }
        };
    }
}
