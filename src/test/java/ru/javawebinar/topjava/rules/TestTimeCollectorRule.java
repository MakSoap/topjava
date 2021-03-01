package ru.javawebinar.topjava.rules;

import org.junit.rules.ExternalResource;

import java.util.Map;

public class TestTimeCollectorRule extends ExternalResource {

    private Map<String, Long> testExecutionTimeCollector;

    public TestTimeCollectorRule(Map<String, Long> testExecutionTimeCollector) {
        this.testExecutionTimeCollector = testExecutionTimeCollector;
    }

    @Override
    protected void after() {
        if (testExecutionTimeCollector != null) {
            testExecutionTimeCollector.forEach((description, time) -> {
                System.out.println("Test \"" + description + "\" execution with time " + (time / 1000) + " sec, " + (time % 1000) + "ms");
            });
        }
    }
}
