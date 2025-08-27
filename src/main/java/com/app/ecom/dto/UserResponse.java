package com.app.ecom.dto;

import com.app.ecom.model.UserRole;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private UserRole role;
    private AddressDTO addressDTO;
}
