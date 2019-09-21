package model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

/**
 * Create by mostafa on 2019-09-14
 */
@Data
@Builder
public class User {

    @NonNull
    private int id;

    @NonNull
    private String name;

    @NonNull
    private String information;

    @NonNull
    private AccountInformation accountInformation;
}
