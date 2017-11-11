package ca.jfmcode.mymangalibrary.System;

import android.content.Context;

/**
 * Created by ONi on 11/11/2017.
 */

public class FileManager {
    //region FileManager singleton method
    private static final FileManager ourInstance = new FileManager();

    public static FileManager getInstance() {
        return ourInstance;
    }

    private FileManager() {
    }
    //endregion

    public void writeSystemFile(Context context){

    }
}
