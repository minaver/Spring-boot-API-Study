package com.umc.hugo.food;

import com.umc.hugo.config.BaseResponse;
import com.umc.hugo.food.FoodProvider;
import com.umc.hugo.food.model.GetFoodRes;
import com.umc.hugo.food.model.PostFoodReq;
import com.umc.hugo.food.model.PostFoodRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/foods")

public class FoodController {

    private FoodProvider foodProvider;
    private FoodService foodService;

    @Autowired
    public FoodController(FoodProvider foodProvider,FoodService foodService){
        this.foodProvider = foodProvider;
        this.foodService = foodService;
    }

    // GET
    // Get ALL Types of Food
    @GetMapping("")
    public BaseResponse<List<GetFoodRes>> getFood(){
        List<GetFoodRes> foodRes = foodProvider.getFood();
        System.out.println("food");
        return new BaseResponse<>(foodRes);
    }

    // POST
    // Post new Type of Food
    @ResponseBody
    @PostMapping("/food")
    public BaseResponse<PostFoodRes> postFood(@RequestBody PostFoodReq postFoodReq){
        PostFoodRes postFoodRes = foodService.postFood(postFoodReq);
        return new BaseResponse<>(postFoodRes);
    }
}
