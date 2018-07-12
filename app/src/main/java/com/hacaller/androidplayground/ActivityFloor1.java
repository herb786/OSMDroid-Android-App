package com.hacaller.androidplayground;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import org.apache.commons.io.output.ByteArrayOutputStream;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ActivityFloor1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_floor1);

        Button btnLaunch = (Button) findViewById(R.id.btn_launch);

        String message = "H.M.S. has been sunk.";
        btnLaunch.setOnClickListener(new LaunchOnClickListener(message));

        Button btnNext = (Button) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new NextOnClickListener());

    }


    private class NextOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), ActivityFloor2.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
    }


    private class LaunchOnClickListener implements View.OnClickListener {

        String message;

        public LaunchOnClickListener(String message){
            this.message = message;
        }

        @Override
        public void onClick(View v) {
            try {
                InputStream inputStream = getResources().openRawResource(R.raw.top_cat);
                byte[] bytes = new byte[inputStream.available()];
                inputStream.read(bytes);
                File folder = new File(getFilesDir(), "/images");
                if (!folder.exists()){
                    folder.mkdir();
                }
                File file = new File(getFilesDir(), "/images/topcat.jpg");
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(bytes);
                outputStream.close();
                inputStream.close();

                Uri fileUri = FileProvider.getUriForFile(ActivityFloor1.this, "com.hacaller.androidplayground.fileprovider", file);
                Intent sendTo = new Intent(Intent.ACTION_SEND);
                sendTo.setType("*/*");
                //sendTo.setDataAndType(fileUri, getContentResolver().getType(fileUri));
                sendTo.putExtra(Intent.EXTRA_EMAIL, new String[]{"herbertacg@gmail.com"});
                sendTo.putExtra(Intent.EXTRA_SUBJECT, "Test SendTo");
                sendTo.putExtra(Intent.EXTRA_TEXT, "Empty content");
                sendTo.putExtra(Intent.EXTRA_STREAM, fileUri);
                sendTo.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(sendTo);
            } catch (Exception e){
                e.getStackTrace();
                e.getMessage();
             }
        }
    }


}
