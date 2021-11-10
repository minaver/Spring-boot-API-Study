package com.umc.hugo.review;

import com.umc.hugo.config.BaseResponse;
import com.umc.hugo.config.ReviewResponse;
import com.umc.hugo.config.ReviewResponseForStore;
import com.umc.hugo.config.ReviewResponseForUser;
import com.umc.hugo.review.model.*;
import com.umc.hugo.store.Store;
import com.umc.hugo.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/app/reviews")

public class ReviewController {

    private ReviewProvider reviewProvider;
    private ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewProvider reviewProvider,ReviewService reviewService){
        this.reviewProvider = reviewProvider;
        this.reviewService = reviewService;
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
    public ReviewResponseForUser<List<GetReviewResForUser>,String> getReviewForUser(@RequestParam Integer userIdx ) {
        List<GetReviewResForUser> reviewResForUser = reviewProvider.getReviewForUser(userIdx);  // Get Review List

        User user = reviewProvider.getUser(userIdx);    // Get User Info
        String userName = user.getName();

        return new ReviewResponseForUser<>(reviewResForUser,userName);
    }

    @ResponseBody
    @PostMapping("/add")
    public BaseResponse<PostReviewRes> postReview(@RequestBody PostReviewReq postReviewReq){
        PostReviewRes postReviewRes = reviewService.postReview(postReviewReq);
        return new BaseResponse<>(postReviewRes);
    }
}
