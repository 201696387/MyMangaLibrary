package ca.jfmcode.mymangalibrary.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ca.jfmcode.mymangalibrary.R;
import ca.jfmcode.mymangalibrary.System.MALSystem;
import ca.jfmcode.mymangalibrary.Tools.MALAuthListener;

import static ca.jfmcode.mymangalibrary.System.FinalVariables.*;

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
        if(TextUtils.isEmpty(user)){
            EditText username = (EditText)findViewById(R.id.act_login_userET);
            username.setError("Please enter username");
            username.requestFocus();
            return;
        }

        if(TextUtils.isEmpty(pass)){
            EditText password = (EditText)findViewById(R.id.act_login_passET);
            password.setError("Please enter password");
            password.requestFocus();
            return;
        }

        MALSystem.getInstance().authenticateProfile(ActivityLogin.this, user, pass, new MALAuthListener() {
            @Override
            public void success() {
                setResult(UPDATEMANGAINFOCODE);
                finish();
            }

            @Override
            public void error() {
                //TODO: Display error message dialog
            }
        });
    }

    @Override
    public void onBackPressed() {
        Drawable icon = ContextCompat.getDrawable(ActivityLogin.this, android.R.drawable.ic_dialog_alert);
        DrawableCompat.setTint(icon, ContextCompat.getColor(ActivityLogin.this, R.color.colorAccent));

        new AlertDialog.Builder(this)
                .setIcon(icon)
                .setTitle("Closing App")
                .setMessage("Are you sure you want to exit this app?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(EXITAPPCODE);
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}
