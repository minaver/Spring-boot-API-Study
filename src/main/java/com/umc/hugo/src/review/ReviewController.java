package com.umc.hugo.src.review;

import com.umc.hugo.config.*;
import com.umc.hugo.src.review.model.*;
import com.umc.hugo.src.store.Store;
import com.umc.hugo.src.user.User;
import com.umc.hugo.util.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.umc.hugo.config.BaseResponseStatus.*;
import static com.umc.hugo.util.ValidationRegex.isUrl;

@RestController
@RequestMapping("/app/reviews")

public class ReviewController {

    private ReviewProvider reviewProvider;
    private ReviewService reviewService;
    private JwtService jwtService;

    @Autowired
    public ReviewController(ReviewProvider reviewProvider,ReviewService reviewService,JwtService jwtService){
        this.reviewProvider = reviewProvider;
        this.reviewService = reviewService;
        this.jwtService = jwtService;
    }

    // 유저별 Review 결과 출력
    @GetMapping("")
    public ReviewResponse<List<GetReviewRes>,String> getReview(@RequestParam Integer storeIdx, @RequestParam Integer userIdx ){
        List<GetReviewRes> reviewRes = reviewProvider.getReview(storeIdx, userIdx);  // Get Review List

        User user = reviewProvider.getUser(userIdx);    // Get User Info
        String userName = user.getName();

        Store store = reviewProvider.getStore(storeIdx);    // Get Store Info
        String storeName = store.getName();

        return new ReviewResponse<>(reviewRes,userName,storeName);
    }

    // store 별 review 출력 (storeIdx 만 들어올 때)
    @GetMapping("/store")
    public ReviewResponseForStore<List<GetReviewResForStore>,String> getReviewForStore(@RequestParam Integer storeIdx ) {
        List<GetReviewResForStore> reviewResForStore = reviewProvider.getReviewForStore(storeIdx);  // Get Review List

        Store store = reviewProvider.getStore(storeIdx);    // Get Store Info
        String storeName = store.getName();

        return new ReviewResponseForStore<>(reviewResForStore,storeName);
    }

    // user 별 review 출력 (userIdx 만 들어올 때)
    @GetMapping("/user")
    public ReviewResponseForUser<List<GetReviewResForUser>,String> getReviewForUser(@RequestParam Integer userIdx) throws BaseException {
        try {
            //jwt에서 idx 추출.
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (userIdx != userIdxByJwt) {
                return new ReviewResponseForUser<>(INVALID_USER_JWT);
            }

            // 같다면 해당 유저의 리뷰 출력
            List<GetReviewResForUser> reviewResForUser = reviewProvider.getReviewForUser(userIdx);  // Get Review List

            User user = reviewProvider.getUser(userIdx);    // Get User Info
            String userName = user.getName();

            return new ReviewResponseForUser<>(reviewResForUser, userName);
        }catch (BaseException exception){
            return new ReviewResponseForUser<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("/add")
    public BaseResponse<PostReviewRes> postReview(@RequestBody PostReviewReq postReviewReq) throws BaseException {
        try {
            //jwt로 해당 user 정보 검사(review를 post 할 수 있는 권한을 가진 user인지 확인)
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (postReviewReq.getUserIdx() != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            // URL 검사
            if (postReviewReq.getReviewImgUrl() == null) {
                return new BaseResponse<>(POST_EMPTY_URL);
            }
            if (!isUrl(postReviewReq.getReviewImgUrl())) {
                return new BaseResponse<>(POST_INVALID_URL);
            }

            PostReviewRes postReviewRes = reviewService.postReview(postReviewReq);
            return new BaseResponse<>(postReviewRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    // PATCH
    // 1. 리뷰 내용 수정
    @ResponseBody
    @PatchMapping("msg/{reviewIdx}")
    public BaseResponse<String> modifyReviewMsg(@PathVariable("reviewIdx") int reviewIdx, @RequestBody Review review){
        try {
            //jwt로 해당 user 정보 검사(review를 patch 할 수 있는 권한을 가진 user인지 확인)
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (review.getUserIdx() != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            PatchReviewMsgReq patchReviewMsgReq = new PatchReviewMsgReq(reviewIdx, review.getReviewMsg());
            reviewService.modifyReviewMsg(patchReviewMsgReq);

            String result = "리뷰내용이 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 2. 리뷰 Img Url 수정
    @ResponseBody
    @PatchMapping("img/{reviewIdx}")
    public BaseResponse<String> modifyReviewImgUrl(@PathVariable("reviewIdx") int reviewIdx, @RequestBody Review review) throws BaseException {

        //jwt로 해당 user 정보 검사(review를 patch 할 수 있는 권한을 가진 user인지 확인)
        int userIdxByJwt = jwtService.getUserIdx();
        //userIdx와 접근한 유저가 같은지 확인
        if (review.getUserIdx() != userIdxByJwt) {
            return new BaseResponse<>(INVALID_USER_JWT);
        }

        //** [ URL 검사 ]
        if (review.getReviewImgUrl() == null) {
            return new BaseResponse<>(POST_EMPTY_URL);
        }
        //URL 정규표현: 입력받은 URL이 와 같은 형식인지 검사합니다. 형식이 올바르지 않다면 에러 메시지를 보냅니다.
        /*
        http나 https로 시작하는 경우에는 ://가 반드시 붙는다. Optional.
        www. 로 시작하는 경우가 있다. Optional.
        알파벳 혹은 숫자를 포함한 문자열이 반드시 1개 이상 있다.
        이후에 . 이 반드시 하나 포함된다. (.)
        . 를 기점으로 이후에 소문자가 반드시 1개 이상 포함됨
        이후에는 계속해서 영문자, 숫자, 특수문자가 붙을 수 있다.
        */
        if (!isUrl(review.getReviewImgUrl())) {
            return new BaseResponse<>(POST_INVALID_URL);
        }
        //**

        try {
            PatchReviewImgUrlReq patchReviewImgUrlReq = new PatchReviewImgUrlReq(reviewIdx, review.getReviewImgUrl());
            reviewService.modifyReviewImgUrl(patchReviewImgUrlReq);

            String result = "리뷰 이미지가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 3. 리뷰 별점 수정
    @ResponseBody
    @PatchMapping("stars/{reviewIdx}")
    public BaseResponse<String> modifyReviewStar(@PathVariable("reviewIdx") int reviewIdx, @RequestBody Review review)  {

        try {
            //jwt로 해당 user 정보 검사(review를 post 인 수 있는 권한을 가진 user인지 확인)
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (review.getUserIdx() != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            PatchReviewStarReq patchReviewStarReq = new PatchReviewStarReq(reviewIdx, review.getReviewStar());
            reviewService.modifyReviewStarNum(patchReviewStarReq);

            String result = "리뷰 별점이 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 4. 리뷰 상태 수정
    @ResponseBody
    @PatchMapping("status/{reviewIdx}")
    public BaseResponse<String> modifyReviewStatus(@PathVariable("reviewIdx") int reviewIdx, @RequestBody Review review)  {

        try {
            //jwt로 해당 user 정보 검사(review를 post 인 수 있는 권한을 가진 user인지 확인)
            int userIdxByJwt = jwtService.getUserIdx();
            //userIdx와 접근한 유저가 같은지 확인
            if (review.getUserIdx() != userIdxByJwt) {
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            PatchReviewStatusReq patchReviewStatusReq = new PatchReviewStatusReq(reviewIdx, review.getStatus());
            reviewService.modifyReviewStatus(patchReviewStatusReq);

            String result = "음식상태가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
