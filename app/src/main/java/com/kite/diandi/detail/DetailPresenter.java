package com.kite.diandi.detail;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.webkit.WebView;

import com.google.gson.Gson;
import com.kite.diandi.bean.BeanType;
import com.kite.diandi.bean.DoubanMomentStory;
import com.kite.diandi.bean.StringModelImpl;
import com.kite.diandi.db.DatabaseHelper;

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

    }
}
