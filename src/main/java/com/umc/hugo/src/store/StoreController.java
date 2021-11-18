package com.umc.hugo.src.store;

import com.umc.hugo.config.BaseException;
import com.umc.hugo.config.BaseResponse;
import com.umc.hugo.config.StoreResponse;
import com.umc.hugo.src.food.Food;
import com.umc.hugo.src.store.model.*;
import com.umc.hugo.util.JwtService;
import com.umc.hugo.util.JwtServiceOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.umc.hugo.config.BaseResponseStatus.*;
import static com.umc.hugo.util.ValidationRegex.isUrl;

@RestController
@RequestMapping("/app/stores")

public class StoreController {

    private StoreProvider storeProvider;
    private StoreService storeService;
    private StoreDao storeDao;
    private JwtService jwtService;
    private JwtServiceOwner jwtServiceOwner;

    @Autowired
    public StoreController(StoreProvider storeProvider,StoreService storeService,StoreDao storeDao,JwtService jwtService,JwtServiceOwner jwtServiceOwner){
        this.storeProvider = storeProvider;
        this.storeService = storeService;
        this.storeDao = storeDao;
        this.jwtService = jwtService;
        this.jwtServiceOwner = jwtServiceOwner;
    }

    // 모든 식당 출력하는 GET
    // 출력 방법은 idx, star, review 로 가능하다.
    // + 총 가게 수 몇개인지 출력하는문 함께 추가
    @GetMapping("/{foodIdx}")
    public StoreResponse<List<GetStoreRes>,String> getStore(@PathVariable("foodIdx") int foodIdx, @RequestParam(required = false) String order){
        // if order parameter is null allocate default value(idx)
        if(order == null)
            order = "idx";
        // order 변수가 idx, star, review 중에 있는지 확인 Validation
        if(!order.equals("idx") && !order.equals("star") && !order.equals("review")){
            return new StoreResponse<>(GET_INVALID_ORDER);
        }

        List<GetStoreRes> storeRes = storeProvider.getStore(foodIdx,order);
        Food food = storeProvider.getFood(foodIdx);
        String foodName = food.getName();

        return new StoreResponse<>(storeRes,foodName);
    }

    @ResponseBody
    @PostMapping("/add")
    public BaseResponse<PostStoreRes> postStore(@RequestBody PostStoreReq postStoreReq) throws BaseException {

        if (postStoreReq.getStoreImgUrl() == null) {
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
        if (!isUrl(postStoreReq.getStoreImgUrl())) {
            return new BaseResponse<>(POST_INVALID_URL);
        }

        PostStoreRes postStoreRes = storeService.postStore(postStoreReq);
        return new BaseResponse<>(postStoreRes);
    }

    // PATCH
    // 1. 식당 이미지 이름 수정
    @ResponseBody
    @PatchMapping("name/{storeIdx}")
    public BaseResponse<String> modifyStoreName(@PathVariable("storeIdx") int storeIdx, @RequestBody Store store){
        try {
            // jwt로 valid한 owner가 접근하는지 확인
            // jwt에서 owneridx 추출.

            // 수정하고자 하는 Store의 ownerIdx 값을 가져온다.
            Store targetStore = storeDao.getStoreBystoreIdx(storeIdx);
            int ownerIdx = targetStore.getOwnerIdx();

            int ownerIdxByJwt = jwtServiceOwner.getOwnerIdx();
            //ownerIdx와 접근한 owner가 같은지 확인
            if( ownerIdx != ownerIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            PatchStoreReq patchStoreReq = new PatchStoreReq(storeIdx, store.getName());
            storeService.modifyStore(patchStoreReq);

            String result = "식당이름이 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    // 2. 식당 Img Url 수정
    @ResponseBody
    @PatchMapping("img/{storeIdx}")
    public BaseResponse<String> modifyStoreImgUrl(@PathVariable("storeIdx") int storeIdx, @RequestBody Store store){

        //** [ URL 검사 ]
        if (store.getStoreImgUrl() == null) {
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
        if (!isUrl(store.getStoreImgUrl())) {
            return new BaseResponse<>(POST_INVALID_URL);
        }
        //**

        try {

            // jwt로 valid한 owner가 접근하는지 확인
            // jwt에서 owneridx 추출.

            // 수정하고자 하는 Store의 ownerIdx 값을 가져온다.
            Store targetStore = storeDao.getStoreBystoreIdx(storeIdx);
            int ownerIdx = targetStore.getOwnerIdx();

            int ownerIdxByJwt = jwtServiceOwner.getOwnerIdx();
            //ownerIdx와 접근한 owner가 같은지 확인
            if( ownerIdx != ownerIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            PatchStoreImgUrlReq patchStoreImgUrlReq = new PatchStoreImgUrlReq(storeIdx, store.getStoreImgUrl());
            storeService.modifyStoreImgUrl(patchStoreImgUrlReq);

            String result = "식당 이미지가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    // 3. 메뉴 상태 수정
    @ResponseBody
    @PatchMapping("status/{storeIdx}")
    public BaseResponse<String> modifyStoreStatus(@PathVariable("storeIdx") int storeIdx, @RequestBody Store store)  {

        try {

            // jwt로 valid한 owner가 접근하는지 확인
            // jwt에서 owneridx 추출.

            // 수정하고자 하는 Store의 ownerIdx 값을 가져온다.
            Store targetStore = storeDao.getStoreBystoreIdx(storeIdx);
            int ownerIdx = targetStore.getOwnerIdx();

            int ownerIdxByJwt = jwtServiceOwner.getOwnerIdx();
            //ownerIdx와 접근한 owner가 같은지 확인
            if( ownerIdx != ownerIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            PatchStoreStatusReq patchStoreStatusReq = new PatchStoreStatusReq(storeIdx, store.getStatus());
            storeService.modifyStoreStatus(patchStoreStatusReq);

            String result = "식당상태가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
