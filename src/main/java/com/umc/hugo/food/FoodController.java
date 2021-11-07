package com.umc.hugo.food;

import com.umc.hugo.food.model.GetFoodRes;
import com.umc.hugo.food.model.PostFoodReq;
import com.umc.hugo.food.model.PostFoodRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

public class FoodController {

    private FoodProvider foodProvider;

    @Autowired
    public FoodController(FoodProvider foodProvider){
        this.foodProvider = foodProvider;
    }

    //GET
    @GetMapping("/foods")
    public List<GetFoodRes> getFood(){
        List<GetFoodRes> foodRes = foodProvider.getFood();
        System.out.println("food");
        return foodRes;
    }

    //POST
    @ResponseBody
    @PostMapping("/food")
    public PostFoodRes postFood(@RequestBody PostFoodReq postFoodReq){
        PostFoodRes postFoodRes = foodProvider.postFood(postFoodReq);
        return postFoodRes;
    }
}
