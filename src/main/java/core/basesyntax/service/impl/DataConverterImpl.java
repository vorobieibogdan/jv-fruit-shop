package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataConverter;
import java.util.ArrayList;
import java.util.List;

public class DataConverterImpl implements DataConverter {

    @Override
    public List<FruitTransaction> convertToTransaction(List<String> data) {
        List<FruitTransaction> transactions = new ArrayList<>();

        for (String line : data) {
            String[] parts = line.split(",");

            FruitTransaction transaction = new FruitTransaction();

            transaction.setFruit(parts[1]);
            transaction.setQuantity(Integer.parseInt(parts[2]));

            for (FruitTransaction.Operation op
                    : FruitTransaction.Operation.values()) {

                if (op.getCode().equals(parts[0])) {
                    transaction.setOperation(op);
                }
            }

            transactions.add(transaction);
        }

        return transactions;
    }
}

