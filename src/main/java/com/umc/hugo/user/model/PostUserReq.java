package com.umc.hugo.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PostUserReq {
    private String name;
    private String email;
    private String profileImgUrl;
}
