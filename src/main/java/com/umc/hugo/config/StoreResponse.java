package com.umc.hugo.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static com.umc.hugo.config.BaseResponseStatus.SUCCESS;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"isSuccess", "code", "message", "food", "result"})
public class StoreResponse<T,String> {
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String message;
    private final int code;
    private String food;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    public StoreResponse(T result, String food) {
        this.isSuccess = SUCCESS.isSuccess();
        this.message = (String) SUCCESS.getMessage();
        this.code = SUCCESS.getCode();
        this.result = result;
        this.food = food;
    }

    // 요청에 실패한 경우
    public StoreResponse(BaseResponseStatus status) {
        this.isSuccess = status.isSuccess();
        this.message = (String) status.getMessage();
        this.code = status.getCode();
    }
}
