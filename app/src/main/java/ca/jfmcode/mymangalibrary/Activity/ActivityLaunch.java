package ca.jfmcode.mymangalibrary.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import ca.jfmcode.mymangalibrary.R;
import ca.jfmcode.mymangalibrary.System.MangaLibrarySystem;
import ca.jfmcode.mymangalibrary.Tools.CheckingSystem;

import static ca.jfmcode.mymangalibrary.System.FinalVariables.*;

public class ActivityLaunch extends AppCompatActivity {

    private ProgressBar loading;
    private final int pbMax = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);

        loading = (ProgressBar)findViewById(R.id.act_launch_loadingPB);
        loading.setMax(pbMax);

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
        startActivityForResult(new Intent(ActivityLaunch.this, ActivityLogin.class), LOGINREQCODE);
    }

    private void systemChecked(){
        startActivity(new Intent(ActivityLaunch.this, ActivityMangaList.class));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == LOGINREQCODE) { //Login Activity
            if(resultCode == EXITAPPCODE){ //exit app
                finish();
            }
            if(resultCode == UPDATEMANGAINFOCODE){
                MangaLibrarySystem.getInstance().updateMangaInfo(ActivityLaunch.this, loading);
            }
        }
    }
}
