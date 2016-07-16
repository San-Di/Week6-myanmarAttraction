package xyz.aungpyaephyo.padc.myanmarattractions.data.agents.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.aungpyaephyo.padc.myanmarattractions.data.agents.LoginUserDataAgent;
import xyz.aungpyaephyo.padc.myanmarattractions.data.models.AttractionModel;
import xyz.aungpyaephyo.padc.myanmarattractions.data.models.LoginUserModel;
import xyz.aungpyaephyo.padc.myanmarattractions.data.responses.AttractionListResponse;
import xyz.aungpyaephyo.padc.myanmarattractions.data.responses.LoginUserResponse;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.CommonInstances;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.MyanmarAttractionsConstants;

/**
 * Created by UNiQUE on 7/15/2016.
 */
public class RetrofitLoginUserDataAgent implements LoginUserDataAgent {

    private static RetrofitLoginUserDataAgent objInstance;

    private final LoginUserApi theApi;

    private RetrofitLoginUserDataAgent() {   //Singleton
        final OkHttpClient okHttpClient = new OkHttpClient.Builder() //need a cliet //The reason of no needing a client in OkHttp is that OkHttp client is the same as th Http
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyanmarAttractionsConstants.LOGIN_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(CommonInstances.getGsonInstance()))//responseObject is already bind in its AttractionApi
                .client(okHttpClient)
                .build();

        theApi = retrofit.create(LoginUserApi.class); //depending on Api we created, the retrofit has created the connection
    }

    public static RetrofitLoginUserDataAgent getInstance() {
        if (objInstance == null) {
            objInstance = new RetrofitLoginUserDataAgent();
        }
        return objInstance;
    }

    @Override
    public void loadLoginUser() {
        Call<LoginUserResponse> loadLoginUserCall = theApi.loadLoginUser(MyanmarAttractionsConstants.ACCESS_TOKEN); //till preparing a request
        loadLoginUserCall.enqueue(new Callback<LoginUserResponse>() { //execution is sent to background thread using enqueue //Callbak type is Retrofit type
            @Override
            public void onResponse(Call<LoginUserResponse> call, Response<LoginUserResponse> response) {
                LoginUserResponse loginUserResponse = response.body();
                if (loginUserResponse == null) {
                    LoginUserModel.getInstance().notifyErrorInLoadingAttractions(response.message());
                } else {
                    LoginUserModel.getInstance().notifyAttractionsLoaded(loginUserResponse.getLoginUser());
                }
            }

            @Override
            public void onFailure(Call<LoginUserResponse> call, Throwable throwable) {
                LoginUserModel.getInstance().notifyErrorInLoadingAttractions(throwable.getMessage());
            }
        });
    }
}
