package com.umc.hugo.food;


import com.umc.hugo.food.model.PostFoodReq;
import com.umc.hugo.food.model.PostFoodRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FoodService {
    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log 처리부분: Log를 기록하기 위해 필요한 함수입니다.

    private final FoodDao foodDao;
    private final FoodProvider foodProvider;

    @Autowired
    public FoodService(FoodDao foodDao, FoodProvider foodProvider){
        this.foodDao = foodDao;
        this.foodProvider = foodProvider;
    }

    //POST
    public PostFoodRes postFood(PostFoodReq postFoodReq){
        int foodIdx= foodDao.addFood(postFoodReq);
        PostFoodRes postFoodRes = new PostFoodRes(foodIdx);
        return postFoodRes;
    }

}
