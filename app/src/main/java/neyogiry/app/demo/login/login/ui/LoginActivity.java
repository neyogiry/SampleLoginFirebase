package neyogiry.app.demo.login.login.ui;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import neyogiry.app.demo.login.R;
import neyogiry.app.demo.login.login.presenter.LoginPresenter;
import neyogiry.app.demo.login.login.presenter.LoginPresenterImpl;
import neyogiry.app.demo.login.profile.ProfileActivity;

public class LoginActivity extends AppCompatActivity implements LoginView, View.OnClickListener{

    private EditText inputEmail;
    private EditText inputPassword;
    private Button btnSignin;
    private Button btnSignup;
    private ProgressBar mProgressBar;
    private RelativeLayout mRelativeLayout;

    private LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initializeViews();

        mLoginPresenter = new LoginPresenterImpl(this);
        mLoginPresenter.onCreate();
        mLoginPresenter.checkForAuthenticatedUser();

        // Attaching listener to button
        btnSignin.setOnClickListener(this);
        btnSignup.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        mLoginPresenter.onDestroy();
        super.onDestroy();
    }

    private void initializeViews(){
        inputEmail = (EditText)findViewById(R.id.editTxtEmail);
        inputPassword = (EditText)findViewById(R.id.editTxtPassword);
        btnSignin = (Button)findViewById(R.id.btnSignin);
        btnSignup = (Button)findViewById(R.id.btnSignup);
        mProgressBar = (ProgressBar)findViewById(R.id.progressBar);
        mRelativeLayout = (RelativeLayout)findViewById(R.id.layoutMainContainer);
    }

    @Override
    public void onClick(View view) {
        if(view == btnSignin) handleSignIn();
        else if(view == btnSignup) handleSignUp();
    }

    @Override
    public void enableInputs() {
        setInputs(true);
    }

    @Override
    public void disableInputs() {
        setInputs(false);
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void handleSignUp() {
        Log.e("LoginActivity", "handleSignUp");
        mLoginPresenter.registerNewUser(inputEmail.getText().toString(),
                inputPassword.getText().toString());
    }

    @Override
    public void handleSignIn() {
        Log.e("LoginActivity", "handleSignIn");
        mLoginPresenter.validateLogin(inputEmail.getText().toString(),
                inputPassword.getText().toString());
    }

    @Override
    public void navigateToMainScreen() {
        startActivity(new Intent(this, ProfileActivity.class));
    }

    @Override
    public void loginError(String error) {
        inputPassword.setText("");
        String mgError = String.format(getString(R.string.login_error_message_signin), error);
        inputPassword.setError(mgError);
    }

    @Override
    public void newUserSuccess() {
        Snackbar.make(mRelativeLayout, R.string.login_notice_message_signup, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void newUserError(String error) {
        inputPassword.setText("");
        String mgError = String.format(getString(R.string.login_error_message_signup), error);
        inputPassword.setError(mgError);
    }

    private void setInputs(boolean enabled){
        inputEmail.setEnabled(enabled);
        inputPassword.setEnabled(enabled);
        btnSignup.setEnabled(enabled);
        btnSignin.setEnabled(enabled);
    }

}
