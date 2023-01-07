package com.cosmos.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAccount {

    String email;
    String newPassword;
    String oldPassword;
    String phonenumber;
    String address;
    String name;
    boolean gender;
}
