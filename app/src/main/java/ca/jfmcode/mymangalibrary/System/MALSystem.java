package ca.jfmcode.mymangalibrary.System;

import android.content.Context;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

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

    public void authenticateProfile(final Context context, String param){ //param = "user:password"

        /* NOTE: This method doesn't seem to be async which would explain why I get a NULL response. Need to make it async somehow, I'll read the documentation more later.


        StringBuffer stringBuffer = new StringBuffer("");
        BufferedReader bufferedReader = null;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpGet httpGet = new HttpGet();

            URI uri = new URI(AUTHURL);
            httpGet.setURI(uri);
            httpGet.addHeader(BasicScheme.authenticate(
                    new UsernamePasswordCredentials("user", "password"),
                    HTTP.UTF_8, false));

            HttpResponse httpResponse = httpClient.execute(httpGet);
            InputStream inputStream = httpResponse.getEntity().getContent();
            bufferedReader = new BufferedReader(new InputStreamReader(
                    inputStream));

            String readLine = bufferedReader.readLine();
            while (readLine != null) {
                stringBuffer.append(readLine);
                stringBuffer.append("\n");
                readLine = bufferedReader.readLine();
            }
        } catch (Exception e) {
            // TODO: handle exception

            Toast.makeText(context, "HTTP Error : "+e.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    // TODO: handle exception

                    Toast.makeText(context, "bufferedReader.close() failed : "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }
        profile.login(stringBuffer.toString());

        /* NOTE: Volley obviously doesn't send curl request


        RequestQueue requestQueue = Volley.newRequestQueue(context);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, "curl -u "+param+" "+AUTHURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                profile.login(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //TODO: Log the authentication error

                Toast.makeText(context, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(stringRequest);
        //*/
    }
}
