package ca.jfmcode.mymangalibrary.System;

import android.content.Context;
import android.content.Intent;
import android.widget.ProgressBar;

import ca.jfmcode.mymangalibrary.Activity.ActivityMangaList;
import ca.jfmcode.mymangalibrary.Tools.CheckingSystem;

/**
 * Created by ONi on 04/11/2017.
 */

public class MangaLibrarySystem {


    //region MangaLibrarySystem singleton method
    private static final MangaLibrarySystem ourInstance = new MangaLibrarySystem();

    public static MangaLibrarySystem getInstance() {
        return ourInstance;
    }

    private MangaLibrarySystem() {
    }
    //endregion

    public void init(Context context, ProgressBar progressBar, CheckingSystem checkingSystem){
        //TODO: check if Files exists
        progressBar.setProgress(25);
        //TODO: check if Internet Connected
        progressBar.setProgress(50);
        //TODO: check if Manga info changed
        progressBar.setProgress(75);
        //TODO: check if Everything works fine
        progressBar.setProgress(100);

        checkingSystem.allOK();
    }
}
