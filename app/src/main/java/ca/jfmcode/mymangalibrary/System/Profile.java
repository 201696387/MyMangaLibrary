package ca.jfmcode.mymangalibrary.System;

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
    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //endregion

    public boolean login(String user, String password){
        setUsername(user);
        setPassword(password);

        //TODO: send request to authenticate with "curl -u user:password https://myanimelist.net/api/account/verify_credentials.xml" and get ID

        return false;
    }
}
