package com.kite.diandi.homepage;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.LocalBroadcastManager;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.kite.diandi.bean.BeanType;
import com.kite.diandi.bean.DoubanMomentNews;
import com.kite.diandi.bean.StringModelImpl;
import com.kite.diandi.db.DatabaseHelper;
import com.kite.diandi.detail.DetailActivity;
import com.kite.diandi.interfaze.OnStringListener;
import com.kite.diandi.service.CacheService;
import com.kite.diandi.util.Api;
import com.kite.diandi.util.DateFormatter;
import com.kite.diandi.util.NetworkState;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by 10648 on 2017/3/16 0016.
 */

public class DoubanMomentPresenter implements DoubanMomentContract.Presenter {

    private DoubanMomentContract.View view;
    private Context context;
    private StringModelImpl model;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private Gson gson = new Gson();

    private ArrayList<DoubanMomentNews.posts> list = new ArrayList<>();


    public DoubanMomentPresenter(DoubanMomentContract.View view, Context context) {
        this.view = view;
        this.context = context;
        this.view.setPresenter(this);
        model = new StringModelImpl(context);
        dbHelper = new DatabaseHelper(context, "History.db", null, 1);
        db = dbHelper.getWritableDatabase();
    }

    @Override
    public void start() {
        refresh();
    }

    @Override
    public void startReading(int position) {
        DoubanMomentNews.posts item = list.get(position);
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("type", BeanType.TYPE_DOUBAN);
        intent.putExtra("id", item.getId());
        intent.putExtra("title", item.getTitle());
        if (item.getThumbs().size() == 0) {
            intent.putExtra("coverUrl", "");
        } else {
            intent.putExtra("coverUrl", item.getThumbs().get(0).getMedium().getUrl());
        }
        context.startActivity(intent);

    }

    @Override
    public void loadPosts(long date, final boolean clearing) {
        if (clearing) {
            view.startLoading();
        }
        if (NetworkState.networkConnected(context)) {
            model.load(Api.DOUBAN_MOMENT + new DateFormatter().DoubanDateFormat(date),new OnStringListener() {

                @Override
                public void onSuccess(String result) {
                    try {
                        DoubanMomentNews post = gson.fromJson(result, DoubanMomentNews.class);
                        ContentValues values = new ContentValues();

                        if (clearing) {
                            list.clear();
                        }

                        for (DoubanMomentNews.posts item : post.getPosts()) {

                            list.add(item);

                            if ( !queryIfIDExists(item.getId())) {
                                db.beginTransaction();
                                try {
                                    values.put("douban_id", item.getId());
                                    values.put("douban_news", gson.toJson(item));
                                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                                    Date date = format.parse(item.getPublished_time());
                                    values.put("douban_time", date.getTime() / 1000);
                                    values.put("douban_content", "");
                                    db.insert("Douban", null, values);
                                    values.clear();
                                    db.setTransactionSuccessful();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                } finally {
                                    db.endTransaction();
                                }
                            }
                            Intent intent = new Intent("com.marktony.zhihudaily.LOCAL_BROADCAST");
                            intent.putExtra("type", CacheService.TYPE_DOUBAN);
                            intent.putExtra("id", item.getId());
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                        }
                        view.showResults(list);
                    } catch (JsonSyntaxException e) {
                        view.showLoadingError();
                    }

                    view.stopLoading();

                }

                @Override
                public void onError(VolleyError error) {
                    view.stopLoading();
                    view.showLoadingError();
                }
            });

        } else {
            if (clearing) {
                list.clear();

                Cursor cursor = db.query("Douban", null, null, null, null, null, null);
                if (cursor.moveToFirst()) {
                    do {
                        DoubanMomentNews.posts post = gson.fromJson(cursor.getString(cursor.getColumnIndex("douban_news")), DoubanMomentNews.posts.class);
                        list.add(post);
                    } while (cursor.moveToNext());
                }
                cursor.close();
                view.stopLoading();
                view.showResults(list);
            }
        }


    }

    @Override
    public void refresh() {
        loadPosts(Calendar.getInstance().getTimeInMillis(), true);
    }

    @Override
    public void loadMore(long date) {
        loadPosts(date, false);
    }

    @Override
    public void feelLucky() {
        if (list.isEmpty()) {
            view.showLoadingError();
            return;
        }
        startReading(new Random().nextInt(list.size()));
    }

    private boolean queryIfIDExists(int id){
        Cursor cursor = db.query("Douban",null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                if (id == cursor.getInt(cursor.getColumnIndex("douban_id"))){
                    return true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();

        return false;
    }
}
