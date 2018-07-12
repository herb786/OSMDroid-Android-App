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
public class OWMApiRunnable implements Runnable {

    final OWMApiMethods coolApis;


    // An interface that defines methods that CoolApis implements.
    interface OWMApiMethods {
        void setOWMApiRunnableThread(Thread currentThread);
        void handleOWMApiRunnableState(int state);
    }

    OWMApiRunnable(OWMApiMethods owmApiMethods) {
        coolApis = owmApiMethods;
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        coolApis.setOWMApiRunnableThread(Thread.currentThread());
        String jsonString = testOpenWeatherMapApi();
        if (jsonString != null){
            coolApis.handleOWMApiRunnableState(1);
        } else {
            coolApis.handleOWMApiRunnableState(0);
        }
    }


    public String testOpenWeatherMapApi() {
        String baseUrl = "http://api.openweathermap.org/data/2.5/weather?";
        String query = "lat=35&lon=139";
        String apiKey = "&appid=2e0e702b649c70c2c40960086adbba38";
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
