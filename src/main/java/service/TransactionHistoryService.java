package service;

import datastore.implementation.InMemoryDataStore;
import datastore.interfaces.DataStore;
import model.MoneyTransferRequest;

/**
 * Create by mostafa on 2019-09-21
 */
public class TransactionHistoryService {

    private static TransactionHistoryService transactionHistoryService;
    private static DataStore dataStore;

    private TransactionHistoryService() {
        dataStore = InMemoryDataStore.getInstance();
    }

    public static TransactionHistoryService getInstance() {
        if (transactionHistoryService == null) {
            transactionHistoryService = new TransactionHistoryService();
        }
        return transactionHistoryService;
    }

    public void storeTransaction(MoneyTransferRequest moneyTransferRequest) {
        dataStore.addMoneyTransferRequest(moneyTransferRequest);
    }
}
