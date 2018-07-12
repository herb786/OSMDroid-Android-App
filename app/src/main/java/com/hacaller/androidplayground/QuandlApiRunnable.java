package com.hacaller.androidplayground;

import android.os.Process;
import android.util.JsonReader;
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
public class QuandlApiRunnable implements Runnable {

    final QuandlApiMethods coolApis;


    // An interface that defines methods that CoolApis implements.
    interface QuandlApiMethods {
        void setQuandlApiRunnableThread(Thread currentThread);
        void handleQuandlApiRunnableState(int state);
    }

    QuandlApiRunnable(QuandlApiMethods quandlApiMethods) {
        coolApis = quandlApiMethods;
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        coolApis.setQuandlApiRunnableThread(Thread.currentThread());
        String jsonString = testQuandlApi();
        if (jsonString != null){
            coolApis.handleQuandlApiRunnableState(1);
        } else {
            coolApis.handleQuandlApiRunnableState(0);
        }
    }

    public String testQuandlApi()  {
        String baseUrl = "http://www.quandl.com/api/v3/datasets/";
        String query = "BOE/XUDLJYS.json";
        String apiKey = "?api_key=r2Cx6rq7cSQUoYLwcXrz";
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
