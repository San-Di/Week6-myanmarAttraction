package xyz.aungpyaephyo.padc.myanmarattractions.data.agents.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.aungpyaephyo.padc.myanmarattractions.data.agents.AttractionDataAgent;
import xyz.aungpyaephyo.padc.myanmarattractions.data.models.AttractionModel;
import xyz.aungpyaephyo.padc.myanmarattractions.data.responses.AttractionListResponse;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.CommonInstances;
import xyz.aungpyaephyo.padc.myanmarattractions.utils.MyanmarAttractionsConstants;

/**
 * Created by aung on 7/9/16.
 */
public class RetrofitDataAgent implements AttractionDataAgent {

    private static RetrofitDataAgent objInstance;

    private final AttractionApi theApi;

    private RetrofitDataAgent() {   //Singleton
        final OkHttpClient okHttpClient = new OkHttpClient.Builder() //need a cliet //The reason of no needing a client in OkHttp is that OkHttp client is the same as th Http
                .connectTimeout(15, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyanmarAttractionsConstants.ATTRACTION_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(CommonInstances.getGsonInstance()))//responseObject is already bind in its AttractionApi
                .client(okHttpClient)
                .build();

        theApi = retrofit.create(AttractionApi.class); //depending on Api we created, the retrofit has created the connection
    }

    public static RetrofitDataAgent getInstance() {
        if (objInstance == null) {
            objInstance = new RetrofitDataAgent();
        }
        return objInstance;
    }

    @Override
    public void loadAttractions() {
        Call<AttractionListResponse> loadAttractionCall = theApi.loadAttractions(MyanmarAttractionsConstants.ACCESS_TOKEN); //till preparing a request
        loadAttractionCall.enqueue(new Callback<AttractionListResponse>() { //execution is sent to background thread using enqueue //Callbak type is Retrofit type
            @Override
            public void onResponse(Call<AttractionListResponse> call, Response<AttractionListResponse> response) {
                AttractionListResponse attractionListResponse = response.body();
                if (attractionListResponse == null) {
                    AttractionModel.getInstance().notifyErrorInLoadingAttractions(response.message());
                } else {
                    AttractionModel.getInstance().notifyAttractionsLoaded(attractionListResponse.getAttractionList());
                }
            }

            @Override
            public void onFailure(Call<AttractionListResponse> call, Throwable throwable) {
                AttractionModel.getInstance().notifyErrorInLoadingAttractions(throwable.getMessage());
            }
        });
    }
}
