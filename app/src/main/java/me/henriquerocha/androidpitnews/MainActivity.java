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
import android.os.Bundle;
import android.widget.TextView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        final TextView textView = (TextView) findViewById(R.id.rss_feed);

        new Thread() {
            @Override
            public void run() {
                try {
                    Request request = new Request.Builder()
                            .url("http://www.androidpit.com/feed/main.xml")
                            .build();

                    OkHttpClient client = new OkHttpClient();
                    Response response = client.newCall(request).execute();
                    final String rssFeed = response.body().string();

                    runOnUiThread(
                            new Runnable() {
                                @Override
                                public void run() {
                                    textView.setText(rssFeed);
                                }
                            });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

}
