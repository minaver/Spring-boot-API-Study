package com.umc.hugo.src.store;


import com.umc.hugo.config.BaseException;
import com.umc.hugo.src.store.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.umc.hugo.config.BaseResponseStatus.*;

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

//        StoreName storeName = storeDao.checkStore(postStoreReq.getName());
//        System.out.println(storeName);

        int storeIdx= storeDao.addStore(postStoreReq);
        PostStoreRes postStoreRes = new PostStoreRes(storeIdx);
        return postStoreRes;
    }

    public int checkExistStore(){

        storeDao.checkExistStore();

        return 0;
    }

    //PATCH
    // 1. modify 식당 이름
    public void modifyStore(PatchStoreReq patchStoreReq) throws BaseException {
        try {
            int result = storeDao.modifyStoreName(patchStoreReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_STORENAME);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 2. modify 식당 ImgUrl
    public void modifyStoreImgUrl(PatchStoreImgUrlReq patchStoreImgUrlReq) throws BaseException {
        try {
            int result = storeDao.modifyStoreImgUrl(patchStoreImgUrlReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_STOREIMGURL);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 3. modify 식당 status
    public void modifyStoreStatus(PatchStoreStatusReq patchStoreStatusReq) throws BaseException {

        // Body로 들어온 status가 active/deactive/out 중 하나인지 검사
        if(!patchStoreStatusReq.getStatus().equals("active") && !patchStoreStatusReq.getStatus().equals("deactive") && !patchStoreStatusReq.getStatus().equals("out")){
            throw new BaseException(PATCH_INVALID_STATUS);
        }

        try {
            int result = storeDao.modifyStoreStatus(patchStoreStatusReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_MENUIMGURL);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
