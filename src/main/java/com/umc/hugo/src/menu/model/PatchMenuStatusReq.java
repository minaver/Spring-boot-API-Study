package com.umc.hugo.src.menu.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PatchMenuStatusReq {
    private int menuIdx;
    private String status;
}
