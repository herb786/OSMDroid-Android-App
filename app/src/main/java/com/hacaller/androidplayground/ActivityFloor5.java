package com.hacaller.androidplayground;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityFloor5 extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_floor5);

        Button btnNext = (Button) findViewById(R.id.btn_next);
        assert btnNext != null;
        btnNext.setOnClickListener(new NextOnClickListener());

        String ticker = "AAPL";
        Button btnGson = (Button) findViewById(R.id.btn_gson);
        Button btnJackson = (Button) findViewById(R.id.btn_jackson);
        final TextView txtContent = (TextView) findViewById(R.id.txt_content);
        assert btnGson != null;
        btnGson.setOnClickListener(new LaunchOnClickListener(ticker, "gson", txtContent));
        assert btnJackson != null;
        btnJackson.setOnClickListener(new LaunchOnClickListener(ticker, "jackson", txtContent));

        Button btnClear = (Button) findViewById(R.id.btn_clear);
        assert btnClear != null;
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert txtContent != null;
                txtContent.setText("");
            }
        });
    }

    private class LaunchOnClickListener implements View.OnClickListener {

        String ticker;
        String flag;
        TextView textView;

        public LaunchOnClickListener(String ticker, String flag, TextView textView) {
            this.ticker = ticker;
            this.textView = textView;
            this.flag = flag;
        }

        @Override
        public void onClick(View v) {
            String[] params = new String[]{ticker,flag};
            MarkitApiTest test = new MarkitApiTest(textView);
            test.execute(params);
        }
    }


    private class NextOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), ActivityFloor6.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
    }


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    public class MarkitApiTest extends AsyncTask<String, Void, Void> {

        TextView textView;
        StockQuote stockQuote;

        public MarkitApiTest(TextView textView) {
            this.textView = textView;
        }

        @Override
        protected Void doInBackground(String... params) {
            String ticker = params[0];
            String flag = params[1];
            if (flag.equals("jackson")) {
                stockQuote = getStockQuotePOJO(ticker);
            } else {
                stockQuote = getStockQuote(ticker);
            }
            return null;
        }

        @SuppressLint("DefaultLocale")
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            textView.setText("");
            SpannableStringBuilder ssb = new SpannableStringBuilder();
            int pivot = 0;
            String line = String.format("Name: %s\n", stockQuote.getName());
            pivot = setStyle(ssb, line, pivot, 1);
            line = String.format("Symbol: %s\n", stockQuote.getSymbol());
            pivot = setStyle(ssb, line, pivot, 2);
            line = String.format("Last price: %f\n", stockQuote.getLastPrice());
            pivot = setStyle(ssb, line, pivot, 1);
            line = String.format("Change: %f\n", stockQuote.getChange());
            pivot = setStyle(ssb, line, pivot, 2);
            line = String.format("Change pct: %f\n", stockQuote.getChangePercent());
            pivot = setStyle(ssb, line, pivot, 1);
            line = String.format("Timestamp: %s\n", stockQuote.getTimestamp());
            pivot = setStyle(ssb, line, pivot, 2);
            line = String.format("MSdate: %f\n", stockQuote.getMSdate());
            pivot = setStyle(ssb, line, pivot, 1);
            line = String.format("Market cap: %f\n", stockQuote.getMarketCap());
            pivot = setStyle(ssb, line, pivot, 2);
            line = String.format("Volume: %d\n", stockQuote.getVolume());
            pivot = setStyle(ssb, line, pivot, 1);
            line = String.format("Change YTD: %f\n", stockQuote.getChangeYTD());
            pivot = setStyle(ssb, line, pivot, 2);
            line = String.format("Change YTD pct: %f\n", stockQuote.getChangePercentYTD());
            pivot = setStyle(ssb, line, pivot, 1);
            line = String.format("High: %f\n", stockQuote.getHigh());
            pivot = setStyle(ssb, line, pivot, 2);
            line = String.format("Low: %f\n", stockQuote.getLow());
            pivot = setStyle(ssb, line, pivot, 1);
            line = String.format("Open: %f\n", stockQuote.getOpen());
            setStyle(ssb, line, pivot, 2);
            textView.setText(ssb);

        }
    }

    private int setStyle(SpannableStringBuilder ssb, String line, int pivot, int flag) {
        if (flag == 1) {
            ssb.append(line).setSpan(new TextAppearanceSpan(getApplicationContext(), R.style.style1), pivot, pivot + line.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return pivot + line.length();
        } else {
            ssb.append(line).setSpan(new TextAppearanceSpan(getApplicationContext(), R.style.style2), pivot, pivot + line.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return pivot + line.length();
        }
    }


    private StockQuote getStockQuote(String ticker) {
        String baseUrl = "http://dev.markitondemand.com/MODApis/Api/v2/Quote/json?";
        String query = "symbol=" + ticker;
        String myUrl = baseUrl + query;
        GsonBuilder builder = new GsonBuilder();
        Gson gson = builder.create();
        URL thisURL;
        HttpURLConnection thisConnection;
        try {
            thisURL = new URL(myUrl);
            thisConnection = (HttpURLConnection) thisURL.openConnection();
            thisConnection.setRequestMethod("GET");
            thisConnection.setRequestProperty("User-Agent", PlaygroundApplication.mAgent);
            Log.i("AAA",String.format("Answer: %d",thisConnection.getResponseCode()));
            InputStream inputStream = thisConnection.getInputStream();
            return gson.fromJson(IOUtils.toString(inputStream), StockQuote.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    private StockQuote getStockQuotePOJO(String ticker) {
        String baseUrl = "http://dev.markitondemand.com/MODApis/Api/v2/Quote/json?";
        String query = "symbol=" + ticker;
        String myUrl = baseUrl + query;
        ObjectMapper mapper = new ObjectMapper();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        try {
            URL url = new URL(myUrl);
            /*
            InputStream in = url.openStream();
            StringBuffer stringBuffer = new StringBuffer();
            char[] chars = new char[1024];
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            while (reader.read(chars,0,1024) > 0) {
                reader.read(chars);
                stringBuffer.append(chars);
            }
            Log.d("JACKSON",stringBuffer.toString());
            */
            StockQuote quote =  mapper.readValue(url, StockQuote.class);
            return quote;
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("ERROR",e.getMessage());
            return new StockQuote();
        }
    }


}
