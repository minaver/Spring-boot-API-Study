package com.umc.hugo.src.menu;

import com.umc.hugo.config.BaseException;
import com.umc.hugo.config.BaseResponse;
import com.umc.hugo.config.MenuResponse;
import com.umc.hugo.src.food.Food;
import com.umc.hugo.src.menu.model.*;
import com.umc.hugo.src.store.Store;
import com.umc.hugo.util.JwtServiceOwner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.umc.hugo.config.BaseResponseStatus.*;
import static com.umc.hugo.util.ValidationRegex.isUrl;

@RestController
@RequestMapping("/app/menus")

public class MenuController {

    private MenuProvider menuProvider;
    private MenuService menuService;
    private JwtServiceOwner jwtServiceOwner;

    @Autowired
    public MenuController(MenuProvider menuProvider,MenuService menuService,JwtServiceOwner jwtServiceOwner){
        this.menuProvider = menuProvider;
        this.menuService = menuService;
        this.jwtServiceOwner = jwtServiceOwner;
    }

    @GetMapping("/{store}")
    public MenuResponse<List<GetMenuRes>,String> getMenu(@PathVariable("store") int storeIdx,
                                                         @RequestParam int page, @RequestParam int pageSize ){
        try {
            // 입력된 page가 1 이상인지 pageSize가 1이상인지 확인 Validation
            if (page < 1 || pageSize < 1)
                return new MenuResponse<>(GET_INVALID_PAGE);

            List<GetMenuRes> menuRes = menuProvider.getMenu(storeIdx, page, pageSize);

            // For Get Food Name
            Food food = menuProvider.getFood(storeIdx);
            String foodName = food.getName();

            // For Get Store Name
            Store store = menuProvider.getStore(storeIdx);
            String storeName = store.getName();

            return new MenuResponse<>(menuRes, foodName, storeName);
        } catch (BaseException exception) {
            return new MenuResponse<>((exception.getStatus()));
        }
    }

    @ResponseBody
    @PostMapping("/add")
    public BaseResponse<PostMenuRes> postMenu(@RequestBody PostMenuReq postMenuReq){

        if (postMenuReq.getMenuImgUrl() == null) {
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
        if (!isUrl(postMenuReq.getMenuImgUrl())) {
            return new BaseResponse<>(POST_INVALID_URL);
        }

        PostMenuRes postMenuRes = menuService.postMenu(postMenuReq);
        return new BaseResponse<>(postMenuRes);
    }

    // PATCH
    // 1. 메뉴 이미지 이름 수정
    @ResponseBody
    @PatchMapping("name/{menuIdx}")
    public BaseResponse<String> modifyMenuName(@PathVariable("menuIdx") int menuIdx, @RequestBody Menu menu){
        try {
            // jwt로 valid한 owner가 접근하는지 확인
            // jwt에서 owneridx 추출.
            // 수정하고자 하는 Store의 ownerIdx 값을 가져온다.
            Store targetStore = menuService.getStoreByMenuIdx(menuIdx);
            int ownerIdx = targetStore.getOwnerIdx();

            int ownerIdxByJwt = jwtServiceOwner.getOwnerIdx();
            //ownerIdx와 접근한 owner가 같은지 확인
            if( ownerIdx != ownerIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            PatchMenuReq patchMenuReq = new PatchMenuReq(menuIdx, menu.getName());
            menuService.modifyMenu(patchMenuReq);

            String result = "음식이름이 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 2. 메뉴 Img Url 수정
    @ResponseBody
    @PatchMapping("img/{menuIdx}")
    public BaseResponse<String> modifyMenuImgUrl(@PathVariable("menuIdx") int menuIdx, @RequestBody Menu menu){

        //** [ URL 검사 ]
        if (menu.getMenuImgUrl() == null) {
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
        if (!isUrl(menu.getMenuImgUrl())) {
            return new BaseResponse<>(POST_INVALID_URL);
        }
        //**

        try {
            // jwt로 valid한 owner가 접근하는지 확인
            // jwt에서 owneridx 추출.
            // 수정하고자 하는 Store의 ownerIdx 값을 가져온다.
            Store targetStore = menuService.getStoreByMenuIdx(menuIdx);
            int ownerIdx = targetStore.getOwnerIdx();

            int ownerIdxByJwt = jwtServiceOwner.getOwnerIdx();
            //ownerIdx와 접근한 owner가 같은지 확인
            if( ownerIdx != ownerIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            PatchMenuImgUrlReq patchMenuImgUrlReq = new PatchMenuImgUrlReq(menuIdx, menu.getMenuImgUrl());
            menuService.modifyMenuImgUrl(patchMenuImgUrlReq);

            String result = "음식 이미지가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    // 3. 메뉴 상태 수정
    @ResponseBody
    @PatchMapping("status/{menuIdx}")
    public BaseResponse<String> modifyMenuStatus(@PathVariable("menuIdx") int menuIdx, @RequestBody Menu menu)  {

        try {
            // jwt로 valid한 owner가 접근하는지 확인
            // jwt에서 owneridx 추출.
            // 수정하고자 하는 Store의 ownerIdx 값을 가져온다.
            Store targetStore = menuService.getStoreByMenuIdx(menuIdx);
            int ownerIdx = targetStore.getOwnerIdx();

            int ownerIdxByJwt = jwtServiceOwner.getOwnerIdx();
            //ownerIdx와 접근한 owner가 같은지 확인
            if( ownerIdx != ownerIdxByJwt){
                return new BaseResponse<>(INVALID_USER_JWT);
            }

            PatchMenuStatusReq patchMenuStatusReq = new PatchMenuStatusReq(menuIdx, menu.getStatus());
            menuService.modifyMenuStatus(patchMenuStatusReq);

            String result = "음식상태가 수정되었습니다.";
            return new BaseResponse<>(result);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

}
