package ca.jfmcode.mymangalibrary.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

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
    }

    @Override
    protected void onResume() {
        super.onResume();

        MangaLibrarySystem.getInstance().init(ActivityLaunch.this, loading, new CheckingSystem() {
            @Override
            public void allOK() {
                systemChecked();
            }
        });
    }

    private void systemChecked(){
        startActivity(new Intent(ActivityLaunch.this, ActivityMangaList.class));
        finish();
    }
}
