package com.hacaller.androidplayground;

import android.os.Process;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Herbert Caller on 25/04/2016.
 */
public class NYTApiRunnable implements Runnable {

    final NYTApiMethods coolApis;


    // An interface that defines methods that CoolApis implements.
    interface NYTApiMethods {
        void setNYTApiRunnableThread(Thread currentThread);
        void handleNYTApiRunnableState(int state);
    }

    NYTApiRunnable(NYTApiMethods nytApiMethods) {
        coolApis = nytApiMethods;
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        coolApis.setNYTApiRunnableThread(Thread.currentThread());
        String jsonString = testNewYorkTimesApi();
        if (jsonString != null){
            coolApis.handleNYTApiRunnableState(1);
        } else {
            coolApis.handleNYTApiRunnableState(0);
        }
    }


    public String testNewYorkTimesApi() {
        String baseUrl = "http://api.nytimes.com/svc/books/v3/reviews.json?";
        String query = "isbn=9781446484197";
        String apiKey = "&api-key=c8da8e22439437d97e448c9161699c10:0:75097244";
        String myUrl = baseUrl+query+apiKey;
        URL thisURL = null;
        HttpURLConnection thisConnection = null;
        try {
            thisURL = new URL(myUrl);
            thisConnection = (HttpURLConnection) thisURL.openConnection();
            thisConnection.setRequestMethod("GET");
            thisConnection.setRequestProperty("User-Agent", PlaygroundApplication.mAgent);
            Log.i("AAA",String.format("Answer: %d",thisConnection.getResponseCode()));
            InputStream inputStream = thisConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String jsonString = bufferedReader.readLine();
            Log.i("AAA",String.format("Answer: %s", jsonString));
            thisConnection.disconnect();
            return jsonString;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
