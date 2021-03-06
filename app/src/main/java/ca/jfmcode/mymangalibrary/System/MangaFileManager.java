package ca.jfmcode.mymangalibrary.System;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import ca.jfmcode.mymangalibrary.R;

import static ca.jfmcode.mymangalibrary.System.FinalVariables.*;

/**
 * Created by ONi on 11/11/2017.
 */

public class MangaFileManager {
    //region private variables
    private String sysFilename;
    private String mangaListFilename;
    //endregion

    //region MangaFileManager singleton method
    private static final MangaFileManager ourInstance = new MangaFileManager();

    public static MangaFileManager getInstance() {
        return ourInstance;
    }

    private MangaFileManager() {
    }
    //endregion

    public void init(Context context){ //filenames are in a resource file that is hidden from GitHub for protection (.gitignore)
        sysFilename = context.getString(R.string.SYSFILENAME);
        mangaListFilename = context.getString(R.string.MANGALISTFILENAME);
    }

    //region SysFile I/O methods
    public void writeSysFile(Context context, Profile input){
        HashMap<String, Object> sysFileHashMap = new HashMap<>();

        sysFileHashMap.putAll(input.getHashMap());

        writeFile(context, sysFilename, sysFileHashMap);
    }

    public void readSysFile(Context context){
        HashMap<String, String> output = (HashMap<String, String>) readFile(context, sysFilename);

        HashMap<String, String> profileHashMap = new HashMap<>();

        profileHashMap.put(IDKEY, output.get(IDKEY));
        profileHashMap.put(USERNAMEKEY, output.get(USERNAMEKEY));
        profileHashMap.put(PASSWORDKEY, output.get(PASSWORDKEY));

        MALSystem.getInstance().init(new Profile(profileHashMap));

    }

    public boolean checkSysFile(Context context){
        return checkFile(context, sysFilename);
    }
    //endregion

    //region MangaListFile I/O methods
    public void writeMangaListFile(Context context, ArrayList<Manga> input){
        HashMap<String, Object> mangaListHashMap = new HashMap<>();

        mangaListHashMap.put(MANGALISTKEY, input);

        writeFile(context, mangaListFilename, mangaListHashMap);
    }

    public void readMangaListFile(Context context){
        HashMap<String, ArrayList<Manga>> output = (HashMap<String, ArrayList<Manga>>) readFile(context, mangaListFilename);

        ArrayList<Manga> mangaList = output.get(MANGALISTKEY);

        MangaLibrarySystem.getInstance().setMangaList(mangaList);
    }

    public boolean checkMangaListFile(Context context){
        return checkFile(context, mangaListFilename);
    }
    //endregion

    //region General I/O methods
    private void writeFile(Context context, String filename, HashMap<String, Object> input){
        FileOutputStream fileOutputStream;
        ObjectOutputStream objectOutputStream;

        try{
            fileOutputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(input);

            objectOutputStream.close();
            fileOutputStream.close();

            Log.i("writeFile success", filename);
        } catch (Exception e){
            Log.e("writeFile error", filename+" : "+e.getMessage());
        }
    }

    private Object readFile(Context context, String filename){
        Object result = null;

        FileInputStream fileInputStream;
        ObjectInputStream objectInputStream;

        try{
            fileInputStream = context.openFileInput(filename);
            objectInputStream = new ObjectInputStream(fileInputStream);

            result = objectInputStream.readObject();

            objectInputStream.close();
            fileInputStream.close();

            Log.i("readFile success", filename);
        } catch (Exception e){
            Log.e("readFile error", filename+" : "+e.getMessage());
        }

        return result;
    }

    private boolean checkFile(Context context, String filename){
        File file = new File(context.getFilesDir(), filename);

        return file.exists();
    }
    //endregion
}
