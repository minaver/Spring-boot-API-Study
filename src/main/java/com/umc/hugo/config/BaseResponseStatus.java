package com.umc.hugo.config;


import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),


    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),

    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),

    // [POST]
    POST_EMPTY_URL(false, 2019, "URL을 입력해주세요."),
    POST_INVALID_URL(false, 2020, "URL 형식을 확인해주세요."),

    // [PATCH]
    PATCH_EMPTY_BODY(false, 2101, "Body 정보가 비어있습니다."),
    PATCH_OVER_SIZE(false, 2101, "Body 정보가 비어있습니다."),

    // [PATCH] /pw
    PATCH_NOW_PASSWORD_WRONG(false, 2111, "현재 비밀번호가 잘못되었습니다."),

    // [PATCH] /menus/status
    PATCH_INVALID_STATUS(false,2121,"지원하지 않는 STATUS 형식입니다."),

    // [GET] /store
    GET_INVALID_ORDER(false,2221,"지원하지 않는 ORDER 형식입니다."),
    GET_INVALID_PAGE(false,2222,"PAGE 정보가 잘못되었습니다."),
    GET_OUTBOUND_PAGE(false,2223,"PAGE 범위를 벗어났습니다."),
    GET_ALL_PAGE(false,2223,"더이상 출력한 PAGE 가 없습니다."),

    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // [POST] /users
    DUPLICATED_EMAIL(false, 3013, "중복된 이메일입니다."),
    FAILED_TO_LOGIN(false,3014,"없는 아이디거나 비밀번호가 틀렸습니다."),



    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    //[PATCH] /users/{userIdx}
    MODIFY_FAIL_USERNAME(false,4014,"유저네임 수정 실패"),

    //[PATCH] /food/{foodIdx}
    MODIFY_FAIL_FOODNAME(false,4015,"음식이름수정 실패"),
    MODIFY_FAIL_FOODIMGURL(false,4016,"음식ImgUrl 수정 실패"),

    //[PATCH] /menu/{menuIdx}
    MODIFY_FAIL_MENUNAME(false,4015,"메뉴이름수정 실패"),
    MODIFY_FAIL_MENUIMGURL(false,4016,"메뉴ImgUrl 수정 실패"),

    //[PATCH] /store/{storeIdx}
    MODIFY_FAIL_STORENAME(false,4017,"식당이름수정 실패"),
    MODIFY_FAIL_STOREIMGURL(false,4018,"식당ImgUrl 수정 실패"),

    //[POST] /shop
    INCREASE_FAIL_STORE_ORDER_NUM(false,4019,"식당 주문 수 증가 실패"),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다.");


    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }

}
