package ca.jfmcode.mymangalibrary.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ca.jfmcode.mymangalibrary.R;
import ca.jfmcode.mymangalibrary.System.MALSystem;
import ca.jfmcode.mymangalibrary.System.Manga;
import ca.jfmcode.mymangalibrary.System.MangaFoundAdapter;
import ca.jfmcode.mymangalibrary.System.MangaLibrarySystem;
import ca.jfmcode.mymangalibrary.System.MangaSelectedAdapter;
import ca.jfmcode.mymangalibrary.Tools.MangaSearchListener;

import static ca.jfmcode.mymangalibrary.System.FinalVariables.*;

public class ActivityMangaAdd extends AppCompatActivity {

    private EditText mangaName, mangaURL;
    private ImageView searchButtonIV;
    private LinearLayout saveButtonLL;
    private ListView foundMangaListView;

    private ArrayList<Manga> mangaFoundList;
    private final int DEFAULTPOS = -1;
    private int mangaSelectionIndex = DEFAULTPOS;
    private MangaFoundAdapter mangaFoundAdapter;

    private boolean isList = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manga_add);

        mangaName = (EditText)findViewById(R.id.act_mangaadd_nameET);
        mangaURL = (EditText)findViewById(R.id.act_mangaadd_urlET);

        foundMangaListView = (ListView)findViewById(R.id.act_mangaadd_mangaLV);

        searchButtonIV = (ImageView)findViewById(R.id.act_mangaadd_searchIV);
        saveButtonLL = (LinearLayout)findViewById(R.id.act_mangaadd_saveLL);

        searchButtonIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchManga();
            }
        });

        saveButtonLL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveManga();
            }
        });

        foundMangaListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(isList)
                    setMangaSelection(position);
            }
        });

        mangaFoundList = new ArrayList<>();
        mangaFoundAdapter = new MangaFoundAdapter(ActivityMangaAdd.this, R.layout.cell_search_list, mangaFoundList);
        foundMangaListView.setAdapter(mangaFoundAdapter);
    }

    private void updateListViewFromList(){
        isList = true;

        mangaFoundAdapter = new MangaFoundAdapter(ActivityMangaAdd.this, R.layout.cell_search_list, mangaFoundList);
        mangaFoundAdapter.notifyDataSetChanged();
        foundMangaListView.setAdapter(mangaFoundAdapter);
    }

    private void updateListViewFromItem(){
        isList = false;

        ArrayList<Manga> temp = new ArrayList<>();
        temp.add(mangaFoundList.get(mangaSelectionIndex));

        MangaSelectedAdapter mangaSelectedAdapter = new MangaSelectedAdapter(ActivityMangaAdd.this, temp);
        foundMangaListView.setAdapter(mangaSelectedAdapter);
    }

    private void searchManga(){
        String name = mangaName.getText().toString();

        if(!TextUtils.isEmpty(name)){
            MALSystem.getInstance().searchManga(ActivityMangaAdd.this, name, new MangaSearchListener() {
                @Override
                public void searchSuccess(ArrayList<Manga> mangasFound) {
                    setMangaFoundList(mangasFound);
                }

                @Override
                public void searchFailed(String message) {
                    Toast.makeText(ActivityMangaAdd.this, message, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void setMangaFoundList(ArrayList<Manga> mangasFound){
        mangaFoundList = mangasFound;
        setMangaSelection(DEFAULTPOS);

        updateListViewFromList();
    }

    private void setMangaSelection(int pos){
        mangaSelectionIndex = pos;

        if(mangaSelectionIndex != DEFAULTPOS){
            updateListViewFromItem();
        }
    }

    private void saveManga(){
        if(mangaSelectionIndex==DEFAULTPOS){
            Toast.makeText(this, "Please search and select a manga", Toast.LENGTH_SHORT).show(); //TODO: Find a better way to display a message to the user

            return;
        }

        if(mangaFoundList!=null && mangaSelectionIndex<mangaFoundList.size() && mangaSelectionIndex>-1) {
            Manga mangaSelection = mangaFoundList.get(mangaSelectionIndex);

            MangaLibrarySystem.getInstance().addToMangaList(mangaSelection);

            setResult(MANGASAVED);
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if(mangaSelectionIndex == DEFAULTPOS){
            super.onBackPressed();
        } else{
            updateListViewFromList();
        }

        setMangaSelection(DEFAULTPOS);
    }
}
