package xyz.aungpyaephyo.padc.myanmarattractions.data.responses;

import com.google.gson.annotations.SerializedName;

import xyz.aungpyaephyo.padc.myanmarattractions.data.vos.LoginUserVO;

/**
 * Created by UNiQUE on 7/15/2016.
 */
public class LoginUserResponse {

    @SerializedName("code")
    private int code;

    @SerializedName("message")
    private String message;

    @SerializedName("login_user")
    private LoginUserVO loginUser;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public LoginUserVO getLoginUser() {
        return loginUser;
    }
}
