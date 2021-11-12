package com.umc.hugo.src.food;


import com.umc.hugo.config.BaseException;
import com.umc.hugo.src.food.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.umc.hugo.config.BaseResponseStatus.*;

@Service
public class FoodService {
    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log 처리부분: Log를 기록하기 위해 필요한 함수입니다.

    private final FoodDao foodDao;
    private final FoodProvider foodProvider;

    @Autowired
    public FoodService(FoodDao foodDao, FoodProvider foodProvider){
        this.foodDao = foodDao;
        this.foodProvider = foodProvider;
    }

    //POST
    public PostFoodRes postFood(PostFoodReq postFoodReq){
        int foodIdx= foodDao.addFood(postFoodReq);
        PostFoodRes postFoodRes = new PostFoodRes(foodIdx);
        return postFoodRes;
    }

    //PATCH
    // 1. modify 음식 이름
    public void modifyFood(PatchFoodReq patchFoodReq) throws BaseException {
        try {
            int result = foodDao.modifyFoodName(patchFoodReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_FOODNAME);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 2. modify 음식 ImgUrl
    public void modifyFoodImgUrl(PatchFoodImgUrlReq patchFoodImgUrlReq) throws BaseException {
        try {
            int result = foodDao.modifyFoodImgUrl(patchFoodImgUrlReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_FOODIMGURL);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 3. modify Food status
    public void modifyFoodStatus(PatchFoodStatusReq patchFoodStatusReq) throws BaseException {

        System.out.println(patchFoodStatusReq.getStatus());

        try {
            int result = foodDao.modifyFoodStatus(patchFoodStatusReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_MENUIMGURL);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
