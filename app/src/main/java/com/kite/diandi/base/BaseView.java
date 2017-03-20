package com.kite.diandi.base;

import android.view.View;

/**
 * Created by 10648 on 2017/3/16 0016.
 */

public interface BaseView<T> {

    void setPresenter(T presenter);


    void initView(View view);
}
