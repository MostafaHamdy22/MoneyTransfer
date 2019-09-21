package model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * Create by mostafa on 2019-09-14
 */
@Data
@Builder
public class AccountInformation {

    @NonNull
    private int id;

    @NonNull
    private double balance;
}
