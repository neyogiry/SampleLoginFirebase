package neyogiry.app.demo.login.login.presenter;

import android.util.Log;

import org.greenrobot.eventbus.Subscribe;

import neyogiry.app.demo.login.events.LoginEvent;
import neyogiry.app.demo.login.lib.EventBus;
import neyogiry.app.demo.login.lib.GreenRobotEventBus;
import neyogiry.app.demo.login.login.ui.LoginView;
import neyogiry.app.demo.login.login.interactor.LoginInteractor;
import neyogiry.app.demo.login.login.interactor.LoginInteractorImpl;

/**
 * Created by Neyo on 13/10/2017.
 */

public class LoginPresenterImpl implements LoginPresenter {

    private EventBus mEvenBus;

    private LoginView mLoginView;
    private LoginInteractor mLoginInteractor;

    public LoginPresenterImpl(LoginView loginView) {
        this.mLoginView = loginView;
        this.mLoginInteractor = new LoginInteractorImpl();
        this.mEvenBus = GreenRobotEventBus.getInstance();
    }

    @Override
    public void onCreate() {
        mEvenBus.register(this);
    }

    @Override
    public void onDestroy() {
        mLoginView = null;
        mEvenBus.unregister(this);
    }

    @Override
    public void checkForAuthenticatedUser() {
        if (mLoginView != null){
            mLoginView.disableInputs();
            mLoginView.showProgress();
        }
        mLoginInteractor.checkSession();
    }

    @Override
    public void validateLogin(String email, String password) {
        if (mLoginView != null){
            mLoginView.disableInputs();
            mLoginView.showProgress();
        }
        mLoginInteractor.doSignIn(email, password);
    }

    @Override
    public void registerNewUser(String email, String password) {
        if (mLoginView != null){
            mLoginView.disableInputs();
            mLoginView.showProgress();
        }
        mLoginInteractor.doSignUp(email, password);
    }

    @Override
    @Subscribe
    public void onEventMainThread(LoginEvent event) {
        Log.e("LoginPresenter", "LoginEvents");
        switch (event.getEventType()){
            case LoginEvent.onSignInSuccess:
                onSignInSuccess();
                break;
            case LoginEvent.onSignUpSuccess:
                onSignUpSuccess();
                break;
            case LoginEvent.onSignInError:
                onSignInError(event.getErrorMessage());
                break;
            case LoginEvent.onSignUpError:
                onSignUpError(event.getErrorMessage());
                break;
            case LoginEvent.onFailedToRecoverSession:
                onFailedToRecoverSession();
                break;
        }
    }

    private void onFailedToRecoverSession() {
        if (mLoginView != null){
            mLoginView.enableInputs();
            mLoginView.hideProgress();
        }
        Log.e("LoginPresenterImpl", "onFailedToRecoverSession");
    }

    private void onSignInSuccess(){
        if (mLoginView != null){
            mLoginView.navigateToMainScreen();
        }
    }

    private void onSignUpSuccess(){
        if (mLoginView != null){
            mLoginView.newUserSuccess();
        }
    }

    private void onSignInError(String error){
        if (mLoginView != null){
            mLoginView.enableInputs();
            mLoginView.hideProgress();
            mLoginView.loginError(error);
        }
    }

    private void onSignUpError(String error){
        if (mLoginView != null){
            mLoginView.enableInputs();
            mLoginView.hideProgress();
            mLoginView.newUserError(error);
        }
    }

}
