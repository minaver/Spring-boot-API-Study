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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private UserProvider userProvider;

    @Autowired
    public UserController(UserProvider userProvider) {
        this.userProvider = userProvider;
    }

    //GET
    @GetMapping("/users")
    public List<GetUserRes> getUser(){
        List<GetUserRes> userRes = userProvider.getUser();
        return userRes;
    }

    @GetMapping("/foods")
    public List<GetFoodRes> getFood(){
        List<GetFoodRes> foodRes = userProvider.getFood();
        return foodRes;
    }

    @GetMapping("/stores")
    public List<GetStoreRes> getStore(@RequestParam String order){
        List<GetStoreRes> storeRes = userProvider.getStore(order);
        return storeRes;
    }

    @GetMapping("/menus")
    public List<GetMenuRes> getMenu(@RequestParam int store){
        List<GetMenuRes> menuRes = userProvider.getMenu(store);
        return menuRes;
    }

    @GetMapping("/reviews")
    public List<GetReviewRes> getReview(@RequestParam int store){
        List<GetReviewRes> reviewRes = userProvider.getReview(store);
        return reviewRes;
    }

    //POST
    @ResponseBody
    @PostMapping("/user")
    public PostUserRes postUser(@RequestBody PostUserReq postUserReq){
        PostUserRes postUserRes = userProvider.postUser(postUserReq);
        return postUserRes;
    }

    @ResponseBody
    @PostMapping("/food")
    public PostFoodRes postFood(@RequestBody PostFoodReq postFoodReq){
        PostFoodRes postFoodRes = userProvider.postFood(postFoodReq);
        return postFoodRes;
    }

    @ResponseBody
    @PostMapping("/store")
    public PostStoreRes postStore(@RequestBody PostStoreReq postStoreReq){
        PostStoreRes postStoreRes = userProvider.postStore(postStoreReq);
        return postStoreRes;
    }

    @ResponseBody
    @PostMapping("/menu")
    public PostMenuRes postMenu(@RequestBody PostMenuReq postMenuReq){
        PostMenuRes postMenuRes = userProvider.postMenu(postMenuReq);
        return postMenuRes;
    }

    @ResponseBody
    @PostMapping("/review")
    public PostReviewRes postReview(@RequestBody PostReviewReq postReviewReq){
        PostReviewRes postReviewRes = userProvider.postReview(postReviewReq);
        return postReviewRes;
    }

}

