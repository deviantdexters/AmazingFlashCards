package com.sahilguptalive.amazingflashcard;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;

import com.sahilguptalive.amazingflashcard.dbmanager.BookmarkedWordDbManager;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created on 21-05-2017.
 */
public class FlashViewerActivity
        extends BaseActivity {

    @BindView(R.id.view_pager)
    ViewPager mViewPager;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_card_viewer_activity);
        ButterKnife.bind(this);
        final FlashFragmentAdapter adapter = new FlashFragmentAdapter(mFragmentManager);
        mViewPager.setAdapter(adapter);
        adapter.addWords(BookmarkedWordDbManager.getAllWords());
        adapter.notifyDataSetChanged();
    }

    static Intent createIntent(final Context context) {
        return new Intent(context, FlashViewerActivity.class);
    }
}
