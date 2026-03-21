package core.basesyntax.service.impl;

import core.basesyntax.service.ReportGenerator;
import java.util.Map;

public class ReportGeneratorImpl implements ReportGenerator {
    @Override
    public String getReport(Map<String, Integer> fruits) {
        StringBuilder sb = new StringBuilder();
        sb.append("fruit,quantity").append(System.lineSeparator());

        for (Map.Entry<String, Integer> entry : fruits.entrySet()) {
            sb.append(entry.getKey())
                    .append(",")
                    .append(entry.getValue())
                    .append(System.lineSeparator());
        }

        return sb.toString();
    }
}

