package ca.jfmcode.mymangalibrary.System;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import java.util.HashMap;
import java.util.Map;

import ca.jfmcode.mymangalibrary.Tools.MALAuthListener;

/**
 * Created by ONi on 06/11/2017.
 */

public class MALSystem {
    private final String AUTHURL = "https://myanimelist.net/api/account/verify_credentials.xml";
    private final String MANGASEARCHURL = "https://myanimelist.net /api/anime|manga/search.xml?"; //in the end add "q=full+metal" : it returns an xml containing a list of results

    private Profile profile;

    //region Description
    private static final MALSystem ourInstance = new MALSystem();

    public static MALSystem getInstance() {
        return ourInstance;
    }

    private MALSystem() {
    }
    //endregion

    public void init(){
        profile = new Profile();
    }
    public void init(Profile profile){
        this.profile = profile;
    }

    public boolean isAuth(){
        return profile.isAuthenticated();
    }

    public void authenticateProfile(final Context context, final String user, final String pass, final MALAuthListener malAuthListener){

        /* NOTE: Proper way to send request that requires authentication in use with Volley
        Credit goes to mattyU from stackoverflow in helping me figure this out.
         */
        //*/

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AUTHURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Auth Success : "+response, Toast.LENGTH_LONG).show();

                profile.authenticated(response);
                malAuthListener.success();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: Log the authentication error

                Toast.makeText(context, "Auth Error : "+error.getMessage(), Toast.LENGTH_SHORT).show();
                malAuthListener.error();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();

                params.put("Content-Type", "application/json");
                String creds = String.format("%s:%s",user,pass);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);

                profile.login(user, pass);

                return params;
            }
        };

        requestQueue.add(stringRequest);
        //*/
    }

    public void searchManga(final Context context, String name){
        final String mangaName = name.replace(" ","+");

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AUTHURL+"q="+mangaName, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "MangaSearch Success : "+response, Toast.LENGTH_SHORT).show();

                profile.authenticated(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: Log the mangasearch error

                Toast.makeText(context, "MangaSearch Error : "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        })
            /*{
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();

                params.put("Content-Type", "application/json");
                String creds = String.format("%s:%s",user,pass);
                String auth = "Basic " + Base64.encodeToString(creds.getBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);

                return params;
            }}//*///TODO: Test if authentication is remembered server-side
        ;
    }
}
