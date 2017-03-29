package com.kite.diandi.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.preference.Preference;

import com.bumptech.glide.Glide;
import com.kite.diandi.R;

import java.lang.ref.WeakReference;

/**
 * Created by 10648 on 2017/3/29 0029.
 */

public class SettingsPresenter implements SettingContract.Presenter {
    private static final int CLEAR_GLIDE_CACHE_DONE = 1;

    private Context context;
    private SettingContract.View view;
    private SharedPreferences sharedPreferences;

    private CleanGlideCacheHandler handler;




    public SettingsPresenter(Context context, SettingContract.View view) {
        this.context = context;
        this.view = view;
        handler = new CleanGlideCacheHandler(view);
        this.view.setPresenter(this);
        sharedPreferences = context.getSharedPreferences("user_settings", Context.MODE_PRIVATE);
    }

    @Override
    public void setNoPictureMode(Preference preference) {
        sharedPreferences.edit().putBoolean("no_picture_mode",
                preference.getSharedPreferences().getBoolean("no_picture_mode", false))
        .apply();
    }

    @Override
    public void setInAppBrowser(Preference preference) {
        sharedPreferences.edit().putBoolean("in_app_browser",
                preference.getSharedPreferences().getBoolean("in_app_browser", false))
                .apply();
    }

    @Override
    public void cleanGlideCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
                Message msg = new Message();
                msg.what = CLEAR_GLIDE_CACHE_DONE;
                handler.sendMessage(msg);
            }
        }).start();
        Glide.get(context).clearMemory();
    }

    @Override
    public void setTimeOfSavingArticles(Preference preference,  Object newValue) {
        sharedPreferences.edit()
                .putString("time_of_saving_articles",
                        (String) newValue)
                .apply();
    }

    @Override
    public String getTimeSummary() {
        String[] options = context.getResources().getStringArray(
                R.array.time_to_save_opts
        );
        String str = sharedPreferences.getString("time_of_saving_articles", "7");
        switch (str) {
            case "1" :
                return options[0];
            case "3":
                return options[1];
            case "15":
                return options[3];
            default:
                return options[2];
        }
    }

    private static class CleanGlideCacheHandler extends Handler {

        private final WeakReference<SettingContract.View> viewWeakReference;

        CleanGlideCacheHandler(SettingContract.View view) {
            viewWeakReference = new WeakReference<>(view);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == CLEAR_GLIDE_CACHE_DONE) {
                SettingContract.View view = viewWeakReference.get();
                view.showCleanGlideCacheDone();
            }

        }
    }
}
