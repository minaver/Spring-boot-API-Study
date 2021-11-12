package com.umc.hugo.src.review;

import com.umc.hugo.src.review.model.GetReviewRes;
import com.umc.hugo.src.review.model.GetReviewResForStore;
import com.umc.hugo.src.review.model.GetReviewResForUser;
import com.umc.hugo.src.store.Store;
import com.umc.hugo.src.user.User;
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
    public List<GetReviewRes> getReview(int storeIdx, int userIdx){
        List<GetReviewRes> reviewRes = reviewDao.reviewRes(storeIdx,userIdx);

        return reviewRes;
    }
    public List<GetReviewResForUser> getReviewForUser(int userIdx){
        List<GetReviewResForUser> reviewRes = reviewDao.reviewResForUser(userIdx);

        return reviewRes;
    }
    public List<GetReviewResForStore> getReviewForStore(int storeIdx){
        List<GetReviewResForStore> reviewRes = reviewDao.reviewResForStore(storeIdx);

        return reviewRes;
    }

    // Inner function
    // Get User Info
    public User getUser(int userIdx){
        User user = reviewDao.getUser(userIdx);

        return user;
    }
    // Get Store Info
    public Store getStore(int storeIdx){
        Store store = reviewDao.getStore(storeIdx);

        return store;
    }
}
