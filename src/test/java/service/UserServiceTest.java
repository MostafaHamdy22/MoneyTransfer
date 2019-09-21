package service;

import model.AccountInformation;
import model.User;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Create by mostafa on 2019-09-21
 */
public class UserServiceTest {

    private UserService userService;
    private User user;
    private AccountInformation accountInformation;

    @Before
    public void setup() {
        userService = UserService.getInstance();
        accountInformation = AccountInformation.builder()
            .balance(1)
            .id(1)
            .build();

        user = User.builder()
            .id(1)
            .name("Test User")
            .information("User Used For Test")
            .accountInformation(accountInformation)
            .build();
    }

    @Test
    public void testGetUserByIntId_UserExistInDataStore() throws Exception {
        userService.addUser(user);

        assertEquals(user, userService.getUserById(user.getId()));
    }

    @Test(expected = Exception.class)
    public void testGetUserById_UserDoesNotExistInDataStore() throws Exception {
        userService.getUserById(user.getId());
    }

    @Test
    public void testGetUserByStringId_UserExistInDataStore() throws Exception {
        userService.addUser(user);

        assertEquals(user, userService.getUserById(String.valueOf(user.getId())));
    }

    @Test
    public void testGetUserByAccountId_UserExistInDataStore() throws Exception {
        userService.addUser(user);

        assertEquals(user, userService.getUserByAccountId(user.getAccountInformation().getId()));
    }

    @Test
    public void testUpdateUserBalance() throws Exception {
        userService.addUser(user);
        user.getAccountInformation().setBalance(100);
        userService.updateUserBalance(user, 100);

        assertEquals(user.getAccountInformation().getBalance(),
            userService.getUserById(user.getId()).getAccountInformation().getBalance(), 0);
    }

    @Test(expected = Exception.class)
    public void testRemoveUser() throws Exception {
        userService.addUser(user);
        userService.removeUser(user);
        userService.getUserById(user.getId());
    }

    @After
    public void tearDown() {
        userService.clearDataStore();
    }
}
