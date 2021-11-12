package com.umc.hugo.src.menu;

import com.umc.hugo.src.food.Food;
import com.umc.hugo.src.menu.model.GetMenuRes;
import com.umc.hugo.src.store.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

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

    //Inner Function
    //Get Store Info
    public Store getStore(int storeIdx){
        Store store = menuDao.getStore(storeIdx);

        return store;
    }

    //Get Food Info
    public Food getFood(int storeIdx){
        Food food = menuDao.getFood(storeIdx);

        return food;
    }

}
