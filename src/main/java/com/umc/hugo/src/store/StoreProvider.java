package com.umc.hugo.src.store;

import com.umc.hugo.src.food.Food;
import com.umc.hugo.src.store.model.GetStoreRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StoreProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final StoreDao storeDao;

    @Autowired
    public StoreProvider(StoreDao storeDao){
        this.storeDao = storeDao;
    }

    public List<GetStoreRes> getStore(int foodIdx, String order){
        List<GetStoreRes> storeRes;
        if(order.equals("star")){
            storeRes = storeDao.storeResByStar(foodIdx);
        }else if(order.equals("review")){
            storeRes = storeDao.storeResByReview(foodIdx);
        }else{
            storeRes = storeDao.storeResByIdx(foodIdx);
        }

        return storeRes;
    }

    public Food getFood(int foodIdx){
        Food food = storeDao.getFood(foodIdx);

        return food;
    }

}
