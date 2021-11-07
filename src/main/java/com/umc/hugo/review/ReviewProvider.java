package com.umc.hugo.review;

import com.umc.hugo.review.model.GetReviewRes;
import com.umc.hugo.review.model.PostReviewReq;
import com.umc.hugo.review.model.PostReviewRes;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class ReviewProvider {

    private final ReviewDao reviewDao;

    @Autowired
    public ReviewProvider(ReviewDao reviewDao){
        this.reviewDao = reviewDao;
    }

    //GET
    public List<GetReviewRes> getReview(int store){
        List<GetReviewRes> reviewRes = reviewDao.reviewRes(store);

        return reviewRes;
    }

    //POST
    public PostReviewRes postReview(PostReviewReq postReviewReq){
        int reviewIdx= reviewDao.addReview(postReviewReq);
        PostReviewRes postReviewRes = new PostReviewRes(reviewIdx);
        return postReviewRes;
    }
}
