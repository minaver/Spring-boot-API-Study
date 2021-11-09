package com.umc.hugo.store;

import com.umc.hugo.config.BaseException;
import com.umc.hugo.config.BaseResponse;
import com.umc.hugo.config.FoodResponse;
import com.umc.hugo.food.Food;
import com.umc.hugo.store.model.GetStoreRes;
import com.umc.hugo.store.model.PostStoreReq;
import com.umc.hugo.store.model.PostStoreRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/app/stores")

public class StoreController {

    private StoreProvider storeProvider;
    private StoreService storeService;

    @Autowired
    public StoreController(StoreProvider storeProvider){
        this.storeProvider = storeProvider;
    }

    // 모든 식당 출력하는 GET
    // 출력 방법은 idx, star, review 로 가능하다.
    // + 총 가게 수 몇개인지 출력하는문 함께 추가
    @GetMapping("/{foodIdx}")
    public FoodResponse<List<GetStoreRes>,String> getStore(@PathVariable("foodIdx") int foodIdx, @RequestParam(required = false) String order){
        if(order == null) // if order parameter is null allocate default value(idx)
            order = "idx";

        List<GetStoreRes> storeRes = storeProvider.getStore(foodIdx,order);
        Food food = storeProvider.getFood(foodIdx);
        String foodName = food.getName();

        return new FoodResponse<>(storeRes,foodName);
    }

    @ResponseBody
    @PostMapping("/add")
    public BaseResponse<PostStoreRes> postStore(@RequestBody PostStoreReq postStoreReq){
        PostStoreRes postStoreRes = storeService.postStore(postStoreReq);
        return new BaseResponse<>(postStoreRes);
    }

}
