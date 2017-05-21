package com.sahilguptalive.amazingflashcard.dbmanager;

import android.support.annotation.NonNull;

import com.sahilguptalive.storage.info.SourceOfWord;
import com.sahilguptalive.storage.models.BookmarkedWord;

import java.util.List;

import io.realm.Realm;

/**
 * Created on 20-05-2017.
 */
public class BookmarkedWordDbManager {

    private static List<BookmarkedWord> sAllWords;

    public static void insertIfNotAdded(
            @NonNull final SourceOfWord sourceOfWord,
            @NonNull final String word) {
        if (sourceOfWord == null || word == null) {
            throw new NullPointerException("word can not be null");
        }
        final String trimmedWord = word.trim();
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        //check if word is not already added
        if (realm.where(BookmarkedWord.class)
                .equalTo(BookmarkedWord.ColumnNames.WORD, trimmedWord)
                .findAll().size() == 0) {
            realm.insert(BookmarkedWord
                    .newBuilder(sourceOfWord)
                    .withWord(trimmedWord)
                    .buildWithid());
        }
        realm.commitTransaction();
    }

    public static List<BookmarkedWord> getAllWords() {
        return Realm.getDefaultInstance().where(BookmarkedWord.class).findAll();
    }

    public static BookmarkedWord getWordForId(final int wordId) {
        return Realm.getDefaultInstance().where(BookmarkedWord.class)
                .equalTo(BookmarkedWord.ColumnNames.ID, wordId)
                .findFirst();
    }
}
