/*
 * Copyright (C) 2015 Henrique Rocha
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.henriquerocha.androidpitnews;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener {

    private List<AndroidPitRssParser.Item> items = new ArrayList<>();
    private ArrayAdapter<AndroidPitRssParser.Item> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        adapter = new ArrayAdapter<>(this, R.layout.rss_feed_item, items);

        ListView listView = (ListView) findViewById(R.id.feed_listview);
        listView.setOnItemClickListener(this);
        listView.setAdapter(adapter);

        new GetFeedTask().execute();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        startActivity(ItemDetailActivity.getStartIntent(this, adapter.getItem(position)));
    }

    private class GetFeedTask extends AsyncTask<Void, Void, List<AndroidPitRssParser.Item>> {

        @Override
        protected List<AndroidPitRssParser.Item> doInBackground(Void... params) {
            try {
                Request request = new Request.Builder()
                        .url("http://www.androidpit.com/feed/main.xml")
                        .build();

                Response response = new OkHttpClient().newCall(request).execute();
                List<AndroidPitRssParser.Item> items = null;
                try {
                    items = new AndroidPitRssParser().parse(response.body().byteStream());
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                }
                return items;

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<AndroidPitRssParser.Item> items) {
            if (items != null) {
                adapter.clear();
                adapter.addAll(items);
            }
        }
    }
}
