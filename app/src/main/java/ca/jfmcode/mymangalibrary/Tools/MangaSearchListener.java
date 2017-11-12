package ca.jfmcode.mymangalibrary.Tools;

import java.util.ArrayList;

import ca.jfmcode.mymangalibrary.System.Manga;

/**
 * Created by ONi on 11/11/2017.
 */

public interface MangaSearchListener {
    void searchSuccess(ArrayList<Manga> mangasFound);
    void searchFailed(String message);
}
