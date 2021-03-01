package ru.javawebinar.topjava.rules;

import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class TestTimeRule extends TestWatcher {

    private static final Logger log = LoggerFactory.getLogger(TestTimeRule.class);

    private Map<String, Long> testExecutionTimeCollector;

    public TestTimeRule() { }

    public TestTimeRule(Map<String, Long> testExecutionTimeCollector) {
        this.testExecutionTimeCollector = testExecutionTimeCollector;
    }

    private long startTimeTest;

    @Override
    protected void starting(Description description) {
        startTimeTest = System.currentTimeMillis();
    }

    @Override
    protected void finished(Description description) {
        long executionTime = System.currentTimeMillis() - startTimeTest;
        log.debug("Test execution time: " + (executionTime / 1000) + " sec, " + (executionTime % 1000) + "ms");
        if (testExecutionTimeCollector != null) {
            testExecutionTimeCollector.put(description.getDisplayName(), executionTime);
        }
    }
}
