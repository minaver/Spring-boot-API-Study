package com.umc.hugo.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PostLoginReq {
    private String email;
    private String password;
}
