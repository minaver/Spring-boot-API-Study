package com.umc.hugo.src.store.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PatchStoreReq {
    private int storeIdx;
    private String name;
}
