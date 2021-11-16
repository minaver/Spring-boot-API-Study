package com.umc.hugo.src.owner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class GetOwnerRes {
    private int ownerIdx;
    private String name;
    private String email;
    private String password;
    private String profileImgUrl;
}
