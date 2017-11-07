package ca.jfmcode.mymangalibrary.System;

import android.text.TextUtils;
import android.widget.Toast;

/**
 * Created by ONi on 05/11/2017.
 */

public class Profile {
    //region private variables
    private int id;
    private String username;
    private String password;
    //endregion

    //region constructor methods
    public Profile() {
        id=-1;
        username="";
        password="";
    }
    //endregion

    //region setter methods
    private void setId(int id) {
        this.id = id;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    private void setPassword(String password) {
        this.password = password;
    }
    //endregion

    //region getter methods
    public boolean isAuthenticated() {
        if (id > -1) {
            return true;
        }

        return false;
    }
    //endregion

    public void login(String user, String pass){
        setUsername(user);
        setPassword(pass);
    }

    public void authenticated(String response){

        if(response == null || response.length()<1)
            return;

        int firstIndexOfID = response.indexOf("<id>")+4;
        int lastIndexOfID = response.indexOf("</id>");
        String id = response.substring(firstIndexOfID,lastIndexOfID);

        try{
            setId(Integer.parseInt(id));
        }catch (Exception e){
            //TODO: Log parseInt error
        }
    }
}
