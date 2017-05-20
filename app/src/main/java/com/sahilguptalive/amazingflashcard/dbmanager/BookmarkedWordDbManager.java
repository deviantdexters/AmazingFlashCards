package com.sahilguptalive.amazingflashcard.dbmanager;

import com.sahilguptalive.storage.info.SourceOfWord;
import com.sahilguptalive.storage.models.BookmarkedWords;

import io.realm.Realm;

/**
 * Created on 20-05-2017.
 */
public class BookmarkedWordDbManager {

    public static void insertIfNotAdded(
            SourceOfWord sourceOfWord,
            String word) {
        final Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.insert(BookmarkedWords
                .newBuilder(sourceOfWord)
                .withWord(word)
                .buildWithid());
        realm.commitTransaction();
    }

    public static void insertWordwebIfNotAdded(String word) {
        insertIfNotAdded(SourceOfWord.WORDWEB_BOOKMARK, word);
    }
}
