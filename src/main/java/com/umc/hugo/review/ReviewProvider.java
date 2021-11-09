package com.umc.hugo.review;

import com.umc.hugo.review.model.GetReviewRes;
import com.umc.hugo.review.model.PostReviewReq;
import com.umc.hugo.review.model.PostReviewRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewProvider {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

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

}
