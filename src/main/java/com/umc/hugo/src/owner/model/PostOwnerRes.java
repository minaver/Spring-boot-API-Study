package com.umc.hugo.src.owner.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PostOwnerRes {
    private String jwt;
    private int ownerIdx;
}
