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

/*
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
//*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ONi on 06/11/2017.
 */

public class MALSystem {
    private final String AUTHURL = "https://myanimelist.net/api/account/verify_credentials.xml";

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

    public void authenticateProfile(final Context context, final String user, final String pass){

        /* NOTE: Proper way to send request that requires authentication in use with Volley
        Credit goes to mattyU from stackoverflow in helping me figure this out.
         */
        //*/

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, AUTHURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(context, "Auth Success : "+response, Toast.LENGTH_SHORT).show();

                profile.authenticated(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: Log the authentication error

                Toast.makeText(context, "Auth Error : "+error.getMessage(), Toast.LENGTH_SHORT).show();
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
}
