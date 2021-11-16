package com.umc.hugo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PostLoginReq {
    public PostLoginReq(){}

    private String email;
    private String password;
}
