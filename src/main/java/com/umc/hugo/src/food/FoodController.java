package com.umc.hugo.src.food;

import com.umc.hugo.config.BaseException;
import com.umc.hugo.config.BaseResponse;
import com.umc.hugo.src.food.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.umc.hugo.config.BaseResponseStatus.*;
import static com.umc.hugo.util.ValidationRegex.isUrl;

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
    @ResponseBody
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

        if (postFoodReq.getFoodImgUrl() == null) {
            return new BaseResponse<>(POST_EMPTY_URL);
        }
        //URL 정규표현: 입력받은 URL이 와 같은 형식인지 검사합니다. 형식이 올바르지 않다면 에러 메시지를 보냅니다.
        /*
        http나 https로 시작하는 경우에는 ://가 반드시 붙는다. Optional.
        www. 로 시작하는 경우가 있다. Optional.
        알파벳 혹은 숫자를 포함한 문자열이 반드시 1개 이상 있다.
        이후에 . 이 반드시 하나 포함된다. (.)
        . 를 기점으로 이후에 소문자가 반드시 1개 이상 포함됨
        이후에는 계속해서 영문자, 숫자, 특수문자가 붙을 수 있다.
        */
        if (!isUrl(postFoodReq.getFoodImgUrl())) {
            return new BaseResponse<>(POST_INVALID_URL);
        }

        PostFoodRes postFoodRes = foodService.postFood(postFoodReq);
        return new BaseResponse<>(postFoodRes);
    }

    // PATCH
    // 1. 음식 종류 이름 수정
    @ResponseBody
    @PatchMapping("name/{foodIdx}")
    public BaseResponse<String> modifyFoodName(@PathVariable("foodIdx") int foodIdx, @RequestBody Food food){

        // 사이즈 검사
        if(food.getName().length() > 30){
            return new BaseResponse<>(PATCH_OVER_SIZE);
        }

        if(food.getName() == null){
            return new BaseResponse<>(PATCH_EMPTY_BODY);
        }

        try {
            PatchFoodReq patchFoodReq = new PatchFoodReq(foodIdx, food.getName());
            foodService.modifyFood(patchFoodReq);

            String result = "음식이름이 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    // 2. 음식 Img Url 수정
    @ResponseBody
    @PatchMapping("img/{foodIdx}")
    public BaseResponse<String> modifyFoodImgUrl(@PathVariable("foodIdx") int foodIdx, @RequestBody Food food){

        //** [ URL 검사 ]
        if (food.getFoodImgUrl() == null) {
            return new BaseResponse<>(POST_EMPTY_URL);
        }
        //URL 정규표현: 입력받은 URL이 와 같은 형식인지 검사합니다. 형식이 올바르지 않다면 에러 메시지를 보냅니다.
        /*
        http나 https로 시작하는 경우에는 ://가 반드시 붙는다. Optional.
        www. 로 시작하는 경우가 있다. Optional.
        알파벳 혹은 숫자를 포함한 문자열이 반드시 1개 이상 있다.
        이후에 . 이 반드시 하나 포함된다. (.)
        . 를 기점으로 이후에 소문자가 반드시 1개 이상 포함됨
        이후에는 계속해서 영문자, 숫자, 특수문자가 붙을 수 있다.
        */
        if (!isUrl(food.getFoodImgUrl())) {
            return new BaseResponse<>(POST_INVALID_URL);
        }
        //**

        try {
            PatchFoodImgUrlReq patchFoodImgUrlReq = new PatchFoodImgUrlReq(foodIdx, food.getFoodImgUrl());
            foodService.modifyFoodImgUrl(patchFoodImgUrlReq);

            String result = "음식 이미지가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    // 3. 음식 상태 수정
    @ResponseBody
    @PatchMapping("status/{foodIdx}")
    public BaseResponse<String> modifyFoodStatus(@PathVariable("foodIdx") int foodIdx, @RequestBody Food food)  {

        if(food.getStatus() == null){
            return new BaseResponse<>(PATCH_EMPTY_BODY);
        }

        // Body로 들어온 status가 active/deactive/out 중 하나인지 검사
        if(!food.getStatus().equals("active") && !food.getStatus().equals("deactive") && !food.getStatus().equals("out")){
            return new BaseResponse<>(PATCH_INVALID_STATUS);
        }

        try {
            PatchFoodStatusReq patchFoodStatusReq = new PatchFoodStatusReq(foodIdx, food.getStatus());
            foodService.modifyFoodStatus(patchFoodStatusReq);

            String result = "음식상태가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


}
