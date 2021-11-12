package com.umc.hugo.src.review;


import com.umc.hugo.config.BaseException;
import com.umc.hugo.src.review.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.umc.hugo.config.BaseResponseStatus.*;

@Service
public class ReviewService {
    final Logger logger = LoggerFactory.getLogger(this.getClass()); // Log 처리부분: Log를 기록하기 위해 필요한 함수입니다.

    private final ReviewDao reviewDao;
    private final ReviewProvider reviewProvider;

    @Autowired
    public ReviewService(ReviewDao reviewDao, ReviewProvider reviewProvider){
        this.reviewDao = reviewDao;
        this.reviewProvider = reviewProvider;
    }

    //POST
    public PostReviewRes postReview(PostReviewReq postReviewReq){
        int reviewIdx= reviewDao.addReview(postReviewReq);
        PostReviewRes postReviewRes = new PostReviewRes(reviewIdx);
        return postReviewRes;
    }

    //PATCH
    // 1. modify 리뷰 내용
    public void modifyReviewMsg(PatchReviewMsgReq patchReviewMsgReq) throws BaseException {
        try {
            int result = reviewDao.modifyReviewMsg(patchReviewMsgReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_MENUNAME);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 2. modify 리뷰 ImgUrl
    public void modifyReviewImgUrl(PatchReviewImgUrlReq patchReviewImgUrlReq) throws BaseException {
        try {
            int result = reviewDao.modifyReviewImgUrl(patchReviewImgUrlReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_MENUIMGURL);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 3. modify 리뷰 별점
    public void modifyReviewStarNum(PatchReviewStarReq patchReviewStarReq) throws BaseException {

        try {
            int result = reviewDao.modifyReviewStarNum(patchReviewStarReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_MENUIMGURL);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

    // 4. modify 리뷰 status
    public void modifyReviewStatus(PatchReviewStatusReq patchReviewStatusReq) throws BaseException {

        // Body로 들어온 status가 active/deactive/out 중 하나인지 검사
        if(!patchReviewStatusReq.getStatus().equals("active") && !patchReviewStatusReq.getStatus().equals("deactive") && !patchReviewStatusReq.getStatus().equals("out")){
            throw new BaseException(PATCH_INVALID_STATUS);
        }

        try {
            int result = reviewDao.modifyReviewStatus(patchReviewStatusReq); // 해당 과정이 무사히 수행되면 True(1), 그렇지 않으면 False(0)입니다.
            if (result == 0) { // result값이 0이면 과정이 실패한 것이므로 에러 메서지를 보냅니다.
                throw new BaseException(MODIFY_FAIL_MENUIMGURL);
            }
        } catch (Exception exception) { // DB에 이상이 있는 경우 에러 메시지를 보냅니다.
            throw new BaseException(DATABASE_ERROR);
        }
    }

}
