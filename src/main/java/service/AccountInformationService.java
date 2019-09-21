package service;

import datastore.implementation.InMemoryDataStore;
import datastore.interfaces.DataStore;
import model.AccountInformation;

/**
 * Create by mostafa on 2019-09-15
 */
public class AccountInformationService {

    private static AccountInformationService accountInformationService;
    private static DataStore dataStore;

    private AccountInformationService() {
        dataStore = InMemoryDataStore.getInstance();
    }

    public static AccountInformationService getInstance() {
        if (accountInformationService == null) {
            accountInformationService = new AccountInformationService();
        }
        return accountInformationService;
    }

    public AccountInformation getAccountInformation(int accountId) throws Exception {
        if (!dataStore.accountExists(accountId)) {
            throw new Exception("Account does not exist");
        }
        return dataStore.getAccountById(accountId);
    }

    public AccountInformation getAccountInformation(String accountId) throws Exception {
        return getAccountInformation(Integer.valueOf(accountId));
    }
}
