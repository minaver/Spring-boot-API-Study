package com.umc.hugo.src.menu;

import com.umc.hugo.config.BaseException;
import com.umc.hugo.src.food.Food;
import com.umc.hugo.src.menu.model.GetMenuRes;
import com.umc.hugo.src.store.Store;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.umc.hugo.config.BaseResponseStatus.GET_INVALID_PAGE;
import static com.umc.hugo.config.BaseResponseStatus.GET_OUTBOUND_PAGE;

@Service
public class MenuProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MenuDao menuDao;

    @Autowired
    public MenuProvider(MenuDao menuDao) {
        this.menuDao = menuDao;
    }


    //GET
    public List<GetMenuRes> getMenu(int storeIdx, int page, int pageSize) throws BaseException{

        // 사용자가 입력한 page가 DB page를 초과했는지 확인
        int pageSizeFromUser = (page-1) * pageSize;
        MenuNum menuNum = menuDao.getMenuNum(storeIdx);

        if(pageSizeFromUser >= menuNum.getMenuNum())
            throw new BaseException(GET_OUTBOUND_PAGE);
        //

        List<GetMenuRes> menuRes = menuDao.menuRes(storeIdx,page,pageSize);

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
