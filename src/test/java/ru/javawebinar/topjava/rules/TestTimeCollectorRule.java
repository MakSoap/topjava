package ru.javawebinar.topjava.rules;

import org.junit.rules.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

public class TestTimeCollectorRule extends ExternalResource {

    private static final Logger log = LoggerFactory.getLogger(TestTimeCollectorRule.class);

    private Map<String, Long> testExecutionTimeCollector;

    public TestTimeCollectorRule(Map<String, Long> testExecutionTimeCollector) {
        this.testExecutionTimeCollector = testExecutionTimeCollector;
    }

    @Override
    protected void after() {
        StringBuilder builder = new StringBuilder();
        int formatStringLength = testExecutionTimeCollector.keySet().stream().mapToInt(String::length).max().orElse(10) + 5;
        int formatTimeLength = testExecutionTimeCollector.values().stream().map(String::valueOf).mapToInt(String::length).max().orElse(2) + 1;
        String format = "%n|%-" + formatStringLength + "s %-" + formatTimeLength + "d ms|";
        int maxLengthTestInfo = 0;
        for (Map.Entry<String, Long> testInfo : testExecutionTimeCollector.entrySet()) {
            String formattingTestInfo = String.format(format, testInfo.getKey(), testInfo.getValue());
            builder.append(formattingTestInfo);
            maxLengthTestInfo = Math.max(maxLengthTestInfo, formattingTestInfo.length());
        }
        StringBuilder borders = new StringBuilder("\n");
        for (int i = 0; i < maxLengthTestInfo - 2; i++) {
            borders.append("-");
        }
        builder.insert(0, borders.toString());
        builder.insert(builder.length(), borders.toString());
        log.debug(builder.toString());
    }
}
