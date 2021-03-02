package ru.javawebinar.topjava.rules;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestTimeRule extends Stopwatch {

    private static final Logger log = LoggerFactory.getLogger(TestTimeRule.class);

    private Map<String, Long> testExecutionTimeCollector;

    public TestTimeRule() {
    }

    public TestTimeRule(Map<String, Long> testExecutionTimeCollector) {
        this.testExecutionTimeCollector = testExecutionTimeCollector;
    }

    @Override
    protected void finished(long nanos, Description description) {
        long runtime = runtime(TimeUnit.MILLISECONDS);
        log.debug("{} {} ms", description.getMethodName(), runtime);
        if (testExecutionTimeCollector != null) {
            testExecutionTimeCollector.put(description.getMethodName(), runtime);
        }
    }
}
