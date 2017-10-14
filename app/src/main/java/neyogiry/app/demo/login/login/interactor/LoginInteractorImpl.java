package neyogiry.app.demo.login.login.interactor;

import neyogiry.app.demo.login.login.repository.LoginRepository;
import neyogiry.app.demo.login.login.repository.LoginRepositoryImpl;

/**
 * Created by Neyo on 13/10/2017.
 */

public class LoginInteractorImpl implements LoginInteractor {

    private LoginRepository mLoginRepository;

    public LoginInteractorImpl() {
        mLoginRepository = new LoginRepositoryImpl();
    }

    @Override
    public void checkSession() {
        mLoginRepository.checkSession();
    }

    @Override
    public void doSignUp(String email, String password) {
        mLoginRepository.signUp(email, password);
    }

    @Override
    public void doSignIn(String email, String password) {
        mLoginRepository.signIn(email, password);
    }

}
