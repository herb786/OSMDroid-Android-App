package com.hacaller.androidplayground;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndEntry;
import com.google.code.rome.android.repackaged.com.sun.syndication.feed.synd.SyndFeed;
import com.google.code.rome.android.repackaged.com.sun.syndication.fetcher.FeedFetcher;
import com.google.code.rome.android.repackaged.com.sun.syndication.fetcher.FetcherException;
import com.google.code.rome.android.repackaged.com.sun.syndication.fetcher.impl.HttpURLFeedFetcher;
import com.google.code.rome.android.repackaged.com.sun.syndication.io.FeedException;

import java.io.IOException;
import java.net.URL;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ActivityFloor6 extends AppCompatActivity {

    @BindView(R.id.btn_next) Button btnNext;
    @BindView(R.id.btn_launch) Button btnLaunch;
    @BindView(R.id.txt_title) TextView txtTitle;
    @BindView(R.id.txt_author) TextView txtAuthor;
    @BindView(R.id.txt_date) TextView txtDate;
    @BindView(R.id.txt_content) TextView txtContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_floor6);
        ButterKnife.bind(this);

        btnNext.setOnClickListener(new NextOnClickListener());
        btnLaunch.setOnClickListener(new LaunchOnClickListener(txtContent));

    }

    private class LaunchOnClickListener implements View.OnClickListener {

        TextView textView;

        public LaunchOnClickListener(TextView textView) {
            this.textView = textView;
        }

        @Override
        public void onClick(View v) {
            ArxivApiTest task = new ArxivApiTest(textView);
            task.execute();
        }
    }

    private class NextOnClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(), ActivityFloor1.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }


    private class ArxivApiTest extends AsyncTask<Void,Void,Void>{

        SyndFeed syndFeed;
        TextView textView;

        public ArxivApiTest(TextView textView){
            this.textView = textView;
        }

        @Override
        protected Void doInBackground(Void... params) {
            syndFeed = getAtomFeed();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if (syndFeed != null) {
                SyndEntry entry = (SyndEntry) syndFeed.getEntries().get(0);
                txtTitle.setText(entry.getTitle());
                txtAuthor.setText(entry.getAuthor());
                txtDate.setText(entry.getPublishedDate().toString());
                txtContent.setText(entry.getDescription().getValue());
            }
        }
    }


    private SyndFeed getAtomFeed(){
        //String baseUrl = "http://export.arxiv.org/api/query?search_query=all:polaritons&start=0&max_results=10";
        //String baseUrl = "http://export.arxiv.org/api/query?search_query=au:sachdev+AND+ti:%22quantum+criticality%22";
        String baseUrl = "http://export.arxiv.org/api/query?search_query=%22quantum+phase+transition%22+AND+cat:%20quant-ph&sortBy=submittedDate&sortOrder=descending";
        String query = "";
        String myUrl = baseUrl + query;
        URL feedUrl;
        try {
            feedUrl = new URL(myUrl);
            //SyndFeedInput input = new SyndFeedInput();
            //SyndFeed feed = input.build(new XmlReader(feedUrl));
            FeedFetcher feedFetcher = new HttpURLFeedFetcher();
            SyndFeed syndFeed = feedFetcher.retrieveFeed( feedUrl );
            return syndFeed;
        } catch (IOException | FeedException | FetcherException | IllegalArgumentException e) {
            e.printStackTrace();
            Log.d("ERROR",e.getMessage());
            return null;
        }
    }

}
