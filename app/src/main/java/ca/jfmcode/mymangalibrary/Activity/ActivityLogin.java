package ca.jfmcode.mymangalibrary.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ca.jfmcode.mymangalibrary.R;
import ca.jfmcode.mymangalibrary.System.MALSystem;

public class ActivityLogin extends AppCompatActivity {

    private TextView termsTV;
    private TextView signupTV;
    private Button loginBTN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        signupTV = (TextView)findViewById(R.id.act_login_linkTV);
        termsTV = (TextView)findViewById(R.id.act_login_termsTV);

        final EditText username = (EditText)findViewById(R.id.act_login_userET);
        final EditText password = (EditText)findViewById(R.id.act_login_passET);
        loginBTN = (Button)findViewById(R.id.act_login_loginBTN);

        signupTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
        termsTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewTerms();
            }
        });
        loginBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                login(user, pass);
            }
        });

    }

    private void signup(){
        startActivity(new Intent(ActivityLogin.this, ActivitySignUp.class));
    }

    private void viewTerms(){
        //TODO: open dialog to view terms
    }

    private void login(String user, String pass){
        MALSystem.getInstance().authenticateProfile(ActivityLogin.this, user+":"+pass);
    }
}
