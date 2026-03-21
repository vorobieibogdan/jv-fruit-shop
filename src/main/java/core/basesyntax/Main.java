package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.service.*;
import core.basesyntax.service.impl.*;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE = "src/main/resources/input.csv";
    private static final String OUTPUT_FILE = "src/main/resources/output.csv";

    public static void main(String[] args) {
        FileReaderService reader = new FileReaderServiceImpl();
        DataConverter converter = new DataConverterImpl();
        FileWriterService writer = new FileWriterServiceImpl();
        ReportGenerator reportGenerator = new ReportGeneratorImpl();

        Map<core.basesyntax.model.FruitTransaction.Operation,
                OperationHandler> handlers = new HashMap<>();

        handlers.put(core.basesyntax.model.FruitTransaction.Operation.BALANCE,
                new BalanceOperation());
        handlers.put(core.basesyntax.model.FruitTransaction.Operation.SUPPLY,
                new SupplyOperation());
        handlers.put(core.basesyntax.model.FruitTransaction.Operation.PURCHASE,
                new PurchaseOperation());
        handlers.put(core.basesyntax.model.FruitTransaction.Operation.RETURN,
                new ReturnOperation());

        OperationStrategy strategy = new OperationStrategyImpl(handlers);
        FruitShopService shopService = new FruitShopServiceImpl(strategy);

        List<String> lines = reader.read(INPUT_FILE);
        var transactions = converter.convertToTransaction(lines);
        shopService.process(transactions);

        String report = reportGenerator.getReport(Storage.fruits);
        writer.write(report, OUTPUT_FILE);
    }
}

