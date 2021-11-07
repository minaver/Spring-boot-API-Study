package com.umc.hugo.menu;

import com.umc.hugo.menu.model.GetMenuRes;
import com.umc.hugo.menu.model.PostMenuReq;
import com.umc.hugo.menu.model.PostMenuRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


public class MenuProvider {

    private final MenuDao menuDao;

    @Autowired
    public MenuProvider(MenuDao menuDao) {
        this.menuDao = menuDao;
    }

    //GET
    public List<GetMenuRes> getMenu(int store){
        List<GetMenuRes> menuRes = menuDao.menuRes(store);

        return menuRes;
    }

    //POST
    public PostMenuRes postMenu(PostMenuReq postMenuReq){
        int menuIdx= menuDao.addMenu(postMenuReq);
        PostMenuRes postMenuRes = new PostMenuRes(menuIdx);
        return postMenuRes;
    }
}
