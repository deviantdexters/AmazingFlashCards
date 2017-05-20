package com.sahilguptalive.storage.info;

/**
 * Created on 20-05-2017.
 */
public enum SourceOfWord {

    WORDWEB_BOOKMARK(1);

    private final int mId;

    SourceOfWord(final int id) {
        mId = id;
    }

    public int getId() {
        return mId;
    }
}
