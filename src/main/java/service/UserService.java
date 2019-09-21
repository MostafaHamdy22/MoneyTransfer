package service;

import datastore.implementation.InMemoryDataStore;
import datastore.interfaces.DataStore;
import lombok.Getter;
import lombok.Setter;
import model.User;

/**
 * Create by mostafa on 2019-09-14
 */
@Setter
@Getter
public class UserService {

    private static UserService userService;
    private static DataStore dataStore;

    private UserService() {
        dataStore = InMemoryDataStore.getInstance();
    }

    public static UserService getInstance() {
        if (userService == null) {
            userService = new UserService();
        }
        return userService;
    }

    public void setDataStore(DataStore dataStore) {
        UserService.dataStore = dataStore;
    }

    public User getUserById(int userId) throws Exception {
        return dataStore.getUserById(userId);
    }

    public User getUserById(String userId) throws Exception {
        return dataStore.getUserById(Integer.valueOf(userId));
    }

    public User getUserByAccountId(int accountId) throws Exception {
        return dataStore.getUserByAccountId(accountId);
    }

    public User getUserByAccountId(String accountId) throws Exception {
        return dataStore.getUserByAccountId(Integer.valueOf(accountId));
    }

    public void addUser(User user) throws Exception {
        dataStore.addUser(user);
    }

    public void removeUser(User user) throws Exception {
        removeUser(user.getId());
    }

    public void removeUser(int userId) throws Exception {
        dataStore.removeUser(userId);
    }

    public void updateUserBalance(User user, double newBalance) throws Exception {
        user.getAccountInformation().setBalance(newBalance);
        dataStore.addUser(user);
    }

    public void clearDataStore() {
        dataStore.clear();
    }
}
