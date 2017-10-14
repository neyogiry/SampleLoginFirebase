package neyogiry.app.demo.login.login.repository;

/**
 * Created by Neyo on 13/10/2017.
 */

public interface LoginRepository {

    void signUp(String email, String password);
    void signIn(String email, String password);
    void checkSession();

}
