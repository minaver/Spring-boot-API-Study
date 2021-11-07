package com.umc.hugo.store;

import com.umc.hugo.store.model.GetStoreRes;
import com.umc.hugo.store.model.PostStoreReq;
import com.umc.hugo.store.model.PostStoreRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class StoreController {

    private StoreProvider storeProvider;

    @Autowired
    public StoreController(StoreProvider storeProvider){
        this.storeProvider = storeProvider;
    }

    @GetMapping("/stores")
    public List<GetStoreRes> getStore(@RequestParam String order){
        List<GetStoreRes> storeRes = storeProvider.getStore(order);
        return storeRes;
    }

    @ResponseBody
    @PostMapping("/store")
    public PostStoreRes postStore(@RequestBody PostStoreReq postStoreReq){
        PostStoreRes postStoreRes = storeProvider.postStore(postStoreReq);
        return postStoreRes;
    }

}
