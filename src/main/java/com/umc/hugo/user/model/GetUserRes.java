package com.umc.hugo.user.model;

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
    private String profileImgUrl;
}

