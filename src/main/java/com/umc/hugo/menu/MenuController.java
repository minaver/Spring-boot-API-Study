package com.umc.hugo.menu;

import com.umc.hugo.config.BaseResponse;
import com.umc.hugo.menu.model.GetMenuRes;
import com.umc.hugo.menu.model.PostMenuReq;
import com.umc.hugo.menu.model.PostMenuRes;
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
    public BaseResponse<List<GetMenuRes>> getMenu(@PathVariable("store") int store ){
        List<GetMenuRes> menuRes = menuProvider.getMenu(store);
        return new BaseResponse<>(menuRes);
    }

    @ResponseBody
    @PostMapping("/add")
    public BaseResponse<PostMenuRes> postMenu(@RequestBody PostMenuReq postMenuReq){
        PostMenuRes postMenuRes = menuService.postMenu(postMenuReq);
        return new BaseResponse<>(postMenuRes);
    }

}
