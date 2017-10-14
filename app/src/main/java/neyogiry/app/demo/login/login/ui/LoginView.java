package neyogiry.app.demo.login.login.ui;

/**
 * Created by Neyo on 13/10/2017.
 */

public interface LoginView {

    void enableInputs();
    void disableInputs();

    void showProgress();
    void hideProgress();

    void handleSignUp();
    void handleSignIn();

    void navigateToMainScreen();
    void loginError(String error);

    void newUserSuccess();
    void newUserError(String error);

}
