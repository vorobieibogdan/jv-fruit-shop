package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;

public class ReportGeneratorImpl implements ReportGenerator {

    @Override
    public String getReport() {

        StringBuilder builder = new StringBuilder();
        builder.append("fruit,quantity\n");

        Storage.fruits.forEach((fruit, quantity) ->
                builder.append(fruit)
                        .append(",")
                        .append(quantity)
                        .append("\n"));

        return builder.toString();
    }
}

