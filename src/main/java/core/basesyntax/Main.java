package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.service.DataConverter;
import core.basesyntax.service.FileReaderService;
import core.basesyntax.service.FileWriterService;
import core.basesyntax.service.FruitShopService;
import core.basesyntax.service.ReportGenerator;
import core.basesyntax.service.impl.DataConverterImpl;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import core.basesyntax.service.impl.FileWriterServiceImpl;
import core.basesyntax.service.impl.FruitShopServiceImpl;
import core.basesyntax.service.impl.ReportGeneratorImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.impl.BalanceOperation;
import core.basesyntax.strategy.impl.OperationStrategyImpl;
import core.basesyntax.strategy.impl.PurchaseOperation;
import core.basesyntax.strategy.impl.ReturnOperation;
import core.basesyntax.strategy.impl.SupplyOperation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final String INPUT_FILE =
            "src/main/resources/input.csv";
    private static final String OUTPUT_FILE =
            "src/main/resources/output.csv";

    public static void main(String[] args) {

        // services
        FileReaderService readerService = new FileReaderServiceImpl();
        DataConverter converter = new DataConverterImpl();
        ReportGenerator reportGenerator = new ReportGeneratorImpl();
        FileWriterService writerService = new FileWriterServiceImpl();

        // strategy handlers
        Map<String, OperationHandler> handlers = new HashMap<>();
        handlers.put("b", new BalanceOperation());
        handlers.put("s", new SupplyOperation());
        handlers.put("p", new PurchaseOperation());
        handlers.put("r", new ReturnOperation());

        OperationStrategy strategy = new OperationStrategyImpl(handlers);
        FruitShopService fruitShopService = new FruitShopServiceImpl(strategy);

        // 1. read file
        List<String> lines = readerService.read(INPUT_FILE);

        // 2. convert data
        var transactions = converter.convertToTransaction(lines);

        // 3. process transactions
        fruitShopService.process(transactions);

        // 4. generate report
        String report = reportGenerator.getReport(Storage.fruits);

        // 5. write file
        writerService.write(report, OUTPUT_FILE);
    }
}

