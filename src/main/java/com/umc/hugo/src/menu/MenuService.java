package com.umc.hugo.src.menu;

import com.umc.hugo.config.BaseException;
import com.umc.hugo.src.menu.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.umc.hugo.config.BaseResponseStatus.*;

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

    //PATCH
    // 1. modify 메뉴 이름
    public void modifyMenu(PatchMenuReq patchMenuReq) throws BaseException {
        try {
            int result = menuDao.modifyMenuName(patchMenuReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_MENUNAME);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 2. modify 메뉴 ImgUrl
    public void modifyMenuImgUrl(PatchMenuImgUrlReq patchMenuImgUrlReq) throws BaseException {
        try {
            int result = menuDao.modifyMenuImgUrl(patchMenuImgUrlReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_MENUIMGURL);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 3. modify 메뉴 status
    public void modifyMenuStatus(PatchMenuStatusReq patchMenuStatusReq) throws BaseException {

        // Body로 들어온 status가 active/deactive/out 중 하나인지 검사
        if(!patchMenuStatusReq.getStatus().equals("active") && !patchMenuStatusReq.getStatus().equals("deactive") && !patchMenuStatusReq.getStatus().equals("out")){
            throw new BaseException(PATCH_INVALID_STATUS);
        }

        try {
            int result = menuDao.modifyMenuStatus(patchMenuStatusReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_MENUIMGURL);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
