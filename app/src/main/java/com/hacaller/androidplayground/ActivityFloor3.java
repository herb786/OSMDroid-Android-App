package com.hacaller.androidplayground;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityFloor3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_floor3);

        Button btnLaunch = (Button) findViewById(R.id.btn_launch);
        ImageView imgTest = (ImageView) findViewById(R.id.img_test);

        btnLaunch.setOnClickListener(new LaunchOnClickListener(imgTest));

        Button btnNext = (Button) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new NextOnClickListener());

    }


    private class LaunchOnClickListener implements View.OnClickListener {

        ImageView imageView;

        public LaunchOnClickListener(ImageView imageView){
            this.imageView = imageView;
        }

        @Override
        public void onClick(View v) {
            String src = String.format("android.resource://%s/drawable/top_cat",getPackageName());
            Log.d("AAA",src);
            Uri imageUri = Uri.parse(src);
            Picasso.get().load(imageUri).fit().centerCrop()
                    .placeholder(android.R.drawable.ic_dialog_email)
                    .error(android.R.drawable.ic_lock_idle_alarm)
                    .into(imageView);
        }
    }


    private class NextOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), ActivityFloor4.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
