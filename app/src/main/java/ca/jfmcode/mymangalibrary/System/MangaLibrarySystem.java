package ca.jfmcode.mymangalibrary.System;

/**
 * Created by ONi on 04/11/2017.
 */

public class MangaLibrarySystem {
    private static final MangaLibrarySystem ourInstance = new MangaLibrarySystem();

    public static MangaLibrarySystem getInstance() {
        return ourInstance;
    }

    private MangaLibrarySystem() {
    }
}
