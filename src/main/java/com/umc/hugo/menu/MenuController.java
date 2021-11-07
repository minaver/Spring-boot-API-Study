package com.umc.hugo.menu;

import com.umc.hugo.menu.model.GetMenuRes;
import com.umc.hugo.menu.model.PostMenuReq;
import com.umc.hugo.menu.model.PostMenuRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class MenuController {

    private MenuProvider menuProvider;

    @Autowired
    public MenuController(MenuProvider menuProvider){
        this.menuProvider = menuProvider;
    }

    @GetMapping("/menus")
    public List<GetMenuRes> getMenu(@RequestParam int store){
        List<GetMenuRes> menuRes = menuProvider.getMenu(store);
        return menuRes;
    }

    @ResponseBody
    @PostMapping("/menu")
    public PostMenuRes postMenu(@RequestBody PostMenuReq postMenuReq){
        PostMenuRes postMenuRes = menuProvider.postMenu(postMenuReq);
        return postMenuRes;
    }

}
