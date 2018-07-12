package com.hacaller.androidplayground;

import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnLaunch = (Button) findViewById(R.id.btn_launch);

        String title = "AndroidPlayground";
        String message = "H.M.S. has been sunk.";

        btnLaunch.setOnClickListener(new LaunchOnClickListener(title, message));

        Button btnNext = (Button) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new NextOnClickListener());

        if  (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.GET_RESOLVED_FILTER
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.GET_RESOLVED_FILTER)
        {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION},
                    21);
        }

    }


    private class NextOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), ActivityFloor1.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
    }

    private class LaunchOnClickListener implements View.OnClickListener {

        String title;
        String message;

        public LaunchOnClickListener(String title, String message){
            this.title = title;
            this.message = message;
        }

        @Override
        public void onClick(View v) {
            launchNotification(title, message);
        }
    }

    @SuppressWarnings("deprecation")
    private void launchNotification(String title, String message){
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        Notification notification = new Notification.Builder(this)
                .setContentText(message)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .addAction(android.R.drawable.ic_dialog_alert, title, pendingIntent)
                .build();

        notificationManager.notify(0,notification);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED)
        {}
    }
}
