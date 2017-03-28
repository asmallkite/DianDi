package com.kite.diandi.detail;

import android.webkit.WebView;

import com.kite.diandi.base.BasePresenter;
import com.kite.diandi.base.BaseView;

/**
 * Created by 10648 on 2017/3/28 0028.
 */

public class DetailContract {

    interface View extends BaseView<Presenter> {

        void showLoading();

        void stopLoading();

        void showLoadingError();

        void showSharingError();

        void showResult(String result);

        void showResultWithoutBody(String url);

        void showCover(String url);

        void showTitle(String title);

        void setImageMode(boolean showImage);

        void showBrowserNotFoundError();

        void showTextCopied();

        void showCopyTextError();

        void showAddedToBookmarks();

        void showDeletedFromBookmarks();

    }

    interface Presenter extends BasePresenter {

        void openInBrowser();

        void shareAsText();

        void openUrl(WebView webView, String url);

        void copyText();

        void copyLink();

        void addToOrDeleteFromBookmarks();

        boolean queryIfIsBookmarked();

        void requestData();

    }
}
