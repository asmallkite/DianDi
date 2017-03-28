package com.kite.diandi.detail;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.customtabs.CustomTabsIntent;
import android.webkit.WebView;

import com.android.volley.Network;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kite.diandi.R;
import com.kite.diandi.bean.BeanType;
import com.kite.diandi.bean.DoubanMomentNews;
import com.kite.diandi.bean.DoubanMomentStory;
import com.kite.diandi.bean.StringModelImpl;
import com.kite.diandi.customtabs.CustomTabActivityHelper;
import com.kite.diandi.db.DatabaseHelper;
import com.kite.diandi.interfaze.OnStringListener;
import com.kite.diandi.util.Api;
import com.kite.diandi.util.NetworkState;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by 10648 on 2017/3/28 0028.
 */

public class DetailPresenter implements DetailContract.Presenter {

    private DetailContract.View view;
    private StringModelImpl model;
    private Context context;

    private DoubanMomentStory doubanMomentStory;

    private SharedPreferences sp;
    private DatabaseHelper dbHelper;

    private Gson gson;

    private BeanType type;
    private int id;
    private String title;
    private String coverUrl;

    public void setType(BeanType type) {
        this.type = type;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public DetailPresenter(@NonNull Context context, DetailContract.View view) {
        this.view = view;
        this.context = context;
        this.view.setPresenter(this);
        model = new StringModelImpl(context);
        sp = context.getSharedPreferences("user_settings", MODE_PRIVATE);
        dbHelper = new DatabaseHelper(context, "History.db", null, 1);
        gson = new Gson();
    }

    @Override
    public void start() {

    }

    @Override
    public void openInBrowser() {

    }

    @Override
    public void shareAsText() {

    }

    @Override
    public void openUrl(WebView webView, String url) {
        if (sp.getBoolean("in_app_browser", true)) {
            CustomTabsIntent.Builder customTabsIntent = new CustomTabsIntent.Builder()
                    .setToolbarColor(context.getResources().getColor(R.color.colorAccent))
                    .setShowTitle(true);
            CustomTabActivityHelper.openCustomTab((Activity) context, customTabsIntent.build(),
                    Uri.parse(url), new CustomTabActivityHelper.CustomTabFallback() {
                        @Override
                        public void openUri(Activity activity, Uri uri) {

                        }
                    });
        }

    }

    @Override
    public void copyText() {

    }

    @Override
    public void copyLink() {

    }

    @Override
    public void addToOrDeleteFromBookmarks() {

    }

    @Override
    public boolean queryIfIsBookmarked() {
        return false;
    }

    @Override
    public void requestData() {
        if (id == 0 || type == null) {
            view.showLoadingError();
            return;
        }

        view.showLoading();
        view.showTitle(title);
        view.showCover(coverUrl);

        view.setImageMode(sp.getBoolean("no_picture_mode", false));

        switch (type) {
            case TYPE_DOUBAN:
                if (NetworkState.networkConnected(context)) {
                    model.load(Api.DOUBAN_ARTICLE_DETAIL + id, new OnStringListener() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                doubanMomentStory = gson.fromJson(result, DoubanMomentStory.class);
                                view.showResult(convertDoubanContent());
                            } catch (JsonSyntaxException e) {
                                view.showLoadingError();
                            }
                        }

                        @Override
                        public void onError(VolleyError error) {
                                view.showLoadingError();
                        }
                    });
                } else {
                    Cursor cursor = dbHelper.getReadableDatabase()
                            .rawQuery("select douban_content from Douban where douban_id = " + id, null);
                    if (cursor.moveToFirst()) {
                        do {
                            if (cursor.getCount() == 1) {
                                doubanMomentStory = gson.fromJson(cursor.getString(0), DoubanMomentStory.class);
                                view.showResult(convertDoubanContent());
                            }
                        } while (cursor.moveToNext());
                    }
                    cursor.close();
                }
                break;
        }
        view.stopLoading();

    }


    private String convertDoubanContent() {

        if (doubanMomentStory.getContent() == null) {
            return null;
        }
        String css;
        if ((context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES) {
            css = "<link rel=\"stylesheet\" href=\"file:///android_asset/douban_dark.css\" type=\"text/css\">";
        } else {
            css = "<link rel=\"stylesheet\" href=\"file:///android_asset/douban_light.css\" type=\"text/css\">";
        }
        String content = doubanMomentStory.getContent();
        ArrayList<DoubanMomentNews.posts.thumbs> imageList = doubanMomentStory.getPhotos();
        for (int i = 0; i < imageList.size(); i++) {
            String old = "<img id=\"" + imageList.get(i).getTag_name() + "\" />";
            String newStr = "<img id=\"" + imageList.get(i).getTag_name() + "\" "
                    + "src=\"" + imageList.get(i).getMedium().getUrl() + "\"/>";
            content = content.replace(old, newStr);
        }
        StringBuilder builder = new StringBuilder();
        builder.append( "<!DOCTYPE html>\n");
        builder.append("<html lang=\"ZH-CN\" xmlns=\"http://www.w3.org/1999/xhtml\">\n");
        builder.append("<head>\n<meta charset=\"utf-8\" />\n");
        builder.append(css);
        builder.append("\n</head>\n<body>\n");
        builder.append("<div class=\"container bs-docs-container\">\n");
        builder.append("<div class=\"post-container\">\n");
        builder.append(content);
        builder.append("</div>\n</div>\n</body>\n</html>");

        return builder.toString();
    }
}
