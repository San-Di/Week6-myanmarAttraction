package xyz.aungpyaephyo.padc.myanmarattractions.data.agents.retrofit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import xyz.aungpyaephyo.padc.myanmarattractions.data.responses.LoginUserResponse;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.MyanmarAttractionsConstants;

/**
 * Created by UNiQUE on 7/15/2016.
 */
public interface LoginUserApi  {
    @FormUrlEncoded
    @POST(MyanmarAttractionsConstants.API_GET_LOGIN_LIST)
    Call<LoginUserResponse> loadLoginUser(
            @Field(MyanmarAttractionsConstants.PARAM_ACCESS_TOKEN) String param);
}
