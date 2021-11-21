package com.umc.hugo.src.shop;

import com.umc.hugo.config.BaseException;
import com.umc.hugo.config.BaseResponse;
import com.umc.hugo.src.shop.model.PostShopReq;
import com.umc.hugo.src.shop.model.PostShopRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app/shop")
public class ShopController {

    private ShopService shopService;
    private ShopProvider shopProvider;
    private ShopDao shopDao;

    @Autowired
    public ShopController(ShopService shopService, ShopProvider shopProvider, ShopDao shopDao){
        this.shopService = shopService;
        this.shopProvider = shopProvider;
        this.shopDao = shopDao;
    }

    @ResponseBody
    @PostMapping("/add")
    public BaseResponse<PostShopRes> postShop(@RequestBody PostShopReq postShopReq){
        try {
            PostShopRes postShopRes = shopService.postShop(postShopReq);
            return new BaseResponse<>(postShopRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }




}
