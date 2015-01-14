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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class ItemDetailActivity extends Activity {

    private static final String ITEM_EXTRA = "item";

    public static Intent getStartIntent(Context context, AndroidPitRssParser.Item item) {
        Intent intent = new Intent(context, ItemDetailActivity.class);
        intent.putExtra(ITEM_EXTRA, item);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);

        TextView itemDetail = (TextView) findViewById(R.id.item_detail);

        AndroidPitRssParser.Item item =
                (AndroidPitRssParser.Item) getIntent().getSerializableExtra(ITEM_EXTRA);
        if (item != null) {
            itemDetail.setText(item.toString());
        }
    }
}
