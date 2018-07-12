package com.hacaller.androidplayground;

import android.os.Process;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

/**
 * Created by Herbert Caller on 25/04/2016.
 */
public class NasaApiRunnable implements Runnable {

    final NasaApiMethods coolApis;


    // An interface that defines methods that CoolApis implements.
    interface NasaApiMethods {
        void setNasaApiRunnableThread(Thread currentThread);
        void handleNasaApiRunnableState(int state);
    }

    NasaApiRunnable(NasaApiMethods nasaApiMethods) {
        coolApis = nasaApiMethods;
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        coolApis.setNasaApiRunnableThread(Thread.currentThread());
        String jsonString = testNasaApi();
        if (jsonString != null){
            coolApis.handleNasaApiRunnableState(1);
        } else {
            coolApis.handleNasaApiRunnableState(0);
        }
    }

    public String testNasaApi()  {
        String baseUrl = "https://api.nasa.gov/planetary/earth/assets?";
        String query = "lon=100.75&lat=1.5&begin=2014-02-01";
        String apiKey = "&api_key=DEMO_KEY";
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
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }



}
