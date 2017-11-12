package ca.jfmcode.mymangalibrary.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabItem;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import ca.jfmcode.mymangalibrary.R;
import ca.jfmcode.mymangalibrary.System.Manga;
import ca.jfmcode.mymangalibrary.System.MangaLibrarySystem;

import static ca.jfmcode.mymangalibrary.System.FinalVariables.*;

public class ActivityMangaList extends AppCompatActivity {

    private FloatingActionButton addButton;
    private Toolbar toolbar;

    private TabItem allItems;
    private TabItem readItems;
    private TabItem unreadItems;

    private ListView myLibrary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_list);

        toolbar = (Toolbar) findViewById(R.id.act_mangalist_toolbar);
        setSupportActionBar(toolbar);

        allItems = (TabItem)findViewById(R.id.act_mangalist_tab1ALLTI);
        readItems = (TabItem)findViewById(R.id.act_mangalist_tab2READTI);
        unreadItems = (TabItem)findViewById(R.id.act_mangalist_tab3UNREADTI);

        myLibrary = (ListView)findViewById(R.id.act_mangalist_listLV);

        addButton = (FloatingActionButton) findViewById(R.id.act_mangalist_addmangaFAB);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                floatingAddButton();
            }
        });

        myLibrary.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                libraryItemSelected(position);
            }
        });
    }

    private void updateLibrary(){
        ArrayList<Manga> temp = MangaLibrarySystem.getInstance().getMangaList();

        //TODO: Write custom adapter for the manga library <- next step (sort the manga if read or unread)
    }

    private void libraryItemSelected(int pos){
        //TODO: When clicking a manga, jump to ActivityMangaViewInfo with the int position
    }

    private void floatingAddButton(){
        startActivityForResult(new Intent(ActivityMangaList.this, ActivityMangaAdd.class), MANGACODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == MANGACODE){
            if(resultCode == MANGASAVED){

            }
        }
    }
}
