/*
 * Copyright 2017 lizhaotailang
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kite.diandi.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.kite.diandi.R;
import com.kite.diandi.homepage.DoubanMomentFragment;


public class MainPagerAdapter extends FragmentPagerAdapter {

    private String[] titles;
    private final Context context;

    private DoubanMomentFragment doubanFragment;


    public DoubanMomentFragment getDoubanFragment() {
        return doubanFragment;
    }

    public MainPagerAdapter(FragmentManager fm,
                            Context context,
                            DoubanMomentFragment doubanMomentFragment) {
        super(fm);
        this.context = context;
        titles = new String[] {
                context.getResources().getString(R.string.douban_moment)
        };

        this.doubanFragment = doubanMomentFragment;

    }

    @Override
    public Fragment getItem(int position) {

        return doubanFragment;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}
