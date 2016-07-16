package xyz.aungpyaephyo.padc.myanmarattractions.events;

import xyz.aungpyaephyo.padc.myanmarattractions.data.vos.LoginUserVO;

/**
 * Created by UNiQUE on 7/15/2016.
 */
public class DataLoginUser {

    public static class LoginUserDataLoadedEvent {
        private String extraMessage;
        private LoginUserVO loginUser;

        public LoginUserVO getLoginUser() {
            return loginUser;
        }

        public LoginUserDataLoadedEvent(String extraMessage, LoginUserVO loginUser) {
            this.extraMessage = extraMessage;
            this.loginUser = loginUser;
        }

        public String getExtraMessage() {
            return extraMessage;
        }
    }
}
