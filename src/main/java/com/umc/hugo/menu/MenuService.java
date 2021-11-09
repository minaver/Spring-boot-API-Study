package com.umc.hugo.menu;

import com.umc.hugo.menu.MenuDao;
import com.umc.hugo.menu.MenuProvider;
import com.umc.hugo.menu.model.PostMenuReq;
import com.umc.hugo.menu.model.PostMenuRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MenuService {

    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log 처리부분: Log를 기록하기 위해 필요한 함수입니다.

    private final MenuDao menuDao;
    private final MenuProvider menuProvider;

    @Autowired
    public MenuService(MenuDao menuDao, MenuProvider menuProvider){
        this.menuDao = menuDao;
        this.menuProvider = menuProvider;
    }

    //POST
    public PostMenuRes postMenu(PostMenuReq postMenuReq){
        int menuIdx= menuDao.addMenu(postMenuReq);
        PostMenuRes postMenuRes = new PostMenuRes(menuIdx);
        return postMenuRes;
    }
}
