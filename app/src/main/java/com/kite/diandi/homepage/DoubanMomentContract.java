package com.kite.diandi.homepage;

import com.kite.diandi.base.BasePresenter;
import com.kite.diandi.base.BaseView;
import com.kite.diandi.bean.DoubanMomentNews;

import java.util.ArrayList;

/**
 * Created by 10648 on 2017/3/16 0016.
 */

public class DoubanMomentContract {

    interface Presenter extends BasePresenter {

        void startReading(int position);

        void loadPosts(long date, boolean clearing);

        void refresh();

        void loadMore(long date);

        void feelLucky();

    }

    interface View extends BaseView<Presenter> {
        void startLoading();

        void stopLoading();

        void showLoadingError();

        void showResults(ArrayList<DoubanMomentNews.posts> list);
    }

}
