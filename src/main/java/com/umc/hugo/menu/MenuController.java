package com.umc.hugo.menu;

import com.umc.hugo.config.BaseResponse;
import com.umc.hugo.config.MenuResponse;
import com.umc.hugo.food.Food;
import com.umc.hugo.menu.model.GetMenuRes;
import com.umc.hugo.menu.model.PostMenuReq;
import com.umc.hugo.menu.model.PostMenuRes;
import com.umc.hugo.store.Store;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/app/menus")

public class MenuController {

    private MenuProvider menuProvider;
    private MenuService menuService;

    @Autowired
    public MenuController(MenuProvider menuProvider,MenuService menuService){
        this.menuProvider = menuProvider;
        this.menuService = menuService;
    }

    @GetMapping("/{store}")
    public MenuResponse<List<GetMenuRes>,String> getMenu(@PathVariable("store") int storeIdx ){
        List<GetMenuRes> menuRes = menuProvider.getMenu(storeIdx);

        // For Get Food Name
        Food food = menuProvider.getFood(storeIdx);
        String foodName = food.getName();

        // For Get Store Name
        Store store = menuProvider.getStore(storeIdx);
        String storeName = store.getName();

        return new MenuResponse<>(menuRes,foodName,storeName);
    }

    @ResponseBody
    @PostMapping("/add")
    public BaseResponse<PostMenuRes> postMenu(@RequestBody PostMenuReq postMenuReq){
        PostMenuRes postMenuRes = menuService.postMenu(postMenuReq);
        return new BaseResponse<>(postMenuRes);
    }

}
