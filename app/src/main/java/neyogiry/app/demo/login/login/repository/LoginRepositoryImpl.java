package neyogiry.app.demo.login.login.repository;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import neyogiry.app.demo.login.events.LoginEvent;
import neyogiry.app.demo.login.lib.EventBus;
import neyogiry.app.demo.login.lib.GreenRobotEventBus;

/**
 * Created by Neyo on 13/10/2017.
 */

public class LoginRepositoryImpl implements LoginRepository {

    private FirebaseAuth mFrbAuth;

    public LoginRepositoryImpl() {
        this.mFrbAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void signUp(final String email, final String password) {
        Log.e("LoginRepository", "SignUp");
        mFrbAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Log.e("LoginRepository", "SignUp / OnSuccess");
                        postEvent(LoginEvent.onSignUpSuccess);
                        signIn(email, password);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("LoginRepository", "SignUp / OnFailure");
                        postEvent(LoginEvent.onSignUpError, e.getMessage());
                    }
                });
    }

    @Override
    public void signIn(final String email, final String password) {
        Log.e("LoginRepository", "SignIn");
        try {
            mFrbAuth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            postEvent(LoginEvent.onSignInSuccess);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            postEvent(LoginEvent.onSignInError, e.getMessage());
                        }
                    });
        }catch (Exception e){
            postEvent(LoginEvent.onSignInError, e.getMessage());
        }
    }

    @Override
    public void checkSession() {
        Log.e("LoginRepository", "CheckSession");
        if(mFrbAuth != null){
            FirebaseUser currentUser = mFrbAuth.getCurrentUser();
            if(currentUser != null){
                postEvent(LoginEvent.onSignInSuccess);
            }else{
                postEvent(LoginEvent.onFailedToRecoverSession);
            }
        } else {
            postEvent(LoginEvent.onFailedToRecoverSession);
        }
        postEvent(LoginEvent.onFailedToRecoverSession);
    }

    private void postEvent (int typeEvent, String errorMessage){
        LoginEvent mLoginEvent = new LoginEvent();
        mLoginEvent.setEventType(typeEvent);
        if(errorMessage != null){
            mLoginEvent.setErrorMessage(errorMessage);
        }
        EventBus mEventBus = GreenRobotEventBus.getInstance();
        mEventBus.post(mLoginEvent);
    }

    private void postEvent (int typeEvent){
        postEvent(typeEvent, null);
    }

}
