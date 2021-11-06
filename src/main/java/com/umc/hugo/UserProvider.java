package com.umc.hugo;

import com.umc.hugo.model.Food.GetFoodRes;
import com.umc.hugo.model.Food.PostFoodReq;
import com.umc.hugo.model.Food.PostFoodRes;
import com.umc.hugo.model.Store.GetStoreRes;
import com.umc.hugo.model.Store.PostStoreReq;
import com.umc.hugo.model.Store.PostStoreRes;
import com.umc.hugo.model.User.GetUserRes;
import com.umc.hugo.model.User.PostUserReq;
import com.umc.hugo.model.User.PostUserRes;
import com.umc.hugo.model.menu.GetMenuRes;
import com.umc.hugo.model.menu.PostMenuReq;
import com.umc.hugo.model.menu.PostMenuRes;
import com.umc.hugo.model.review.GetReviewRes;
import com.umc.hugo.model.review.PostReviewReq;
import com.umc.hugo.model.review.PostReviewRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProvider {

    private final UserDao userDao;

    @Autowired
    public UserProvider(UserDao userDao) {
        this.userDao = userDao;
    }

    //GET
    public List<GetUserRes> getUser(){
        List<GetUserRes> userRes = userDao.userRes();

        return userRes;
    }

    public List<GetFoodRes> getFood(){
        List<GetFoodRes> foodRes = userDao.foodRes();

        return foodRes;
    }

    public List<GetStoreRes> getStore(String order){
        List<GetStoreRes> storeRes;
        if(order.equals("star")){
            storeRes = userDao.storeResByStar();
        }else if(order.equals("review")){
            storeRes = userDao.storeResByReview();
        }else{
            storeRes = userDao.storeResByIdx();
        }

        return storeRes;
    }

    public List<GetMenuRes> getMenu(int store){
        List<GetMenuRes> menuRes = userDao.menuRes(store);

        return menuRes;
    }

    public List<GetReviewRes> getReview(int store){
        List<GetReviewRes> reviewRes = userDao.reviewRes(store);

        return reviewRes;
    }

    // POST
    public PostUserRes postUser(PostUserReq postUserReq){
        int userIdx= userDao.addUser(postUserReq);
        PostUserRes postUserRes = new PostUserRes(userIdx);
        return postUserRes;
    }

    public PostFoodRes postFood(PostFoodReq postFoodReq){
        int foodIdx= userDao.addFood(postFoodReq);
        PostFoodRes postFoodRes = new PostFoodRes(foodIdx);
        return postFoodRes;
    }

    public PostStoreRes postStore(PostStoreReq postStoreReq){
        int storeIdx= userDao.addStore(postStoreReq);
        PostStoreRes postStoreRes = new PostStoreRes(storeIdx);
        return postStoreRes;
    }

    public PostMenuRes postMenu(PostMenuReq postMenuReq){
        int menuIdx= userDao.addMenu(postMenuReq);
        PostMenuRes postMenuRes = new PostMenuRes(menuIdx);
        return postMenuRes;
    }

    public PostReviewRes postReview(PostReviewReq postReviewReq){
        int reviewIdx= userDao.addReview(postReviewReq);
        PostReviewRes postReviewRes = new PostReviewRes(reviewIdx);
        return postReviewRes;
    }

}
