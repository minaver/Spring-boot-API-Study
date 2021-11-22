package com.umc.hugo.src.store;

import com.umc.hugo.config.BaseException;
import com.umc.hugo.src.food.Food;
import com.umc.hugo.src.store.model.GetStoreRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.umc.hugo.config.BaseResponseStatus.*;

@Service
public class StoreProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final StoreDao storeDao;

    @Autowired
    public StoreProvider(StoreDao storeDao){
        this.storeDao = storeDao;
    }

    public List<GetStoreRes> getStore(int foodIdx, String order, int page,int pageSize) throws BaseException {

        // 사용자가 입력한 page가 DB page를 초과했는지 확인
        int pageSizeFromUser = (page-1) * pageSize;
        StoreNum storeNum = storeDao.getStoreNum(foodIdx);

        if(pageSizeFromUser > storeNum.getStoreNum())
            throw new BaseException(GET_OUTBOUND_PAGE);

        List<GetStoreRes> storeRes;
        if(order.equals("star")){
            storeRes = storeDao.storeResByStar(foodIdx,page,pageSize);
        }else if(order.equals("review")){
            storeRes = storeDao.storeResByReview(foodIdx,page,pageSize);
        }else{
            storeRes = storeDao.storeResByIdx(foodIdx,page,pageSize);
        }

        return storeRes;
    }

    public List<GetStoreRes> getStorePaging(int foodIdx, String order, int last_data_id, int pageSize) throws BaseException {

        List<GetStoreRes> storeRes;
        storeRes = storeDao.storeResByIdxPaging(foodIdx,last_data_id,pageSize);

        return storeRes;
    }

    public StoreNum getStoreNum(int foodIdx){
        StoreNum storeNum = storeDao.getStoreNum(foodIdx);

        return storeNum;
    }


    public Food getFood(int foodIdx){
        Food food = storeDao.getFood(foodIdx);

        return food;
    }

}
