package facade;

import model.MoneyTransferRequest;
import model.User;
import service.TransactionHistoryService;
import service.UserService;

/**
 * Create by mostafa on 2019-09-15
 */
public class MoneyTransferFacade {

    private static UserService userService;
    private static MoneyTransferFacade moneyTransferFacade;
    private static TransactionHistoryService transactionHistoryService;

    private MoneyTransferFacade() {
        userService = UserService.getInstance();
        transactionHistoryService = TransactionHistoryService.getInstance();
    }

    public static MoneyTransferFacade getInstance() {
        if (moneyTransferFacade == null) {
            moneyTransferFacade = new MoneyTransferFacade();
        }
        return moneyTransferFacade;
    }

    public void setUserService(UserService userService) {
        MoneyTransferFacade.userService = userService;
    }

    public void setTransactionHistoryService(TransactionHistoryService transactionHistoryService) {
        MoneyTransferFacade.transactionHistoryService = transactionHistoryService;
    }

    /**
     * This method should be transactional.
     */
    public boolean transferMoney(MoneyTransferRequest moneyTransferRequest) throws Exception {
        if (moneyTransferRequest.getAmountToBeSent() < 0) {
            return false;
        }

        final User sender = userService.getUserById(moneyTransferRequest.getSenderId());
        final User receiver = userService.getUserById(moneyTransferRequest.getReceiverId());
        final double amountToBeSent = moneyTransferRequest.getAmountToBeSent();

        if (sender.getAccountInformation().getBalance() < amountToBeSent) {
            return false;
        }

        final double senderNewBalance =
            sender.getAccountInformation().getBalance() - amountToBeSent;
        final double receiverNewBalance =
            receiver.getAccountInformation().getBalance() + amountToBeSent;

        userService.updateUserBalance(sender, senderNewBalance);
        userService.updateUserBalance(receiver, receiverNewBalance);
        transactionHistoryService.storeTransaction(moneyTransferRequest);
        return true;
    }
}
