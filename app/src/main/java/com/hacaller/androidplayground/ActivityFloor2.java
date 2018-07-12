package com.hacaller.androidplayground;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ActivityFloor2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_floor2);

        TextView txtCores = (TextView) findViewById(R.id.txt_core_number);

        int cores = Runtime.getRuntime().availableProcessors();
        txtCores.setText(String.format("Number of Cores: %d",cores));

        Button btnLaunch = (Button) findViewById(R.id.btn_launch);
        final TextView textView1 = (TextView) findViewById(R.id.txt_result1);
        final TextView textView2 = (TextView) findViewById(R.id.txt_result2);
        final TextView textView3 = (TextView) findViewById(R.id.txt_result3);
        final TextView textView4 = (TextView) findViewById(R.id.txt_result4);

        btnLaunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoolApisManager.startTask(textView1,textView2,textView3,textView4);
            }
        });

        Button btnNext = (Button) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new NextOnClickListener());

    }

    private class NextOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), ActivityFloor3.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
    }

}
