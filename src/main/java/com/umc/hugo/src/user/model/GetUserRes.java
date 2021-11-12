package com.umc.hugo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class GetUserRes {
    private int userIdx;
    private String name;
    private String email;
    private String password;
    private int mailFlag;
    private int smsFlag;
    private String profileImgUrl;
}

