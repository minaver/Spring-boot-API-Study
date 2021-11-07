package com.umc.hugo.review;

import com.umc.hugo.review.model.GetReviewRes;
import com.umc.hugo.review.model.PostReviewReq;
import com.umc.hugo.review.model.PostReviewRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public class ReviewController {

    private ReviewProvider reviewProvider;

    @Autowired
    public ReviewController(ReviewProvider reviewProvider){
        this.reviewProvider = reviewProvider;
    }

    @GetMapping("/reviews")
    public List<GetReviewRes> getReview(@RequestParam int store){
        List<GetReviewRes> reviewRes = reviewProvider.getReview(store);
        return reviewRes;
    }

    @ResponseBody
    @PostMapping("/review")
    public PostReviewRes postReview(@RequestBody PostReviewReq postReviewReq){
        PostReviewRes postReviewRes = reviewProvider.postReview(postReviewReq);
        return postReviewRes;
    }
}
