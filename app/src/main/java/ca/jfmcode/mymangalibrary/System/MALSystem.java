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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ca.jfmcode.mymangalibrary.Tools.MALAuthListener;
import ca.jfmcode.mymangalibrary.Tools.MangaSearchListener;

import static ca.jfmcode.mymangalibrary.System.FinalVariables.*;

/**
 * Created by ONi on 06/11/2017.
 */

public class MALSystem {
    private final String AUTHURL = "https://myanimelist.net/api/account/verify_credentials.xml";
    private final String MANGASEARCHURL = "https://myanimelist.net/api/manga/search.xml?"; //in the end add "q=full+metal" : it returns an xml containing a list of results

    private final int MAXSEARCHRESULTS = 10;

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
                MangaFileManager.getInstance().writeSysFile(context, profile); //saves the profile obj to avoid logging-in every time

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

    public void searchManga(final Context context, String name, final MangaSearchListener mangaSearchListener){
        final String mangaName = name.replace(" ","+");

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, MANGASEARCHURL+"q="+mangaName, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(context, "MangaSearch Success : "+response, Toast.LENGTH_SHORT).show(); //debug

                mangaSearchListener.searchSuccess(parseResult(response));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: Log the mangasearch error

                mangaSearchListener.searchFailed("MangaSearch Error : "+error.getMessage());
            }
        })
            {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> params = new HashMap<String, String>();

                params.put("Content-Type", "application/json");
                String auth = "Basic " + Base64.encodeToString(profile.getCredBytes(), Base64.DEFAULT);
                params.put("Authorization", auth);

                return params;
            }}
        ;

        requestQueue.add(stringRequest);
        //*/
    }

    private ArrayList<Manga> parseResult(String input){
        ArrayList<Manga> result = new ArrayList<>();

        int length = input.length();

        for(int index = 0 ; index<(length-18) && result.size()<MAXSEARCHRESULTS ; index++){
            index = input.indexOf("<entry>", index);
            int lastIndex = input.indexOf("</entry>", index);

            if(index != -1) {
                String trimmed = input.substring(index, lastIndex);

                String id = getXMLContents(trimmed, "id");
                String title = getXMLContents(trimmed, "title");
                String english = getXMLContents(trimmed, "english");
                String synonyms = getXMLContents(trimmed, "synonyms");
                String chapters = getXMLContents(trimmed, "chapters");
                String volumes = getXMLContents(trimmed, "volumes");
                String score = getXMLContents(trimmed, "score");
                String type = getXMLContents(trimmed, "type");
                String status = getXMLContents(trimmed, "status");
                String start_date = getXMLContents(trimmed, "start_date");
                String end_date = getXMLContents(trimmed, "end_date");
                String synopsis = getXMLContents(trimmed, "synopsis");
                String image = getXMLContents(trimmed, "image");

                result.add(new Manga(id, title, english, synonyms, chapters, volumes, score, type, status, start_date, end_date, synopsis, image));
            }

            index = lastIndex;
        }

        return result;
    }

    private String getXMLContents(String trimmed, String tag){
        String content = "";

        String startTag = "<"+tag+">";
        String endTag = "</"+tag+">";

        int beginIndex = trimmed.indexOf(startTag)+startTag.length();
        int lastIndex = trimmed.indexOf(endTag);

        content = trimmed.substring(beginIndex, lastIndex);

        return content;
    }
}
