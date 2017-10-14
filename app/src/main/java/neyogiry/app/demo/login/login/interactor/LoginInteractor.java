package neyogiry.app.demo.login.login.interactor;

/**
 * Created by Neyo on 13/10/2017.
 */

public interface LoginInteractor {

    void checkSession();

    void doSignUp(String email, String password);
    void doSignIn(String email, String password);

}
