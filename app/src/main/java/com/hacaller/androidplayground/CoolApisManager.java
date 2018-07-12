package com.hacaller.androidplayground;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by Herbert Caller on 25/04/2016.
 */
public class CoolApisManager {


    TextView t1, t2, t3, t4;

    // A queue of Runnables for the json task pool
    private final BlockingQueue<Runnable> runnables;

    // A queue of tasks. Tasks are handed to a ThreadPool.
    private final Queue<CoolApis> queue;

    // A managed pool of background json task threads
    public final ThreadPoolExecutor threadPool;

    // An object that manages Messages in a Thread
    private Handler mHandler;

    // A single instance of CoolApisManager, used to implement the singleton pattern
    private static CoolApisManager sInstance = null;


    static {
        sInstance = new CoolApisManager();
    }

    public static CoolApisManager getInstance() {
        return sInstance;
    }


    private CoolApisManager() {

        runnables = new LinkedBlockingQueue<Runnable>();

        queue = new LinkedBlockingQueue<CoolApis>();

        threadPool = new ThreadPoolExecutor(4, 8, 1, TimeUnit.SECONDS, runnables);

        /*
         * Instantiates a new anonymous Handler object and defines its
         * handleMessage() method. The Handler *must* run on the UI thread.
         * Since the View runs on the UI Thread, so does the constructor and the Handler.
         */
        mHandler = new Handler(Looper.getMainLooper()) {

            /*
             * handleMessage() defines the operations to perform when the
             * Handler receives a new Message to process.
             */
            @Override
            public void handleMessage(Message inputMessage) {

                // Gets the task from the incoming Message object.
                CoolApis coolApis = (CoolApis) inputMessage.obj;

                t1 = coolApis.getT1();
                t2 = coolApis.getT2();
                t3 = coolApis.getT3();
                t4 = coolApis.getT4();

                switch (inputMessage.what) {
                    case 1:
                        t1.setText(String.format("Nasa API %s","OK"));
                        break;
                    case 2:
                        t2.setText(String.format("Quandl API %s","OK"));
                        break;
                    case 3:
                        t3.setText(String.format("NYT API %s","OK"));
                        break;
                    case 4:
                        t4.setText(String.format("OWM API %s","OK"));
                        break;
                    default:
                        super.handleMessage(inputMessage);
                        break;
                }
            }
        };


    }


    @SuppressLint("HandlerLeak")
    public void handleState(CoolApis coolApis, int state) {
        t1 = coolApis.getT1();
        t2 = coolApis.getT2();
        t3 = coolApis.getT3();
        t4 = coolApis.getT4();
        Message message = mHandler.obtainMessage(state, coolApis);
        message.sendToTarget();
    }


    static public CoolApis startTask(TextView t1, TextView t2, TextView t3, TextView t4) {

        CoolApis coolApis1 = sInstance.queue.poll();
        CoolApis coolApis2 = null;

        // If the queue was empty, create a new task instead.
        if (null == coolApis1) {
            coolApis1 = new CoolApis();
            coolApis2 = new CoolApis();
        }


        // Initializes the task
        coolApis1.initializeTask(CoolApisManager.sInstance,t1,t2,t3,t4);
        coolApis2.initializeTask(CoolApisManager.sInstance,t1,t2,t3,t4);
        sInstance.threadPool.execute(coolApis1.getNasaApiRunnable());
        sInstance.threadPool.execute(coolApis2.getNYTApiRunnable());
        sInstance.threadPool.execute(coolApis1.getOMWApiMethods());
        sInstance.threadPool.execute(coolApis2.getQuandlApiRunnable());
        //sInstance.handleState(coolApis, 0);

        return coolApis1;
    }


    public static void cancelAll() {

        CoolApis[] coolApis = new CoolApis[sInstance.queue.size()];;
        sInstance.queue.toArray(coolApis);

        /*
         * Locks on the singleton to ensure that other processes aren't mutating Threads, then
         * iterates over the array of tasks and interrupts the task's current Thread.
         */
        synchronized (sInstance) {
                // Gets the task's current thread
                Thread thread = coolApis[0].getCurrentThread();
                // if the Thread exists, post an interrupt to it
                if (null != thread) {
                    thread.interrupt();
                }
        }
    }


    void recycleTask(CoolApis coolApis) {
        // Frees up memory in the task
        coolApis.recycle();
        // Puts the task object back into the queue for re-use.
        queue.offer(coolApis);
    }


}
