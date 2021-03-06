package ca.jfmcode.mymangalibrary.System;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

import ca.jfmcode.mymangalibrary.Tools.CheckingSystem;
import ca.jfmcode.mymangalibrary.Tools.FileCheckListener;
import ca.jfmcode.mymangalibrary.Tools.InternetCheckListener;

import static ca.jfmcode.mymangalibrary.System.FinalVariables.*;

/**
 * Created by ONi on 04/11/2017.
 */

public class MangaLibrarySystem {

    //region private variables
    private boolean gotSysFiles = false;
    private boolean gotFiles = false;
    private boolean gotInternet = false;

    private CheckingSystem checkingSystem;

    private ArrayList<Manga> mangaList;
    //endregion

    //region MangaLibrarySystem singleton method
    private static final MangaLibrarySystem ourInstance = new MangaLibrarySystem();

    public static MangaLibrarySystem getInstance() {
        return ourInstance;
    }

    private MangaLibrarySystem() {
        mangaList = new ArrayList<>();
    }
    //endregion

    //region Getter + Setter methods
    public ArrayList<Manga> getMangaList(){
        return mangaList;
    }

    public void setMangaList(ArrayList<Manga> input){
        mangaList = input;
    }

    public void addToMangaList(Manga input){
        if(!mangaList.contains(input))
            mangaList.add(input);
    }
    //endregion

    private void incrementPB(ProgressBar progressBar, int amount){
        int max = progressBar.getMax();
        int current = progressBar.getProgress();

        if(current+amount<=max)
            progressBar.setProgress(current+amount);
    }

    public void init(final Context context, final ProgressBar progressBar, final CheckingSystem chSys){
        this.checkingSystem = chSys;

        MangaFileManager.getInstance().init(context);

        //PART 1 -- FILE CHECKING
        gotFiles = checkFiles(context, new FileCheckListener() {
            @Override
            public void sysFileFound() {
                incrementPB(progressBar, 10);
            }

            @Override
            public void allFilesFound() {
                incrementPB(progressBar, 15);
                MangaFileManager.getInstance().readMangaListFile(context);

                Log.i("MangaLibrarySystem_init","All Files found");
            }

            @Override
            public void noFiles() {
                incrementPB(progressBar, 25);

                Log.i("MangaLibrarySystem_init","Files not found");
            }

            @Override
            public void missingMangaFile() {
                incrementPB(progressBar, 15);

                Log.i("MangaLibrarySystem_init","Manga file not found");
            }

            @Override
            public void error(String message) {
                checkingSystem.error(message);

                Log.e("MangaLibrarySystem_init",message);
            }
        });

        //PART 2 -- CONNECTION CHECKING
        checkInternetAvailable(context, new InternetCheckListener() {
            @Override
            public void internetOK() {
                incrementPB(progressBar, 25);

                Log.i("connection_checking","Internet is ok");

                authentication(context, progressBar, checkingSystem); //PART 2.5 <-- HERE
            }

            @Override
            public void error(String message) {
                checkingSystem.error(message);

                Log.e("connection_checking",message);
            }
        });
        //*/
    }

    private void authentication(Context context, ProgressBar progressBar, final CheckingSystem checkingSystem){

        //PART 2.5 -- AUTHENTICATION
        if(gotInternet) { //Check if there's a connection to https://myanimelist.net/
            if (gotSysFiles) { //Check if File exists
                MangaFileManager.getInstance().readSysFile(context);

                if(MALSystem.getInstance().isAuth()){
                    updateMangaInfo(context, progressBar);
                }
            } else {
                MALSystem.getInstance().init();
                checkingSystem.callLogin();
            }
        } else{
            //TODO: Show error dialog message about connection error to MAL and quit app
            checkingSystem.error("Authentication error!");
        }
    }

    public void updateMangaInfo(final Context context, final ProgressBar progressBar){
        //PART 3 -- MANGALIST UPDATE
        //TODO: check if Manga info changed
        incrementPB(progressBar, 25);
        updateNotification(context, progressBar);
    }

    public void updateNotification(final Context context, final ProgressBar progressBar){
        //PART 4 -- NOTIFICATION
        //TODO: check if Everything works fine
        incrementPB(progressBar, 25);
        allDone();
    }

    private void allDone(){
        checkingSystem.allOK();
    }

    private void checkInternetAvailable(Context context, InternetCheckListener internetCheckListener){
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if(networkInfo == null){
            internetCheckListener.error("checkInternetAvailable Error : "+"is null!");
            gotInternet=false;
            return;
        }
        if(!networkInfo.isAvailable()){
            internetCheckListener.error("checkInternetAvailable Error : "+"is not available!");
            gotInternet=false;
            return;
        }
        /*
        //TODO: Find a better to check internet connection, preferably toward MAL's website

        if(networkInfo.isConnectedOrConnecting()){
            internetCheckListener.error("checkInternetAvailable Error : "+"is not connected!");
            return false;
        }//*/

        gotInternet=true;
        internetCheckListener.internetOK();

        /*
        //NOTE: For some reason it returns null

        try {
            InetAddress ipAddr = InetAddress.getByName(FinalVariables.MALURL);
            if(!ipAddr.equals("")){
                internetCheckListener.internetOK();
            }

            return true;

        } catch (Exception e) {
            internetCheckListener.error("checkInternetAvailable Error : "+e.getMessage());

            return false;
        }//*/
    }

    private boolean checkFiles(Context context, FileCheckListener fileCheckListener){
        try{
            if(MangaFileManager.getInstance().checkSysFile(context)){
                fileCheckListener.sysFileFound();
                gotSysFiles = true;
            }
            else{
                fileCheckListener.noFiles();
                return false;
            }
            if(MangaFileManager.getInstance().checkMangaListFile(context)){
                fileCheckListener.allFilesFound();
                return true;
            }
            else{
                fileCheckListener.missingMangaFile();
            }
        }catch (Exception e){
            fileCheckListener.error("checkFiles Error : "+e.getMessage());
        }

        return false;
    }
}
// https://myanimelist.net/api/manga/search.xml?q=bleach