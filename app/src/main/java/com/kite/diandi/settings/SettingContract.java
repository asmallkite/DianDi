package com.kite.diandi.settings;

import android.support.v7.preference.Preference;

import com.kite.diandi.base.BaseView;

/**
 * Created by 10648 on 2017/3/29 0029.
 */

public interface SettingContract {

    interface Presenter {

        void setNoPictureMode(Preference preference);

        void setInAppBrowser(Preference preference);

        void cleanGlideCache();

        void setTimeOfSavingArticles(Preference preference, Object newValue);

        String getTimeSummary();
    }

    interface View extends BaseView<Presenter> {

        void showCleanGlideCacheDone();
    }
}
