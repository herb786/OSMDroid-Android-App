package com.hacaller.androidplayground;

import android.app.Application;

import org.acra.ACRA;
import org.acra.ReportingInteractionMode;
import org.acra.annotation.ReportsCrashes;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Herbert Caller on 25/04/2016.
 */

@ReportsCrashes(formUri = "",
        mailTo = "herbertacg@gmail.com",
        mode = ReportingInteractionMode.TOAST,
        resToastText = R.string.crash_toast_text)

public class PlaygroundApplication extends Application {

    public static String mAgent = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.112 Safari/537.36";

    @Override
    public void onCreate() {
        ACRA.init(this);
        super.onCreate();
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Amaranth-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }

}
