package com.umc.hugo.src.food;

import com.umc.hugo.src.food.model.GetFoodRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final FoodDao foodDao;

    @Autowired
    public FoodProvider(FoodDao foodDao)
    {
        this.foodDao = foodDao;
    }

    //GET
    public List<GetFoodRes> getFood(){
        List<GetFoodRes> foodRes = foodDao.foodRes();

        return foodRes;
    }


}
