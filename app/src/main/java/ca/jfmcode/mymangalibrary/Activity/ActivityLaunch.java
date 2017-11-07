package ca.jfmcode.mymangalibrary.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import ca.jfmcode.mymangalibrary.R;
import ca.jfmcode.mymangalibrary.System.MangaLibrarySystem;
import ca.jfmcode.mymangalibrary.Tools.CheckingSystem;

public class ActivityLaunch extends AppCompatActivity {

    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        loading = (ProgressBar)findViewById(R.id.act_launch_loadingPB);
        loading.setMax(100);

        MangaLibrarySystem.getInstance().init(ActivityLaunch.this, loading, new CheckingSystem() {
            @Override
            public void callLogin() {
                callLoginActivity();
            }

            @Override
            public void allOK() {
                systemChecked();
            }

            @Override
            public void error(String message) {
                //TODO: display error message
                Toast.makeText(ActivityLaunch.this, message, Toast.LENGTH_SHORT).show(); //debug
            }
        });
    }

    public void callLoginActivity(){
        startActivityForResult(new Intent(ActivityLaunch.this, ActivityLogin.class), 123);
    }

    private void systemChecked(){
        startActivity(new Intent(ActivityLaunch.this, ActivityMangaList.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 123) { //Login Activity
            if(resultCode == 1){ //exit app
                finish();
            }
            if(resultCode == 7){
                MangaLibrarySystem.getInstance().updateMangaInfo(ActivityLaunch.this, loading);
            }
        }
    }
}
