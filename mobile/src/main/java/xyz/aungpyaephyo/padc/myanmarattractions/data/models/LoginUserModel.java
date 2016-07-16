package xyz.aungpyaephyo.padc.myanmarattractions.data.models;

import de.greenrobot.event.EventBus;
import xyz.aungpyaephyo.padc.myanmarattractions.data.agents.retrofit.RetrofitLoginUserDataAgent;
import xyz.aungpyaephyo.padc.myanmarattractions.data.vos.LoginUserVO;
import xyz.aungpyaephyo.padc.myanmarattractions.events.DataLoginUser;

/**
 * Created by UNiQUE on 7/15/2016.
 */
public class LoginUserModel {

    public static final String BROADCAST_DATA_LOADED = "BROADCAST_DATA_LOADED";
    private static final int INIT_DATA_AGENT_RETROFIT = 1;

    private static LoginUserModel objInstance;

    private LoginUserVO mLoginUser;

    private RetrofitLoginUserDataAgent dataAgent;

    private LoginUserModel() {
        mLoginUser = new LoginUserVO();
        initDataAgent(INIT_DATA_AGENT_RETROFIT);
        dataAgent.loadLoginUser();
    }

    public static LoginUserModel getInstance() {
        if (objInstance == null) {
            objInstance = new LoginUserModel();
        }
        return objInstance;
    }

    private void initDataAgent(int initType) {
        switch (initType) {
            case INIT_DATA_AGENT_RETROFIT:
                dataAgent = RetrofitLoginUserDataAgent.getInstance();
                break;
        }
    }

    public LoginUserVO getLoginUser() {
        return mLoginUser;
    }

//    public LoginUserVO getUserByEmail(String email) {
//        for (LoginUserVO user : mLoginUser) {
//            if (user.getEmail().equals(email))
//                return user;
//        }
//
//        return null;
//    }

    public void notifyAttractionsLoaded(LoginUserVO loginUser) {
        //Notify that the data is ready - using LocalBroadcast
        mLoginUser = loginUser;

        //keep the data in persistent layer.
//        LoginUserVO.saveAttractions(mLoginUser);

        broadcastLoginUserWithEventBus();
        //broadcastAttractionLoadedWithLocalBroadcastManager();
    }

    public void notifyErrorInLoadingAttractions(String message) {

    }

//    private void broadcastAttractionLoadedWithLocalBroadcastManager() {
//        Intent intent = new Intent(BROADCAST_DATA_LOADED);
//        intent.putExtra("key-for-extra", "extra-in-broadcast");
//        LocalBroadcastManager.getInstance(MyanmarAttractionsApp.getContext()).sendBroadcast(intent);
//    }

    private void broadcastLoginUserWithEventBus() {
        EventBus.getDefault().post(new DataLoginUser.LoginUserDataLoadedEvent("extra-in-broadcast", mLoginUser));
    }

}
