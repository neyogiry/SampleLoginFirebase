package neyogiry.app.demo.login.profile;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import neyogiry.app.demo.login.R;
import neyogiry.app.demo.login.login.ui.LoginActivity;

public class ProfileActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView txtUserEmail;
    private Button btnSignout;

    private FirebaseAuth auth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtUserEmail = (TextView)findViewById(R.id.txtUserEmail);
        btnSignout = (Button)findViewById(R.id.btnLogout);
        btnSignout.setOnClickListener(this);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        checkSession();
        populate();
    }

    private void checkSession(){
        if(user == null) returnToLogin();
    }

    private void populate(){
        txtUserEmail.setText(user.getEmail());
    }

    @Override
    public void onClick(View v) {
        auth.signOut();
        returnToLogin();
    }

    private void returnToLogin(){
        finish();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
