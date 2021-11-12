package com.umc.hugo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PatchUserPasswordReq {
    private int userIdx;
    private String nowPassword;
    private String newPassword;
}
