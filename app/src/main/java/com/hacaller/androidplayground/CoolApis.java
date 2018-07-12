package com.hacaller.androidplayground;

import android.widget.TextView;

/**
 * Created by Herbert Caller on 25/04/2016.
 */
public class CoolApis implements NasaApiRunnable.NasaApiMethods, QuandlApiRunnable.QuandlApiMethods,
        NYTApiRunnable.NYTApiMethods, OWMApiRunnable.OWMApiMethods {

    TextView t1, t2, t3, t4;

    // Field containing the Thread this task is running on.
    Thread thread;

    // Fields containing references to the runnable object.
    private Runnable nasaApiRunnable;

    public TextView getT1() {
        return t1;
    }

    public TextView getT2() {
        return t2;
    }

    public TextView getT3() {
        return t3;
    }

    public TextView getT4() {
        return t4;
    }

    private Runnable quandlApiRunnable;
    private Runnable owmApiRunnable;
    private Runnable nytApiRunnable;

    // The Thread on which this task is currently running.
    private Thread currentThread;

    // An object that contains the ThreadPool singleton.
    private static CoolApisManager coolApisManager;


    CoolApis() {
        nasaApiRunnable = new NasaApiRunnable(this);
        quandlApiRunnable = new QuandlApiRunnable(this);
        owmApiRunnable = new OWMApiRunnable(this);
        nytApiRunnable = new NYTApiRunnable(this);
        coolApisManager = coolApisManager.getInstance();
    }

    Runnable getNasaApiRunnable() {
        return nasaApiRunnable;
    }

    Runnable getNYTApiRunnable() {
        return nytApiRunnable;
    }

    Runnable getQuandlApiRunnable() {
        return quandlApiRunnable;
    }

    Runnable getOMWApiMethods() {
        return owmApiRunnable;
    }


    public Thread getCurrentThread() {
        synchronized(coolApisManager) {
            return currentThread;
        }
    }


    public void setCurrentThread(Thread thread) {
        synchronized(coolApisManager) {
            currentThread = thread;
        }
    }


    void initializeTask(CoolApisManager coolApisManager, TextView t1, TextView t2, TextView t3, TextView t4)
    {
        this.coolApisManager = coolApisManager;
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
        this.t4 = t4;
    }

    // Delegates handling the current state of the task to the CoolApisManager object
    void handleState(int state) {
        coolApisManager.handleState(this, state);
    }

    void recycle(){}

    @Override
    public void setNasaApiRunnableThread(Thread currentThread) {
        setCurrentThread(currentThread);
    }

    @Override
    public void handleNasaApiRunnableState(int state) {
        int outState;
        switch(state) {
            case 1:
                outState = 1;
                break;
            default:
                outState = 0;
                break;
        }
        handleState(outState);
    }


    @Override
    public void setQuandlApiRunnableThread(Thread currentThread) {
        setCurrentThread(currentThread);
    }

    @Override
    public void handleQuandlApiRunnableState(int state) {
        int outState;
        switch(state) {
            case 1:
                outState = 2;
                break;
            default:
                outState = 0;
                break;
        }
        handleState(outState);
    }

    @Override
    public void setNYTApiRunnableThread(Thread currentThread) {
        setCurrentThread(currentThread);
    }

    @Override
    public void handleNYTApiRunnableState(int state) {
        int outState;
        switch(state) {
            case 1:
                outState = 3;
                break;
            default:
                outState = 0;
                break;
        }
        handleState(outState);
    }

    @Override
    public void setOWMApiRunnableThread(Thread currentThread) {
        setCurrentThread(currentThread);
    }

    @Override
    public void handleOWMApiRunnableState(int state) {
        int outState;
        switch(state) {
            case 1:
                outState = 4;
                break;
            default:
                outState = 0;
                break;
        }
        handleState(outState);
    }
}
