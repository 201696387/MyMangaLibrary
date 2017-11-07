package ca.jfmcode.mymangalibrary.System;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.net.ConnectivityManagerCompat;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.net.InetAddress;

import ca.jfmcode.mymangalibrary.Activity.ActivityLaunch;
import ca.jfmcode.mymangalibrary.Activity.ActivityLogin;
import ca.jfmcode.mymangalibrary.Activity.ActivityMangaList;
import ca.jfmcode.mymangalibrary.Tools.CheckingSystem;
import ca.jfmcode.mymangalibrary.Tools.FileCheckListener;
import ca.jfmcode.mymangalibrary.Tools.InternetCheckListener;

/**
 * Created by ONi on 04/11/2017.
 */

public class MangaLibrarySystem {

    private final String MALURL = "myanimelist.net";
    private boolean gotFiles = false;
    private boolean gotInternet = false;

    private CheckingSystem checkingSystem;

    //region MangaLibrarySystem singleton method
    private static final MangaLibrarySystem ourInstance = new MangaLibrarySystem();

    public static MangaLibrarySystem getInstance() {
        return ourInstance;
    }

    private MangaLibrarySystem() {
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

        //PART 1 -- FILE CHECKING
        gotFiles = checkFiles(new FileCheckListener() {
            @Override
            public void filesFound() {
                incrementPB(progressBar, 25);

                Toast.makeText(context, "Files found", Toast.LENGTH_SHORT).show(); //debug
                //TODO: log file_check success
            }

            @Override
            public void noFiles() {
                incrementPB(progressBar, 25);

                Toast.makeText(context, "Files not found", Toast.LENGTH_SHORT).show(); //debug
                //TODO: log file_check success and file not found
            }

            @Override
            public void error(String message) {
                checkingSystem.error(message);

                //TODO: log file_check error message
            }
        });

        //PART 2 -- CONNECTION CHECKING
        checkInternetAvailable(context, new InternetCheckListener() {
            @Override
            public void internetOK() {
                incrementPB(progressBar, 25);

                Toast.makeText(context, "Internet is ok", Toast.LENGTH_SHORT).show(); //debug
                //TODO: log internet_check success

                authentication(checkingSystem); //PART 2.5 <-- HERE
            }

            @Override
            public void error(String message) {
                checkingSystem.error(message);

                //TODO: log internet_check error message
            }
        });
        //*/
    }

    public void updateMangaInfo(final Context context, final ProgressBar progressBar){
        //PART 3 -- MANGALIST UPDATE
        //TODO: check if Manga info changed
        incrementPB(progressBar, 25);
    }

    public void updateNotification(final Context context, final ProgressBar progressBar){
        //PART 4 -- NOTIFICATION
        //TODO: check if Everything works fine
        //incrementPB(progressBar, 25);
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
            InetAddress ipAddr = InetAddress.getByName(MALURL);
            if(!ipAddr.equals("")){
                internetCheckListener.internetOK();
            }

            return true;

        } catch (Exception e) {
            internetCheckListener.error("checkInternetAvailable Error : "+e.getMessage());

            return false;
        }//*/
    }

    private boolean checkFiles(FileCheckListener fileCheckListener){
        //TODO: check if Files exists
        try{

        }catch (Exception e){
            fileCheckListener.error("checkFiles Error : "+e.getMessage());
        }

        fileCheckListener.noFiles();
        return false;
    }

    private void authentication(final CheckingSystem checkingSystem){

        //PART 2.5 -- AUTHENTICATION
        if(gotInternet) { //Check if there's a connection to https://myanimelist.net/
            if (gotFiles) { //Check if encrypted File exists
                //TODO: get Profile objected from encrypted File
                //MALSystem.getInstance().init(profile);
            } else {
                MALSystem.getInstance().init();
                checkingSystem.callLogin();
            }
        } else{
            //TODO: Show error dialog message about connection error to MAL and quit app
            checkingSystem.error("Authentication error!");
        }
    }
}
// https://myanimelist.net/api/manga/search.xml?q=bleach