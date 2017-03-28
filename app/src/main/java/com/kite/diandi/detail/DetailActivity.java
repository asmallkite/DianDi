package com.kite.diandi.detail;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.kite.diandi.R;
import com.kite.diandi.bean.BeanType;

public class DetailActivity extends AppCompatActivity {
    private static final String TAG = "DetailActivity";

    private DetailFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame);

        if (savedInstanceState != null) {
            fragment = (DetailFragment) getSupportFragmentManager().getFragment(savedInstanceState, "detailFragment");
        } else {
            fragment = new DetailFragment();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();

        Intent intent = getIntent();

        DetailPresenter presenter = new DetailPresenter(this, fragment);

        presenter.setType((BeanType) intent.getSerializableExtra("type"));
        presenter.setCoverUrl(intent.getStringExtra("coverUrl"));
        presenter.setTitle(intent.getStringExtra("title"));
        presenter.setId(intent.getIntExtra("id", 0));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragment.isAdded()) {
            getSupportFragmentManager().putFragment(outState, "detailFragment", fragment);
        }
    }
}
