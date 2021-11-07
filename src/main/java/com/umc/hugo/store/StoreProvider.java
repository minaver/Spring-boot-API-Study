package com.umc.hugo.store;

import com.umc.hugo.review.ReviewDao;
import com.umc.hugo.store.model.GetStoreRes;
import com.umc.hugo.store.model.PostStoreReq;
import com.umc.hugo.store.model.PostStoreRes;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class StoreProvider {

    private final StoreDao storeDao;

    @Autowired
    public StoreProvider(StoreDao storeDao){
        this.storeDao = storeDao;
    }

    public List<GetStoreRes> getStore(String order){
        List<GetStoreRes> storeRes;
        if(order.equals("star")){
            storeRes = storeDao.storeResByStar();
        }else if(order.equals("review")){
            storeRes = storeDao.storeResByReview();
        }else{
            storeRes = storeDao.storeResByIdx();
        }

        return storeRes;
    }

    public PostStoreRes postStore(PostStoreReq postStoreReq){
        int storeIdx= storeDao.addStore(postStoreReq);
        PostStoreRes postStoreRes = new PostStoreRes(storeIdx);
        return postStoreRes;
    }
}
