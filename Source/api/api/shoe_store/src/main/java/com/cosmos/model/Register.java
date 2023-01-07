package com.cosmos.model;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Register {
    private  String email;

    private String password;
    private String name;
    private boolean gender;
    private String address;
    private String phone;
    private String birthday;


}
