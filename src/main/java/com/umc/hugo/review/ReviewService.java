package com.umc.hugo.review;


import com.umc.hugo.review.model.PostReviewReq;
import com.umc.hugo.review.model.PostReviewRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
