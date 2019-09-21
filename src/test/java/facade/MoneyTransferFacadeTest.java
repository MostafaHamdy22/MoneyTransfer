package facade;

import model.AccountInformation;
import model.MoneyTransferRequest;
import model.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import service.TransactionHistoryService;
import service.UserService;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

/**
 * Create by mostafa on 2019-09-21
 */
@RunWith(MockitoJUnitRunner.class)
public class MoneyTransferFacadeTest {

    @Mock
    private UserService userService;

    @Mock
    private TransactionHistoryService transactionHistoryService;


    private MoneyTransferFacade moneyTransferFacade;
    private User sender;
    private User receiver;
    private MoneyTransferRequest moneyTransferRequest;
    private AccountInformation senderAccountInformation;
    private AccountInformation receiverAccountInformation;

    @Before
    public void setup() {
        moneyTransferFacade = MoneyTransferFacade.getInstance();
        senderAccountInformation = AccountInformation.builder()
            .balance(10)
            .id(1)
            .build();

        receiverAccountInformation = AccountInformation.builder()
            .balance(1)
            .id(2)
            .build();

        sender = User.builder()
            .id(1)
            .name("Sender User")
            .information("User Used For Test")
            .accountInformation(senderAccountInformation)
            .build();

        receiver = User.builder()
            .id(2)
            .name("Receiver User")
            .information("User Used For Test")
            .accountInformation(receiverAccountInformation)
            .build();

        moneyTransferFacade.setUserService(userService);
        moneyTransferFacade.setTransactionHistoryService(transactionHistoryService);
    }

    @Test
    public void testTransferMoneyBetweenUsers() throws Exception {
        moneyTransferRequest = MoneyTransferRequest.builder()
            .senderId(sender.getId())
            .receiverId(receiver.getId())
            .amountToBeSent(5)
            .build();

        when(userService.getUserById(sender.getId())).thenReturn(sender);
        when(userService.getUserById(receiver.getId())).thenReturn(receiver);

        assertTrue(moneyTransferFacade.transferMoney(moneyTransferRequest));

        verify(userService).updateUserBalance(sender, 5);
        verify(userService).updateUserBalance(receiver, 6);
        verify(transactionHistoryService).storeTransaction(moneyTransferRequest);

    }

    @Test
    public void testTransferMoneyBetweenUsers_balanceNotEnough() throws Exception {
        moneyTransferRequest = MoneyTransferRequest.builder()
            .senderId(sender.getId())
            .receiverId(receiver.getId())
            .amountToBeSent(50)
            .build();

        when(userService.getUserById(sender.getId())).thenReturn(sender);
        when(userService.getUserById(receiver.getId())).thenReturn(receiver);

        assertFalse(moneyTransferFacade.transferMoney(moneyTransferRequest));

        verify(userService, never()).updateUserBalance(any(User.class), anyInt());
        verify(userService, never()).updateUserBalance(any(User.class), anyInt());
        verifyZeroInteractions(transactionHistoryService);

    }
}
