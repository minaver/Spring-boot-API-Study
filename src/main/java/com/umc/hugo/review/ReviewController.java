package com.umc.hugo.review;

import com.umc.hugo.config.BaseResponse;
import com.umc.hugo.review.model.GetReviewRes;
import com.umc.hugo.review.model.PostReviewReq;
import com.umc.hugo.review.model.PostReviewRes;
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

    @GetMapping("/{store}")
    public BaseResponse<List<GetReviewRes>> getReview(@PathVariable("store") int store){
        List<GetReviewRes> reviewRes = reviewProvider.getReview(store);
        return new BaseResponse<>(reviewRes);
    }

    @ResponseBody
    @PostMapping("/add")
    public BaseResponse<PostReviewRes> postReview(@RequestBody PostReviewReq postReviewReq){
        PostReviewRes postReviewRes = reviewService.postReview(postReviewReq);
        return new BaseResponse<>(postReviewRes);
    }
}
