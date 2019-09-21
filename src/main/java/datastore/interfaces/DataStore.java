package datastore.interfaces;

import model.AccountInformation;
import model.MoneyTransferRequest;
import model.User;

public abstract class DataStore {

    public static DataStore getInstance() {
        return null;
    }

    public abstract void addUser(User user) throws Exception;

    public abstract void addAccountInformation(AccountInformation accountInformation);

    public abstract void removeUser(int userId) throws Exception;

    public abstract boolean userExists(int userId);

    public abstract boolean accountExists(int accountId);

    public abstract User getUserById(int userId) throws Exception;

    public abstract User getUserByAccountId(int accountId) throws Exception;

    public abstract AccountInformation getAccountById(int accountId) throws Exception;

    public abstract void addMoneyTransferRequest(MoneyTransferRequest moneyTransferRequest);

    public abstract void clear();
}
