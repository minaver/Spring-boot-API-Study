package com.umc.hugo.src.shop;

import com.umc.hugo.config.BaseException;
import com.umc.hugo.src.shop.model.PostShopReq;
import com.umc.hugo.src.shop.model.PostShopRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.umc.hugo.config.BaseResponseStatus.*;

@Service
public class ShopService {

    private ShopProvider shopProvider;
    private ShopDao shopDao;

    @Autowired
    public ShopService(ShopProvider shopProvider, ShopDao shopDao){
        this.shopProvider = shopProvider;
        this.shopDao = shopDao;
    }

    // POST
    @Transactional // <- 안 걸린다...
    public PostShopRes postShop(PostShopReq postShopReq) throws BaseException {
        // 1. shop DB에 결재 내역 저장
        int shopIdx = shopDao.addShop(postShopReq);

        // 2. store DB에 orderNum 증가
        try {
            int result = shopDao.increaseStoreOrderNum(postShopReq);
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(INCREASE_FAIL_STORE_ORDER_NUM);
            }

        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }

        return new PostShopRes(shopIdx);
    }



}
