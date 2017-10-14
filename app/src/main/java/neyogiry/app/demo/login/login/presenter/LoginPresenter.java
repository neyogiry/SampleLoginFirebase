package neyogiry.app.demo.login.login.presenter;

import neyogiry.app.demo.login.events.LoginEvent;

/**
 * Created by Neyo on 13/10/2017.
 */

public interface LoginPresenter {

    void onCreate();

    void onDestroy();
    void checkForAuthenticatedUser();
    void validateLogin(String email, String password);
    void registerNewUser(String email, String password);

    void onEventMainThread(LoginEvent event);

}
