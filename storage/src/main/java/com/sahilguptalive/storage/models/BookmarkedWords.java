package com.sahilguptalive.storage.models;

import android.support.annotation.Nullable;

import com.sahilguptalive.storage.info.SourceOfWord;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created on 20-05-2017.
 */
public class BookmarkedWords
        extends RealmObject {

    @PrimaryKey
    private final int id;
    private final String word;
    private final int source;
    private final Date insertionDate;

    public BookmarkedWords() {
    }

    private BookmarkedWords(final Builder builder) {
        this.id = builder.id;
        this.word = builder.word;
        this.source = builder.source;
        this.insertionDate = builder.insertionDate;
    }

    public BookmarkedWords(final Builder builder,
                           final int id) {
        this.id = id;
        this.word = builder.word;
        this.source = builder.source;
        this.insertionDate = builder.insertionDate;
    }

    public int getId() {
        return id;
    }

    public String getWord() {
        return word;
    }

    public int getSource() {
        return source;
    }

    public Date getInsertionDate() {
        return insertionDate;
    }

    public static Builder newBuilder(SourceOfWord source) {
        return new Builder(source);
    }


    public static Builder newBuilder(final BookmarkedWords copy) {
        Builder builder = new Builder();
        builder.id = copy.id;
        builder.word = copy.word;
        builder.source = copy.source;
        builder.insertionDate = copy.insertionDate;
        return builder;
    }


    /**
     * {@code BookmarkedWords} builder static inner class.
     */
    public static final class Builder {
        private int id;
        private String word;
        private int source;
        private Date insertionDate;

        private Builder(@Nullable final SourceOfWord source) {
            this.insertionDate = new Date();
            if (source != null) {
                this.source = source.getId();
            }
        }

        private Builder() {
            this(null);
        }


        public Builder withId(final int val) {
            id = val;
            return this;
        }

        /**
         * Sets the {@code word} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code word} to set
         * @return a reference to this Builder
         */
        public Builder withWord(final String val) {
            word = val;
            return this;
        }

        /**
         * Sets the {@code source} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code source} to set
         * @return a reference to this Builder
         */
        public Builder withSource(final int val) {
            source = val;
            return this;
        }

        /**
         * Sets the {@code insertionDate} and returns a reference to this Builder so that the methods can be chained together.
         *
         * @param val the {@code insertionDate} to set
         * @return a reference to this Builder
         */
        public Builder withInsertionDate(final Date val) {
            insertionDate = val;
            return this;
        }

        /**
         * Returns a {@code BookmarkedWords} built from the parameters previously set.
         *
         * @return a {@code BookmarkedWords} built with parameters of this {@code BookmarkedWords.Builder}
         */
        public BookmarkedWords build() {
            return new BookmarkedWords(this);
        }

        public BookmarkedWords buildWithid() {
            return new BookmarkedWords(this, Realm.getDefaultInstance().where(BookmarkedWords.class).findAll().size() + 1)
        }
    }
}
