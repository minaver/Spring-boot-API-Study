package com.umc.hugo.store;


import com.umc.hugo.store.model.PostStoreReq;
import com.umc.hugo.store.model.PostStoreRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StoreService {
    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log 처리부분: Log를 기록하기 위해 필요한 함수입니다.

    private final StoreDao storeDao;
    private final StoreProvider storeProvider;

    @Autowired
    public StoreService(StoreDao storeDao,StoreProvider storeProvider){
        this.storeDao = storeDao;
        this.storeProvider = storeProvider;
    }

    //POST
    public PostStoreRes postStore(PostStoreReq postStoreReq){
        int storeIdx= storeDao.addStore(postStoreReq);
        PostStoreRes postStoreRes = new PostStoreRes(storeIdx);
        return postStoreRes;
    }
}
