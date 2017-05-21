package com.sahilguptalive.amazingflashcard;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.sahilguptalive.storage.models.BookmarkedWord;

import java.util.ArrayList;
import java.util.List;

/**
 * Created on 21-05-2017.
 */
class FlashFragmentAdapter extends FragmentStatePagerAdapter {

    private List<BookmarkedWord> mData;

    public FlashFragmentAdapter(final FragmentManager fm) {
        super(fm);
        mData = new ArrayList<>();
    }


    @Override
    public Fragment getItem(final int position) {
        return FlashCardFragment.createInstance(mData.get(position));
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    public void addWords(List<BookmarkedWord> bookmarkedWords) {
        mData.addAll(bookmarkedWords);
    }
}
