package model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * Create by mostafa on 2019-09-15
 */
@Data
@Builder
public class MoneyTransferRequest {

    @NonNull
    private int senderId;

    @NonNull
    private int receiverId;

    @NonNull
    private double amountToBeSent;
}
