package datastore.implementation;

import datastore.interfaces.DataStore;
import model.AccountInformation;
import model.MoneyTransferRequest;
import model.User;

import java.util.HashMap;
import java.util.HashSet;

/**
 * Create by mostafa on 2019-09-14
 */
public class InMemoryDataStore extends DataStore {

    private static InMemoryDataStore inMemoryDataStore;
    private static HashMap<Integer, User> idToUsersMap;
    private static HashMap<Integer, User> accountToUserMap;
    private static HashSet<MoneyTransferRequest> moneyTransferRequests;

    private InMemoryDataStore() {
        idToUsersMap = new HashMap<>();
        accountToUserMap = new HashMap<>();
        moneyTransferRequests = new HashSet<>();
    }

    public static DataStore getInstance() {
        if (inMemoryDataStore == null) {
            inMemoryDataStore = new InMemoryDataStore();
        }
        return inMemoryDataStore;
    }

    @Override
    public void addUser(User user) throws Exception {
        idToUsersMap.put(user.getId(), user);
        accountToUserMap.put(user.getAccountInformation().getId(), user);
    }

    @Override
    public void addAccountInformation(AccountInformation accountInformation) {
        final int accountId = accountInformation.getId();
        final User user = accountToUserMap.get(accountId);
        user.setAccountInformation(accountInformation);
    }

    @Override
    public void removeUser(int userId) throws Exception {
        User user = getUserById(userId);
        idToUsersMap.remove(user.getId());
        accountToUserMap.remove(user.getAccountInformation().getId());
    }

    @Override
    public boolean userExists(int userId) {
        return idToUsersMap.containsKey(userId);
    }

    @Override
    public boolean accountExists(int accountId) {
        return accountToUserMap.containsKey(accountId);
    }

    @Override
    public User getUserById(int userId) throws Exception {
        if (!userExists(userId)) {
            throw new Exception(String.format("UserId {} does not exit", userId));
        }
        return idToUsersMap.get(userId);
    }

    @Override
    public User getUserByAccountId(int accountId) throws Exception {
        if (!accountExists(accountId)) {
            throw new Exception(String.format("AccountId {} does not exist", accountId));
        }
        return accountToUserMap.get(accountId);
    }

    @Override
    public AccountInformation getAccountById(int accountId) throws Exception {
        User user = getUserByAccountId(accountId);
        return user.getAccountInformation();
    }

    @Override
    public void addMoneyTransferRequest(MoneyTransferRequest moneyTransferRequest) {
        moneyTransferRequests.add(moneyTransferRequest);
    }

    @Override
    public void clear() {
        idToUsersMap.clear();
        accountToUserMap.clear();
        accountToUserMap.clear();
        moneyTransferRequests.clear();
    }
}
