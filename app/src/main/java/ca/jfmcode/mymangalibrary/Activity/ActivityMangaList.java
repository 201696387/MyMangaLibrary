package ca.jfmcode.mymangalibrary.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import ca.jfmcode.mymangalibrary.R;

import static ca.jfmcode.mymangalibrary.System.FinalVariables.*;

public class ActivityMangaList extends AppCompatActivity {

    private final int MANGACODE = 456;

    private FloatingActionButton addButton;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_list);

        toolbar = (Toolbar) findViewById(R.id.act_mangalist_toolbar);
        setSupportActionBar(toolbar);

        addButton = (FloatingActionButton) findViewById(R.id.act_mangalist_addmangaFAB);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingAddButton();
            }
        });
    }

    private void floatingAddButton(){
        startActivityForResult(new Intent(ActivityMangaList.this, ActivityMangaAdd.class), MANGACODE);
    }

}
