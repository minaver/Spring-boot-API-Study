package com.umc.hugo.src.menu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PatchMenuReq {
    private int menuIdx;
    private String name;
}
